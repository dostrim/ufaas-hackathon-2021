<?php 
class Farmers extends Controller{

    public function __construct()
    {
        if(!isLoggedIn()){
            redirect('users/login');
        }
        //new model instance
        $this->farmerModel = $this->model('Farmer');
        $this->userModel = $this->model('User');
    }

    public function index(){

        $farmers = $this->farmerModel->getFarmers();
        $data = [
            'farmers' => $farmers
        ];

        $this->view('farmers/index', $data);
    }

    //add new farmer
    public function add(){
        $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);
        if($_SERVER['REQUEST_METHOD'] == 'POST'){
            $data = [
                'fullname' => trim($_POST['fullname']),
                'telno' => trim($_POST['telno']),
                'user_id' => $_SESSION['user_id'],
                'fullname_err' => '',
                'telno_err' => '',
            ];

            if(empty($data['fullname'])){
                $data['fullname_err'] = 'Please enter farmer full name';
            }
            if(empty($data['telno'])){
                $data['telno_err'] = 'Please enter the farmer Telphone number';
            }

            //validate error free
            if(empty($data['fullname_err']) && empty($data['telno_err'])){
                if($this->farmerModel->addFarmer($data)){
                    flash('post_message', 'Your farmer have been added');
                    redirect('farmers');
                }else{
                    die('something went wrong');
                }
               
                //laod view with error
            }else{
                $this->view('farmers/add', $data);
            }
        }else{
            $data = [
                'fullname' => (isset($_POST['fullname']) ? trim($_POST['fullname']) : ''),
                'telno' =>  (isset($_POST['telno'])? trim($_POST['telno']) : '')
            ];

            $this->view('farmers/add', $data);
        }
    }

    //show single farmer 
    public function show($id){
        $farmer = $this->farmerModel->getFarmerById($id);
        $user = $this->userModel->getUserById($farmer->user_id);

        $data = [
            'farmer' => $farmer,
            'user' => $user
        ];

        $this->view('farmers/show', $data);
    }

     //edit farmer
     public function edit($id){
        if($_SERVER['REQUEST_METHOD'] == 'POST'){
            $_POST = filter_input_array(INPUT_POST, FILTER_SANITIZE_STRING);
            $data = [
                'id' => $id,
                'fullname' => trim($_POST['fullname']),
                'telno' => trim($_POST['telno']),
                'user_id' => $_SESSION['user_id'],
                'fullname_err' => '',
                'telno_err' => '',
            ];
            //validate the title
            if(empty($data['fullname'])){
                $data['fullname_err'] = 'Please enter farmer full name';
            }
            //validate the body
            if(empty($data['body'])){
                $data['telno_err'] = 'Please enter the farmer telphone nnumber';
            }

            //validate error free
            if(empty($data['fullname_err']) && empty($data['telno_err'])){
                if($this->farmerModel->updatefarmer($data)){
                    flash('post_message', 'Your farmer have been updated');
                    redirect('farmers');
                }else{
                    die('something went wrong');
                }
               
                //laod view with error
            }else{
                $this->view('farmers/edit', $data);
            }
        }else{
            //check for the owner and call method from farmer model
            $farmer = $this->farmerModel->getFarmerById($id);
            if($farmer->user_id != $_SESSION['user_id']){
                redirect('farmers');
            }
            $data = [
                'id' => $id,
                'fullname' => $farmer->fullname,
                'telno' => $farmer->telno
            ];

            $this->view('farmers/edit', $data);
        }
    }
    
    //delete farmer
    public function delete($id){
        if($_SERVER['REQUEST_METHOD'] == 'POST'){
            //check for owner
            $farmer = $this->farmerModel->getFarmerById($id);
            if($farmer->user_id != $_SESSION['user_id']){
                redirect('farmers');
            }
            
            //call delete method from farmer model
            if($this->farmerModel->deleteFarmer($id)){
                flash('post_message', 'farmer Removed');
                redirect('farmers');
            }else{
                die('something went wrong');
            }
        }else{
            redirect('farmers');
        }
    }
}                            
                        