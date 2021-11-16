<?php  

    header('Content-Type: application/json');
    require "../connection.php";

    $sql = "SELECT * FROM `plants`";

    $response = mysqli_query($link,$sql);				
    $database_array = array();

    while ( $row = mysqli_fetch_assoc($response) ) {
        array_push($database_array, $row);
    }   

	echo json_encode($database_array);

?>
