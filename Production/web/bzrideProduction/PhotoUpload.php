<?php
include("includes/common.php");

/////
LOGDATA("Uploading image"); 

// Path to move uploaded files
$target_path = "uploads/images/";

// array for final json respone
$data = array(); 

// getting server ip address
//$server_ip = gethostbyname(gethostname());
//echo $server_ip;

// final file url that is being uploaded
$file_upload_url = $BASE_URL . $target_path;

if(isset($_REQUEST['base64']))
{
    $imgname = $file_upload_url.'1.jpg';
    $imsrc = str_replace(' ','+',$_REQUEST['base64']);
    $imsrc = base64_decode($imsrc);
    $fp = fopen($imgname, 'w');
    fwrite($fp, $imsrc);
    if(fclose($fp))
    {
        $data["status"] ="F";
        $data["info"] = "photo upload success for driver";
    }
    else
    {
        showError("file error");
    }
}  
echo json_encode($data);
?>