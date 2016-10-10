<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Update driver location");

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
$Lat = $_REQUEST['Lat'];
$Long = $_REQUEST['Long'];
					
// update driver values in DB as available
$driver_details="UPDATE bztbl_drivers SET currentlat = ".$Lat. ", currentlong = ".$Long. " where Id = ".$driverID;
						
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}
$data = array();
$data["status"] ="S";
$data["info"] = "Update driver location success";
echo json_encode($data);

mysql_close();
}
?>