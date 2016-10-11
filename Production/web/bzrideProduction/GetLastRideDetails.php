<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Driver card details");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details

$token = $_REQUEST['token'];
LOGDATA($token);
$riderID = GetIdByCheckforTimeout($token);

$requestSQL = "SELECT * FROM  bztbl_riderequests where RequestorId = " .$riderID. " and status=". "'C'". " order by Id DESC" ;
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
	$RideDateTime = $rowIn["RideDateTime"];
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get Last ride details success for rider";
	$data["StartLocation"] = $StartLocation;
	$data["EndLocation"] = $EndLocation;
	$data["RideDateTime"] = $RideDateTime;
	
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Last ride failed for rider";
	echo json_encode($data);
}
mysql_close();
}
?>