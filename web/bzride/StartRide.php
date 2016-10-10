<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Starting ride");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details
$requestId = $_REQUEST['rideRequestId'];
$token = $_REQUEST['token'];
$currentLat = $_REQUEST['currentLat'];
$currentLong = $_REQUEST['currentLong'];

LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);
//set as riding
$requestSQL = "UPDATE bztbl_riderequests SET status = 'R', ActualStartLat = $currentLat,ActualStartLong = $currentLong, ActualRideDateTimeStart = now() where Id = " .$requestId ;
LOGDATA($requestSQL);
$resultUpdate = mysql_query($requestSQL,$conn);
if (!$resultUpdate) {
	showError(mysql_error());
}


$data = array();
$data["status"] ="S";
$data["info"] = "Start ride success for driver";
echo json_encode($data);
mysql_close();
}
?>