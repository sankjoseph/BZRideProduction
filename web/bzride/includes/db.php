<?php
    $conn = mysql_connect("localhost","bzride5_admin", "P@ssw0rd1");
    
    if (!$conn) {
        die('Could not connect: ' . mysql_error());
    }
    
    $db_selected = mysql_select_db('bzride5_bzRide', $conn);
    
    if (!$db_selected) {
        die ('Can\'t use bzride5_bzRide : ' . mysql_error());
    }
    

?>
