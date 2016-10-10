<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Getting ride data");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//rider token details
LOGDATA($token);
$riderID = GetIdByCheckforTimeout($token);

$requestSQL = "SELECT R.Id, R.RequestorId, D.FirstName V.VehicleNumber, V.VehicleModel FROM  bztbl_riderequests AS R  INNER JOIN bztbl_drivers as D ON  D.Id =  R.DriverId  INNER JOIN bztbl_drivervehicledetails V ON V.DriverId = D.Id where R.RequestorId = ".$riderID." and R.status=". "'C'"." order by R.Id DESC" ;

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
	$FirstName = $rowIn["FirstName"];//driver name
	$VehicleNumber = $rowIn["VehicleNumber"];
	$VehicleModel = $rowIn["VehicleModel"];
	$FinalCharge = $rowIn["FinalCharge"];//charged in card
	$requestID = $rowIn["Id"];
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get rider trip details success";
	$data["StartLocation"] = $StartLocation;
	echo json_encode($data);

}
$data = array();
$data["status"] ="S";
$data["info"] = "Start ride success for driver";
echo json_encode($data);
mysql_close();
}
?>