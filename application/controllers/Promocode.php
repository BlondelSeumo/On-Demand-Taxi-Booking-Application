<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Promocode extends CI_Controller {
	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('promocode_model');
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

	public function create() {
		$template['page'] = 'Promocode/add-promocode';
		$template['nav'] = '<li><a href="'.base_url('promocode').'">Promocode</a></li><li>Create</li>';
		$template['page_title'] = "Create Promcocode";
		if($_POST) {
			$data = $_POST;
			$code = preg_replace('/\s+/', ' ',$data['code']);
			$data['code'] = preg_replace('/[^a-zA-Z0-9\s]/', '', $code);
			
			
			$result = $this->promocode_model->save_promocode($data);
			if($result == "Exist") {
				$this->session->set_flashdata('message', array('message' => 'Promocode already exist','class' => 'danger'));
			}
			else {	
				$this->session->set_flashdata('message', array('message' => 'Promocode Saved successfully','class' => 'success'));
			}
			redirect(base_url().'promocode');
		}
		$this->load->view('template', $template);
	}


	public function index() {
		$template['page'] = 'Promocode/view-promocode';
		$template['nav'] = '<li><a href="'.base_url('promocode').'">Promocode</a></li><li>View</li>';
		$template['page_title'] = "View Promocode";
		$template['data'] = $this->promocode_model->get_promocode();
		$this->load->view('template',$template);
	}





	public function edit($id=null) {
		if($id==null){
			$this->session->set_flashdata('message', array('message' => 'Invalid information','class' => 'error'));
			redirect(base_url('driver'));
		}
		$template['page'] = 'Promocode/edit-promocode';
		$template['page_title'] = "Edit Promocode";
		$id = $this->uri->segment(3);
		$template['data'] = $this->promocode_model->get_single_promocode($id);
		if($_POST) {
			$data = $_POST;
			unset($data['submit']);
			$notify = '';
			if(isset($data['nofity'])) {
				$notify = $data['nofity'];
				unset($data['notify']);
			}


			$result = $this->promocode_model->update_promocode($data, $id);
			if($result == "Exist") {
				$this->session->set_flashdata('message', array('message' => 'Promocode already exist','class' => 'danger'));
			}

			else {
				$this->session->set_flashdata('message', array('message' => 'Promocode  Updated Successfully','class' => 'success'));
			}
			redirect(base_url().'promocode');
		}
		else {
			$this->load->view('template', $template);
		}
	}


	public function view_single_promocode() {
		$id = $_POST['id'];
		$template['data'] = $this->promocode_model->get_single_promocode($id);
		$this->load->view('Promocode/view-promocode-popup',$template);
	}


	public function delete_promocode() {
		$id = $this->uri->segment(3);
		$result = $this->promocode_model->delete_promocode($id);
		$this->session->set_flashdata('message', array('message' => 'Promocode Deleted Successfully','class' => 'success'));
		redirect(base_url().'promocode');
	}


		


}
