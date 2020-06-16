<!DOCTYPE html>
<html lang="en">
    <head>
    <meta charset="utf-8">
    <title>Installer</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/installer.css">
    <script src="js/jquery-1.10.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    </head>
    
    
    <body>

<div class="container">
	<div class="bms-wizard-wrapper">
		
			<div class="bms-logo">
        <div class="logo_img">
							<img src="img/logo.png">
            </div>
						</div>
      
      <form role="form" id='Values' action="" method="post">
    <div class="row setup-content" id="step-1">
          
        <div class="col-md-12">
              
          <div class="form-group">
            <label class="control-label whtclr">Database Host</label>
            <input  maxlength="100" type="text" required="required" id="db_host" name="db_host" class="form-control chclr" placeholder="Database Host"  />
          </div>
          <div class="form-group">
            <label class="control-label whtclr">Database Name</label>
            <input maxlength="100" type="text" required="required" id="db_name" name="db_name" class="form-control chclr" placeholder="Database Name" />
          </div>
          <div class="form-group">
            <label class="control-label whtclr">Database Username</label>
             <input maxlength="100" type="text" required="required" id="db_username" name="db_username" class="form-control chclr" placeholder="Database Username" />
          </div>
          <div class="form-group">
            <label class="control-label whtclr">Database Password</label>
             <input maxlength="100" type="password"  name="db_password" id="db_password" class="form-control chclr" placeholder="Database Password" />
          </div>
              <button class="btn btn-success btn-lg pull-right next" id="formData" type="button">Submit</button>
             <div class="message"></div>
             <div class="loader" >
                <img src="img/loader.gif" />
             </div>
        </div>
     
        </div>
    

    
  </form>
    </div>
		</div>

<script type="text/javascript">
  $(document).ready(function () {

    $('#formData').click(function(){
      var allDetails=$('#Values').serialize();
         $('.loader').show();
         $.ajax({
            url:'dbconnect.php',
            type:'post',
            data:allDetails,
            success:function(result){
              
              if(result=="Success"){

                $.ajax({
                    url:'allDetails.php',
                    type:'post',
                    data:allDetails,
                    success:function(result){
                      $('.loader').hide();
                      if(result=="Success"){
                        window.location.href ='../index.php';
                      }else{
                        
                        $('.message').html('<br><div class="label label-danger">Error Occured</div>');
                      } 

                    }
                  });
                
              }else{
                isValid = false;
                $('.message').html('<br><div class="label label-danger">Could not connect to MYSQL!</div>');
              } 

            }
          });
          
    });

});
  </script>
</body>
</html>
