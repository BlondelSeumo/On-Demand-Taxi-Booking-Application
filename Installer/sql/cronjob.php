<?php

// Name of the file
$filename = 'lataxi.sql';
// MySQL host
$mysql_host = "localhost";
// MySQL username
$mysql_username = "techlabz_lataxi";
// MySQL password
$mysql_password = "DUBU(EpTwGTH";
// Database name
$mysql_database = "techlabz_lataxi";





// Connect to MySQL server
mysql_connect($mysql_host, $mysql_username, $mysql_password) or die('Error connecting to MySQL server: ' . mysql_error());
// Select database
mysql_select_db($mysql_database) or die('Error selecting MySQL database: ' . mysql_error());

// Temporary variable, used to store current query
$templine = '';
// Read in entire file
$lines = file($filename);
// Loop through each line
foreach ($lines as $line)
{
// Skip it if it's a comment
if (substr($line, 0, 2) == '--' || $line == '')
   continue;

// Add this line to the current segment
$templine .= $line;
// If it has a semicolon at the end, it's the end of the query
$status=false;
if (substr(trim($line), -1, 1) == ';')
{
   // Perform the query
   if(mysql_query($templine))
   {
       $status=true;
   } else{
    print('Error performing query \'<strong>' . $templine . '\': ' . mysql_error() . '<br /><br />');
   }
   // Reset temp variable to empty
   $templine = '';
}
}
date_default_timezone_set("Asia/Kolkata");
 $date = date('Y-m-d H:i:s');
if($status=false)
{
 mysql_query("INSERT INTO cronjob (status,date_time)VALUES ('Error','$date')");
}
else
{
 mysql_query("INSERT INTO cronjob (status,date_time)VALUES ('Success','$date')");
}
?>