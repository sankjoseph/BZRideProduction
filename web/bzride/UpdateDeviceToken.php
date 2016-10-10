<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update device token");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details

$token = $_REQUEST['token'];
$devicetoken = $_REQUEST['devicetoken'];
$usertype = $_REQUEST['usertype'];
LOGDATA($token);
$userID = GetIdByCheckforTimeout($token);

if ($usertype == 'Driver')
{
	// update driver values in DB as available
	$user_details="UPDATE bztbl_drivers SET DeviceToken = '".$devicetoken. "' where Id = ".$userID;
}	
else
{
	$user_details="UPDATE bztbl_riders SET DeviceToken = '".$devicetoken. "' where Id = ".$userID;
}

						
LOGDATA($user_details);
$result = mysql_query($user_details,$conn);
if (!$result) {
	showError(mysql_error());
}
else
{
	$data = array();
	$data["status"] ="S";
	$data["info"] = "Update device token success";
	echo json_encode($data);
}

mysql_close();
}
?>