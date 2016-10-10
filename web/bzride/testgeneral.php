<?php

include("includes/db.php");
include("includes/common.php");



function isweekend($date){
    $date = strtotime($date);
    $date = date("l", $date);
    $date = strtolower($date);
    echo $date;
    if($date == "friday" || $date == "saturday" || $date == "sunday") {
        return "true";
    } else {
        return "false";
    }
}

function FindTimeDiff($start,$end = false) { 
 	
	$startTime = date("H:i:s",strtotime($start ));
	$endTime = date("H:i:s",strtotime($end ));
	
	echo 'hello';

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
function ifTimeBetween($start, $end, $timetoCheck)
{
	$date1 = DateTime::createFromFormat('H:i:s', $timetoCheck);
	$date2 = DateTime::createFromFormat('H:i:s', $start);
	$date3 = DateTime::createFromFormat('H:i:s', $end);
	if ($date1 > $date2 && $date1 < $date3)
	{
	   return true;
	}
	else
	{
		return false;
	}
}

function roundToTheNearestAnything($value, $roundTo)
{
    $mod = $value%$roundTo;
    return $value+($mod<($roundTo/2)?-$mod:$roundTo-$mod);
}


/*$fulltime = '2013-01-22 22:45:45';
 echo isNightRide($fulltime);
 return;*/

 
 
$ActualRideDateTimeStart = '2016-09-30 9:45:45';

 echo isweekend ($ActualRideDateTimeStart);
 
 /*
$ActualRideDateTimeStart = '2013-01-22 9:45:45';

$ActualRideDateTimeEnd = '2013-01-22 10:45:45';

$timetakenminutes  = FindTimeDiff($ActualRideDateTimeStart,$ActualRideDateTimeEnd);

echo $timetakenminutes ;

return;*/

 
  
		   
/*$start  = strtotime('22:13:37');
LOGDATA('time start ->'. $start);

$end  = strtotime('06:13:37');
LOGDATA('time end->'. $end);*/

	/*$tokenGeneric = SECRET_KEY;

	$Id = '11';
	$text = time(). ":". $Id;
	LOGDATA('time and id->'. $text);
	$token = bz_crypt($tokenGeneric,$text,'encrypt');
	LOGDATA('token created->'.$token);
	
	
	
	$decToken = bz_crypt($tokenGeneric,$token,'decrypt');
	LOGDATA($decToken);
	LOGDATA('token decrypted for user is ->'.$decToken);
	
	
	LOGDATA('token decrypted->'.$token);
	
return;*/


$time = date("H:i:s",strtotime($fulltime ));
echo $time;
echo '<br>';
echo ifTimeBetween("10:00:00 pm","11:59:00 pm",'10:45:45 pm');
echo ifTimeBetween("12:00:00 am","6:00:00 am",'10:45:45 am');

//echo ifTimeBetween("5:42:22 am","6:26:22 pm", "4:59:33 pm");
echo '<br>';
echo ceil(1234.33);
//echo roundToTheNearestAnything(ceil(1234.799), 10).'<br>';
return;
//phpinfo();

/*$dobin= getIfSet($_REQUEST['dob']);
$format = 'm/d/Y';
$date = DateTime::createFromFormat($format, $dobin);

echo "Format: $format; " . $date->format('Y-m-d H:i:s') . "\n";*/
$final = getMYSQLDate($_REQUEST['dob']);

LOGDATA($dateIn);
$driver_details="insert into bztbl_test values('',$final)";
									
LOGDATA($driver_details);
$result = mysql_query($driver_details,$conn);
if (!$result) {
	showError(mysql_error());
}

mysql_close();

?>
