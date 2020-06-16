 <?php 

class Booking_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}

 	function get_all() {
		
		
		$this->db->select('bh.id,bh.booking_id,bh.pattern_id,bh.car_type,customer.name,sd.driver_name, bh.book_date,bh.fare,bh.source,bh.destination,bh.status');

		$this->db->from('booking as bh');
		$this->db->join('customer', 'bh.user_id = customer.id','left');
		$this->db->join('driver as sd', 'bh.driver_id = sd.id','left');
		$this->db->group_by("bh.id"); 
		$this->db->order_by('bh.id','DESC');

	
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



	function get_completed() {
		
		
		$this->db->select('bh.id,bh.booking_id,bh.car_type,customer.name,sd.driver_name, bh.book_date,bh.fare,bh.source,bh.destination,bh.status')->where('bh.status','3')->order_by('bh.id','desc');

		$this->db->from('booking as bh');
		$this->db->join('customer', 'bh.user_id = customer.id','left');
		$this->db->join('driver as sd', 'bh.driver_id = sd.id','left');
		$this->db->group_by("bh.id"); 
		$this->db->order_by("bh.id","desc");

	
		$menu = $this->session->userdata('admin');
	 	if($menu!='1'){
			$user = $this->session->userdata('id');
			$this->db->where('sd.created_user', $user);
		}

		$query = $this->db->get();
		//echo $this->db->last_query();
		$result = $query->result();
		//print_r($result);
		return $result;
	}



	function get_onprocess() {
		
		
		$this->db->select('bh.id,bh.booking_id,bh.car_type,customer.name,sd.driver_name, bh.book_date,bh.fare,bh.source,bh.destination,bh.status')->where('bh.status','2')->order_by('bh.id','desc');

		$this->db->from('booking as bh');
		$this->db->join('customer', 'bh.user_id = customer.id','left');
		$this->db->join('driver as sd', 'bh.driver_id = sd.id','left');
		$this->db->group_by("bh.id");
		$this->db->order_by("bh.id","desc"); 

	
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



	function get_cancelled() {
		
		
		$this->db->select('bh.id,bh.booking_id,bh.car_type,customer.name,sd.driver_name, bh.book_date,bh.fare,bh.source,bh.destination,bh.status')->where('bh.status','0')->order_by('bh.id','desc');

		$this->db->from('booking as bh');
		$this->db->join('customer', 'bh.user_id = customer.id','left');
		$this->db->join('driver as sd', 'bh.driver_id = sd.id','left');
		$this->db->group_by("bh.id"); 
		$this->db->order_by("bh.id","desc");
	
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



function get_single_completed($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('booking');
		$result = $query->row();
		return $result;
	}	



function get_single_cancelled($id) {
	//print_r($id);
		$query = $this->db->where('id', $id);
		$query = $this->db->get('booking');
		$result = $query->row();
		return $result;
	}	


function get_single_onprocess($id) {
	//print_r($id);
		$query = $this->db->where('id', $id);
		$query = $this->db->get('booking');
		$result = $query->row();
		return $result;
	}	


function get_single_all($id) {
	//print_r($id);
		$query = $this->db->where('id', $id);
		$query = $this->db->get('booking');
		//$query = $this->db->order_by('id',desc);
		//echo $this->db->last_query();
		$result = $query->row();
		return $result;
	}	



}	