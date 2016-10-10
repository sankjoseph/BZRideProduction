<?php
include("includes/common.php");
include("includes/db.php");
session_start();
// call web service for putting data
$bz_req_url = $BASE_URL . 'RequestHandler.php';
$ch =  curl_init();
$last_id = '1';
$postData =  http_build_query(array('rideRequestId' => $last_id,
					'startLat' => '38.7521',
					'startLong' => '121.2880'	));
curl_setopt($ch, CURLOPT_URL, $bz_req_url);
curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);	
curl_setopt($ch, CURLOPT_POST, 1);																					
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
curl_setopt($ch, CURLOPT_TIMEOUT, 10);
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));
$return = curl_exec($ch);

/*LOGDATA($bz_req_url);
LOGDATA($postData);*/
LOGDATA('Result coming');
LOGDATA($return);

/*if (!$return)
	showError("Failed to handle the immediate ride request, Please retry.");
else
	showSuccess("Immediate Ride request successfully created.");*/
		
// if (preg_match("/OK/i", $return)) {
  //      showError("Failed to handle the ride request, Please retry.");
    //} 
?>