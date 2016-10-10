<?php
include("includes/common.php");
include("includes/db.php");
session_start();
//Android notification
$deviceToken = 'eZ9SkTga1Ac:APA91bGYIEBrtHyYvHaxv6liHn0erZr8jeSTH_6h-BVeWpTlKhKxxIWZVrjcpc80KV5K4it3HRrfST4Qr7vz-GUv9lW1FLUVymDNP5xGESrsrMvsdNplZy5Fa7ojvBRWNr3oItuvKu2Q';

LOGDATA($deviceToken);			
$pushMessage = "You have a ride request from santhosh";
LOGDATA($pushMessage);
$apiKey = $ANDROID_GCM_KEY; // Give api key here.
LOGDATA('Android notification');
androidpush($deviceToken,$pushMessage,$apiKey);


?>