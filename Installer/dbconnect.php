<?php
if($_POST){
    $db_host = $_POST["db_host"];
    $db_name = $_POST["db_name"];
	$db_username = $_POST["db_username"];
	$db_password = $_POST["db_password"];
	
	$code = $_POST;
	$link = mysqli_connect($db_host, $db_username, $db_password,$db_name);
		
		if(!$link) {
		    echo "Error";
		}else{
			echo "Success";
		}
}			
?>