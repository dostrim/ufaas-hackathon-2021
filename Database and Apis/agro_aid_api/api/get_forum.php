<?php  

    header('Content-Type: application/json');
    require "../connection.php";

    $sql = "SELECT * FROM `forums`";

    $response = mysqli_query($link,$sql);				
    $database_array = array();

    while ( $row = mysqli_fetch_assoc($response) ) {
        
        $row["farmer"] = mysqli_fetch_assoc( mysqli_query($link,"SELECT * FROM `farmers` WHERE id = '".$row["farmer_id"]."' ") );
        
        array_push($database_array, $row);
    }   

	echo json_encode($database_array);

?>
