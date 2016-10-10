<?php

include("includes/db.php");
include("includes/common.php");

$requestId = $_REQUEST['rideRequestId'];
$startLat = $_REQUEST['startLat'];
$startLong = $_REQUEST['startLong'];



$startLatFloat = sprintf("%f",$startLat);
$startLongFloat = sprintf("%f",$startLong);

LOGDATA( $startLat);
LOGDATA( $startLong);

LOGDATA( $startLatFloat);
LOGDATA( $startLongFloat);


/*
$startLatFloat = floatval($startLat);
$startLongFloat = floatval($startLong);


// prepare sql and bind parameters
    $stmt = $conn->prepare("SELECT * , (3956 * 2 * ASIN(SQRT( POWER(SIN(( :startLatFloat - currentlat) *  pi()/180 / 2), 2) +COS( :startLatFloat * pi()/180) * COS(currentlat * pi()/180) * POWER(SIN(( :startLatFloat - currentlong) * pi()/180 / 2), 2) ))) as distance  from bztbl_drivers WHERE STATUS =  'A'  AND isActive =1 having  distance <= 10 order by distance");
    //$stmt->bindParam(':startLatFloat', $startLatFloat);
    //$stmt->bindParam(':startLongFloat', $startLongFloat);
	$stmt->bindValue(':startLatFloat', $startLatFloat, PDO::PARAM_INT);
	$stmt->bindValue(':startLongFloat', $startLongFloat, PDO::PARAM_INT);
	$result  = $stmt->execute();
	
	LOGDATA( $stmt);
	
if (!$result) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($result);
LOGDATA($num_rows); */

$findriverSQL = "SELECT * , (3956 * 2 * ASIN(SQRT( POWER(SIN(( ".$startLatFloat. " - currentlat) *  pi()/180 / 2), 2) +COS(". $startLongFloat. "* pi()/180) * COS(currentlat * pi()/180) * POWER(SIN((" .$startLatFloat. "- currentlong) * pi()/180 / 2), 2) ))) as distance  from bztbl_drivers WHERE STATUS =  'A'  AND isActive =1 having  distance <= 10 order by distance";

LOGDATA($findriverSQL);

$result = mysql_query($findriverSQL,$conn);
if (!$result) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($result);
LOGDATA($num_rows);

mysql_close();

?>
