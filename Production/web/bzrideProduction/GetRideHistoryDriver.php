<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("Get Driver trip details");

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

$requestSQL = "SELECT * FROM  bztbl_riderequests AS R INNER JOIN bztbl_riders AS U ON R.requestorId = U.Id  where R.DriverId = " .$driverID. " and R.status=". "'C'"." order by R.Id DESC" ;
LOGDATA($requestSQL);

$resultHistory = mysql_query($requestSQL,$conn);
if (!$resultHistory) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultHistory);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$inc = 0;
	$data = array();
	$datainner = array();
	$data["status"] ="S";
	$data["info"] = "Get Driver trip details success";
	while($rowIn = mysql_fetch_assoc($resultHistory)) {
	$InfoObject = (array('RideDateTime' => $rowIn["RideDateTime"], 
							'StartLocation' => $rowIn["StartLocation"], 
							'EndLocation' => $rowIn["EndLocation"], 
							'RequestorId' => $rowIn["RequestorId"], 
							'FirstName' => $rowIn["FirstName"], 
							'FaretoCompany' => $rowIn["FaretoCompany"]));
                $datainner[$inc] = $InfoObject;
                $inc++;
				
	}
	$data["data"] =  $datainner;
	echo json_encode($data);
}
else
{
	$data = array();
	$data["status"] ="F";
	$data["info"] = "Get Driver trip details failed";
	echo json_encode($data);
}
mysql_close();
}
?>