<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update Driver");

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


$AccountType = getIfSet($_REQUEST['AccountType']);
$BankName = getIfSet($_REQUEST['BankName']);
$AccountToken = getIfSet($_REQUEST['AccountToken']);

									
// update driver bank values in DB
$driver_details="UPDATE bztbl_driverbankdetails SET AccountType = $AccountType, BankName = $BankName, AccountToken = $AccountToken, LastModifiedDate=now() where userid = ".$driverID;
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}


$data = array();
$data["status"] ="S";
$data["info"] = "Driver bank details completed";
echo json_encode($data);

mysql_close();
}
?>