<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Login Driver");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details
$mobilenumber = getIfSet($_REQUEST['mobilenumber']);
$password = getIfSet($_REQUEST['password']);

$requestSQL = "SELECT Id, FirstName, MiddleName, LastName,DeviceId FROM  bztbl_drivers where Phone = ".$mobilenumber. " and Password = ".$password ;
LOGDATA($requestSQL);

$resultLogin = mysql_query($requestSQL,$conn);
if (!$resultLogin) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultLogin);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultLogin);
	$Id = $rowIn["Id"];
	$firstName = $rowIn["FirstName"];
	$middleName = $rowIn["MiddleName"];
	$lastName = $rowIn["LastName"];
	$DeviceId = $rowIn["DeviceId"];
	
	$tokenGeneric = SECRET_KEY;
	
	LOGDATA('generic key is ->'.$tokenGeneric);
	LOGDATA('id ->'.$Id);
	LOGDATA('device id ->'.$DeviceId);
	$text = time(). ":". $Id;
	LOGDATA('time and id->'. $text);
	$token = bz_crypt($tokenGeneric,$text,'encrypt');
	LOGDATA('encrypted token created for user is ->'.$token);
	
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Login success for driver";
	$data["token"] = $token;
	$data["userid"] = $Id;
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Login failed for driver";
	echo json_encode($data);
}
mysql_close();
}
?>