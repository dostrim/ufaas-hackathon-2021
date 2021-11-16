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


    $type = $_REQUEST['type'];
    
    $sql = "SELECT * FROM `workers` WHERE type = '$type'";

    $response = mysqli_query($link,$sql);				
    $database_array = array();

    while ( $row = mysqli_fetch_assoc($response) ) {
        
        $row['distance'] = getDistanceBetweenPointsNew($_REQUEST['latitude'], $_REQUEST['longitude'],$row['latitude'],$row['longitude'],'kilometers' );
        array_push($database_array, $row);
    }   

	echo json_encode($database_array);

?>
