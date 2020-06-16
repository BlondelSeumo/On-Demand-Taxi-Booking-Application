<?php

  defined('BASEPATH')OR exit('No direct script access allowed');

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

  class Webservices_driver extends CI_Controller {

      public function __construct() {

          parent::__construct();

          $this->load->model('Webservices_driver_model');

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

      public function registration() {

          header('Content-type: application/json');

          $postdata = file_get_contents("php://input");

          $request = json_decode($postdata, true);

          $result = $this->Webservices_driver_model->driver_reg($request);

          header('Content-type: application/json');

          if ($result['status'] == 'success') {

              $result = array('status' => "success", 'data' => array('auth_token' => $result['auth_token'], 'user' => array('user_id' => $result['user_id'], 'name' => $result['name'], 'phone' => $result['phone'], 'email' => $result['email'], 'city' => $result['city'], 'profile_photo' => $result['image'], 'is_phone_verified' => true)));

          } else {

              //$result = array('status'=>"error",'message' =>'Mobile Number already Exists','error'=>'201');
              $result = array('status' => 'error', 'message' => $result['message'], 'error' => '501');

          }

          $this->response($result);

      }

      function response($res) {

          header('Content-type: application/json');

          print json_encode($res);

      }

      public function login() {

          $postdata = file_get_contents("php://input");

          $request = json_decode($postdata, true);

          $result = $this->Webservices_driver_model->login($request);

          header('Content-type: application/json');

          if ($result) {

              print json_encode(array('status' => 'success', 'data' => array('auth_token' => $result['auth_token'], 'user' => array('user_id' => $result['user_id'], 'name' => $result['name'], 'phone' => $result['phone'], 'email' => $result['email'], 'city' => $result['city'], 'profile_photo' => $result['profile_photo'], 'is_all_documents_uploaded' => true, 'is_all_documents_verified' => true, 'is_phone_verified' => true))));

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Unknown Credential! Try Again', 'error' => '202'));

          }

      }

      public function update_fcm_token() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              // print_r($request);


              $result = $this->Webservices_driver_model->update_fcm($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '203'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '204'));

          }

      }

      public function mobile_number_availability() {

          header('Content-type: application/json');

          $postdata = file_get_contents("php://input");

          $request = json_decode($postdata, true);

          if (isset($request['phone'])) {

              $result = $this->Webservices_driver_model->mobile_availability($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => array('phone' => $request['phone'], 'is_available' => filter_var($result['is_available'], FILTER_VALIDATE_BOOLEAN))));

              } else {

                  print json_encode(array('status' => 'success', 'data' => array('phone' => $request['phone'], 'is_available' => filter_var(true, FILTER_VALIDATE_BOOLEAN))));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Mobile Number is missing. Please try again', 'error' => 'phone is missing'));

          }

      }

      public function get_profile() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request = $_GET;

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->profile($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => array('id' => $result['id'], 'name' => $result['name'], 'email' => $result['email'], 'phone' => $result['phone'], 'address' => $result['address'], 'city' => $result['city'], 'state' => $result['state'], 'postal_code' => $result['postal_code'], 'profile_photo' => $result['profile_photo'], 'is_phone_verified' => false)));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '205', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '205', 'message' => 'Something Went wrong'));

          }

      }

      public function update_profile() {

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

              $this->db->from('driver');
              $count = $this->db->count_all_results();
              //print_r($count);
              if ($count == 0) {

                  $result = $this->Webservices_driver_model->prof_update($request);
                  if ($result) {
                      //print_r($result);
                      print json_encode(array('status' => 'success', 'data' => array('id' => $result->id, 'name' => $result->driver_name, 'phone' => $result->phone, 'email' => $result->email, 'address' => $result->address, 'city' => $result->city, 'state' => $result->state, 'postal_code' => $result->post_code, 'profile_photo' => $result->image, 'is_phone_verified' => false)));
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

      public function document_upload() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = $_POST['profile_update'];

              if (is_uploaded_file($_FILES['image']['tmp_name'])) {

                  $uploads_dir = './assets/uploads/document/';

                  $tmp_name = $_FILES['image']['tmp_name'];

                  $pic_name = $_FILES['image']['name'];

                  $pic_name = str_replace(' ', '_', mt_rand().$pic_name);

                  move_uploaded_file($tmp_name, $uploads_dir.$pic_name);

                  $request = $_POST;

                  $request['image'] = $uploads_dir.$pic_name;

                  $request['auth'] = $auth;

                  $result = $this->Webservices_driver_model->doc_upload($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '207'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '207'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '207'));

          }

      }

      public function document_status() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->doc_status($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => array('documents' => $result)));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function get_driver_status() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->driver_status($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => $result));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function update_driver_type() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->type_driver($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function profile_photo_upload() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = $_POST['profile_update'];
			  //print_r($postdata);exit;

              

              if (is_uploaded_file($_FILES['image']['tmp_name'])) {
					//print_r($_FILES['image']['tmp_name']);
                  $uploads_dir = './assets/uploads/driver/';

                  $tmp_name = $_FILES['image']['tmp_name'];

                  $pic_name = $_FILES['image']['name'];

                  $pic_name = str_replace(' ', '_', mt_rand().$pic_name);

                  move_uploaded_file($tmp_name, $uploads_dir.$pic_name);

                  $request = $_POST;

                  $request['image'] = $uploads_dir.$pic_name;

                  $request['auth'] = $auth;

                  $result = $this->Webservices_driver_model->photo_upload($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '2071'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '2027'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '207'));

          }

      }

      public function update_driver_status() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              //print_r($auth);

              if (isset($request['driver_status'])) {

                  $result = $this->Webservices_driver_model->status($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                      //$this->updatedriver_status($request);

                      $this->Webservices_driver_model->driver_onstatus($request);

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

              //'a534457488937db4b21d0a043eb6581a'

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_accept() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $data = $this->db->where('id', $request['request_id'])->get('request')->row();
              //echo $this->db->last_query();die();
              //print_r($data->status);
              if ($data->status == 1) {
                  print json_encode(array('status' => 'error', 'message' => 'Trip Assigned '));
              }
              elseif($data->status == 3) {
                  print json_encode(array('status' => 'error', 'message' => 'Trip Cancelled '));
              }
              else {

                  $result = $this->Webservices_driver_model->accept($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success', 'data' => $result));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '207', 'message' => 'Something Went wrong'));

                  }
              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_start() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              if (isset($request['trip_id'])) {

                  $result = $this->Webservices_driver_model->start_trip($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

              //'a534457488937db4b21d0a043eb6581a'

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function help() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = $_GET;

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->help_pages($request);

              header('Content-type: application/json');

              if (isset($request['id'])) {

                  if ($result) {

                      $query = $this->db->where('unique_id', $request['auth'])->get('driver_auth_table');

                      $rs = $query->row();

                      $driv_id = $rs->driver_id;

                      $is_help = $this->Webservices_driver_model->is_help_status($driv_id, $result['id']);

                      $rs->is_helpful = $is_help;

                      print json_encode(array('status' => 'success', 'data' => array('id' => $result['id'], 'title' => $result['title'], 'icon' => $result['icon'], 'content' => $result['content'], 'is_helpful' => $is_help)));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

              //'a534457488937db4b21d0a043eb6581a'

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function help_page_list() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              //$request = $_GET;

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->help_list($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => array('help' => $result)));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

              //'a534457488937db4b21d0a043eb6581a'

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function help_page_review() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->help_review($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function update_vehicle_details() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->update_vehicle($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '210', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '210', 'message' => 'Something Went wrong'));

          }

      }

      public function update_accesibility_settings() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->update_settings($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function fetch_accesibility_settings() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request = $_GET;

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->fetch_settings($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => array('is_deaf' => filter_var($result['is_deaf'], FILTER_VALIDATE_BOOLEAN), 'is_flash_required_for_requests' => filter_var($result['is_flash_required_for_requests'], FILTER_VALIDATE_BOOLEAN))));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function update_driver_location() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->driver_location($request);

              header('Content-type: application/json');

              if ($result) {

                  print json_encode(array('status' => 'success'));

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function request_details($request_id = null) {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request = $_GET;

              $request['auth'] = $auth;

              if (isset($request['request_id'])) {

                  $result = $this->Webservices_driver_model->req_details($request);

                  header('Content-type: application/json');

                  if (count($result) > 0) {

                      $result->request_id = $request['request_id'];

                      $car_image = $this->Webservices_driver_model->car_type_image($result->car_type);

                      $result->car_type_image = $car_image;

                      $car_type = $this->Webservices_driver_model->car_type($result->car_type);

                      $result->car_type = $car_type;

                      // 	$is_help = $this->Webservices_driver_model->is_help_status($driv_id,$result['id']);

                      // $rs->is_helpful = $is_help;


                      print json_encode(array('status' => 'success', 'data' => $result));

                  } else {

                      print json_encode(array('status' => 'error'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'error' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'error' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_summary($id = null) {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $request = $_GET;

              if (isset($request['trip_id'])) {

                  $result = $this->Webservices_driver_model->summary_trip($request);

                  header('Content-type: application/json');

                  if (count($result) > 0) {

                      // print_r($result);

                      // $pattern = $result['pattern_id'];

                      // print_r($pattern);


                      print json_encode(array('status' => 'success', 'data' => $result));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function app_status() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->statusof_app($request);

              header('Content-type: application/json');

              if ($result) {
                  $result->app_status = '1';
                  //print_r($result->trip_id);
                  $drvr_status = $this->Webservices_driver_model->status_driver($result->trip_id);
                  $result->driver_status = $drvr_status;
                  print json_encode(array('status' => 'success', 'data' => $result));
              } else {
                  print json_encode(array('status' => 'success', 'data' => array('app_status' => '0')));
              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function confirm_car_arrival() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              if (isset($request['trip_id'])) {

                  $result = $this->Webservices_driver_model->confirm_arrival($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function confirm_cash_collection() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              if (isset($request['trip_id'])) {

                  $result = $this->Webservices_driver_model->confirm_cash($request);

                  header('Content-type: application/json');

                  if ($result) {

                      print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function rider_feedback_issues() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request['auth'] = $auth;

              $page = $_GET['page'] >= 1 ? $_GET['page'] : 1;

              $result = $this->Webservices_driver_model->ride_feedback($request);

              header('Content-type: application/json');

              if ($result) {

                  $total = count((array)$result);

                  $per_page = 20;

                  $total_pages = ceil($total / $per_page);

                  $current_page = $page;

                  print json_encode(array('status' => 'success', 'data' => array('issues' => $result), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

              } else {

                  print json_encode(array('status' => 'success', 'data' => []));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

          }

      }

      public function rider_feedback_comments() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request['auth'] = $auth;

              $page = $_GET['page'] >= 1 ? $_GET['page'] : 1;

              $result = $this->Webservices_driver_model->feedback_comments($request);

              header('Content-type: application/json');

              if ($result) {

                  //print_r($result);

                  //$start_time =$result['trip_id'];

                  //print_r($start_time);


                  // $total = $this->db->query($result)->num_rows();


                  $total = count((array)$result);
                  //	print_r($total);

                  $per_page = 20;

                  $total_pages = ceil($total / $per_page);

                  $current_page = $page;

                  print json_encode(array('status' => 'success', 'data' => array('comments' => $result), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

              } else {

                  print json_encode(array('status' => 'success', 'data' => []));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

          }

      }

      public function rating_details() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              //$postdata = file_get_contents("php://input");

              $request = $_GET;

              $request['auth'] = $auth;

              $result = $this->Webservices_driver_model->rating($request);

              header('Content-type: application/json');

              if ($result) {

                  //$str =(int)$result->average_rating;

                  //$str = preg_replace('/"([^"]+)"\s*:\s*/', '$1:', $str);

                  //print_r($str);

                  //echo trim($str, '"');

                  $query = $this->db->where('unique_id', $request['auth'])->get('driver_auth_table');

                  $rs = $query->row();

                  $driv_id = $rs->driver_id;

                  $average_rating = $this->Webservices_driver_model->avg_rating($driv_id);

                  //print_r($average_rating);

                  if ($average_rating == '') {

                      $avg = '0';

                  } else {

                      $avg = $average_rating;

                  }

                  $result->average_rating = $avg;

                  $tot_requests = $this->Webservices_driver_model->num_rides($driv_id);

                  //print_r($tot_requests);

                  $result->total_requests = $tot_requests;

                  $req_accepted = $this->Webservices_driver_model->num_requests($driv_id);

                  $result->requests_accepted = $req_accepted;

                  $tot_trips = $this->Webservices_driver_model->num_trips($driv_id);

                  $result->total_trips = $tot_trips;

                  $trips_cancelled = $this->Webservices_driver_model->num_cancelled($driv_id);

                  //print_r($trips_cancelled);

                  $result->trips_cancelled = $trips_cancelled;

                  //$test = json_encode(array('status' => 'success','data' =>$result));


                  //$con = str_replace("\"", "", $test);

                  //print $con;


                  print json_encode(array('status' => 'success', 'data' => $result), JSON_NUMERIC_CHECK);

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

              //'a534457488937db4b21d0a043eb6581a'

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_details($trip_id = null) {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $request = $_GET;

              if (isset($request['trip_id'])) {

                  $result = $this->Webservices_driver_model->tripdetails($request);

                  header('Content-type: application/json');

                  if (count($result) > 0) {

                      //print_r($result);

                      $rate = $this->Webservices_driver_model->driver_rate($request['trip_id']);

                      $result->rating = $rate;

                      print json_encode(array('status' => 'success', 'data' => $result));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '209', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_history() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request['auth'] = $auth;

              $page = isset($_GET['page']) ? $_GET['page'] : 1;

              if ($page == 0) {

                  $page = 1;

              }

              //echo "string";


              $result = $this->Webservices_driver_model->history_trips($request);

              if ($result) {

                  $tmp = $this->db->query($result)->result();

                  $total = $this->db->query($result)->num_rows();

                  $per_page = 5;

                  $total_pages = ceil($total / $per_page);

                  $current_page = $page;

                  $query = $this->db->where('unique_id', $auth)->get('driver_auth_table');

                  $rs = $query->row();

                  $driv_id = $rs->driver_id;

                  $tot_onlinetime = $this->Webservices_driver_model->total_online_time($driv_id);

                  $rs->total_online_time = $tot_onlinetime;

                  //print_r($total);


                  if ($total > 0) {

                      $limit = ($per_page * ($current_page - 1));

                      $limit_sql = " ORDER BY booking.id DESC LIMIT $limit,$per_page";

                      //  echo $result.$limit_sql;


                      $result = $this->db->query($result.$limit_sql)->result();

                      $query = $this->db->where('unique_id', $auth)->get('driver_auth_table');

                      $rs = $query->row();

                      $driv_id = $rs->driver_id;

                      $fare = $this->Webservices_driver_model->total_fare($driv_id);

                      $rs->total_fare = $fare;

                      $tot_ride = $this->Webservices_driver_model->total_rides_history($driv_id);

                      $rs->total_rides_taken = $tot_ride;

                      print json_encode(array('status' => 'success', 'data' => array('total_fare' => $fare, 'total_rides_taken' => $tot_ride, 'total_online_time' => $tot_onlinetime, 'trips' => $result), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

                  } else {

                      print json_encode(array('status' => 'success', 'data' => array('total_fare' => 0, 'total_rides_taken' => 0, 'total_online_time' => $tot_onlinetime, 'trips' => []), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

          }

      }

      public function trip_list_for_today() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $request['auth'] = $auth;

              $page = isset($_GET['page']) ? $_GET['page'] : 1;

              if ($page == 0) {

                  $page = 1;

              }

              // $start_time = strtotime(date('Y-m-d 00:00:00'));

              // $end_time = strtotime(date('Y-m-d 23:59:59'));


              $start_time = strtotime(date('Y-m-d 00:00:00'));

              $end_time = strtotime(date('Y-m-d 23:59:59'));

              $result = $this->Webservices_driver_model->today_trips($request, $start_time, $end_time);

              if ($result) {

                  //	print_r($result);

                  $total = $this->db->query($result)->num_rows();

                  $per_page = 2;

                  $total_pages = ceil($total / $per_page);

                  $current_page = $page;

                  if ($total > 0) {

                      $limit = ($per_page * ($current_page - 1));

                      $limit_sql = " LIMIT $limit,$per_page";

                      $result = $this->db->query($result.$limit_sql)->result();

                      $id = $result->driver_id;

                      $start_time = strtotime(date('Y-m-d 00:00:00'));

                      $end_time = strtotime(date('Y-m-d 23:59:59'));

                      $id = $result->driver_id;

                      $query = $this->db->where('unique_id', $auth)->get('driver_auth_table');

                      $rs = $query->row();

                      $driv_id = $rs->driver_id;

                      $fare = $this->Webservices_driver_model->totalfare_today($start_time, $end_time, $driv_id);

                      $rs->total_fare = $fare;

                      $rides = $this->Webservices_driver_model->total_rides($start_time, $end_time, $driv_id);

                      $rs->total_rides_taken = $rides;

                      $tot_onlinetime = $this->Webservices_driver_model->total_online_time($driv_id);

                      $rs->total_online_time = $tot_onlinetime;

                      //print_r($result);

                      print json_encode(array('status' => 'success', 'data' => array('total_fare' => $fare, 'total_online_time' => $tot_onlinetime, 'total_rides_taken' => $rides, 'trips' => $result), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

                  } else {

                      print json_encode(array('status' => 'success', 'data' => array('total_fare' => 0, 'total_rides_taken' => 0, 'total_online_time' => 0, 'trips' => []), 'meta' => array('total' => $total, 'per_page' => $per_page, 'total_pages' => $total_pages, 'current_page' => $current_page)));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '503'));

          }

      }

      function fcm_usermessage($request) {

          /*$postdata = file_get_contents("php://input");

          $request = json_decode($postdata,true);*/

          $this->Webservices_driver_model->message($request);

          /*

          if($result){

          print json_encode(array('status'=>'success','data'=>array('id'=>$result['trip_id'])));



          }else{

          print json_encode(array('status'=>'error','message'=>'Something Went wrong','error'=>'605'));

          }

           */

      }

      public function updatedriver_status() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              if (isset($request['driver_status'])) {

                  $result = $this->Webservices_driver_model->driver_onstatus($request);

                  header('Content-type: application/json');

                  if ($result) {

                      return 0;

                      //print json_encode(array('status' => 'success'));

                  } else {

                      print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

                  }

              } else {

                  print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'code' => '208', 'message' => 'Something Went wrong'));

          }

      }

      public function trip_end() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $request['auth'] = $auth;

              //$result = '[{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.9976592","latitude":"40.6905615"},{"longitude":"-73.933783","latitude":"40.659569"},{"longitude":"-73.851524","latitude":"40.729029"},{"longitude":"-73.6334271","latitude":"40.6860072"},{"longitude":"-73.7527626","latitude":"40.598566"},{"longitude":"-73.933783","latitude":"40.659569"},{"longitude":"-73.851524","latitude":"40.729029"},{"longitude":"-73.6334271","latitude":"40.6860072"},{"longitude":"-73.7527626","latitude":"40.598566"}]';


              $result = $request['path'];

              //$result = json_decode($result);
              $trip_path = json_encode($result);

              $array = array();

              foreach($result as $rs) {

                  $latlng = $rs['latitude'].','.$rs['longitude'];

                  $array[] = $latlng;

              }

              //print_r($array);


              $count = count($array);

              $i = 0;

              $distance_val = 0;
              while ($i < $count - 1) {

                  list($lat1, $lon1) = explode(',', $array[$i]);

                  list($lat2, $lon2) = explode(',', $array[$i + 1]);

                  $unit = 'K';

                  $distance_val += $this->distance_calculate($lat1, $lon1, $lat2, $lon2, $unit);

                  $i++;

              }

              //print_r($distance_val);


              $rs = $distance_val;
              $trip_end_time = time();
              $this->db->where('id', $request['trip_id'])->update('booking', array('trip_end_time' => $trip_end_time, 'trip_path' => $trip_path));
              $res = $this->db->where('id', $request['trip_id'])->get('booking')->row();

              $trip_start_time = $res->trip_start_time;

              $trip_end_time = $res->trip_end_time;

              $distance = ($rs);

              $time = (($trip_end_time - $trip_start_time) / 60);

              $total_time = $time;

              $hours = floor($time / 60);

              $minutes = ($time % 60);

              $time = $hours.':'.$minutes;

              $minutes_travel = intval($total_time);

              if ($minutes_travel == '0') {
                  $hours = '0 : 01';
              } else {
                  $hours = convertToHoursMins($minutes_travel, '%2d : %02d ');
              }
              //    print_r($minutes_travel);die;


              $array = array('distance' => $distance,

                      'start_time' => $request['start_time'],

                      'end_time' => $request['end_time'],

                      'time' => $hours,

                      //'trip_path' => $request['path'],

                      //    'book_status' => 0,

                      'status' => 3);

              $this->db->where('id', $request['trip_id'])->update('booking', $array);

              // echo $this->db->last_query();die;

              $data = $this->db->select('car_type,id,distance,time,source_lat AS source_latitude,source_lng AS source_longitude,

                      destination_lat AS destination_latitude,destination_lng AS destination_longitude')->where('id', $request['trip_id'])->get('booking')->row_array();

              $trip_id = $request['trip_id'];

              $data['time'] = $total_time;

              $fare = $this->Webservices_driver_model->fare_calculate($data, $trip_id);

              $result = $this->Webservices_driver_model->get_trip_info($request['trip_id']);

              $this->Webservices_driver_model->message($request['trip_id']);

              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => $result));

              } else {

                  print json_encode(array('status' => 'error', 'error' => '211', 'message' => 'Invalid booking details'));

              }

          } else {

              print json_encode(array('status' => 'error', 'error' => '210', 'message' => 'Something Went wrong'));

          }

      }

      public function distance($origins, $destination) {

          $data1 = "SELECT * FROM settings WHERE id = '1' ";

          $query1 = $this->db->query($data1);

          $rs = $query1->row();
          $key = $rs->key;

          $url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=$origins&destinations=$destination&mode=driving&key=$key";

          //$url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=40.6905615,-73.9976592&40.6905615,-73.9976592&40.6905615,-73.9976592&40.6905615,-73.9976592&40.6905615,-73.9976592&40.6905615,-73.9976592&40.659569,-73.933783&40.729029,-73.851524&40.6860072,-73.6334271&40.598566,-73.7527626&40.659569,-73.933783&40.729029,-73.851524&40.6860072,-73.6334271&40.598566,-73.7527626&key=AIzaSyB1br9lwKFyEpCnS5elLan_90CCsYeak6I";

          $ch = curl_init();

          curl_setopt($ch, CURLOPT_URL, $url);

          curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

          curl_setopt($ch, CURLOPT_PROXYPORT, 3128);

          curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);

          curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);

          $response = curl_exec($ch);

          curl_close($ch);

          $response_a = json_decode($response, true);

          return $dist = $response_a['rows'][0]['elements'][0]['distance']['value'];

          //print_r($response_a);

      }

      function distance_calculate($lat1, $lon1, $lat2, $lon2, $unit) {

          $theta = $lon1 - $lon2;

          $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) + cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));

          $dist = acos($dist);

          $dist = rad2deg($dist);

          $miles = $dist * 60 * 1.1515;

          $unit = strtoupper($unit);

          return ($miles * 1.609344);

      }

      public function send_email() {
          $data = '{"name":"Adarsh","email":"adarsh.techware@gmail.com","message" :"Hi Team"}';

          $data = json_decode($data);

          $this->load->library('email');
          $config = Array(
                  'protocol' => 'smtp',
                  'smtp_host' => 'mail.techlabz.in',
                  'smtp_port' => 587,
                  'smtp_user' => 'no-reply@techlabz.in', // change it to yours
                  'smtp_pass' => 'k4$_a4%eD?Hi', // change it to yours
                  'smtp_timeout' => 20,
                  'mailtype' => 'html',
                  'charset' => 'iso-8859-1',
                  'wordwrap' => TRUE);

          $this->email->initialize($config); // add this line

          $subject = 'New Mail';
          $name = $data->name;
          $mailTemplate = $data->message;

          //$this->email->set_newline("\r\n");
          $this->email->from('no-reply@techlabz.in', $name);
          $this->email->to($data->email);
          $this->email->subject($subject);
          $this->email->message($mailTemplate);
          echo $this->email->send();
          $rs = $this->email->print_debugger();
      }

      public function forgot_password() {

          header('Content-type: application/json');

          $data = json_decode(file_get_contents("php://input"));

          $res = $this->Webservices_driver_model->forgetpassword($data);

          if ($res) {

              echo json_encode(array('status' => 'success'));

          } else {

              echo json_encode(array('status' => 'error', 'message' => 'Sorry. Please Enter Your Correct Email.'));

          }

      }

      public function weekly_earnings() {

          header('Content-type: application/json');

          if (isset(apache_request_headers()['Auth'])) {

              $auth = apache_request_headers()['Auth'];

              $postdata = file_get_contents("php://input");

              $request = json_decode($postdata, true);

              $query = $this->db->where('unique_id', $auth)->get('driver_auth_table');

              $rs = $query->row();

              $driv_id = $rs->driver_id;

              //print_r($driv_id);

              $request = $_GET;

              // print_r($request);

              if (!isset($request['week_of_year'])) {

                  $week_of_year = date('W') - 1;

              } else {

                  $week_of_year = $request['week_of_year'] - 1;

              }

              if (!isset($request['year'])) {

                  $year = date('Y');

              } else {

                  $year = $request['year'];

              }

              $week_days = $this->getStartAndEndDate($week_of_year, $year);

              $array = $week_days;

              $result = $this->Webservices_driver_model->get_payout($array, $driv_id);

              $result['week_of_the_year'] = $week_of_year + 1;

              $result['year'] = $year;

              //        $result = array();


              //       $fare = $this->Webservices_driver_model->get_payout($array,$driv_id);

              // $result['total_payout'] = $fare;


              if ($result) {

                  print json_encode(array('status' => 'success', 'data' => $result));

              } else {

                  print json_encode(array('status' => 'error', 'error' => '211', 'message' => 'Something Went wrong'));

              }

          } else {

              print json_encode(array('status' => 'error', 'message' => 'Something Went wrong', 'error' => '605'));

          }

      }

      function getStartAndEndDate($week, $year) {

          $time = strtotime("1 January $year", time());

          $day = date('w', $time);

          $time += ((7 * $week) - $day) * 24 * 3600;

          $return[0] = date('Y-m-d', $time);

          $j = 1;

          for ($i = 1; $i < 7; $i++) {

              $time += $j * 24 * 3600;

              $return[$i] = date('Y-m-d', $time);

          }

          return $return;

      }

      function get_cal($array) {
          //$array = array(10,12,15,18,24,30,42);
          $count = count($array);
          $i = 0;
          $distance = 0;
          while ($i < $count - 1) {
              $distance += $array[$i + 1] - $array[$i];
              $i++;
          }
          return $distance;
      }
  }