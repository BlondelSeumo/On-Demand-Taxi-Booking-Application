<?php


function get_car_type($id){
	$CI = & get_instance();
	$rs = $CI->db->where('id',$id)->get('car_type')->row();
//echo $CI->db->last_query();
	if(count($rs)>0){
		return $rs->name;
	}else{
		return null;
	}
}



function get_likes($id){
	$CI = & get_instance();
	$rs = $CI->db->select('COUNT(*) AS likes')->where('help_id',$id)->where('help_status','1')->get('help_review');
	return $rs->row()->likes;
}


function get_dislikes($id){
	$CI = & get_instance();
	$rs = $CI->db->select('COUNT(*) AS dislikes')->where('help_id',$id)->where('help_status','0')->get('help_review');
	return $rs->row()->dislikes;
}


function get_bookings($id){
	$CI = & get_instance();
	$rs = $CI->db->select('COUNT(*) AS bookings')->where('user_id',$id)->get('booking');
	return $rs->row()->bookings;
}


function get_driver_name($id){
	$CI = & get_instance();
	$rs = $CI->db->select('driver_name')->where('id',$id)->get('driver')->row();
//echo $CI->db->last_query();
//print_r($rs);
	return $rs->driver_name;
}


function get_custname($id){
	$CI = & get_instance();
	$rs = $CI->db->select('name')->where('id',$id)->get('customer')->row();
	return $rs->name;
}


function get_carname($id){
	$CI = & get_instance();
	$rs = $CI->db->select('name')->where('id',$id)->get('car_type')->row();
	return $rs->name;
}


function document_list($id=null){


	$document = array('1'=>'Driver Licence',
		'2'=>'Police Clearance Certificate',
		'3'=>'Fitness Certificate',
		'4'=>'Vehicle Registration',
		'5'=>'Vehicle Permit',
		'6'=>'Commercial Insurance',
		'7'=>'Tax Receipt',
		'8'=>'Pass Book',
		'9'=>'Driver Licence with Badge Number',
		'10'=>'Background Check with Consent Form',
		'11'=>'PAN Card',
		'12'=>'No Objection Certification'
	);
	return $document[$id];
}


function get_doc_name($id){

	$CI = & get_instance();
// $rs = $CI->db->where('help_id',$id)->get('help_review')->row();
//$rs = $CI->db->select('('*') AS likes')->where('id',$id)->where('type','1')->get('help_review');\
	$rs = $CI->db->select('type')->where('id',$id)->where('status','2')->get('driver_document')->row();
	if($rs->type == '0'){
		return   "Driver Licence";
	}elseif($rs->type == '1'){
		return   "Police Clearance Certificate";
	}elseif($rs->type == '2'){
		return   "Fitness Certificate";
	}elseif($rs->type == '3'){
		return  "Vehicle Registration";
	}elseif($rs->type == '4'){
		return  "Vehicle Permit";
	}elseif($rs->type == '5'){
		return   "Commercial Insurance";
	}elseif($rs->type =='6'){
		return  "Tax Receipt";
	}elseif($rs->type == '7'){
		return  "Pass Book";
	}elseif($rs->type == '8'){
		return   "Driver Licence with Badge Number";
	}elseif($rs->type =='9'){
		return  "Background Check with Consent Form";
	}elseif($rs->type == '10'){
		return  "PAN Card";
	}elseif($rs->type == '11'){
		return  "No Objection Certification";
	}
//return $rs->type;
}


function get_document_name($id){
	if($id == '1'){
		return   "Driver Licence";
	}elseif($id == '2'){
		return   "Police Clearance Certificate";
	}elseif($id == '3'){
		return   "Fitness Certificate";
	}elseif($id == '4'){
		return  "Vehicle Registration";
	}elseif($id == '5'){
		return  "Vehicle Permit";
	}elseif($id == '6'){
		return   "Commercial Insurance";
	}elseif($id =='7'){
		return  "Tax Receipt";
	}elseif($id == '8'){
		return  "Pass Book";
	}elseif($id == '9'){
		return   "Driver Licence with Badge Number";
	}elseif($id =='10'){
		return  "Background Check with Consent Form";
	}elseif($id == '11'){
		return  "PAN Card";
	}elseif($id == '12'){
		return  "No Objection Certification";
	}
}


function get_document($id){
	$CI = & get_instance();
	$rs = $CI->db->select('type')->where('id',$id)->where('status!=','2')->get('driver_document')->row();
	if($rs->type == '0'){
		return   "Driver Licence";
	}elseif($rs->type == '1'){
		return   "Police Clearance Certificate";
	}elseif($rs->type == '2'){
		return   "Fitness Certificate";
	}elseif($rs->type == '3'){
		return  "Vehicle Registration";
	}elseif($rs->type == '4'){
		return  "Vehicle Permit";
	}elseif($rs->type == '5'){
		return   "Commercial Insurance";
	}elseif($rs->type =='6'){
		return  "Tax Receipt";
	}elseif($rs->type == '7'){
		return  "Pass Book";
	}elseif($rs->type == '8'){
		return   "Driver Licence with Badge Number";
	}elseif($rs->type =='9'){
		return  "Background Check with Consent Form";
	}elseif($rs->type == '10'){
		return  "PAN Card";
	}elseif($rs->type == '11'){
		return  "No Objection Certification";
	}
//return $rs->type;
}


function get_source($id){
	$CI = & get_instance();
	$rs = $CI->db->select('source')->where('id',$id)->get('booking')->row();
	return $rs->source;
}


function get_destination($id){
	$CI = & get_instance();
	$rs = $CI->db->select('destination')->where('id',$id)->get('booking')->row();
//echo $CI->db->last_query();
	return $rs->destination;
}




function get_driverrating($id){
	$CI = & get_instance();
	$rs = $CI->db->select('AVG(rating) AS rating')->where('rating >',0)->where('driver_id',$id)->get('feedback')->row();
	$rate = ROUND($rs->rating,2);
	return $rate;
}


function get_status($status){
	$array = array('1'=>'Booking',
		'2'=>'Processing',
		'3'=>'Completed',
		'0'=>'Cancelled');
	return $array[$status];
}


function get_color($status){
	$array = array('1'=>'info',
		'2'=>'warning',
		'3'=>'success',
		'0'=>'danger');
	return $array[$status];
}


function get_reqstatus($status){
	$array = array('1'=>'Assigned',
		'2'=>'Failed',
		'3'=>'Cancelled',
		'0'=>'Pending');
	return $array[$status];
}


function get_reqcolor($status){
	$array = array('1'=>'success',
		'2'=>'danger',
		'3'=>'warning',
		'0'=>'info');
	return $array[$status];
}


function get_documentstatus($status){
	$array = array('1'=>'Pending ',
		'2'=>'Approved',
		'3'=>'Rejected'
	);
	return $array[$status];
}


function get_doccolor($status){
	$array = array('1'=>'info',
		'2'=>'success',
		'3'=>'danger'
	);
	return $array[$status];
}




function get_deaf_status($id){
	$CI = & get_instance();
	$rs = $CI->db->select('is_deaf')->where('id',$id)->get('driver')->row();
	if($rs->is_deaf == '1'){
		return   "Yes";
	}else{
		return "No";
	}
}


function get_flash($id){
	$CI = & get_instance();
	$rs = $CI->db->select(' 	is_flash_required ')->where('id',$id)->get('driver')->row();
	if($rs->is_flash_required  == '1'){
		return   "Yes";
	}else{
		return "No";
	}
}


function get_drivertype($id){
	$CI = & get_instance();
	$rs = $CI->db->select('driver_type')->where('id',$id)->get('driver')->row();
	if($rs->driver_type == '0'){
		return   "Driver Cum Owner";
	}else{
		return "Non - Driving Partner";
	}
}


function get_patternname($id){
	$CI = & get_instance();
	$rs = $CI->db->select('pattern_name')->where('id',$id)->get('pattern')->row();
	return $rs->pattern_name;
}


function get_cars(){
	$CI = & get_instance();
	$rs = $CI->db->select('name')->get('car_type')->row();
//echo $CI->db->last_query();
	return $rs->name;
}


function get_booking_id($id){
	$CI = & get_instance();
	$rs = $CI->db->select('booking_id')->where('id',$id)->get('booking')->row();
	return $rs->booking_id;
}


function get_drivers(){
	$CI = & get_instance();
	$rs = $CI->db->select('count(*) AS number')->where('status',1)->get('driver')->row();
	return $rs->number;
}


function get_profile_image(){
	$CI = & get_instance();
	$rs = $CI->db->select('logo')->get('settings')->row();
	if(count($rs)>0){
		return $rs->logo;
	} else {
		return base_url('assets/images/image.png');
	}
}


function set_log($class,$method,$postdata,$auth){
	$CI = & get_instance();
	$url = $class.'/'.$method;
	$data = array('url'=>$url,
		'parameter'=>$postdata,
		'auth'=>$auth,
		'time'=>date('Y-m-d h:i:s'));
	$CI->db->insert('service_log',$data);
	return $CI->db->insert_id();
}


function get_key(){
	$CI = & get_instance();
	$rs = $CI->db->select('key')->where('id','1')->get('settings');
	return $rs->row()->key;
}

function get_app_name(){
	$CI = & get_instance();
	$rs = $CI->db->select('title')->where('id',1)->get('settings')->row();
	return $rs->title;
}

function get_app_pic(){
	$CI = & get_instance();
	$rs = $CI->db->select('logo')->where('id',1)->get('settings')->row();
	return $rs->logo;
}


function convertToHoursMins($time, $format = '%02d:%02d') {
	if ($time < 1) {
		return;
	}
	$hours = floor($time / 60);
	$minutes = ($time % 60);
	return sprintf($format, $hours, $minutes);
}


?>
