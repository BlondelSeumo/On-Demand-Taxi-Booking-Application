<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Request extends CI_Controller {


	public function __construct() {
	parent::__construct();

		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('request_model');
		if(!$this->session->userdata('logged_in')) {
			redirect(base_url());
		}
		else {
			$menu = $this->session->userdata('admin');
			 if( $menu!=1  ) {
				 $this->session->set_flashdata('message', array('message' => "You don't have permission to access testimonials page.",'class' => 'danger'));
				 redirect(base_url().'dashboard');
			 }
		}
		
 	}
	





public function index() {
		$template['page'] = 'Request/view-request';
		$template['nav'] = '<li><a href="'.base_url('request').'">Request</a></li><li>View</li>';
		$template['page_title'] = "View Request";
		$template['data'] = $this->request_model->get_request();
		$this->load->view('template',$template);
	}

public function view_single_request() {
		$id = $_POST['id'];
		$template['data'] = $this->request_model->get_single_request($id);
		$this->load->view('Request/view-request-popup',$template);
	}

	
 	
}