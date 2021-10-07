<?php
  class Pages extends Controller {
    public function __construct(){
     
    }
    
    public function index(){
      if(isLoggedIn()){
        redirect('farmers');
      }
      $data = [
        'title' => 'FGD Services',
        'description' => 'Farmer Graphical Directory Services',
        'info' => '',
        'name' => 'Joseph',
        'location' => 'Uganda, Mbale',
        'contact' => '078',
        'mail' => 'fdg@gmail.com'
      ];
     
      $this->view('pages/index', $data);
    }

    public function about(){
      $data = [
        'title' => 'About Us',
        'description' => 'Graphical Directory'
      ];

      $this->view('pages/about', $data);
    }

    public function contact(){
      $data = [
        'title' => 'FGD Services',
        'description' => 'Farmer Graphical Directory Services',
        'info' => '',
        'name' => 'Joseph',
        'location' => 'Uganda, Mbale',
        'contact' => '078',
        'mail' => 'fdg@gmail.com'
      ];

      $this->view('pages/contact', $data);
    }
  }