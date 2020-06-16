 <?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Booking extends CI_Controller {


	public function __construct() {
	parent::__construct();

		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('booking_model');
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



	


 public function view_all() {
 		$template['nav'] = '<li><a href="'.base_url('booking/view_all').'">Booking</a></li><li>All Booking</li>';
		$template['page'] = 'Booking/view-all';
		$template['page_title'] = "View Booking";
		$template['data'] = $this->booking_model->get_all();
		$this->load->view('template',$template);
	}

 public function view_completed() {
 	$template['nav'] = '<li><a href="'.base_url('booking/view_all').'">Booking</a></li><li>Completed</li>';
		$template['page'] = 'Booking/view-completed';
		$template['page_title'] = "View Completed";
		$template['data'] = $this->booking_model->get_completed();
		$this->load->view('template',$template);
	}
public function view_onprocess() {
	$template['nav'] = '<li><a href="'.base_url('booking/view_all').'">Booking</a></li><li>On Process</li>';
		$template['page'] = 'Booking/view-onprocess';
		$template['page_title'] = "View Processing";
		$template['data'] = $this->booking_model->get_onprocess();
		$this->load->view('template',$template);
	}
public function view_cancelled() {
	$template['nav'] = '<li><a href="'.base_url('booking/view_all').'">Booking</a></li><li>Cancelled</li>';
		$template['page'] = 'Booking/view-cancelled';
		$template['page_title'] = "View Cancelled";
		$template['data'] = $this->booking_model->get_cancelled();
		$this->load->view('template',$template);
	}


public function view_single_completed() {
		$id = $_POST['id'];
		//print_r($id);
		$template['data'] = $this->booking_model->get_single_completed($id);
		$this->load->view('Booking/view-completed-popup',$template);
	}


public function view_single_cancelled() {
		$id = $_POST['id'];
		$template['data'] = $this->booking_model->get_single_cancelled($id);
		$this->load->view('Booking/view-cancelled-popup',$template);
	}

public function view_single_onprocess() {
		$id = $_POST['id'];
		$template['data'] = $this->booking_model->get_single_onprocess($id);
		$this->load->view('Booking/view-onprocess-popup',$template);
	}


public function view_single_all() {
		$id = $_POST['id'];
		$template['data'] = $this->booking_model->get_single_all($id);
		$this->load->view('Booking/view-all-popup',$template);
	}
		
 	
}
