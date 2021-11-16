<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $plant_id = $_REQUEST['plant_id'];
    $farmer_id = $_REQUEST['farmer_id'];
    $planting_date = $_REQUEST['planting_date'];

     $sql_insert = " UPDATE `farmers_plants` SET `planting_date` = '$planting_date' WHERE plant_id = '$plant_id' AND farmer_id = '$farmer_id'  ";

    if ($conn->query($sql_insert)){
        $obj->status_message = "Farmer Plant Updated";
        $obj->status_code = 200;
    }else{
        $obj->status_message = "Farmer Plant Failed";
        $obj->status_code = 400;
    }


    echo json_encode($obj);