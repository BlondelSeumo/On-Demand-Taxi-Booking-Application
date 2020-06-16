 <?php

 defined('BASEPATH')OR exit('No direct script access allowed');
 header('Content-Type: text/html; charset=utf-8');

 // Allow from any origin
 if (isset($_SERVER['HTTP_ORIGIN'])) {
       header("Access-Control-Allow-Origin: {$_SERVER['HTTP_ORIGIN']}");
       header('Access-Control-Allow-Credentials: true');
       header('Access-Control-Max-Age: 86400'); // cache for 1 day
 }

 // Access-Control headers are received during OPTIONS requests
 if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {

       if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
             header("Access-Control-Allow-Methods: GET, POST, OPTIONS");

       if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
             header("Access-Control-Allow-Headers: {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");

       exit(0);
 }

 class Webservices extends CI_Controller { 
       public function __construct() {
             parent::__construct();
             $this->load->model('Webservice_model');
             $class = $this->router->fetch_class();
             $method = $this->router->fetch_method();
             if ($this->input->server('REQUEST_METHOD') == 'GET')
                   $postdata = json_encode($_GET);
             else if ($this->input->server('REQUEST_METHOD') == 'POST')
                   $postdata = file_get_contents("php://input");

             $auth = '';

             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
             }

             $this->last_id = set_log($class, $method, $postdata, $auth);

       }

            public function user_registration() {

             header('Content-type: application/json');
             $postdata = file_get_contents("php://input");
             $request = json_decode($postdata, true);

             $result = $this->Webservice_model->user_reg($request);
             header('Content-type: application/json');

             $otp = rand(1111, 9999);

             if ($result['status'] == 'success') {
                   $result = array('status' => "success", 'data' => array('auth_token' => $result['auth_token'], 'user_id' => $result['user_id'], 'user_id' => $result['user_id'], 'phone' => $result['phone'],'email' => $result['email'],'profile_photo' => $result['image']));
             } else {
                   $result = array('status' => 'error', 'message' => $result['message'], 'error' => '501');
             }

             $this->response($result);

       }

       function response($res) {
           //  $this->db->where('id', $this->last_id)->update('service_log', array('result' => $res));
             print json_encode($res);
       }

       public function do_login() {

             header('Content-type: application/json');
             $postdata = file_get_contents("php://input");
             $request = json_decode($postdata, true);
             //print_r($request);exit;
             $result = $this->Webservice_model->login($request);
             header('Content-type: application/json');
             if ($result) {
                   $result = array('status' => 'success', 'data' => $result);
             } else {
                   $result = array('status' => 'error', 'message' => 'Unknown Credential! Try Again', 'error' => '502');
             }

             $this->response($result);

       }

       public function trip_list() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $request['auth'] = $auth;
                   //print_r($auth);
                   $result = $this->Webservice_model->trip($request);
                   if ($result) {

                         print json_encode(array('status' => 'success', 'data' => $result));
                   } else {
                         print json_encode(array('status' => 'success', 'data' => []));
                   }

             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));
             }

       }

       public function trip_details($id = null) {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $request = $_GET;

                   if (isset($request['id'])) {
                         $result = $this->Webservice_model->tripdetails($request);
                         if (count($result) > 0) {

                               $rate = $this->Webservice_model->driver_rate($request);
                               $result->rating = $rate;
                               print json_encode(array('status' => 'success', 'data' => $result));

                         } else {
                               print json_encode(array('status' => 'error'));
                         }
                   } else {
                         print json_encode(array('status' => 'error'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '505'));

             }

       }

       public function user_details() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->details($request);
                   if ($result) {

                         print json_encode(array('status' => 'success', 'data' => $result));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '505'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '505'));
             }

       }

       public function edit_user() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = $_POST['profile_update'];
                    
                   $request = $_POST;
                   $request['auth'] = $auth;

                   $email = $request['email'];
                   $phone = $request['phone'];
                   $this->db->where('email', $email);
                   $this->db->or_where("phone", $phone);

                   $this->db->from('customer');
                   $count = $this->db->count_all_results();

                   if ($count == 0) {

                         $result = $this->Webservice_model->edit($request);
                         if ($result) {
                               print json_encode(array('status' => 'success'));
                         } else {
                               print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '508'));
                         }
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Email Already Exists', 'error' => '508'));
                   }

             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '508'));
             }

       }

       public function save_location() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->save($request);
                   if ($result) {
                         print json_encode(array('status' => 'success'));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Unable to save details', 'code' => '506'));
                   }

             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Unable to save details', 'code' => '506'));
             }

       }

       public function saved_location() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->location_details($request);
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => $result));

                   } else {
                         print json_encode(array('status' => "success", 'data' => array('home' => 'null', 'work' => 'null', 'home_latitude' => 'null', 'home_longitude' => 'null', 'work_latitude' => 'null', 'work_longitude' => 'null')));

                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '507'));
             }

       }

       public function request_ride() {
             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {

                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $auth = apache_request_headers()['Auth'];
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->ride($request);
                   header('Content-type: application/json');
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => $result));

                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '507'));

                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '508'));
             }

       }

       public function request_status() {

             header('Content-type: application/json');

             $request = $_GET;
             if (isset($request['id'])) {
                   $result = $this->Webservice_model->req_status($request);
                   header('Content-type: application/json');
                   if ($result->request_status == '0') {
                         print json_encode(array('status' => 'success', 'data' => array('request_status' => '0')));
                   }
                   elseif($result->request_status == '1') {
                         print json_encode(array('status' => 'success', 'data' => $result));
                   }
                   elseif($result->request_status == '2') {
                         print json_encode(array('status' => 'success', 'data' => array('request_status' => '2')));
                   }
                   else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '606'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '606'));

             }
       }

       public function cars() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];

                   $request = $_GET;
                   $request['auth'] = $auth;

                   //print_r($request);
                   $result = $this->Webservice_model->types_car($request);
                   if ($result) {
                         $new_result = array('status' => 'success', 'data' => $result);
                   } else {
                         // $new_result = array('status' => 'error','message'=>'Something Went wrong','error'=>'601');
                         $new_result = array('status' => 'success', 'data' => []);
                   }
             } else {
                   $new_result = array('status' => 'error', 'message' => 'You are not authorized to access the server', 'error' => 'Authentication Failed');
             }

             print json_encode($new_result, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

       }

       public function tripfeedback() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->trip_feedback($request);
                   if ($result) {
                         print json_encode(array('status' => 'success'));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '601'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'You are not authorized to access the server', 'error' => 'Authentication Failed'));
             }

       }

       public function driver_rating() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->driver_feedback($request);
                   if ($result) {
                         print json_encode(array('status' => 'success'));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
             }

       }

       public function trip_completiondetails() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $request = $_GET;
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->trip_completion($request);
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => $result));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
             }

       }

       public function trip_cancellation() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true); ;
                   $request['auth'] = $auth;
                   if (isset($request['trip_id'])) {
                         $result = $this->Webservice_model->cancel_trip($request);
                         if ($result) {
                               print json_encode(array('status' => 'success'));
                         } else {
                               print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '1602'));
                         }
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '608'));

                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
             }

       }

       public function request_cancelled() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true); ;
                   $request['auth'] = $auth;
                   if (isset($request['request_id'])) {
                         $result = $this->Webservice_model->cancel_request($request);
                         if ($result) {
                               print json_encode(array('status' => 'success'));
                         } else {
                               print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
                         }
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));

                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '602'));
             }

       }

       public function mobile_number_availability() {

             header('Content-type: application/json');
             $postdata = file_get_contents("php://input");
             $request = json_decode($postdata, true);
             $request['auth'] = $auth;
             if (isset($request['phone'])) {
                   $result = $this->Webservice_model->mobile_availability($request);
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => array('phone' => $request['phone'], 'is_available' => filter_var($result['is_available'], FILTER_VALIDATE_BOOLEAN))));
                   } else {
                         print json_encode(array('status' => 'success', 'data' => array('phone' => $request['phone'], 'is_available' => filter_var(true, FILTER_VALIDATE_BOOLEAN))));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Mobile Number is missing. Please try again', 'error' => 'phone is missing'));

             }

       }

       public function car_availability() {

             header('Content-type: application/json');
             $request = $_GET;

             $result = $this->Webservice_model->available_cars($request);

             if ($result) {
                   print json_encode(array('status' => 'success', 'data' => $result));
             } else {
                   $rs = $this->Webservice_model->car_fare($request);
                   $result = array('cars_available' => 'No Cars Available',
                               'min_fare' => $rs->min_fare,
                               'eta_time' => '0 Min',
                               'max_size' => $rs->max_seat);
                   print json_encode(array('status' => 'success', 'data' => $result));
             }
       }

       public function fare_calculate() {

             header('Content-type: application/json');
             //echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
             // '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';

             $request = $_GET;

             $pattern = 0;

             $pattern = $this->Webservice_model->get_pattern($request['car_type']);

            //print_r($pattern);
             //die();


             if (!empty($pattern)) {

                   foreach($pattern as $rs) {

                         if ($rs->lat == '' || $rs->lng == '') {
                               continue;
                         }

                         $check_in_range = $this->Webservice_model->check_in_range($rs, $request);
					   
                         if ($check_in_range) {
                               $pattern_id = $rs->id;
                               break;
                         }
                   }

                   if ($pattern_id != 0) {

                         $distance = $request['distance'] / 1000;
                         $time = $request['time'] / 60;
                         $max_time = $time + 10;
                         $booking_time = strtotime('Y-m-d H:i:s');

                         /************************************/

                         //get which pattern used


                         $rs = $this->Webservice_model->cal_equation($pattern_id);
					 
					 

                         /***********************************/
                         if ($rs) {
                               $fare_cal = $rs->base_price + ($rs->km_rate * $distance);
                               $least_min = $time * $rs->min_rate;
                               $max_min = $max_time * $rs->min_rate;

                               $min_fare = $fare_cal + $least_min;
                               $max_fare = $fare_cal + $max_min;

                               $extra_cost = 0;

                               if (!empty($rs->extra)) {

                                     foreach($rs->extra as $row) {
                                           $start_time = $row->start_time;
                                           $end_time = $row->end_time;
                                           if ($start_time > $end_time) {
                                                 $end_time = date('Y-m-d '.$end_time.':s');
                                                 $end_time = strtotime($stop_date.' +1 day');

                                           } else {
                                                 $end_time = date('Y-m-d '.$end_time.':s');
                                                 $end_time = strtotime($stop_date);
                                           }
                                           $start_time = strtotime(date('Y-m-d '.$row->start_time.':s'));

                                           if ($start_time <= $booking_time && $booking_time <= $end_time) {
                                                 $extra_cost = $extra_cost + $row->fare;
                                           }
                                     }
                               }

                               //print_r($rs->currency);
                               $min_fare = floor($min_fare + $extra_cost);
                               $max_fare = floor($max_fare + $extra_cost);

                               $result = array('status' => 'success', 'data' => array('total_fare' => $rs->currency.$min_fare.' - '.$max_fare, 'estimated_fare' => $rs->currency.$min_fare.' - '.$max_fare));
                         } else {
                               $result = array('status' => 'error', 'message' => 'Server error');
                         }

                   } else {
                         $result = array('status' => 'error', 'message' => 'Out of Location');
                   }

             } else {
                   $result = array('status' => 'error', 'message' => 'Car not Available');
             }

             print json_encode($result, JSON_UNESCAPED_UNICODE | JSON_UNESCAPED_SLASHES);

       }

       public function save_fcmtoken() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;
                   if (isset($request['fcm_token'])) {
                         $result = $this->Webservice_model->save_token($request);
                         if ($result) {
                               print json_encode(array('status' => 'success'));
                         } else {
                               print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '605'));
                         }
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '605'));

                   }

             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '605'));
             }

       }

       public function save_promocode() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;
                   //print_r($request);

                   $result = $this->Webservice_model->promo_save($request);
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => $result['message']));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '607'));
                   }
                   //'a534457488937db4b21d0a043eb6581a'
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '607'));
             }

       }

       function fcm_message() {

             $postdata = file_get_contents("php://input");
             $request = json_decode($postdata, true);

             $this->Webservice_model->message($request);

       }

       public function ride_accept() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;

                   $result = $this->Webservice_model->rides($request);
                   if ($result) {
                         print json_encode(array('status' => 'success', 'data' => $result));
                   } else {
                         print json_encode(array('status' => 'error'));
                   }
             } else {
                   print json_encode(array('status' => 'error'));
             }

       }

       public function request_trigger() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $postdata = file_get_contents("php://input");
                   $request = json_decode($postdata, true);
                   $request['auth'] = $auth;
                   $result = $this->Webservice_model->trigger($request);
                   if ($result) {
                         print json_encode(array('status' => 'success'));
                   } else {
                         print json_encode(array('status' => 'error', 'message' => 'No Driver avaliable', 'code' => '606'));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '606'));
             }

       }

       public function app_status() {

             header('Content-type: application/json');
             if (isset(apache_request_headers()['Auth'])) {
                   $auth = apache_request_headers()['Auth'];
                   $request['auth'] = $auth;
                   $result = $this->Webservice_model->statusof_app($request);
                   header('Content-type: application/json');
                   if ($result) {
                         $result->app_status = '1';
                         //print_r($result->trip_id);
                         // $drvr_status = $this->Webservices_model->status_driver($result->trip_id);
                         // $result->driver_status = $drvr_status;


                         print json_encode(array('status' => 'success', 'data' => $result));
                   } else {
                         print json_encode(array('status' => 'success', 'data' => array('app_status' => '0')));
                   }
             } else {
                   print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));
             }
       }

   

       public function forgot_password() {

             header('Content-type: application/json');

             $data = json_decode(file_get_contents("php://input"));

             $res = $this->Webservice_model->forgetpassword($data);

             if ($res)
                   {

                   echo json_encode(array('status' => 'success'));

             } else {

                   echo json_encode(array('status' => 'error', 'message' => 'Sorry. Please Enter Your Correct Email.'));
             }
       }
 }
 ?>