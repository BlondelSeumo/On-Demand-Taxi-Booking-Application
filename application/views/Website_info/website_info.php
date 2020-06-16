<?php
if($this->session->flashdata('message')) {
  $message = $this->session->flashdata('message');
  ?>
<div class="alert alert-<?php echo $message['class']; ?>">
<button class="close" data-dismiss="alert" type="button">×</button>
<?php echo $message['message']; ?>
</div>
<?php
}
?>

<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo $page_title; ?></h2>

            </div>
            <div class="box-content">
                 <form role="form" method="post" class="validate" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="control-label" for="shop_name">Title</label>
                        <input type="text" name="title" class="form-control required" value="<?php echo $result->title; ?>" placeholder="Enter Title">
                    </div>
                    
                     <div class="form-group">
                        <label class="control-label" for="image">Logo</label>
                        <input type="file"  name="logo" accept="image/*" class="" size="20" />
                        <img src="<?php echo $result->logo; ?>" width="100px" height="100px" alt="Picture Not Found"/>
                       
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="image">Favicon</label>
                        <input type="file"  name="favicon" accept="image/*" class="" size="20" />
                        <img src="<?php echo $result->favicon;?>" width="100px" height="100px" alt="Picture Not Found"/>
                       
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="location">Smtp Username</label>
                        <input type="text" name="smtp_username" class="form-control required" placeholder="Enter Smtp Username" value="<?php echo $result->smtp_username; ?>"> 
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="location">Smtp Host</label>
                        <input type="text" name="smtp_host" class="form-control required" placeholder="Enter Smtp Host" value="<?php echo $result->smtp_host; ?>">
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="state">Smtp Password</label>
                        <input type="password" name="smtp_password" class="form-control required" placeholder="Enter Smtp Password" value="<?php echo $result->smtp_password; ?>">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="location">Admin Email</label>
                        <input type="text" name="admin_email" class="form-control required" placeholder="Admin Email" value="<?php echo $result->admin_email; ?>"> 
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="location">Sms Gateway Username</label>
                        <input type="text" name="sms_gateway_username" class="form-control " placeholder="Enter Sms Gateway Username" value="<?php echo $result->sms_gateway_username; ?>"> 
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="location">Sms Gateway Password</label>
                        <input type="password" name="sms_gateway_password" class="form-control " placeholder="Enter Sms Gateway Password" value="<?php echo $result->sms_gateway_password; ?>">
                    </div>
                    
                   <!-- <div class="form-group">
                        <label class="control-label" for="country">Security Key</label>
                        <input type="password" name="security_key" class="form-control required" placeholder="Enter Security Key" value="<?php //echo $result->security_key; ?>">
                    </div> -->
                    
                   
                  
                    
                    
                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Add Website Info</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->

