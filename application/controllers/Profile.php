<?php

error_reporting(0);
defined('BASEPATH') OR exit('No direct script access allowed');

class Profile extends CI_Controller {


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
		$template['page'] = 'Profile/profile';
		$template['nav'] = '<li><a href="'.base_url('profile').'">Profile</a></li><li>View</li>';
		$template['page_title'] = "Profile Info";

		
		
		if($_POST){
			 $data=$this->input->post();

			 $user = $this->session->userdata('logged_in');

			 $rs = $this->db->where('id',$user['id'])->where('password',md5($data['curr_password']))->get('admin_users')->row();




			 if(count($rs)>0){
			 	if($data['new_password']==$data['confirm_password']){
			 		$this->db->where('id',$user['id'])->update('admin_users',array('password'=>md5($data['confirm_password'])));
			 		$this->session->set_flashdata('message',array('message' => 'Password  Updated Successfully','class' => 'success'));
			 	} else {
			 		$this->session->set_flashdata('message', array('message' => 'Password mismatch','class' => 'error'));
			 	}
			 } else {
			 	$this->session->set_flashdata('message', array('message' => 'Current Password Wrong','class' => 'error'));
			 }
		    

			
		}
		
		$this->load->view('template',$template);
			
		   
	}
	//upload an image options
  
}	

