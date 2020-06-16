<?php 
class Help_model extends CI_Model {
	public function _consruct(){
		parent::_construct();
	}


	function save_help($data) {
		$name = $data['head'];
		$this->db->where('head', $name);
		$this->db->from('help_table');
		$count = $this->db->count_all_results();
		if($count > 0) {
			return "Exist";
		}
		else {
			$result = $this->db->insert('help_table', $data); 
			if($result) {
				return "Success";
			}
			else {
				return "Error";
			}
		}
	}

	function get_help() {
		$query = $this->db->where('status', '1');
		$query = $this->db->order_by("id","desc")->get('help_table');
		$result = $query->result();
		return $result;
	}

	function get_single_help($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('help_table');
		$result = $query->row();
		return $result;
	}




	function update_help($data, $id) {
		$head = $data['head'];
		$this->db->where("id !=",$id);
		$this->db->where("(head = '$head')");

		$query= $this->db->get('help_table');
		if($query -> num_rows() >0 ) {
			return "Exist";
		}
		else {
			$this->db->where('id', $id);
			$result = $this->db->update('help_table', $data); 
			return "Success";
		}
	}


	function delete_help($id) {
		$data = array(  
			'status' => '0'   
		); 
		$this->db->where('id', $id);
		$result = $this->db->update('help_table',$data); 
		if($result) {
			return "Success";
		}
		else {
			return "Error";
		}
	}

	
}	
