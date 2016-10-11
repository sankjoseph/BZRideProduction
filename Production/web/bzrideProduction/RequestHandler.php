<?php
include("includes/common.php");
include("includes/db.php");
session_start();
LOGDATA ('inside request handler');
$requestId = '';
$reqSQL = "SELECT * FROM  bztbl_riderequests where Status = 'N' order by CreatedByDate desc LIMIT 1";
LOGDATA($reqSQL);

$resultstat = mysql_query($reqSQL,$conn);
if (!$resultstat) {
	return false;
}
$num_rows = mysql_num_rows($resultstat);
LOGDATA('no of active request going to be handled->'.$num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultstat);
	$requestId = $rowIn["Id"];
	$startLat = $rowIn["StartLat"];
	$startLong = $rowIn["StartLong"];
}
else
{
	return false;
}
LOGDATA('active request going to be handled->id->'.$requestId);
LOGDATA('active request going to be handled->startLat->'.$startLat);
LOGDATA('active request going to be handled->startLong->'.$startLong);
//fixed values testing
//$requestId = '1';
//$startLat = '38.7521';
//$startLong = '121.2880';


/*$requestId = '5';
$startLat = '10.0268';
$startLong = '76.3487';*/

/*$requestId = '5';
$startLat = '9.8';
$startLong = '76.7487';*/


//$requestId = mysql_real_escape_string($requestId);
//$startLat = mysql_real_escape_string($startLat);
//$startLong = mysql_real_escape_string($startLong);

$startLatFloat = doubleval($startLat);
$startLongFloat = doubleval($startLong);

LOGDATA($startLatFloat);
LOGDATA($startLongFloat);
			 
$findriverSQL = "SELECT * , (3956 * 2 * ASIN(SQRT( POWER(SIN(( $startLatFloat - currentlat) *  pi()/180 / 2), 2) +COS( $startLatFloat * pi()/180) * COS(currentlat * pi()/180) * POWER(SIN(( $startLongFloat - currentlong) * pi()/180 / 2), 2) ))) as distance  from bztbl_drivers WHERE STATUS =  'A'  AND isActive =1 having  distance <= 10 order by distance";

LOGDATA($findriverSQL);

$result = mysql_query($findriverSQL,$conn);
if (!$result) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($result);
LOGDATA($num_rows);
$goodcount= 0;

if ( $num_rows > 0) {
    // output data of each row
	LOGDATA('fetching driver');
    while($row = mysql_fetch_array($result)) {
		LOGDATA('fetching driver data');
		$deviceType = $row["DeviceType"];
		$driverID = $row["Id"];
										
		// update driver values in DB as S mean selected.
		// This will prevent further notification if a request comes from another rider
		
		/*$driver_details="UPDATE bztbl_drivers SET Status = 'S' where Id = ".$driverID;
										
		LOGDATA($driver_details);
		$result = mysql_query($driver_details,$conn);
		if (!$result) {
			showError(mysql_error());
		}*/


		/*if ($isLicenseAccepted != true) continue;*/
		
		LOGDATA($deviceType);
		LOGDATA(status);
		LOGDATA($isActive);
		LOGDATA($isLicenseAccepted);
		
		
		if ($deviceType == 'A')
		{
			$requestSQL = "SELECT R.StartLocation,R.StartLat,R.StartLong, R.EndLocation,R.EndLat,R.EndLong, U.FirstName, U.Phone FROM  bztbl_riderequests AS R INNER JOIN  bztbl_riders U ON R.RequestorId = U.Id where R.Id = ".$requestId;
			LOGDATA($requestSQL);
			
			$resultIn = mysql_query($requestSQL,$conn);
			if (!$resultIn) {
				showError(mysql_error());
			}
			$num_rows = mysql_num_rows($resultIn);
			LOGDATA($num_rows);
			if ( $num_rows > 0) {
				$rowIn = mysql_fetch_array($resultIn);
				$start = $rowIn["StartLocation"];
				$startLat = $rowIn["StartLat"];
				$startLong = $rowIn["StartLong"];
				$end = $rowIn["EndLocation"];
				$endLat = $rowIn["EndLat"];
				$endLong = $rowIn["EndLong"];
				$firstName = $rowIn["FirstName"];
				$phone = $rowIn["Phone"];
				LOGDATA($start);
				LOGDATA($end);
				LOGDATA($firstName);
			}
			
			//Android notification
			$deviceToken = $row["DeviceToken"];
			//'dGxQW_4WW6M:APA91bHa_pRIqqH8SpO5LH7kiDAsFwErVkp4hYQTkxcZHSv0i-5FVByKKYhRIvybep6Q_X9rARa8VG5ycxbu6LEw4wihSA5MK4Yup6ZbchUAq2TdkLIjilKUXMnF8D_66hcb5-CHQfIi';
			//$row["DeviceId"];	
			LOGDATA($deviceToken);			
			$pushMessage = "You have a ride request from ". $firstName. " start from ". $start. " to ". $end. ":".$requestId. ":".$firstName.":".$phone.":".$start.":".$end.
			":".$startLat.":".$startLong.":".$endLat.":".$endLong;
			LOGDATA($pushMessage);
			$apiKey = $ANDROID_GCM_KEY; // Give api key here.
			LOGDATA('Android notification');
			androidpush($deviceToken,$pushMessage,$apiKey);
			

		}
		else if ($deviceType == 'I')
		{
			//ios notification
			$devicetoken = '';  // Give device token here.
			$from = ''; // Give from name.
			$fromname = ''; // Give from.
			LOGDATA('IOS notification');
			apns($deviceToken,$from,$fromname);
		}
		
		sleep(20);//sleep for response from driver
		if (checkIfRequestAccepted($requestId,$conn))
		{
			LOGDATA('Request accepted '.$requestId);
			$goodcount= $goodcount+1;
			break;
		}
		LOGDATA('Request not accepted '.$requestId);
    }
	if($goodcount<=0)
	{
		showError("None of drivers accepted ride request. Please retry.");
	}
} else {
   showError("Could not find any drivers available at this time. Please retry.");
}

function checkIfRequestAccepted($requestId,$conn )
{
	$statSQL = "SELECT Status FROM  bztbl_riderequests where Id = ".$requestId;
	LOGDATA($statSQL);
	
	$resultstat = mysql_query($statSQL,$conn);
	if (!$resultstat) {
		return false;
	}
	$num_rows = mysql_num_rows($resultstat);
	LOGDATA($num_rows);
	if ( $num_rows > 0) {
		$rowIn = mysql_fetch_array($resultstat);
		$Status = $rowIn["Status"];
		LOGDATA($Status);
		if ($Status == 'A')// Accepted
		{
			return true;
		}
	}
	return false;
}

function getLastRequest($conn )
{

}

?>