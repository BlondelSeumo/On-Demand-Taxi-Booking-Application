<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends CI_Controller {

	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		
		$this->load->model('dashboard_model');
		
		if(!$this->session->userdata('logged_in')) {
			redirect(base_url());
			
		}
 	}
	
	
	public function index() {
		$template['page'] = 'dashboard';
		// $template['nav'] = '<li><a href="'.base_url('driver').'">Driver</a></li><li>Create</li>';
		$template['page_title'] = "Dashboard";
		$template['shops'] = $this->dashboard_model->get_rides_count();
		$template['users'] = $this->dashboard_model->get_drivers_count();
		$template['customers'] = $this->dashboard_model->get_customers_count();
		$template['bookings'] = $this->dashboard_model->get_bookings_count();
		
		$this->load->view('template',$template);
	}


	 public function running() {
	 	$template['nav'] = '<li>Running Rides</li>';
		$template['page'] = 'Dashboard/view-running';
		$template['page_title'] = "View Running";
		$template['data'] = $this->dashboard_model->get_running();
		$this->load->view('template',$template);
	}
	
	
}
