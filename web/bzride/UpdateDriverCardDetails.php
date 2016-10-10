<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update driver card details");

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


$CardType = getIfSet($_REQUEST['cardType']);
$CardProvider = getIfSet($_REQUEST['cardProvider']);
$cardBillingAddress1 = getIfSet($_REQUEST['cardBillingAddress1']);
$cardBillingAddress2 = getIfSet($_REQUEST['cardBillingAddress2']);
$cardBillingCity = getIfSet($_REQUEST['cardBillingCity']);
$cardBillingState = getIfSet($_REQUEST['cardBillingState']);
$cardBillingZip = getIfSet($_REQUEST['cardBillingZip']);
$CardToken = getIfSet($_REQUEST['cardToken']);
					
// update driver values in DB
$driver_details="UPDATE bztbl_drivers SET CardType = $CardType, CardProvider = $CardProvider,
cardBillingAddress1 = $cardBillingAddress1,cardBillingAddress2=$cardBillingAddress2,
cardBillingCity = $cardBillingCity,cardBillingState=$cardBillingState,cardBillingZip=$cardBillingZip,
CardToken=$CardToken, LastModifiedDate=now() where Id = ".$driverID;

									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}

$data = array();
$data["status"] ="S";
$data["info"] = "Update driver card details success";
echo json_encode($data);

mysql_close();
}
?>