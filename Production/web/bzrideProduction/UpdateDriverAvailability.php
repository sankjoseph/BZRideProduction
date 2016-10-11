<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update driver availability");

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
$Flag = $_REQUEST['Flag'];
					
if ($Flag == 'TRUE'){
// update driver values in DB as available
$driver_details="UPDATE bztbl_drivers SET status = 'A',LastModifiedDate=now() where Id = ".$driverID;
}
else{
// update driver values in DB as offline
$driver_details="UPDATE bztbl_drivers SET status = 'O',LastModifiedDate=now() where Id = ".$driverID;
}
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}

$data = array();
$data["status"] ="S";
$data["info"] = "Update driver status success";
echo json_encode($data);

mysql_close();
}
?>