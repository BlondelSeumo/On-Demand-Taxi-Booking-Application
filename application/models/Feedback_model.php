 <?php 

class Feedback_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}

function get_feedback() {
		$query = $this->db->order_by("id","desc")->get('feedback');
		//echo $this->db->last_query();
		$result = $query->result();
		return $result;
	}

function get_single_feedback($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('feedback');
		//echo $this->db->last_query();
		$result = $query->row();
		return $result;
	                     }	




}	