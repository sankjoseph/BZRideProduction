<?php

function FindTimeDiff($start,$end = false) { 
    $to_time = strtotime($end);
    $from_time = strtotime($start);
    $min = round(abs($to_time - $from_time) / 60,2);
    return  $min; 
} 

//echo FindTimeDiff("2016-10-14 17:43:43","2016-10-14 21:31:49");
$current = date('Y/m/d H:i:s');
//echo $current;
echo FindTimeDiff("2016-10-14 20:43:43",$current);

//echo round( 4.0, 2, PHP_ROUND_HALF_UP);

?>