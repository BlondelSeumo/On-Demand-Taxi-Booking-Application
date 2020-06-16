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

                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo $page_title; ?></h2>



            </div>

            

            <div class="box-content">

                 <form role="form" method="post" class="validate" enctype="multipart/form-data">

                    

                    

                    <!-- <div class="form-group" style="width:820px;">



                        <label class="control-label" for="name">Name</label>

                        <input type="text" name="driver_name"  class="form-control required" placeholder=" Please Enter Driver Name">

                    </div> -->





    <div class="form-group" style="width:820px;">



                        <label class="control-label" for="name">Name</label>

                      <!--   <input type="text" title="Only enter letters" /> -->

                       <input type="text" title="Only enter letters" name="driver_name"  class="form-control required" placeholder=" Please Enter Driver Name">

                   </div>





                    <!--  <div class="form-group">

                        <label class="control-label" for="category">Car Type</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="car_id">

                             <option value="1" name="aft_qst">Logo</option>

                             <option value="2" name="aft_exm">LaX</option>

                             <option value="3" name="aft_exm">LaXL</option>

                             <option value="4" name="aft_exm">LaXLL</option>

                        </select>  

                    </div> -->







                   <div class="form-group" style="width:500px;">

                        <label class="control-label" for="name">Car Type</label>

                        <select name="car_id" class="form-control required">

                            <option value="" selected="selected">Select Car type </option>

                            <?php

                            //get_carname($data->car_type);

                            foreach ($car_type as $rs) {?>

                            <option value="<?php echo $rs->id; ?>"><?php echo $rs->name; ?></option>

                            <?php } ?>

                        </select>

                    </div>









                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">License Number</label>

                        <input type="text" name="license_no" class="form-control required" placeholder=" Please Enter License Number">

                    </div>







                    

                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Car Model</label>

                        <input type="text" name="name" class="form-control required" placeholder=" Please Enter Car Model">

                    </div>





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Vehicle Registration Number</label>

                        <input type="text" name="vehicle_reg_no" class="form-control required" placeholder=" Please Enter Registration Number">

                    </div>



                     <div class="form-group" style="width:820px;">

                        <label class="control-label"   for="name">Phone</label>

                        <input type="number" name="phone"  pattern="[0-9]{5}[-][0-9]{7}[-][0-9]{1}[+]{1}" class="form-control required" placeholder=" Please Enter Phone Number">

                    </div>



                    



                   <!--   <div class="form-group" style="width:820px;">

                        <label class="control-label"   for="name">Phone</label>

                        <input type="text" name="phone"  class="form-control required" placeholder=" Please Enter Phone Number">

                    </div> -->





                   <!--  <div class="form-group">

                        <label class="control-label" for="name">Email</label>

                        <input type="text" name="email" class="form-control required" placeholder=" Please Enter Email">

                    </div> -->



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="email_id">Email</label>

                        <input type="email" name="email" class="form-control required" placeholder="Please Enter Email">

                    </div>


                      <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Password</label>
                        <input type="password" name="password" class="form-control required" value="" placeholder="Enter Password">
                    </div>



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">City</label>

                        <input type="text" name="city" class="form-control required" placeholder=" Please Enter City">

                    </div>





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">State</label>

                        <input type="text" name="state" class="form-control required" placeholder=" Please Enter State">

                    </div>

<!-- 

                    <div class="form-group">

                        <label class="control-label" for="name">Address</label>

                        <input type="text" name="address" class="form-control required" placeholder=" Please Enter Address">

                    </div> -->



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="description">Address</label>

                        <textarea name="address" placeholder="Please Enter Address" rows="3" class="form-control required"></textarea>

                    </div>



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Post Code</label>

                        <input type="number" name="post_code" maxlength="10" class="form-control required" placeholder=" Please Enter Post Code">

                    </div>







                   







                    





                    <div class="form-group">

                        <label class="control-label" for="category">Status</label><br>

                         <select id="dropdown" class="col-md-1" data-rel="chosen" name="status">

                             <option value="1" name="aft_qst">Active</option>

                             <option value="0" name="aft_exm">InActive</option>   

                        </select>  

                    </div>

                    

                    <div class="form-group">

                        <label class="control-label" for="category">Is Deaf</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="is_deaf">

                             <option value="1" name="aft_qst">Yes</option>

                             <option value="0" name="aft_exm" selected>No</option>   

                        </select>  

                    </div>



                    <div class="form-group">

                        <label class="control-label" for="category">Flash Needed</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen" name="is_flash_required">

                             <option value="1" name="aft_qst">Yes</option>

                             <option value="0"  name="aft_exm" selected>No</option>   

                        </select>  

                    </div>



                    <div class="form-group">

                        <label class="control-label" for="category">Driver Type</label><br>

                         <select id="dropdown" class="col-md-3" data-rel="chosen"  name="driver_type">

                             <option value="0" name="aft_qst">Driver Cum Owner</option>

                             <option value="1" name="aft_exm">Non Driving Partner</option>   

                        </select>  

                    </div>





                     <div class="form-group">

                        <label class="control-label" for="image">Image</label>

                        <input type="file" multiple name="image" id="image" class="required" class size="20" />

                         <div id="image_req" style="color: red"></div>

                       

                    </div>



                    

                     

                     

                    

                    

                    <button type="submit" class="btn btn-custom new_driver"><i class="glyphicon glyphicon-plus"></i> Add Driver</button>

                </form>



            </div>

        </div>

    </div>

    <!--/span-->



</div><!--/row-->





