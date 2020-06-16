<?php 

class Promocode_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}


	function save_promocode($data) {
		
	 $promo = $data['code'];
	
	 $this->db->where('code', $promo);

	 $this->db->from('promocode');
	
	 $count = $this->db->count_all_results();
	 
	 if($count > 0) {
		 return "Exist";
	 }
	 else {
	 //	unset($data['created_user']);
	 $result = $this->db->insert('promocode', $data); 
	 echo $this->db->last_query();die;
	 if($result) {
		 return "Success";
	 }
	 else {
		 return "Error";
	 }
	 }
	}





function get_promocode() {
		$query = $this->db->where('status', '1');
		$query = $this->db->order_by("id","desc")->get('promocode');


		$result = $query->result();

		return $result;
	}

function get_single_promocode($id) {
		$query = $this->db->where('id', $id);
		$query = $this->db->get('promocode');

		$result = $query->row();
		return $result;
	                     }	




function update_promocode($data, $id) {
       $code = $data['code'];
		
	 $this->db->where("id !=",$id);
	 
	 $this->db->where("(code = '$code')");
	 //$this->db->where('username', $username);
	// $this->db->or_where('email_id', $email_id);
	 $query= $this->db->get('promocode');
	 
	   
	    if($query -> num_rows() >0 ) {
	    
		 return "Exist";
	           }
	 else {
	 $this->db->where('id', $id);
	 $result = $this->db->update('promocode', $data); 
	 // $array =array('type'=>$data['car_id']);
	 // $result = $this->db->where('id',$data['car_id'])->update('car', $array);
		 return "Success";
	 
	 }
	}


function delete_promocode($id) {

			$data = array(  
				'status' => '0'   
		  );  
  
		
	 $this->db->where('id', $id);
	$result =  $this->db->update('promocode',$data); 

	 if($result) {
		 return "Success";
	 }
	 else {
		 return "Error";
	 }
	}

}	