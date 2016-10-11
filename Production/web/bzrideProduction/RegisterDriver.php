<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Reg Driver");

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
$final = getMYSQLDate($_REQUEST['dob']);
$phone = getIfSet($_REQUEST['phone']);
LOGDATA($phone);
LOGDATA($dobin);

$ssn = getIfSet($_REQUEST['ssn']);
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
$requestSQL = "SELECT * FROM  bztbl_drivers where Phone = ".$phone;
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

									
// insert driver values in DB
$driver_details="insert into bztbl_drivers values('', $firstName, $middleName, $lastName, $email, $password,
									$address1, $address2,$city,$state,$zip, $phone,$final,$ssn, $deviceId,$devicetoken, $deviceType, 1,1,
									'A', 0.0,0.0,$cardType, $cardProvider, $cardBillingAddress1,$cardBillingAddress2,$cardBillingCity,$cardBillingState,$cardBillingZip,$cardToken, now(),now())";
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}

$last_id = mysql_insert_id();
LOGDATA("last inserted driver =".$last_id );
if (!$last_id) {
	showError(mysql_error());
}

  
// bank details blank
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// insert values: ID, USER ID, TYPE, BANK NAME, AccountToken, CREATED BY DATE
$bank_details="insert into bztbl_driverbankdetails values('', $last_id, '', '','', now(),now() )";
LOGDATA($bank_details);
$result  = mysql_query($bank_details,$conn);
if (!$result) {
	showError(mysql_error());
}

//vehicledetais 
$vModel = getIfSet($_REQUEST['vModel']);
$vMake = getIfSet($_REQUEST['vMake']);
$vColor = getIfSet($_REQUEST['vColor']);
$vYear = getIfSet($_REQUEST['vYear']);
$vNumber = getIfSet($_REQUEST['vNumber']);
$vDateRegistered = getMYSQLDate($_REQUEST['vDateRegistered']);
$vStateRegistered = getIfSet($_REQUEST['vStateRegistered']);
$vExpiryDate = getMYSQLDate($_REQUEST['vExpiryDate']);


// insert values: ID, DRIVER ID, VEHICLE MODEL, V MAKE, V COLOUR, V YEAR, V REG NO, V REG STATE, V DATE REGISTERED, V EXPIRY DATE, CREATED BY DATE 
$driver_vehicledetais = "insert into bztbl_drivervehicledetails values('', $last_id, $vModel, $vMake, $vColor, $vYear , 
									$vNumber, $vStateRegistered ,$vDateRegistered, $vExpiryDate,  now(),now() )";			

LOGDATA($driver_vehicledetais);
$result = mysql_query($driver_vehicledetais,$conn);
if (!$result) {
	showError(mysql_error());
}

//vehicleinsurancedetais 
$insCompany = getIfSet($_REQUEST['insCompany']);
$insPolicyNumber = getIfSet($_REQUEST['insPolicyNumber']);
$insValidFromDate = getMYSQLDate($_REQUEST['insValidFromDate']);
$insExpDate = getMYSQLDate($_REQUEST['insExpDate']);

//insert values: ID, DRIVER ID, INSURANCE COMPANY, INS PLICY NO, INS DATE, INS EXPIRY DATE, CREATED BY DATE  //
$driver_insdetails="insert into bztbl_driverinsurancedetails values('', $last_id, $insCompany, $insPolicyNumber, $insValidFromDate,$insExpDate, now(),now() )";
LOGDATA($driver_insdetails);
$result = mysql_query($driver_insdetails,$conn);
if (!$result) {
	showError(mysql_error());
}									

$licenseNumber = getIfSet($_REQUEST['licenseNumber']);
$licenceStateIssued = getIfSet($_REQUEST['licenceStateIssued']);
$licenseDateIssued = getMYSQLDate($_REQUEST['licenseDateIssued']);
$licenseExpDate = getMYSQLDate($_REQUEST['licenseExpDate']);
							
//insert values: ID, DRIVER ID, LICENCE NO, LICENCE ISSUE STATE,LIC ISSUE DATE, LIC EXPIRY DATE, CREATED BY DATE  //
$driver_licdetails="insert into bztbl_driverlicensedetails values('', $last_id, $licenseNumber, $licenceStateIssued,$licenseDateIssued,$licenseExpDate, now(),now())";
LOGDATA($driver_licdetails);
$result = mysql_query($driver_licdetails,$conn);
if (!$result) {
	showError(mysql_error());
}

$data = array();
$data["status"] ="S";
$data["info"] = "Driver registration completed";
$data["Id"] = "".$last_id."";
echo json_encode($data);
mysql_close();
}
?>