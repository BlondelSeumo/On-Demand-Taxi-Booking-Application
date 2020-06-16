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
            <div class="box-content" style="width:820px;">
                 <form role="form" method="post" class="validate">
                    <div class="form-group">
                        <label class="control-label" for="name">Current Password</label>
                        <input type="password" name="curr_password" class="form-control required" value="" placeholder="Enter Password">
                    </div>
                    
                  
                    
                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="image">New Password</label>
                        <input type="password" name="new_password" class="form-control required" value="" placeholder="Enter new Password">
                     </div>

                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="image">New Password</label>
                        <input type="password" name="confirm_password" class="form-control required" value="" placeholder="Enter new Password">
                     </div>


                    
                    
                   
                  
                    
                    
                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->

