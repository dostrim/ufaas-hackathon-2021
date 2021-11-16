<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $first_name = $_REQUEST['first_name'];
    $last_name = $_REQUEST['last_name'];
    $phone_number = $_REQUEST['phone_number'];
    $password = $_REQUEST['password'];
    $gender = $_REQUEST['gender'];

    $sql_insert = "INSERT INTO `farmers` (`id`, `first_name`, `last_name`, `phone_number`, `password`, `gender`) VALUES (NULL, '$first_name', '$last_name', '$phone_number', '$password', '$gender') ";

    if ($conn->query($sql_insert)){
        $obj->status_message = "User Created";
        $obj->status_code = 200;
        $obj->user = $conn->query("SELECT * FROM `farmers` where phone_number = '$phone_number'")->fetch_assoc();
    }else{
        $obj->status_message = "User Failed";
        $obj->status_code = 400;
    }


    echo json_encode($obj);