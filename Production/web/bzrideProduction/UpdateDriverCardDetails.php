<?php
include("includes/db.php");
include("includes/common.php");
include("stripe/init.php");

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
    
$cardToken = $_REQUEST['cardToken'];

try
{
     \Stripe\Stripe::setApiKey($STRIPE_RUNNING_SECRET_KEY); //Replace with your Secret Key
    
     $customer = \Stripe\Customer::create(array(
    "description" => "Customer for BZRide Inc.",
    "source" => $cardToken // obtained with Stripe.js
    ));
    
    LOGDATA($customer->id);
    
    //customer id as future token
    $customerId = "'".$customer->id."'";
    
    // update driver values in DB
    $driver_details="UPDATE bztbl_drivers SET CardType = $CardType, CardProvider = $CardProvider,
    cardBillingAddress1 = $cardBillingAddress1,cardBillingAddress2=$cardBillingAddress2,
    cardBillingCity = $cardBillingCity,cardBillingState=$cardBillingState,cardBillingZip=$cardBillingZip,
    CardToken=$customerId, LastModifiedDate=now() where Id = ".$driverID;


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