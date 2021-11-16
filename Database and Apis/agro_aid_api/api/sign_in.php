<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $phone_number = $_REQUEST['phone_number'];
    $password = $_REQUEST['password'];

    $sql_insert = "SELECT * FROM `farmers` where phone_number = '$phone_number' and password = '$password' ";

    if ( $row = $conn->query( $sql_insert )->fetch_assoc() ){
        $obj->status_message = "User Found";
        $obj->status_code = 200;
        $obj->user = $row;
    }else{
        $obj->status_message = "User Not Found";
        $obj->status_code = 400;
    }


    echo json_encode($obj);