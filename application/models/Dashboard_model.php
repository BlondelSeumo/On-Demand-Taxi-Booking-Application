<?php 

class Dashboard_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}
	
	function get_rides_count() {
		
		$menu = $this->session->userdata('admin');
		if($menu!='1'){
			$user = $this->session->userdata('id');
			
		}
		$this->db->where('status',2);
		$this->db->from("booking");
		$result = $this->db->count_all_results();

		return $result;
	}
	
	function get_drivers_count() {
		$result = $this->db->count_all_results('driver');
		return $result;
	}
	
	function get_customers_count() {
		$result = $this->db->count_all_results('customer');
		return $result;
	}
	
	function get_bookings_count() {
		
		
		$menu = $this->session->userdata('admin');
		if($menu!='1'){
			$user = $this->session->userdata('id');
			
		}
		
		$result = $this->db->count_all_results('booking');
		
		return $result;
	}




	function get_running() {
		
		
		$this->db->select('bh.id,bh.booking_id,bh.car_type,bh.time,customer.name,sd.driver_name, bh.book_date,bh.fare,bh.source,bh.destination,bh.status')->order_by('bh.id','desc')->where('bh.status',2);

		$this->db->from('booking as bh');
		$this->db->join('customer', 'bh.user_id = customer.id','left');
		$this->db->join('driver as sd', 'bh.driver_id = sd.id','left');
		$this->db->group_by("bh.id"); 

	
		$menu = $this->session->userdata('admin');
	 	if($menu!='1'){
			$user = $this->session->userdata('id');
			$this->db->where('sd.created_user', $user);
		}

		$query = $this->db->get();
		//echo $this->db->last_query();
		$result = $query->result();
		return $result;
	}
}