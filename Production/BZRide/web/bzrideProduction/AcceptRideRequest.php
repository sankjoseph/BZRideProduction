<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Accepting ride request");

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

$requestSQLCheck = "select * from bztbl_riderequests where Id = " .$requestId ;
LOGDATA($requestSQLCheck);
$resultCheck = mysql_query($requestSQLCheck,$conn);
if (!$resultCheck) {
	showError(mysql_error());
}

$num_rows = mysql_num_rows($resultCheck);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultCheck);
	$status = $rowIn["Status"];
	if ($status == 'A')
	{
		$data = array();
		$data["status"] ="F";
		$data["info"] = "Timeout. Request already accepted by another driver";
		echo json_encode($data);
		die();
	}
}
//request accepted
$requestSQL = "UPDATE bztbl_riderequests SET Status = 'A',DriverId = ".$driverID. " where Id = " .$requestId ;

LOGDATA($requestSQL);

$resultUpdate = mysql_query($requestSQL,$conn);
if (!$resultUpdate) {
	showError(mysql_error());
}

//driver status as driving
$requestSQLDriver = "UPDATE bztbl_drivers SET status = 'D' where Id = " .$driverID ;

LOGDATA($requestSQLDriver);

$resultUpdateDriver = mysql_query($requestSQLDriver,$conn);
if (!$resultUpdateDriver) {
	showError(mysql_error());
}

// RequestorId

$requestSQL = "SELECT R.Id, R.RequestorId, R.DriverId, D.FirstName,D.Phone,U.DeviceToken, U.DeviceType, V.VehicleNumber, V.VehicleModel FROM  bztbl_riderequests AS R INNER JOIN  bztbl_riders U ON R.RequestorId = U.Id INNER JOIN bztbl_drivers as D ON  D.Id =  R.DriverId  INNER JOIN bztbl_drivervehicledetails V ON V.DriverId = D.Id where R.Id = ".$requestId;

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
	// get driver and vehicle data
	$FirstName = $rowIn["FirstName"];
	$Phone = $rowIn["Phone"];
	$VehicleNumber = $rowIn["VehicleNumber"];
	$VehicleModel = $rowIn["VehicleModel"];
	$requestID = $rowIn["Id"];
	//notify rider with details
	LOGDATA('notify rider with details');	
	$deviceType = $rowIn["DeviceType"];
	if ($deviceType == 'A')
	{
		LOGDATA($deviceToken);			
		$pushMessage = "Your request accepted by".":".$requestID.":".$FirstName.":".$Phone.":".$VehicleNumber. ":".$VehicleModel;
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
	LOGDATA('notify rider failed');
}
$data = array();
$data["status"] ="S";
$data["info"] = "Accepting ride request success for driver";
echo json_encode($data);
	
mysql_close();
}
?>