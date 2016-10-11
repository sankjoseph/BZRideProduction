<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Login Rider");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details
$mobilenumber = getIfSet($_REQUEST['mobilenumber']);
$password = getIfSet($_REQUEST['password']);

$requestSQL = "SELECT Id, FirstName, MiddleName, LastName,DeviceId FROM  bztbl_riders where Phone = ".$mobilenumber. " and Password = ".$password ;
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
	
	LOGDATA($tokenGeneric);
	LOGDATA($Id);
	LOGDATA($DeviceId);
	$text = time(). ":". $Id;
	LOGDATA($text);
	
	$token = bz_crypt($tokenGeneric,$text,'encrypt');
	LOGDATA($token);
	
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Login success for rider";
	$data["token"] = $token;
	$data["userid"] = $Id;
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Login failed for rider";
	echo json_encode($data);
}
mysql_close();
}
?>