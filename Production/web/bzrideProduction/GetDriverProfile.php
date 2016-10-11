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

$requestSQL = "SELECT * FROM  bztbl_drivers where Id = " .$driverID ;
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
	$email = $rowIn["Email"];
	$userStatus = $rowIn["status"];
	$address1 = $rowIn["Address1"];
	$address2 = $rowIn["Address2"];
	$city = $rowIn["City"];
	$state = $rowIn["State"];
	$zip = $rowIn["Zip"];

	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get Profile success for driver";
	$data["firstName"] = $firstName;
	$data["middleName"] = $middleName;
	$data["lastName"] = $lastName;
	$data["email"] = $email;
	$data["userStatus"] = $userStatus;
	$data["address1"] = $address1;
	$data["address2"] = $address2;
	$data["city"] = $city;
	$data["state"] = $state;
	$data["zip"] = $zip;

	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Profile failed for driver";
	echo json_encode($data);
}
mysql_close();
}
?>