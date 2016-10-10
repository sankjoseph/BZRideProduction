<?php
include("includes/common.php");
include("stripe/init.php");

session_start();

$token = $_REQUEST['token'];
$currency = $_REQUEST['currency'];
$amount = $_REQUEST['amount'] *100;//cents
$requestRefNumber = $_REQUEST['requestId'];

LOGDATA ('inside charge card operation');
try {
	
	 \Stripe\Stripe::setApiKey($STRIPE_RUNNING_SECRET_KEY); //Replace with your Secret Key
	 
	  $result = \Stripe\Token::create(
                    array(
                        "card" => array(
                            "number" => "4242424242424242",
                            "exp_month" => 12,
                            "exp_year" => 2016,
                            "cvc" => "314"
                        )
                    )
                );

if (defined('TEST_CARD')) {
	$token = $result['id'];
}
				
  //echo $token;
 
  $description = "Charge for BZRide Inc. Ref number ".$requestRefNumber;
  $charge = \Stripe\Charge::create(array(
  "amount" => $amount,
  "currency" => $currency,
  "card" => $token,
  "description" => $description
));
 //send the file, this line will be reached if no error was thrown above
$data = array();
$data["status"] ="S";
$data["info"] = "charging successful";
echo json_encode($data);
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

?>