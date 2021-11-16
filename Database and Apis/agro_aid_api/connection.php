<?php  

	$database_name = "agro_aid";
	$host = "localhost";
	$user_name = "root";
    $password = "";

  	$link = mysqli_connect($host,$user_name,$password,$database_name);
	$conn = new mysqli($host, $user_name, $password, $database_name);

?>