<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Driver card details");

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

$requestSQL = "SELECT CardType,CardProvider,cardBillingAddress1,cardBillingAddress2,cardBillingCity,cardBillingState,cardBillingZip,CardToken FROM  bztbl_drivers where Id = " .$driverID ;
LOGDATA($requestSQL);

$resultLogin = mysql_query($requestSQL,$conn);
if (!$resultLogin) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultLogin);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultLogin);
	$CardType = $rowIn["CardType"];
	$CardProvider = $rowIn["CardProvider"];
	$cardBillingAddress1 = $rowIn["cardBillingAddress1"];
	$cardBillingAddress2 = $rowIn["cardBillingAddress2"];
	
	$cardBillingCity = $rowIn["cardBillingCity"];
	$cardBillingState = $rowIn["cardBillingState"];
	$cardBillingZip = $rowIn["cardBillingZip"];
	
	$CardToken = $rowIn["CardToken"];

	$data = array();
	$data["status"] ="S";
	$data["info"] = "Get Card details success for driver";
	$data["CardType"] = $CardType;
	$data["CardProvider"] = $CardProvider;
	$data["cardBillingAddress1"] = $cardBillingAddress1;
	$data["cardBillingAddress2"] = $cardBillingAddress2;
	$data["cardBillingCity"] = $cardBillingCity;
	$data["cardBillingState"] = $cardBillingState;
	$data["cardBillingZip"] = $cardBillingZip;
	
	$data["CardToken"] = $CardToken;
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Card detailsfailed for driver";
	echo json_encode($data);
}
mysql_close();
}
?>