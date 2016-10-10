<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Ride details");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details

$token = $_REQUEST['token'];
$requestId = $_REQUEST['rideRequestId'];
LOGDATA($token);
$riderID = GetIdByCheckforTimeout($token);

$requestSQL = "SELECT * FROM  bztbl_riderequests INNER JOIN tbl_riders ON bztbl_riderequests.requestorId = tbl_riders.Id where bztbl_riderequests.Id = " .$requestId ;
LOGDATA($requestSQL);

$resultLogin = mysql_query($requestSQL,$conn);
if (!$resultLogin) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultLogin);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultLogin);
	$StartLocation = $rowIn["StartLocation"];
	$EndLocation = $rowIn["EndLocation"];
	$FirstName = $rowIn["FirstName"];
	$Phone = $rowIn["Phone"];
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get ride details success for driver";
	$data["StartLocation"] = $StartLocation;
	$data["EndLocation"] = $EndLocation;
	$data["FirstName"] = $FirstName;
	$data["Phone"] = $Phone;
	
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get ride detail failed for driver";
	echo json_encode($data);
}
mysql_close();
}
?>