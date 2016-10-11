<?php
include("includes/db.php");
include("includes/common.php");
include("stripe/init.php");

LOGDATA("Update Driver bank details for first time");

$driverID = $_REQUEST['driverID'];
LOGDATA($driverID);

$AccountType = getIfSet($_REQUEST['AccountType']);
$BankName = getIfSet($_REQUEST['BankName']);


try {
		\Stripe\Stripe::setApiKey($STRIPE_RUNNING_SECRET_KEY);

		$result = \Stripe\Token::create(array(
		"bank_account" => array(
		"country" => "US",
		"currency" => $_REQUEST['currency'],
		"account_holder_name" => $_REQUEST['accountholdername'],
		"account_holder_type" => "individual",// set as individual now
		"routing_number" =>$_REQUEST['routingnumber'],
		"account_number" =>  $_REQUEST['accountnumber']
	 )
));

$Token = $result['id'];
$AccountToken = getIfSet($Token);

LOGDATA ('bank token sample '.$AccountToken);
}
//catch the errors in any way you like
 
catch(Stripe_CardError $e) {
 showError( $e->getMessage());
}
catch (Stripe_InvalidRequestError $e) {
// Invalid parameters were supplied to Stripe's API
showError( $e->getMessage());
 
} catch (Stripe_AuthenticationError $e) {
// Authentication with Stripe's API failed
// (maybe you changed API keys recently)
showError( $e->getMessage());
} catch (Stripe_ApiConnectionError $e) {
// Network communication with Stripe failed
showError( $e->getMessage());
} catch (Stripe_Error $e) {
 showError( $e->getMessage());
// Display a very generic error to the user, and maybe send
// yourself an email
} catch (Exception $e) {
 showError( $e->getMessage());
// Something else happened, completely unrelated to Stripe
}
// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
									
// update driver values in DB
$driver_details="UPDATE bztbl_driverbankdetails SET AccountType = $AccountType, BankName = $BankName, AccountToken = ".$AccountToken. ", LastModifiedDate=now() where userid = ".$driverID;
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}


$data = array();
$data["status"] ="S";
$data["info"] = "Driver bank details update completed";
echo json_encode($data);

mysql_close();
}
?>