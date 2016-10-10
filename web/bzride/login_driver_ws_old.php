<?php
include("includes/db.php");
$phone=$_POST['txtphone'];
$password=$_POST['txtpass'];
// Check connection
if (!$conn) {
    echo "Not connected";
} 
else{
//echo $phone;
//echo $password;
//exit();

if(isset($_REQUEST['phone']) and $_REQUEST['phone']!='' and isset($_REQUEST['password']) and $_REQUEST['password']!='')
{
    $phone1 = mysql_real_escape_string($_REQUEST['phone']);
    $password1 = mysql_real_escape_string($_REQUEST['password']);

    $query = 'select id from bztbl_drivers where Phone = "'.$phone1.'" and Password = "'.$password1.'"';
    $result=mysql_query($query) or die('error getting admin details : '.mysql_error());

    if(mysql_num_rows($result)==1)
    {
        echo 'Login Success!!';
    }
    else
    {
        echo 'Incorrect login details!!';
    }
}
else
{

    echo 'Error recieving data!!';

}



mysql_close();
//header("location:home.php");
}
?>