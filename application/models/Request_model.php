 <?php 

class Request_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}

function get_request() {
		$query = $this->db->order_by("id","desc")->get('request');
		$result = $query->result();
		return $result;
	}

function get_single_request($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('request');
		$result = $query->row();
		return $result;
	                     }	



}	