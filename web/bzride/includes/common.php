<?php
//define("DEBUG_L","Console");
define("DEBUG_F","File");
define("TEST_CARD","CARD");
define('SECRET_KEY', "zxcvbnmasdfghjkl");//16 digit key

$ANDROID_GCM_KEY = 'AIzaSyDpkMnJYFvd41lI7Bz8IrTZTw6V8WNOm40';
$STRIPE_SANDBOX_SECRET_KEY = "sk_test_2rCnQT2VGQl5ndFbgfEas7g2";
$STRIPE_LIVE_SECRET_KEY = "sk_live_XeSRUrmQRkfeohItbtPFfTjF";

$STRIPE_RUNNING_SECRET_KEY = $STRIPE_SANDBOX_SECRET_KEY;

$BASE_URL = 'http://bzride.com/bzride/'; //change when production



function isweekend($date){
    $date = strtotime($date);
    $date = date("l", $date);
    $date = strtolower($date);
    if($date == "friday" || $date == "saturday" || $date == "sunday") {
        return "true";
    } else {
        return "false";
    }
}

function FindTimeDiff($start,$end = false) { 
 	
	$startTime = date("H:i:s",strtotime($start ));
	$endTime = date("H:i:s",strtotime($end ));
	

	$diff = $endTime - $startTime;
	
	$hour = abs($diff);
	
	$min = $hour * 60 ;
   
    return  $min; 
} 


function isNightRide($datetimeIn)
{
	$currentTime = date("H:i:s",strtotime($datetimeIn ));
	if ((((int) date('H', strtotime($currentTime))) >= 22) && (((int) date('H', strtotime($currentTime))) <= 24))
	{
		return true;
	}
	if ((((int) date('H', strtotime($currentTime))) >= 0) && (((int) date('H', strtotime($currentTime))) <= 6))
	{
		return true;
	}
}

function getMYSQLDate($dateToFormat)
{
	$dateIn = strtotime($dateToFormat);
	$new_date_format = date("Y-m-d H:i:s", $dateIn );  
	$final = "'".$new_date_format."'";
	return $final;
}
// For Android devices
function androidpush($deviceToken,$pushMessage,$apiKey)
{
	LOGDATA('Android push notification begin');		
    $gcmRegID  = $deviceToken;
	LOGDATA($gcmRegID);	
	LOGDATA($pushMessage);	
	LOGDATA($apiKey);	
	
    if ($gcmRegID && $pushMessage) {    
            $registatoin_ids = array($gcmRegID);
            $message = array("m" => $pushMessage,'x' => 'call');
            
            $url = 'https://android.googleapis.com/gcm/send';
			$fields = array(
             'registration_ids' => $registatoin_ids,
             'data' => $message,
             'priority' => 'high'
          ); 
          $headers = array(
              'Authorization: key=' . $apiKey,
              'Content-Type: application/json'
          );
          $ch = curl_init();
          curl_setopt($ch, CURLOPT_URL, $url);
          curl_setopt($ch, CURLOPT_POST, true);
          curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
          curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
          curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0); 
          curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
          curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
          $result = curl_exec($ch);    
		  LOGDATA($result);		  
          curl_close($ch);
	
	
		if (!$result)
			showError("Android push message not delivered. Please retry.");
		else
			//showSuccess("Ride request message successfully delivered.");
		return true;
		
    }
	else
	{
		showError("Message or device token not set. Please retry.");
		return false;
	}
    return true;
}
// For apple devices
function apns($deviceToken,$from,$fromname)
{
	LOGDATA('IOS push notification begin');	
      $passphrase = 'xxxxxxx';      
      $message['loc-key'] = "IC_MSG";
      $message['message'] = "$fromname";
      
      $ctx = stream_context_create();
      stream_context_set_option($ctx, 'ssl', 'local_cert', 'production.pem');
      stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);
      
      // Open a connection to the APNS server
      $fp = stream_socket_client(
          'ssl://gateway.push.apple.com:2195', $err,
          $errstr, 60, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $ctx);
      
      if (!$fp)
		  showError("Failed to connect. Please retry.");
          //exit("Failed to connect: $err $errstr" . PHP_EOL);
      else
	  {
		  showSuccess("Connected to APNS.");
	  }
      
      $body['aps'] = array(
          'alert' => "You have a ride request from ".$fromname,
          'sound' => "default"
    );
      
      // Encode the payload as JSON
      $payload = json_encode($body);
      
      // Build the binary notification
      $msg = chr(0) . pack('n', 32) . pack('H*', $deviceToken) . pack('n', strlen($payload)) . $payload;
      
      // Send it to the server
      $result = fwrite($fp, $msg, strlen($msg));
            
      if (!$result)
			showError("Ride request message not delivered. Please retry.");
      else
			showSuccess("Ride request message successfully delivered.");
      
      // Close the connection to the server
      fclose($fp);
}

function GetIdByCheckforTimeout($token)
{
	$tokenGeneric = SECRET_KEY;
	$decToken = bz_crypt($tokenGeneric,$token,'decrypt');
	LOGDATA($decToken);
	LOGDATA('token decrypted for user is ->'.$decToken);
	$userid = after(':', $decToken);
	$timecreated = before(':', $decToken);
	LOGDATA('time created'. $timecreated);

	$time = (int)($timecreated);
	$curtime = time();

	LOGDATA($time);

	LOGDATA('time now'.$curtime);
	
	$diff = $curtime-$time;

	LOGDATA('diff'.$diff);
	LOGDATA('useridretruning'.$userid);
	return $userid;
	
	/*if($diff > 600) {   
		showError('Session time out. Please login again.');
	}
	else 
	{
		return $driverID;
	}*/

};

function after($this, $inthat)
{
	if (!is_bool(strpos($inthat, $this)))
	return substr($inthat, strpos($inthat,$this)+strlen($this));
};

function before($this, $inthat)
{
	return substr($inthat, 0, strpos($inthat, $this));
};
	
 function bz_crypt($key, $string, $action = 'encrypt'){
            $res = '';
            if($action !== 'encrypt'){
                $string = base64_decode($string);
            } 
            for( $i = 0; $i < strlen($string); $i++){
                    $c = ord(substr($string, $i));
                    if($action == 'encrypt'){
                        $c += ord(substr($key, (($i + 1) % strlen($key))));
                        $res .= chr($c & 0xFF);
                    }else{
                        $c -= ord(substr($key, (($i + 1) % strlen($key))));
                        $res .= chr(abs($c) & 0xFF);
                    }
            }
            if($action == 'encrypt'){
                $res = base64_encode($res);
            } 
            return $res;
    };
	

//This function is used to encrypt data.
 function bz_encrypt($text, $salt = "bzride.com")
 {
    return trim(base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_256, $salt, $text, MCRYPT_MODE_ECB, mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND))));
	
	// Create the initialization vector for added security.
/*$iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND);

// Encrypt $string
$encrypted_string = mcrypt_encrypt(MCRYPT_RIJNDAEL_256, $salt, $text, MCRYPT_MODE_CBC, $iv);
return $encrypted_string;*/


}
// This function will be used to decrypt data.
 function bz_decrypt($text, $salt = "bzride.com")
 {
    return trim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $salt, base64_decode($text), MCRYPT_MODE_ECB, mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND)));
	// Create the initialization vector for added security.
/*$iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_ECB), MCRYPT_RAND);
// Decrypt $string
$decrypted_string = mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $salt, $text, MCRYPT_MODE_CBC, $iv);
return $decrypted_string;*/
}

function LOGDATA($value, $default = null)
{	
  if (defined('DEBUG_L')) {
      echo $value;
  }
   if (defined('DEBUG_F')) {
   $myfile = fopen("Logdata.txt", "a+");
   if ($myfile)
   {
		$date = date("D M d, Y G:i", time());
		$logtext = $date . " --- " . $value. "\n";
		fwrite($myfile,  $logtext);
		fclose($myfile);
   }
	else
	{
		echo "file opening failed";
	}
  }
}
function getIfSet($value, $default = null)
{
	
    return isset($value) ? "'".$value."'" : $default;
}

function showError($value, $default = null)
{ /* Output header */
	LOGDATA($value);
	header('Content-type: application/json');
	$data = array();
    $data["status"] ="F";
    $data["info"] = $value;
    echo json_encode($data);
    die();
}
function showSuccess($value, $default = null)
{ /* Output header */
	LOGDATA($value);
	header('Content-type: application/json');
	$data = array();
    $data["status"] ="S";
    $data["info"] = $value;
    echo json_encode($data);
    die();
}
?>