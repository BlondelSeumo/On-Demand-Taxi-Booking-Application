<?php

error_reporting(0);
defined('BASEPATH') OR exit('No direct script access allowed');

class Settings extends CI_Controller {


	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('settings_model');
		if(!$this->session->userdata('logged_in')) {
			redirect(base_url());
		}
		else {
			$profile = $this->router->fetch_method();
			if($profile != 'profile') {
			$menu = $this->session->userdata('admin');
			 if( $menu!=1  ) {
				 $this->session->set_flashdata('message', array('message' => "You don't have permission to access user page.",'class' => 'danger'));
				 redirect(base_url().'dashboard');
			 }
			}
		}
 	}
	
    public function index() {
		$template['page'] = 'Settings/settings_info';
		$template['nav'] = '<li><a href="'.base_url('settings').'">Settings</a></li><li>View</li>';
		$template['page_title'] = "Settings Info";
		
		if($_POST){
			 $data=$this->input->post();
		    if(isset($_FILES['logo'])) {  
		        
			    $config = $this->set_upload_options();
			    $path = $_FILES['logo']['name'];
                $ext = pathinfo($path, PATHINFO_EXTENSION);
                $new_name = time().".".$ext;
                $config['file_name'] = $new_name;
                $this->upload->initialize($config);

			if(!$this->upload->do_upload('logo')) {
					unset($data['logo']);
			}else {
					$upload_data = $this->upload->data();
					$data['logo'] = $config['upload_path']."/".$upload_data['file_name'];
			}
			}
			
			if(isset($_FILES['favicon'])) {  
			    $config = $this->set_upload_faviconoptions();
			    $path = $_FILES['favicon']['name'];
                $ext = pathinfo($path, PATHINFO_EXTENSION);
                $new_name = time().".".$ext;
                $config['file_name'] = $new_name;
                $this->upload->initialize($config);

			if(!$this->upload->do_upload('favicon')) {
					unset($data['favicon']);
			}else{
					$upload_data = $this->upload->data();
					$data['favicon'] = $config['upload_path']."/".$upload_data['file_name'];
				}
			}

			$result =$this->settings_model->settings_info($data);
			if($result) {
			    array_walk($data, "remove_html");
				$this->session->set_flashdata('message',array('message' => 'Data  Updated Successfully','class' => 'success'));
			}else {
				
				$this->session->set_flashdata('message', array('message' => 'Error Occured','class' => 'error'));  
			}
		}
		$template['result'] = $this->settings_model->get_info(); 
		$this->load->view('template',$template);
			
		   
	}
	//upload an image options
  private function set_upload_options() {   
    
    $config = array();
    $config['upload_path'] = 'assets/uploads/settings/';
    $config['allowed_types'] = 'gif|jpg|png';
    $config['max_size']      = '5000';
    $config['overwrite']     = FALSE;
  
    return $config;
  }
  private function set_upload_faviconoptions() {   
	//upload an image options
	$config = array();
	$config['upload_path'] ='assets/uploads/settings/';
	$config['allowed_types'] = 'ico';
	$config['max_size']      = '0';
	$config['overwrite']     = FALSE;

	return $config;
} 
}	

