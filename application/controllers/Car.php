<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class Car extends CI_Controller {
	public function __construct() {
		parent::__construct();
		date_default_timezone_set("Asia/Kolkata");
		$this->load->model('car_model');
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
		$template['page'] = 'Car/add-car';
		$template['nav'] = '<li><a href="'.base_url('car').'">Car</a></li><li>Create</li>';
		$template['page_title'] = "Create Car";
		$template['car_type'] = $this->db->where('status','1')->get('car_type')->result();
		if($_POST) {
			$data = $_POST;
			$name = preg_replace('/\s+/', ' ',$data['name']);
			$data['name'] = preg_replace('/[^a-zA-Z0-9\s]/', '', $name);
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
				$result = $this->car_model->save_car($data);
				if($result == "Exist") {
					$message .= 'Car already exist';
				} else {	
					$this->session->set_flashdata('message', array('message' => 'Car Saved successfully','class' => 'success'));
				}
			}
			if($message!=''){
				$this->session->set_flashdata('message', array('message' => $message,'class' => 'danger'));
			}
			redirect(base_url().'car');
		} else {
			$this->load->view('template', $template);
		}
	}



	public function index() {
		$template['page'] = 'Car/view-car';
		$template['nav'] = '<li><a href="'.base_url('car').'">Car</a></li><li>View</li>';
		$template['page_title'] = "View Car";
		$template['data'] = $this->car_model->get_car();
		$this->load->view('template',$template);
	}


	public function edit($id=null) {
		if($id==null){
			$this->session->set_flashdata('message', array('message' => 'Invalid information','class' => 'error'));
			redirect(base_url('car'));
		}
		$template['page'] = 'Car/edit-car';
		$template['page_title'] = "Edit Car";
		$template['car_type'] = $this->db->where('status','1')->get('car_type')->result();
		$id = $this->uri->segment(3);
		$template['data'] = $this->car_model->get_single_car($id);
		if(empty($template['data'])){
			redirect(base_url('car'));
		}
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
			$result = $this->car_model->update_car($data, $id);
			if($result == "Exist") {
				$this->session->set_flashdata('message', array('message' => 'Car already exist','class' => 'danger'));
			}
// else if($result == "Already Exist") {
// 	$this->session->set_flashdata('message', array('message' => 'Email id already exist','class' => 'danger'));
//    }
			else {
				$this->session->set_flashdata('message', array('message' => 'Car  Updated Successfully','class' => 'success'));
			}
			redirect(base_url().'car');
		}
		else {
			$this->load->view('template', $template);
		}
	}


	public function view_single_car() {
		$id = $_POST['id'];
		$template['data'] = $this->car_model->get_single_car($id);
		$this->load->view('Car/view-car-popup',$template);
	}


	public function delete_car() {
		$id = $this->uri->segment(3);
		$result = $this->car_model->delete_car($id);
		$this->session->set_flashdata('message', array('message' => 'Car Deleted Successfully','class' => 'success'));
		redirect(base_url().'car');
	}


	private function set_upload_options() {   
//upload an image options
		$config = array();
		$config['upload_path'] = 'assets/uploads/cars';
		$config['allowed_types'] = 'gif|jpg|png|jpeg|mp3|mp4';
		$config['max_size']      = '50000';
		$config['overwrite']     = FALSE;
		return $config;
	}

	
}
