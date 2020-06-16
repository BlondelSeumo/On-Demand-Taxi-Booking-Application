<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Help extends CI_Controller {
	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('help_model');
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
		$message = '';
		$template['page'] = 'Help/add-help';
		$template['nav'] = '<li><a href="'.base_url('help').'">Help</a></li><li>Create</li>';
		$template['page_title'] = "Create Help";
		$template['car_type'] = $this->db->where('status','1')->get('car_type')->result();
		if($_POST) {
			$data = $_POST;
			$name = preg_replace('/\s+/', ' ',$data['head']);
			$data['head'] = preg_replace('/[^a-zA-Z0-9\s]/', '', $name);
			unset($data['submit']);
			$config = $this->set_upload_options();
			$this->load->library('upload');
			$this->upload->initialize($config);
			if ( ! $this->upload->do_upload('image')) {
				$message = $this->upload->display_errors().'Error Occured While Uploading Files. ';
			}
			else {
				$upload_data = $this->upload->data();
				$data['image'] = $config['upload_path']."/".$upload_data['file_name'];
				$result = $this->help_model->save_help($data);
				if($result == "Exist") {
					$message .= 'Help already exist';
				} else {	
					$this->session->set_flashdata('message', array('message' => 'Help Saved successfully','class' => 'success'));
				}
			}
			if($message!=''){
				$this->session->set_flashdata('message', array('message' => $message,'class' => 'danger'));
			}
			redirect(base_url().'help');
		} else {
			$this->load->view('template', $template);
		}
	}

	public function index() {
		$template['page'] = 'Help/view-help';
		$template['nav'] = '<li><a href="'.base_url('help').'">Help</a></li><li>View</li>';
		$template['page_title'] = "View Help";
		$template['data'] = $this->help_model->get_help();
		$this->load->view('template',$template);
	}

	public function edit($id=null) {
		if($id==null){
			$this->session->set_flashdata('message', array('message' => 'Invalid information','class' => 'error'));
			redirect(base_url('driver'));
		}
		$template['page'] = 'Help/edit-help';
		$template['page_title'] = "Edit Help";
		$id = $this->uri->segment(3);
		$template['data'] = $this->help_model->get_single_help($id);
		if($_POST) {
			$data = $_POST;
			unset($data['submit']);
			$notify = '';
			if(isset($data['nofity'])) {
				$notify = $data['nofity'];
				unset($data['notify']);
			}
			if(isset($_FILES['image'])) {
				$config = $this->set_upload_options();
				$this->load->library('upload');
				$this->upload->initialize($config);
				if ( ! $this->upload->do_upload('image')) {
					$message = $this->upload->display_errors().'Error Occured While Uploading Files. ';
				}
				else {
					$upload_data = $this->upload->data();
					$data['image'] = $config['upload_path']."/".$upload_data['file_name'];
				}
			}

			$result = $this->help_model->update_help($data, $id);
			if($result == "Exist") {
				$this->session->set_flashdata('message', array('message' => 'Help already exist','class' => 'danger'));
			}
// else if($result == "Already Exist") {
// 	$this->session->set_flashdata('message', array('message' => 'Email id already exist','class' => 'danger'));
//    }
			else {
				$this->session->set_flashdata('message', array('message' => 'Help Details Updated Successfully','class' => 'success'));
			}
			redirect(base_url().'help');
		}
		else {
			$this->load->view('template', $template);
		}
	}


	public function view_single_help() {
		$id = $_POST['id'];
//print_r($id);
		$template['data'] = $this->help_model->get_single_help($id);
	
		$this->load->view('Help/view-help-popup',$template);
	}


	public function delete_help() {
		$id = $this->uri->segment(3);
		$result = $this->help_model->delete_help($id);
		$this->session->set_flashdata('message', array('message' => 'Help Deleted Successfully','class' => 'success'));
		redirect(base_url().'help');
	}


	private function set_upload_options() {   
//upload an image options
		$config = array();
		$config['upload_path'] = 'assets/uploads/help';
		$config['allowed_types'] = 'gif|jpg|png|jpeg';
		$config['max_size']      = '5000';
		$config['overwrite']     = FALSE;
		return $config;
	}		
}
