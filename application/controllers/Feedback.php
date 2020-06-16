<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Feedback extends CI_Controller {


	public function __construct() {
	parent::__construct();

		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('feedback_model');
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
		$template['page'] = 'Feedback/view-feedback';
		$template['nav'] = '<li><a href="'.base_url('feedback').'">Feedback</a>';
		$template['page_title'] = "View Feedback";
		$template['data'] = $this->feedback_model->get_feedback();
		$this->load->view('template',$template);
	}

public function view_single_feedback() {
		$id = $_POST['id'];
		$template['data'] = $this->feedback_model->get_single_feedback($id);
		$this->load->view('Feedback/view-feedback-popup',$template);
	}




		
 	
}