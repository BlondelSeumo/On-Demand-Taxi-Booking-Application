<?php 
class Driver_model extends CI_Model {
	public function _consruct(){
		parent::_construct();
	}


	function save_driver($data) {
		$email_id = $data['email'];
		$phone = $data['phone'];
		$this->db->where('email', $email_id);
		$this->db->or_where("phone",$phone);
		$this->db->from('driver');
		$password = md5($data['password']);
		$array = array('password'=>$password);
// print_r($password);
// die();
		$count = $this->db->count_all_results();
		if($count > 0) {
			return "Exist";
		}
		else {
			$result = $this->db->insert('driver', $data); 
			$user_id = $this->db->insert_id();
			$this->db->where('id',$user_id)->update('driver',$array);
// $this->db->update('address',array('cust_id'=>$user_id));
			if($result) {
				return "Success";
			}
			else {
				return "Error";
			}
		}
	}


	function get_driver() {
		$where = '(status="0" or status = "1")';
		$query = $this->db->where($where);
		$query = $this->db->order_by("id","desc")->get('driver');
		$result = $query->result();
		return $result;
	}


	function get_single_driver($id) {
		$this->db->where('id', $id);
		$this->db->where('status!=', '2');
		$query = $this->db->get('driver');
		$result = $query->row();
		return $result;
	}	



	function update_driver($data, $id) {
		$phone = $data['phone'];
		$email_id = $data['email'];
		$update_data = array('driver_name'=>$data['driver_name'],
			'car_id'=>$data['car_id'],
			'license_no'   =>$data['license_no'],
			'name'   =>$data['name'],
			'vehicle_reg_no'   =>$data['vehicle_reg_no'],
			'phone'   =>$data['phone'],
			'email'   =>$data['email'],
			'city'   =>$data['city'],
			'state'   =>$data['state'],
			'address'   =>$data['address'],
			'post_code'   =>$data['post_code'],
			'state'   =>$data['state'],
			'status'   =>$data['status'],
			'is_deaf'   =>$data['is_deaf'],
			'is_flash_required'   =>$data['is_flash_required'],
			'driver_type'   =>$data['driver_type'],
			'image'   =>$data['image']
		);
		$this->db->where("id !=",$id);
		$this->db->where("(phone = '$phone' OR email = '$email_id' )");
		$query= $this->db->get('driver');
		if(($data['password'])!=""){
			$password = md5($data['password']);
			$array = array('password'=>$password);
		}
		if($query -> num_rows() >0 ) {
			return "Exist";
		}
		else {
			$this->db->where('id', $id);
			$result = $this->db->update('driver', $update_data);
			if(($data['password'])!=""){
				$this->db->where('id',$id)->update('driver',$array); 
			}
			$array =array('type'=>$data['car_id']);
			$result = $this->db->where('id',$data['car_id'])->update('car', $array);
			return "Success";
		}
	}



	function delete_driver($id) {
		$data = array(  
			'status' => '2'   
		);  
		$this->db->where('id', $id);
		$result =  $this->db->update('driver', $data); 
		if($result) {
			return "Success";
		}
		else {
			return "Error";
		}
	}


	
}	
