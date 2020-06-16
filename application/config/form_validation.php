<?php
defined('BASEPATH') OR exit('No direct script access allowed');

$config = array(
	'test_get' =>array(
		array('field'=>'id', 'label'=>'required', 'rules'=>'trim | required ')
	),
	
	'signUp' =>array(
		array('field'=>'username', 'label'=>'User name', 'rules'=>'trim|required|min_length[6]|max_length[16]'),
		array('field'=>'email_id', 'label'=>'Email', 'rules'=>'trim|required|valid_email'),
		array('field'=>'password', 'label'=>'Password', 'rules'=>'trim|required'),
		array('field'=>'phone_no', 'label'=>'Phone number', 'rules'=>'trim|numeric|required'),
	),
	
	'signIn' =>array(
		array('field'=>'username', 'label'=>'Username/Email/mobile', 'rules'=>'trim|required'),
		array('field'=>'password', 'label'=>'Password', 'rules'=>'trim|required'),
	),
	
	'joinSalons' =>array(
		array('field'=>'email_id', 'label'=>'Email', 'rules'=>'trim|required|valid_email'),
		array('field'=>'phone_no', 'label'=>'Phone number', 'rules'=>'trim|numeric|required'),
	)
	
	
);
