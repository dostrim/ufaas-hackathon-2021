<?php

    require "../connection.php";

    $obj = new stdClass();
    $obj->status_message = null;
    $obj->status_code = 200;

    $id = $_REQUEST['id'];
    $type = $_REQUEST['type'];
    
    $sql_insert = "";
    if($type=="up"){
        $sql_insert = " UPDATE `forums` SET `up_vote` = up_vote+1 WHERE `forums`.`id` = ".$id;
    }else{
        $sql_insert = " UPDATE `forums` SET `down_vote` = down_vote+1 WHERE `forums`.`id` = ".$id;
    }


    if ($conn->query($sql_insert)){
        $obj->status_message = "Vote Successful";
        $obj->status_code = 200;
    }else{
        $obj->status_message = "Vote Failed";
        $obj->status_code = 400;
    }


    echo json_encode($obj);