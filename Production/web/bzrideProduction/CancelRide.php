<?php
include("includes/db.php");
include("includes/common.php");

/////
LOGDATA("cancelling ride with charge- No show");

// Check connection
if (!$conn) {
      showError("Data base connection error");
} 
else
{  
//basic driver details
$requestId = $_REQUEST['rideRequestId'];
$token = $_REQUEST['token'];

LOGDATA($token);
$driverID = GetIdByCheckforTimeout($token);
   
$selectSQL = "select status,LastModifiedDate from bztbl_riderequests where Id = " .$requestId ;// LastModifiedDate is the date where he set arrived
LOGDATA($selectSQL);
$resultSQL = mysql_query($selectSQL,$conn);
if (!$resultSQL) {
	showError(mysql_error());
}    

$num_rows = mysql_num_rows($resultSQL);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultSQL);
	$status = $rowIn["status"];//status
    $LastModifiedDate = $rowIn["LastModifiedDate"];//LastModifiedDate
    if ($status != 'AR')
    {
        showError("Cannot cancel the request as the request is not in arrived state.");
    }
    else
    {
        $current = date('Y/m/d H:i:s');
        $minutesleft = FindTimeDiff($LastModifiedDate,$current);
        LOGDATA('minutes after arriving->'.$minutesleft);
        if ( $minutesleft < 5)
        {
            showError("Cannot cancel the request before 5 minutes after arriving.");
        }
    }
}
else
{
    showError("Could not find matching ride request.");
}
    
//set as suspended
$requestSQL = "UPDATE bztbl_riderequests SET status = 'S' where Id = " .$requestId ;
LOGDATA($requestSQL);
$resultUpdate = mysql_query($requestSQL,$conn);
if (!$resultUpdate) {
	showError(mysql_error());
}

//////
//update the driver as available
$requestSQLDriver = "UPDATE bztbl_drivers SET status = 'A' where Id = " .$driverID ;

LOGDATA($requestSQLDriver);

$resultUpdateDriver = mysql_query($requestSQLDriver,$conn);
if (!$resultUpdateDriver) {
	showError(mysql_error());
}


$requestSQL = "SELECT R.Id, R.RequestorId, R.DriverId,U.DeviceToken, U.DeviceType, U.CardToken FROM  bztbl_riderequests AS R INNER JOIN  bztbl_riders U ON R.RequestorId = U.Id  where R.Id = ".$requestId;

LOGDATA($requestSQL);

$resultIn = mysql_query($requestSQL,$conn);
if (!$resultIn) {
	showError(mysql_error());
}
$num_rows = mysql_num_rows($resultIn);
LOGDATA($num_rows);
if ( $num_rows > 0) {
	$rowIn = mysql_fetch_array($resultIn);
	$deviceToken = $rowIn["DeviceToken"];//rider device token
	$riderId = $rowIn["RequestorId"];
	$CardToken = $rowIn["CardToken"];
	//notify rider with details
	LOGDATA('notify rider for cancel ride ');	
	$deviceType = $rowIn["DeviceType"];
	if ($deviceType == 'A')
	{
		LOGDATA($deviceToken);			
		$pushMessage = "Your ride request is cancelled by driver and cancellation charges are applicable.";
		LOGDATA($pushMessage);
		$apiKey = $ANDROID_GCM_KEY; // Give api key here.
		LOGDATA('Android notification');
		androidpush($deviceToken,$pushMessage,$apiKey);		
	}
	else if ($deviceType == 'I')
	{
		// ios notifications
	}
	///
	$cancelCharge = 5.0;
	// charge card for the amount usinhg card token todo
	$bz_req_url = $BASE_URL . 'charge.php';
	$ch =  curl_init();

	$postData = http_build_query(array('token' => $CardToken,	
						'amount' => $cancelCharge,//dollar
						'requestId' => $requestId,
						'currency' => 'usd'	));
	curl_setopt($ch, CURLOPT_URL, $bz_req_url);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);
	curl_setopt($ch, CURLOPT_POST, 1);																							
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
	curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
	curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 5);
	curl_setopt($ch, CURLOPT_TIMEOUT, 10);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));
	$result = curl_exec($ch);
	LOGDATA($result);
		
if (preg_match("/Could not/i", $result)) {
    showError("Failed to handle charge cancellation, Please retry.");
 } 
}
else
{
	LOGDATA('notify rider for cancel ride failed');
}
$data = array();
$data["status"] ="S";
$data["info"] = "Cancel ride success for driver";
echo json_encode($data);
mysql_close();
}
?>