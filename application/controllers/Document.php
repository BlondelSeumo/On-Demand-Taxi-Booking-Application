<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Document extends CI_Controller {


	public function __construct() {
	parent::__construct();

		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('document_model');
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
		$template['page'] = 'Document/view-verified';
		$template['nav'] = '<li><a href="'.base_url('document').'">Document</a></li><li>View</li>';
		$template['page_title'] = "View Document";
		$template['data'] = $this->document_model->get_verified();
		$this->load->view('template',$template);
	}

	


	public function request() {
		$template['page'] = 'Document/view-request';
		$template['nav'] = '<li><a href="'.base_url('document').'">Document</a></li><li>Request</li>';
		$template['page_title'] = "View Request";
		$template['data'] = $this->document_model->get_request();
		$this->load->view('template',$template);
	}

	public function approval_request() {
		$id = $_POST['id'];
		$data['status'] = "Completed";
		$this->booking_model->update_request($data, $id);
		echo "success";
		//var_dump($template['data']);
	}


public function view_single_verified() {
		$id = $_POST['id'];
		$template['data'] = $this->document_model->get_single_verified($id);
		$this->load->view('Document/view-verified-popup',$template);
	}


public function view_single_request() {
		$id = $_POST['id'];
		$template['data'] = $this->document_model->get_single_request($id);
		$this->load->view('Document/view-request-popup',$template);
	}

public function update_doc_status(){
	$id = $_POST['id'];
	$status = $_POST['status'];
	$rs = $this->db->where('id',$id)->update('driver_document',array('status'=>$status));
	if($rs){
		echo 'success';
	} else {
		echo 'error';
	}
}


public function view_single_approved() {
	//print_r($id);
		$id = $_POST['id'];
		$template['data'] = $this->document_model->get_single_approved($id);
		$this->load->view('Document/view-request-popup',$template);
	}



		
 	
}