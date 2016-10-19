<?php
include("includes/common.php");

if (isweekend ('2016-10-17 17:48:25'))
{
   echo 'week end days ';
}
else
{
     echo 'normal days ';
}
    
    
        
/*
//$distancetraveledmi = distanceCalculation(38.8181,-121.279,38.7918,-121.222,'mi');
  
$distancetraveledmi = distanceCalculation(38.8181,-121.279,38.7918,-121.221,'mi');
//LOGDATA('distance travelled in miles '.$distancetraveledmi);

echo $distancetraveledmi.'\n';


$result = haversineGreatCircleDistance(38.8181,-121.279,38.7918,-121.221,3959);
echo $result.'\n';

//$dis = GetDrivingDistance(38.8181,38.8085,-121.278,-121.273);
//16 38.8181 	-121.279 	38.7918 	-121.222 2016-10-15 11:26:17 
$dis = GetDrivingDistance(38.8181,38.7918,-121.279,-121.222);

  //9.83086 	76.7429 	9.96665 	76.316
//$dis = GetDrivingDistance(9.83086,9.96665,76.7429,76.316);
echo $dis.'\n';*/

// find time difference
/*$timetakenminutes  = FindTimeDiff($ActualRideDateTimeStart,$ActualRideDateTimeEnd);
//$timetakenminutes = round(abs($ActualRideDateTimeEnd - $ActualRideDateTimeStart) / 60,2);
LOGDATA('time travelled in minutes '.$timetakenminutes);*/



?>