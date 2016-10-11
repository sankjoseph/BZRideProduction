<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Driver trip details");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details

$token = $_REQUEST['token'];
LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);

$requestSQL = "SELECT * FROM  bztbl_riderequests NNER JOIN tbl_riders ON bztbl_riderequests.requestorId = tbl_riders.Id  where DriverId = " .$driverID. " and status=". "'C'"." order by Id DESC" ;
LOGDATA($requestSQL);

$resultHistory = mysql_query($requestSQL,$conn);
if (!$resultHistory) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultHistory);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultHistory);
	$StartLocation = $rowIn["StartLocation"];
	$EndLocation = $rowIn["EndLocation"];
	$RideDateTime = $rowIn["RideDateTime"];
	$RequestorId = $rowIn["RequestorId"]; 
	$FirstName = $rowIn["FirstName"]; // rider first name
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get Driver trip details success";
	$data["StartLocation"] = $StartLocation;
	$data["EndLocation"] = $EndLocation;
	$data["RideDateTime"] = $RideDateTime;
	$data["RequestorId"] = $RequestorId;
	$data["FirstName"] = $FirstName;
	
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Driver trip details failed";
	echo json_encode($data);
}
mysql_close();
}
?>