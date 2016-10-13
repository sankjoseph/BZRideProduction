<?php
include("includes/common.php");
include("includes/db.php");
include("stripe/init.php");
 
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
$cardToken = $_REQUEST['cardToken'];

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


try {
	
	 \Stripe\Stripe::setApiKey($STRIPE_RUNNING_SECRET_KEY); //Replace with your Secret Key
	 
	if (defined('TEST_CARD')) { 
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
		$cardToken = $result['id'];
	}
	
    
    
        
    $customer = \Stripe\Customer::create(array(
    "description" => "Customer for BZRide Inc.",
    "source" => $cardToken // obtained with Stripe.js
    ));
    
    LOGDATA($customer->id);
    
    //customer id as future token
    $customerId = "'".$customer->id."'";
    
    $rider_details="insert into bztbl_riders values('', $firstName,$middleName, $lastName, $email, $password,
                                        $address1, $address2,$city,$state,$zip, $phone, $deviceId,$devicetoken, $deviceType,1,1,$status, $cardType, $cardProvider, $cardBillingAddress1,$cardBillingAddress2,$cardBillingCity,$cardBillingState,$cardBillingZip,$customerId, now(),now() )"; 

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

}
?>