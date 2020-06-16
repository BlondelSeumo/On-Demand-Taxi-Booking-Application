<?php 

class Login_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}


	function login($username, $password) { 
	  $this->db->where('username', $username);
	  $this->db->where('password', md5($password));	  
	  $query = $this->db->get('admin_users');
	   if ($query->num_rows()>0) {
	  	return $query->row();
	  } else {
		return false; 
	  }
	}
	

}