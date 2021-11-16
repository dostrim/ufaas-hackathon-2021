<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $farmer_id = $_REQUEST['farmer_id'];
    $plant_id = $_REQUEST['plant_id'];

    $sql_insert = "SELECT * FROM `farmers_plants` where farmer_id = '$farmer_id' and plant_id = '$plant_id' ";
    $farmer_plant = $conn->query( $sql_insert )->fetch_assoc();



    
    if ( $farmer_plant["planting_date"] != "" && $farmer_plant["planting_date"] != null ){
        $obj->status_message = "Farm Plant Found Found";
        $obj->status_code = 200;
        
        $now = time();
        $your_date = strtotime($farmer_plant["planting_date"]);
        $datediff = $now - $your_date;
        $days = round($datediff / (60 * 60 * 24));
        $obj->days =  $days;
        
        $plant_age = $conn->query( "SELECT * FROM `plant_age` WHERE maximum >= ".$days )->fetch_assoc();
        $obj->plant_age = $plant_age;
        
        //diseases
        $response = mysqli_query($link,"SELECT * FROM `plant_disease` WHERE plant_age = '".$plant_age["id"]."' AND plant_id = '$plant_id' AND attack = 'disease' ");				
        $database_array = array();
        while ( $row = mysqli_fetch_assoc($response) ) {
            array_push($database_array, $row);
        }  
        $obj->diseases = $database_array;
        
        //pest
        $response = mysqli_query($link,"SELECT * FROM `plant_disease` WHERE plant_age = '".$plant_age["id"]."' AND plant_id = '$plant_id' AND attack = 'pest' ");				
        $database_array = array();
        while ( $row = mysqli_fetch_assoc($response) ) {
            array_push($database_array, $row);
        }  
        $obj->pests = $database_array;
        
        
        
    }else{
        $obj->status_message = "No Planting Date Found";
        $obj->status_code = 400;
    }


    echo json_encode($obj);