<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Driver Profile");

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

$requestSQL = "SELECT * FROM  bztbl_driverbankdetails where userid = " .$driverID ;
LOGDATA($requestSQL);

$resultbank = mysql_query($requestSQL,$conn);
if (!$resultbank) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultbank);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultbank);
	$Id = $rowIn["Id"];
	$AccountType = $rowIn["AccountType"];
	$BankName = $rowIn["BankName"];
	$AccountToken = $rowIn["AccountToken"];
	
	LOGDATA($AccountType);
	LOGDATA($BankName);
	LOGDATA($AccountToken);// how to get details from token

	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get Bank Info success for driver";
	$data["AccountType"] = $AccountType;
	$data["BankName"] = $BankName;
	$data["AccountToken"] = $AccountToken;
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Bank Info failed for driver";
	echo json_encode($data);
}
mysql_close();
}
?>