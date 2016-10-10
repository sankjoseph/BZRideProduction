<?php
include("includes/common.php");
session_start();
// call web service for putting data
$bz_req_url = $BASE_URL . 'RegisterDriver.php?';
$ch =  curl_init($bz_req_url);

LOGDATA($_POST["txtphone"]);
$postData = array('firstName' => $_POST["txtfirstname"], 
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
				'ssn' => $_POST["txtssn"],
				
				'deviceId' => '',
				'deviceType' => '',
				'cardType' => '',
				'cardProvider' => '',
				'cardBillingAddress1' => '',
				'cardBillingAddress2' => '',
				'cardBillingCity' => '',
				'cardBillingState' => '',
				'cardBillingZip' => '',
				'cardToken' => '',

	
				'vModel' => $_POST["txtmodel"],
				'vMake' => $_POST["txtmake"],
				'vColor' => $_POST["txtcolor"],
				'vYear' => $_POST["txtyear"],
				'vNumber' => $_POST["txtregno"],
				'vDateRegistered' => $_POST["txtregdate"],
				'vStateRegistered' => $_POST["txtregstate"],
				'vExpiryDate' => $_POST["txtregexpiry"],
				
				'insCompany' => $_POST["txtinscompany"],
				'insPolicyNumber' => $_POST["txtpolicyno"],
				'insValidFromDate' => $_POST["txtinsdate"],
				'insExpDate' => $_POST["txtinsexpiry"],
				
				'licenseNumber' => $_POST["txtlicenseno"],
				'licenceStateIssued' => $_POST["txtlicensestate"],
				'licenseDateIssued' => $_POST["txtlicenseissue"],
				'licenseExpDate' => $_POST["txtlicenseexpiry"]	);

curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);										
										
												
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
curl_setopt($ch, CURLOPT_TIMEOUT, 10);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));
$result = curl_exec($ch);
header("location:home.php");

?>