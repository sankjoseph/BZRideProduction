<?php
include("includes/db.php");
$phone=$_POST['txtphone'];
$password=$_POST['txtpass'];
// Check connection
if (!$conn) {
    echo "Not connected";
} 
else{

$q="select * from bztbl_riders where phone='$phone' and password='$password'";
$r=mysql_query($q,$conn);
$f=mysql_fetch_row($r);
$count=mysql_num_rows($r);
if($count==1)
{
	echo "Rider Login Successfull";
}
else echo "Rider Not Found";


mysql_close();
//header("location:home.php");
}
?>