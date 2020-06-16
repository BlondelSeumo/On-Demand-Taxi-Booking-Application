<?php
error_reporting(-1);
ini_set('display_errors', 0);
if(($_POST)){
  $db_host = $_POST["db_host"];
  $db_name = $_POST["db_name"];
  $db_username = $_POST["db_username"];
  $db_password = $_POST["db_password"];
  
  $smtp_host=$_POST["smtp_host"];
  $smtp_username=$_POST["smtp_username"];
  $smtp_password=$_POST["smtp_password"];

  $admin_email=$_POST["admin_email"];
 

  /*$code = $_POST;
  $result=array('value'=>$code);
  print_r(($result));*/
  $filename = 'sql/techlabz_lataxi.sql';
  $con = mysqli_connect($db_host,$db_username,$db_password,$db_name) or die('Error connecting to MySQL server');
  // Temporary variable, used to store current query
  $templine = '';
  // Read in entire file
  $lines = file($filename);
  // Loop through each line
  foreach ($lines as $line){
  // Skip it if it's a comment
  if (substr($line, 0, 2) == '--' || $line == '')
    continue;
   // Add this line to the current segment
   $templine .= $line;
   // If it has a semicolon at the end, it's the end of the query
   if (substr(trim($line), -1, 1) == ';'){
     // Perform the query
     mysqli_query($con,$templine);
     // Reset temp variable to empty
     $templine = '';
    }
  }
   $myfile = fopen("../application/config/database.php", "w") or die("Unable to open file!");
          $active_record='';
          $txt = '<?php defined("BASEPATH") OR exit("No direct script access allowed");$active_group = "default";
          $query_builder = TRUE;'."\r\n";

          $txt .='$db["default"] = array("dsn"  => "",'."\r\n";
          $txt .='"hostname" => "'.$db_host.'", '."\r\n";
          $txt .='"username" => "'.$db_username.'" ,'."\r\n";
          $txt .='"password" => "'.$db_password.'",'."\r\n";
          $txt .='"database" => "'.$db_name.'",'."\r\n";
          $txt .='"dbdriver" => "mysqli",'."\r\n";
          $txt .='"pconnect" => FALSE,'."\r\n";
          $txt .='"db_debug" => (ENVIRONMENT !== "production"),'."\r\n";
          $txt .='"cache_on" => FALSE,'."\r\n";
          $txt .='"cachedir" => "",'."\r\n";
          $txt .='"char_set" => "utf8",'."\r\n";
          $txt .='"dbcollat" => "utf8_general_ci",'."\r\n";
          $txt .='"swap_pre" => "",'."\r\n";
          $txt .='"encrypt" => FALSE,'."\r\n";
          $txt .='"compress" => FALSE,'."\r\n";
          $txt .='"stricton" => FALSE,'."\r\n";
          $txt .='"failover" => array(),'."\r\n";
          $txt .='"save_queries" => TRUE);'."\r\n";
  fwrite($myfile, $txt);
  fclose($myfile);

    /*$myconfg = fopen("../application/config/config.php", "w") or die("Unable to open file!");
          $txt1 = '<?php defined("BASEPATH") OR exit("No direct script access allowed");'."\r\n";
          $txt1 .='$config["base_url"] = "http://".$_SERVER["HTTP_HOST"].dirname($_SERVER["SCRIPT_NAME"]);'."\r\n";          
          $txt1 .='$config["index_page"] = "index.php";'."\r\n";
          $txt1 .='$config["uri_protocol"]  = "REQUEST_URI"; '."\r\n";
          $txt1 .='$config["url_suffix"] = " ";'."\r\n";
          $txt1 .='$config["language"]  = "english";'."\r\n";
          $txt1 .='$config["charset"] = "UTF-8";'."\r\n";
          $txt1 .='$config["enable_hooks"] = FALSE;'."\r\n";
          $txt1 .='$config["subclass_prefix"] = "BMS_";'."\r\n";
          $txt1 .='$config["composer_autoload"] = FALSE;'."\r\n";;
          $txt1 .='$config["permitted_uri_chars"] = "a-z 0-9~%.:_\-";'."\r\n";
          $txt1 .='$config["allow_get_array"] = TRUE;'."\r\n";
          $txt1 .='$config["enable_query_strings"] = FALSE;'."\r\n";
          $txt1 .='$config["controller_trigger"] = "c";'."\r\n";
          $txt1 .='$config["function_trigger"] = "m";'."\r\n";
          $txt1 .='$config["directory_trigger"] = "d";'."\r\n";
          $txt1 .='$config["log_threshold"] = 0;'."\r\n";
          $txt1 .='$config["log_path"] = "";'."\r\n";
          $txt1 .='$config["log_file_extension"] = "";'."\r\n";
          $txt1 .='$config["log_file_permissions"] = 0644;'."\r\n";
          $txt1 .='$config["log_date_format"] = "Y-m-d H:i:s";'."\r\n";
          $txt1 .='$config["error_views_path"] = "";'."\r\n";
          $txt1 .='$config["cache_path"] = "";'."\r\n";
          $txt1 .='$config["cache_query_string"] = FALSE;'."\r\n";
          $txt1 .='$config["encryption_key"] = "";'."\r\n";
          $txt1 .='$config["sess_driver"] = "files";'."\r\n";
          $txt1 .='$config["sess_cookie_name"] = "ci_session";'."\r\n";
          $txt1 .='$config["sess_expiration"] = 7200;'."\r\n";
          $txt1 .='$config["sess_save_path"] = NULL;'."\r\n";
          $txt1 .='$config["sess_match_ip"] = FALSE;'."\r\n";
          $txt1 .='$config["sess_time_to_update"] = 300;'."\r\n";
          $txt1 .='$config["sess_regenerate_destroy"] = FALSE;'."\r\n";
          $txt1 .='$config["cookie_prefix"] = "";'."\r\n";
          $txt1 .='$config["cookie_domain"] = "";'."\r\n";
          $txt1 .='$config["cookie_path"]   = "/";'."\r\n";
          $txt1 .='$config["cookie_secure"] = FALSE;'."\r\n";
          $txt1 .='$config["cookie_httponly"]   = FALSE;'."\r\n";
          $txt1 .='$config["standardize_newlines"] = FALSE;'."\r\n";
          $txt1 .='$config["global_xss_filtering"] = FALSE;'."\r\n";
          $txt1 .='$config["csrf_protection"] = FALSE;'."\r\n";
          $txt1 .='$config["csrf_token_name"] = "csrf_test_name";'."\r\n";
          $txt1 .='$config["csrf_cookie_name"] = "csrf_cookie_name";'."\r\n";
          $txt1 .='$config["csrf_expire"] = 7200;'."\r\n";
          $txt1 .='$config["csrf_regenerate"] = TRUE;'."\r\n";
          $txt1 .='$config["csrf_exclude_uris"] = array();'."\r\n";
          $txt1 .='$config["compress_output"] = FALSE;'."\r\n";
          $txt1 .='$config["time_reference"] = "local";'."\r\n";
          $txt1 .='$config["rewrite_short_tags"] = FALSE;'."\r\n";
          $txt1 .='$config["proxy_ips"] = "";'."\r\n";
         

    fwrite($myconfg, $txt1);
    fclose($myconfg);*/

    

 $conn = mysql_connect($db_host,$db_username,$db_password) ;
 if($conn){
  

      //$key=array('key'=>$data['key']);   
         
       //$query=mysql_query(" UPDATE `keys` SET security_key='$security_key' WHERE id='1' ");
       
         
         
          $file = "../INSTALLER_TRUE.php";
            if(file_exists($file)){
                unlink('../INSTALLER_TRUE.php');
                echo 'Success';
               
            }else{
                echo "no result";
            }  
         
         
         
 } else {
  die(mysql_error());
 }
}
        


?>