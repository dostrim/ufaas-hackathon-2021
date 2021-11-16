<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $plant_id = $_REQUEST['plant_id'];
    $farmer_id = $_REQUEST['farmer_id'];

     $sql_insert = " INSERT INTO `farmers_plants` (`id`, `plant_id`, `farmer_id`, `created_at`) VALUES (NULL, '$plant_id', '$farmer_id', CURRENT_TIMESTAMP) ";

    if ($conn->query($sql_insert)){
        $obj->status_message = "Farmer Plant Saved";
        $obj->status_code = 200;
    }else{
        $obj->status_message = "Farmer Plant Save Failed";
        $obj->status_code = 400;
    }


    echo json_encode($obj);