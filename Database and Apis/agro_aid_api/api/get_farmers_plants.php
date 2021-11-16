<?php  

    header('Content-Type: application/json');
    require "../connection.php";

    $farmer_id = $_REQUEST['farmer_id'];
    $sql = "SELECT plants.* FROM `farmers_plants` INNER JOIN plants ON plants.id = farmers_plants.plant_id WHERE farmer_id = ".$farmer_id;

    $response = mysqli_query($link,$sql);				
    $database_array = array();

    while ( $row = mysqli_fetch_assoc($response) ) {
        array_push($database_array, $row);
    }   

	echo json_encode($database_array);

?>
