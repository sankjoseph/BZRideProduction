<?php

	//var_dump($_REQUEST);
	$firstname= $_REQUEST["txtfirstname"];
	$lastname= $_REQUEST["txtlastname"];
	//echo $lastname;
	//echo $firstname;
	
	$data = array();
    $data["status"] ="S";
    $data["info"] = "Completed request for" . $firstname . ' ' . $lastname; 
    echo json_encode($data);
?>