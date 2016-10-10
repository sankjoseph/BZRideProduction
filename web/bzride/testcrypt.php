<?php
include("includes/common.php");
include("includes/db.php");

// Set the password
$password = 'password';

// Get the hash, letting the salt be automatically generated
$hash = crypt($password);

LOGDATA($hash);

echo $hash;

$hashed_password = $hash;

if (hash_equals($hashed_password, crypt($password, $hashed_password))) {
   echo "\nPassword verified!";
}



/*LOGDATA($bz_req_url);
LOGDATA($postData);*/
LOGDATA('Result coming');


/*if (!$return)
	showError("Failed to handle the immediate ride request, Please retry.");
else
	showSuccess("Immediate Ride request successfully created.");*/
		
// if (preg_match("/OK/i", $return)) {
  //      showError("Failed to handle the ride request, Please retry.");
    //} 
?>