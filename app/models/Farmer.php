<?php

class Farmer {
    private $db;
    public function __construct()
    {
        $this->db = new Database;
    }

    public function getFarmers(){
        $this->db->query('SELECT *,
                            farmers.id as farmerId,
                            user.id as userId,
                            farmers.created_at as farmerCreated,
                            user.created_at as userCreated
                            FROM farmers
                            INNER JOIN user
                            ON farmers.user_id = user.id
                            ORDER BY farmers.created_at DESC');
        $result = $this->db->resultSet();

        return $result;
    }

    public function addFarmer($data){
        $this->db->query('INSERT INTO farmers(user_id, fullname, telno,address_id,farm_id) VALUES (:user_id, :fullname, :telno, :address_id, :farm_id)');
        $this->db->bind(':user_id', $data['user_id']);
        $this->db->bind(':fullname', $data['fullname']);
        $this->db->bind(':telno', $data['telno']);
        $this->db->bind(':address_id', $data['address_id']);
        $this->db->bind(':farm_id', $data['farm_id']);
        
        //execute 
        if($this->db->execute()){
            return true;
        }else{
            return false;
        }
    }

    public function getFarmerById($id){
        $this->db->query('SELECT * FROM farmers WHERE id = :id');
        $this->db->bind(':id', $id);
        $row = $this->db->single();

        return $row;
    }

    public function updateFarmer($data){
        $this->db->query('UPDATE farmers SET fullname = :fullname, telno = :telno,address_id = :address_id, farm_id = :farm_id WHERE id = :id');
        $this->db->bind(':id', $data['id']);
        $this->db->bind(':fullname', $data['fullname']);
        $this->db->bind(':telno', $data['telno']);
        $this->db->bind(':address_id', $data['address_id']);
        $this->db->bind(':farm_id', $data['farm_id']);
        
        //execute 
        if($this->db->execute()){
            return true;
        }else{
            return false;
        }
    }

    //delete a Farmer
    public function deleteFarmer($id){
        $this->db->query('DELETE FROM farmers WHERE id = :id');
        $this->db->bind(':id', $id);

        if($this->db->execute()){
            return true;
        }else{
            return false;
        }
    }
}