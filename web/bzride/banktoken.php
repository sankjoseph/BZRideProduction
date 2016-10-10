<?php
include("includes/common.php");
include("stripe/init.php");
session_start();

$token = $_REQUEST['token'];

try {
		\Stripe\Stripe::setApiKey("sk_test_2rCnQT2VGQl5ndFbgfEas7g2");

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

$banktoken = $result['id'];
LOGDATA ('bank token sample '.$banktoken);
}
//catch the errors in any way you like
 
catch(Stripe_CardError $e) {
 LOGDATA($e);

}
 
 
catch (Stripe_InvalidRequestError $e) {
// Invalid parameters were supplied to Stripe's API
 LOGDATA($e);
 
} catch (Stripe_AuthenticationError $e) {
// Authentication with Stripe's API failed
// (maybe you changed API keys recently)
  LOGDATA($e);
} catch (Stripe_ApiConnectionError $e) {
// Network communication with Stripe failed
 LOGDATA($e);
} catch (Stripe_Error $e) {
  LOGDATA($e);
// Display a very generic error to the user, and maybe send
// yourself an email
} catch (Exception $e) {
  LOGDATA($e);
// Something else happened, completely unrelated to Stripe
}
$data = array();
$data["status"] ="S";
$data["info"] = $banktoken;
echo json_encode($data);
?>