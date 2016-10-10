<?php
include("includes/common.php");
include("includes/db.php");

 
 // Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic user details
$firstName = getIfSet($_REQUEST['firstName']);
$middleName = getIfSet($_REQUEST['middleName']);
$lastName = getIfSet($_REQUEST['lastName']);
$email = getIfSet($_REQUEST['email']);
$password = getIfSet($_REQUEST['password']);
$address1 = getIfSet($_REQUEST['address1']);
$address2 = getIfSet($_REQUEST['address2']);
$city = getIfSet($_REQUEST['city']);
$state = getIfSet($_REQUEST['state']);
$zip = getIfSet($_REQUEST['zip']);
$phone = getIfSet($_REQUEST['phone']);
$status = getIfSet($_REQUEST['status']);
$deviceId = getIfSet($_REQUEST['deviceId']);
$devicetoken = getIfSet($_REQUEST['devicetoken']);
$deviceType = getIfSet($_REQUEST['deviceType']);
$cardType = getIfSet($_REQUEST['cardType']);
$cardProvider = getIfSet($_REQUEST['cardProvider']);
$cardBillingAddress1 = getIfSet($_REQUEST['cardBillingAddress1']);
$cardBillingAddress2 = getIfSet($_REQUEST['cardBillingAddress2']);
$cardBillingCity = getIfSet($_REQUEST['cardBillingCity']);
$cardBillingState = getIfSet($_REQUEST['cardBillingState']);
$cardBillingZip = getIfSet($_REQUEST['cardBillingZip']);
$cardToken = getIfSet($_REQUEST['cardToken']);

//check if same user exist for phone and email.
$requestSQL = "SELECT * FROM  bztbl_riders where Phone = ".$phone ;
LOGDATA($requestSQL);

$resultUser = mysql_query($requestSQL,$conn);
if (!$resultUser) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultUser);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	showError("User with given phone already registered. Registration failed.");
}
//date taken as current time
//$date = date("D M d, Y G:i", time());

// insert rider values in DB
//(1, 'Ameer', '22','name' 'myemail', 'newpassword', 'my addr1', 'myaddr2', '234466', '44455588', 'android', 0, 0, 'debit', 'mastro', '', now()),
$rider_details="insert into bztbl_riders values('', $firstName,$middleName, $lastName, $email, $password,
									$address1, $address2,$city,$state,$zip, $phone, $deviceId,$devicetoken, $deviceType,1,1,$status, $cardType, $cardProvider, $cardBillingAddress1,$cardBillingAddress2,$cardBillingCity,$cardBillingState,$cardBillingZip,$cardToken, now(),now() )"; 
									
$result = mysql_query($rider_details,$conn);
LOGDATA($rider_details);

if (!$result) {
	showError(mysql_error());
}
	
$last_id = mysql_insert_id();
LOGDATA("last inserted rider =".$last_id );


if (!$last_id) {
	showError(mysql_error());
}
$data = array();
$data["status"] ="S";
$data["info"] = "Rider registration completed";

$data["Id"] = "".$last_id."";

echo json_encode($data);
	
mysql_close();
}
?>