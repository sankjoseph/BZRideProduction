<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update Driver Profile");

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
$firstName = getIfSet($_REQUEST['firstName']);
$middleName = getIfSet($_REQUEST['middleName']);
$lastName = getIfSet($_REQUEST['lastName']);
$email = getIfSet($_REQUEST['email']);
$address1 = getIfSet($_REQUEST['address1']);
$address2 = getIfSet($_REQUEST['address2']);
$city = getIfSet($_REQUEST['city']);
$state = getIfSet($_REQUEST['state']);
$zip = getIfSet($_REQUEST['zip']);

								
// update driver values in DB
$driver_details="UPDATE bztbl_drivers SET FirstName = $firstName, MiddleName = $middleName,lastName= $lastName, email=$email,address1=$address1,
address2=$address2,City = $city, State = $state,Zip = $zip, LastModifiedDate=now() where Id = ".$driverID;
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}


$data = array();
$data["status"] ="S";
$data["info"] = "Driver profile updation completed";
echo json_encode($data);

mysql_close();
}
?>