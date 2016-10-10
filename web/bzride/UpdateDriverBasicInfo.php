<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update Driver Basic details");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  

$token = $_REQUEST['token'];
LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);

LOGDATA("Update Driver Basic details for ".$driverID );

//basic user details
$firstName = getIfSet($_REQUEST['firstName']);
$middleName = getIfSet($_REQUEST['middleName']);
$lastName = getIfSet($_REQUEST['lastName']);
$email = getIfSet($_REQUEST['email']);
$address1 = getIfSet($_REQUEST['address1']);
$address2 = getIfSet($_REQUEST['address2']);
$city = getIfSet($_REQUEST['city']);
$state = getIfSet($_REQUEST['state']);
$zip = getIfSet($_REQUEST['zip']);
$phone = getIfSet($_REQUEST['phone']);
$ssn = getIfSet($_REQUEST['ssn']);

$dobin= getIfSet($_REQUEST['dob']);
$old_date_format = strtotime($dobin);
$new_date_format = date("Y-m-d H:i:s", $old_date_format);  
$final = "'".$new_date_format."'";

LOGDATA($phone);
LOGDATA($dobin);
									
// Update driver values in DB
$driver_details="UPDATE bztbl_drivers SET FirstName = $firstName, MiddleName = $middleName, LastName = $lastName,
Email = $email,Address1 = $address1,Address2 = $address2,City = $city,State = $state,Zip = $zip,Phone = $phone,
DOB = $final, SSN = $ssn, LastModifiedDate=now() where Id = ".$driverID;
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}


$data = array();
$data["status"] ="S";
$data["info"] = "Driver basic details updated";
echo json_encode($data);

mysql_close();
}
?>