<?php
include("includes/db.php");
include("includes/common.php");

echo 'cron test';
LOGDATA("cron sample details");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  

$token = $_REQUEST['token'];
LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);
}


$data = array();
$data["status"] ="S";
$data["info"] = "cron sample details";
echo json_encode($data);

mysql_close();

?>