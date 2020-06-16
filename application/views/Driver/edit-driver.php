<style type="text/css">

  input::-webkit-outer-spin-button,

input::-webkit-inner-spin-button {

    /* display: none; <- Crashes Chrome on hover */

    -webkit-appearance: none;

    margin: 0; /* <-- Apparently some margin are still there even though it's hidden */

}

</style>

<div class="row">

    <div class="box col-md-12">

        <div class="box-inner">

            <div class="box-header well" data-original-title="">
           
            <h2><i class="glyphicon glyphicon-edit"></i> <?php echo $page_title; ?></h2>



            </div>

            <div class="box-content" style="width:820px;">

                <form role="form" method="post" class="validate" enctype="multipart/form-data">

                    <div class="form-group">

                        <label class="control-label" for="name">Name</label>

                        <input type="text" name="driver_name" value="<?php echo $data->driver_name; ?>" class="form-control required" placeholder="Please Enter Driver Name">

                    </div>





                  
                    <div class="form-group">

                        <label class="control-label" for="name">Password</label>

                        <input type="password" name="password"  class="form-control " placeholder="Please Enter Driver Password">

                    </div>








                    <div class="form-group" style="width:500px;">

                        <label class="control-label" for="name">Car Type</label>

                        <!-- <input type="text" name="car_name" class="form-control required" placeholder=" Please Enter Car Name"> -->

                        <select name="car_id" class="form-control required">

                            <?php

                            foreach ($car_type as $rs) {?>

                            <option value="<?php echo $rs->id; ?>" <?php if($data->car_id == $rs->id) echo 'selected="SELECTED"'; ?>><?php echo $rs->name; ?></option>

                            <?php } ?>

                        </select>

                    </div>







                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">License Number</label>

                        <input type="text" name="license_no" value="<?php echo $data->license_no; ?>" class="form-control required" placeholder="Please Enter License Number">

                    </div>







                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Car Model</label>

                        <input type="text" name="name" value="<?php echo $data->name; ?>" class="form-control required" placeholder="Please Enter Car Model">

                    </div>



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Vehicle Registration Number</label>

                        <input type="text" name="vehicle_reg_no" value="<?php echo $data->vehicle_reg_no ; ?>" class="form-control required" placeholder="Please Enter Vehicle Registration Number">

                    </div>



                 

                  





                  



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Phone</label>

                        <input type="text" name="phone" value="<?php echo $data->phone ; ?>" class="form-control required" placeholder="Please Enter Phone Number">

                    </div>



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="email_id">Email</label>

                        <input type="email"  name="email" value="<?php echo $data->email ; ?>" class="form-control required" placeholder="Please Enter Email">

                    </div>




                 <!-- <div class="form-group">
                        <label class="control-label" for="password">Password</label>
                        <input type="password"  name="password" value="<?php echo $data->password; ?>" class="form-control required" placeholder="Enter Password">
                    </div>-->






                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">City</label>

                        <input type="text" name="city" value="<?php echo $data->city ; ?>" class="form-control required" placeholder="Please Enter City">

                    </div>



                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">State</label>

                        <input type="text" name="state" value="<?php echo $data->state ; ?>" class="form-control required" placeholder="Please Enter State">

                    </div>





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Address</label>

                        <input type="text" name="address" value="<?php echo $data->address ; ?>" class="form-control required" placeholder="Please Enter Address">

                    </div>





                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Post Code</label>

                        <input type="number" name="post_code" maxlength="10" value="<?php echo $data->post_code ; ?>" class="form-control required" placeholder="Please Enter Post Code">

                    </div>





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="location">Status</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="status">

                             <option value="1" <?php if ($data->status=="1") {echo "selected"; }?> name="aft_qst">Active</option>

                             <option value="0" <?php if ($data->status=="0") {echo "selected"; }?> name="aft_exm">InActive</option>



                        </select>  

                    </div>





                     <div class="form-group" style="width:820px;">

                        <label class="control-label" for="location">Is Deaf</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="is_deaf">

                             <option value="1" <?php if ($data->is_deaf=="1") {echo "selected"; }?> name="aft_qst">Yes</option>

                             <option value="0" <?php if ($data->is_deaf=="0") {echo "selected"; }?> name="aft_exm">No</option>



                        </select>  

                    </div>





                     <div class="form-group" style="width:820px;">

                        <label class="control-label" for="location">Flash Needed</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="is_flash_required">

                             <option value="1" <?php if ($data->is_flash_required=="1") {echo "selected"; }?> name="aft_qst">Yes</option>

                             <option value="0" <?php if ($data->is_flash_required=="0") {echo "selected"; }?> name="aft_exm">No</option>



                        </select>  

                    </div>



                



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="location">Driver Type</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="driver_type">

                             <option value="0" <?php if ($data->driver_type=="0") {echo "selected"; }?> name="aft_qst">Driver Cum Owner</option>

                             <option value="1" <?php if ($data->driver_type=="1") {echo "selected"; }?> name="aft_exm">Non Driving Partner</option>

                             

                        </select>  

                    </div>





                



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="image">Image</label>

                        <div class="form-control ">

                        <input type="file" name="image" class="" size="20" />

                        </div>

                       <div>

                       <img src="<?php echo base_url($data->image); ?>" width="100px;" height="100px" />

                       </div>

                    </div>



                    

                   

                    

                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update Driver</button>

                </form>



            </div>

        </div>

    </div>

    <!--/span-->



</div><!--/row-->





