<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Ending ride");

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
$shortDistance = 4;//4 miles
//basic driver details
$requestId = $_REQUEST['rideRequestId'];
$ActualEndLat = $_REQUEST['ActualEndLat'];
$ActualEndLong = $_REQUEST['ActualEndLong'];


$token = $_REQUEST['token'];
LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);


$updateSQL = "UPDATE bztbl_riderequests SET status = 'C', ActualRideDateTimeEnd = now()".", ActualEndLat = ". $ActualEndLat. ", ActualEndLong = ". $ActualEndLong. " where Id = " .$requestId ;

// update time for table
LOGDATA($updateSQL);

$result = mysql_query($updateSQL,$conn);
if (!$result) {
	showError(mysql_error());
}

//////
//update the driver as available
$requestSQLDriver = "UPDATE bztbl_drivers SET status = 'A' where Id = " .$driverID ;

LOGDATA($requestSQLDriver);

$resultUpdateDriver = mysql_query($requestSQLDriver,$conn);
if (!$resultUpdateDriver) {
	showError(mysql_error());
}

// get data from request table for calculation
$requestSQL = "Select * from bztbl_riderequests where Id = " .$requestId ;

LOGDATA($requestSQL);

$result = mysql_query($requestSQL,$conn);
if (!$result) {
	showError(mysql_error());
}

$row = mysql_fetch_array($result);

$ActualStartLat = doubleval($row["ActualStartLat"]);
$ActualStartLong = doubleval($row["ActualStartLong"]);
$ActualEndLat = doubleval($row["ActualEndLat"]);
$ActualEndLong = doubleval($row["ActualEndLong"]);

LOGDATA($ActualStartLat);
LOGDATA($ActualStartLong);
LOGDATA($ActualEndLat);
LOGDATA($ActualEndLong);

$ActualRideDateTimeStart = $row["ActualRideDateTimeStart"];
$ActualRideDateTimeEnd = $row["ActualRideDateTimeEnd"];

$riderId = $row["RequestorId"];

$saftyandTrustFee = 1.75;//dollar

$baseFare = 4.0;// 4Dollar defaukt
$rateForTimeCents = 0.0;
$rateforDistanceCents = 0.0;
$pickUpCharge = 0.95; // pick up charge
// find distance
/*$distancetraveledmi = distanceCalculation($ActualStartLat,$ActualStartLong,$ActualEndLat,$ActualEndLong,'mi');*/
// old code

$distancetraveledmi = GetDrivingDistance($ActualStartLat,$ActualEndLat,$ActualStartLong,$ActualEndLong);

    
LOGDATA('distance travelled in miles '.$distancetraveledmi);

if ($distancetraveledmi >4 )// greater than 4 miles
{
	$rateforDistanceCents = $distancetraveledmi * 0.76; //dollar//0.76 per mile
}

// find time difference
$timetakenminutes  = FindTimeDiff($ActualRideDateTimeStart,$ActualRideDateTimeEnd);
//$timetakenminutes = round(abs($ActualRideDateTimeEnd - $ActualRideDateTimeStart) / 60,2);
LOGDATA('time travelled in minutes '.$timetakenminutes);
LOGDATA('time start '.$ActualRideDateTimeStart);
LOGDATA('time end '.$ActualRideDateTimeEnd);

    
if ($distancetraveledmi >$shortDistance )// greater than 4 miles consider time also
{
	// fare time
	$rateForTimeCents = $timetakenminutes * 0.12;//dollar//0.12 dollar per minute
}

// calculate rate for above and fit in table
$travelFare = ($rateforDistanceCents + $rateForTimeCents); //dollar
LOGDATA('normal ride fare '.$travelFare);
// for night drive between 10- 6 
if (isNightRide($ActualRideDateTimeStart))
{
	$nightCharge =  ($travelFare *0.3);
    LOGDATA('night ride 10 to 6 '.$nightCharge);
}

// for friday saturday sunday
if (isweekend ($ActualRideDateTimeStart))
{
	$weekEndCharge = ($travelFare *0.3);
    LOGDATA('week end days '.$weekEndCharge);
}

$travelFare = $travelFare + $nightCharge + $weekEndCharge;
        
if ($distancetraveledmi <= $shortDistance )
{
	$pickUpCharge = 0.0;// not applicable for short distance
}
if ($distancetraveledmi > $shortDistance )// greater than 4 miles basefare not appicable
{
	$baseFare = 0.0;
}
$finalFare = $pickUpCharge + $baseFare + $travelFare;//dollar
// sum total fare and update in table

LOGDATA('$rateforDistanceCents '.$rateforDistanceCents);
LOGDATA('$rateForTimeCents '.$rateForTimeCents);
LOGDATA('$pickUpCharge '.$pickUpCharge);
LOGDATA('$baseFare '.$baseFare);
LOGDATA('$travelFare '.$travelFare);
LOGDATA('summed $finalFare '.$finalFare);
$finalFare = round( $finalFare, 2, PHP_ROUND_HALF_UP);
$FaretoCompany = $finalFare;
LOGDATA('rounded $finalFare '.$finalFare);
$finalFare = $finalFare + $saftyandTrustFee;

$FareCommission = $FaretoCompany * 0.20;// 20 percent commission
$FarePayableToDriver =  $FaretoCompany * 0.80;// 80 percent 
// update fare for table
$updateFareSQL = "UPDATE bztbl_riderequests SET ChargeDistance = $rateforDistanceCents, ChargeTime = $rateForTimeCents, FinalCharge = $finalFare, FaretoCompany = $FaretoCompany,FareCommission = $FareCommission, FarePayableToDriver = $FarePayableToDriver where Id = " .$requestId ;
LOGDATA($updateFareSQL);

$result = mysql_query($updateFareSQL,$conn);
if (!$result) {
	showError(mysql_error());
}
//

// get daily amount got
$requestDailySQL = "Select sum(FarePayableToDriver) AS DayTotal from bztbl_riderequests where DriverId = " .$driverID. " GROUP BY date(CreatedByDate)" ;//driver id
LOGDATA($requestDailySQL);
$result = mysql_query($requestDailySQL,$conn);
if (!$result) {
	showError(mysql_error());
}
$rowDay = mysql_fetch_array($result);
$DayTotal = $rowDay["DayTotal"];
$DayTotal = round($DayTotal,2);

// get card details
// get data from request table for calculation and notification
$requestCardSQL = "Select DeviceToken,DeviceType, CardToken from bztbl_riders where Id = " .$riderId ;//RequestorId
LOGDATA($requestCardSQL);
$result = mysql_query($requestCardSQL,$conn);
if (!$result) {
	showError(mysql_error());
}
$rowToken = mysql_fetch_array($result);
$CustomerId = $rowToken["CardToken"];//$CustomerId as token

// charge card for the amount usinhg card token todo
// last ride will have fare details for user
// call web service for putting data
$bz_req_url = $BASE_URL . 'charge.php';
$ch =  curl_init();

$postData = http_build_query(array('token' => $CustomerId,	
					'amount' => $finalFare,//dollar
					'requestId' => $requestId,
					'currency' => 'usd'	));
curl_setopt($ch, CURLOPT_URL, $bz_req_url);
curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);
curl_setopt($ch, CURLOPT_POST, 1);																							
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
curl_setopt($ch, CURLOPT_TIMEOUT, 10);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));
$result = curl_exec($ch);
LOGDATA($result);
		
if (preg_match("/Could not/i", $result)) {
    showError("Failed to handle charge, Please retry.");
 } 

$deviceType = $rowToken["DeviceType"];
if ($deviceType == 'A')
{
	//notify rider with details
	$deviceToken = $rowToken["DeviceToken"];//rider device token
	LOGDATA($deviceToken);			
	$pushMessage = "Your card is charged by USD ". $finalFare;
	LOGDATA($pushMessage);
	$apiKey = $ANDROID_GCM_KEY; // Give api key here.
	LOGDATA('Android notification');
	androidpush($deviceToken,$pushMessage,$apiKey);
}

$data = array();
$data["status"] ="S";
$data["faredriver"] =  "".$FarePayableToDriver."";
$data["faredriverdaytotal"] =  "".$DayTotal."";
$data["info"] = "End ride success for driver";
echo json_encode($data);
mysql_close();
}
?>