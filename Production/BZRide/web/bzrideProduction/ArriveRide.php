<?php
include("includes/db.php");
include("includes/common.php");
LOGDATA("Arrive for ride");

/* test $result = haversineGreatCircleDistance(10.0268,76.3487,9.59157,76.5222);
LOGDATA($result);
$result = distanceCalculation(10.0268,76.3487,9.59157,76.5222);
LOGDATA($result); */

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details
$requestId = $_REQUEST['rideRequestId'];

$token = $_REQUEST['token'];
LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);

// arrived R
$updateSQL = "UPDATE bztbl_riderequests SET status = 'AR' where Id = " .$requestId ;

// update time for table
LOGDATA($updateSQL);

$result = mysql_query($updateSQL,$conn);
if (!$result) {
	showError(mysql_error());
}


$requestSQL = "SELECT R.Id, R.RequestorId, R.DriverId,U.DeviceToken, U.DeviceType FROM  bztbl_riderequests AS R INNER JOIN  bztbl_riders U ON R.RequestorId = U.Id  where R.Id = ".$requestId;

LOGDATA($requestSQL);

$resultIn = mysql_query($requestSQL,$conn);
if (!$resultIn) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultIn);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultIn);
	$deviceToken = $rowIn["DeviceToken"];//rider device token
	//notify rider with details
	LOGDATA('notify rider for arrival ');	
	$deviceType = $rowIn["DeviceType"];
	if ($deviceType == 'A')
	{
		LOGDATA($deviceToken);			
		$pushMessage = "Your driver is ready for ride";
		LOGDATA($pushMessage);
		$apiKey = $ANDROID_GCM_KEY; // Give api key here.
		LOGDATA('Android notification');
		androidpush($deviceToken,$pushMessage,$apiKey);		
	}
	else if ($deviceType == 'I')
	{
		// ios notifications
	}
}
else
{
	LOGDATA('notify rider for arrival failed');
}
$data = array();
$data["status"] ="S";
$data["info"] = "Arrive ride success for driver";
echo json_encode($data);
mysql_close();
}
?>