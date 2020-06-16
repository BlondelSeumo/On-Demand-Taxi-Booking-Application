<?php 
class Customers_model extends CI_Model {
	public function _consruct(){
		parent::_construct();
	}
	function get_customer() {
		$query = $this->db->order_by("id","desc")->get('customer');
//echo $this->db->last_query();
		$result = $query->result();
		return $result;
	}
	function get_single_customer($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('customer');
		$result = $query->row();
		return $result;
	}	
}
