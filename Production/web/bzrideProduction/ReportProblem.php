<?php
include("includes/db.php");
include("includes/common.php");

LOGDATA("Reg Driver");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic user details

$token = $_REQUEST['token'];
LOGDATA($token);
$userID = GetIdByCheckforTimeout($token);

$ReportTitle = getIfSet($_REQUEST['ReportTitle']);
$ReportDescription = getIfSet($_REQUEST['ReportDescription']);
					
// insert problme details  in DB
$problem_details="insert into bztbl_reportedproblems values('',$userID, $ReportTitle, $ReportDescription,now(),now())";
									
LOGDATA($problem_details);
$result = mysql_query($problem_details,$conn);
if (!$result) {
	showError(mysql_error());
}

$last_id = mysql_insert_id();
LOGDATA("last inserted problem =".$last_id );
if (!$last_id) {
	showError(mysql_error());
}

$data = array();
$data["status"] ="S";
$data["info"] = "Problem reporting completed";
$data["Id"] = "".$last_id."";
echo json_encode($data);
mysql_close();
}
?>