<?php 

class Settings_model extends CI_Model {
	
	public function _consruct(){
		parent::_construct();
 	}
	
	function settings_info($data){

        $this->db->where('id',1);
        $query = $this->db->update('settings',$data);

		return $query; 
	}
	function get_info(){
        
        $this->db->select('settings.*,keys.id,keys.security_key');
        $this->db->from('settings');
        $this->db->join('keys', '1=1','left');
		$query = $this->db->get();
		$result = $query->row();
		return $result;  
	 }	
}









