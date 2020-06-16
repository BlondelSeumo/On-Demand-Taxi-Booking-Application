<?php

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	class Model_users extends My_model{
		
		protected $_table = "";
		protected $primary_key = "id";
		protected $return_type = "array";
		
		protected $after_get = array( "remove_sensitive_data" );
		
		protected function remove_sensitive_data($user){
			
			unset( $user['password'] );
			
			return $user;
			
		}
		
	}
	
?>