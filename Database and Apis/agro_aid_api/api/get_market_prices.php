<?php  

    header('Content-Type: application/json');
    require "../connection.php";

    
    function getDistanceBetweenPointsNew($latitude1, $longitude1, $latitude2, $longitude2, $unit = 'miles') {
      $theta = $longitude1 - $longitude2; 
      $distance = (sin(deg2rad($latitude1)) * sin(deg2rad($latitude2))) + (cos(deg2rad($latitude1)) * cos(deg2rad($latitude2)) * cos(deg2rad($theta))); 
      $distance = acos($distance); 
      $distance = rad2deg($distance); 
      $distance = $distance * 60 * 1.1515; 
      switch($unit) { 
        case 'miles': 
          break; 
        case 'kilometers' : 
          $distance = $distance * 1.609344; 
      } 
      return (round($distance,2)); 
    }


    $plant_id = $_REQUEST['plant_id'];
    
    $sql = "SELECT * FROM `market_prices` WHERE plant_id = '$plant_id'";

    $response = mysqli_query($link,$sql);				
    $database_array = array();

    while ( $row = mysqli_fetch_assoc($response) ) {
        
        $row['market'] = mysqli_fetch_assoc(mysqli_query($link,"SELECT * FROM `markets` WHERE id = ".$row["market_id"] ));
        $row['market']['distance'] = getDistanceBetweenPointsNew($_REQUEST['latitude'], $_REQUEST['longitude'],$row['market']['latitude'],$row['market']['longitude'],'kilometers' );
        
        array_push($database_array, $row);
    }   

	echo json_encode($database_array);

?>
