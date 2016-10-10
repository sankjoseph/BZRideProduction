<?php
include("includes/common.php");
session_start();
// call web service for putting data
$bz_req_url = $BASE_URL . 'RegisterRider.php?';
$ch =  curl_init($bz_req_url);

curl_setopt($ch, CURLOPT_POSTFIELDS, array('firstName' => $_POST["txtfirstname"], 
											'middleName' => $_POST["txtmiddlename"],
											'lastName' => $_POST["txtlastname"],
											'email' => $_POST["txtemail"],
											'password' => $_POST["txtpass"],
											'address1' => $_POST["txtaddr1"],
											'address2' => $_POST["txtaddr2"],
											'city' => $_POST["txtcity"],
											'state' => $_POST["txtstate"],
											'zip' => $_POST["txtzip"],
											'phone' => $_POST["txtphone"],
											'deviceId' => '',
											'deviceType' => '',
											'cardType' => $_POST["cardtype"],
											'cardProvider' => $_POST["subtype"],
											'cardBillingAddress1' => $_POST["txtbillingaddr1"],
											'cardBillingAddress2' => $_POST["txtbillingaddr2"],
											'cardBillingCity' => $_POST["txtbillingCity"],
											'cardBillingState' => $_POST["txtbillingState"],
											'cardBillingZip' => $_POST["txtbillingZip"],
											'cardToken' => $_POST["cardToken"]));
												
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
curl_setopt($ch, CURLOPT_TIMEOUT, 10);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));
$result = curl_exec($ch);
header("location:home.php");
?>