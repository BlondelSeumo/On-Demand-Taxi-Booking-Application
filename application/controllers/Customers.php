<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Customers extends CI_Controller {
	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('customers_model');
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
	public function view_customer() {
		$template['nav'] = '<li>All Customers</li>';
		$template['page'] = 'Customers/view-customer';
		$template['page_title'] = "View Customer";
		$template['data'] = $this->customers_model->get_customer();
		$this->load->view('template',$template);
	}
	public function view_single_customer() {
		$id = $_POST['id'];
		$template['data'] = $this->customers_model->get_single_customer($id);
		$this->load->view('Customers/view-customer-popup',$template);
	}
}	
