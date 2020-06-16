        

<div class="row">

    <div class="box col-md-12">

        <div class="box-inner">

            <div class="box-header well" data-original-title="">



                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo "Create New Region"; ?></h2>

            </div>

            <div class="box-content">

                 <form role="form" method="post" class="validate" >



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Region Name</label>

                        <input type="text" name="pattern_name" class="form-control required" placeholder=" Please Enter Region Name">

                    </div>







                     <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Car Type</label>

                        <select name="car_type" class="form-control required">

                            <option value="" selected="selected">Select Car type </option>

                            <?php

                            //get_carname($data->car_type);

                            foreach ($car_type as $rs) {?>

                            <option value="<?php echo $rs->id; ?>"><?php echo $rs->name; ?></option>

                            <?php } ?>

                        </select>

                    </div>





                    <!-- <div class="controls">

                        <select id="selectError" class="col-md-3" data-rel="chosen" name="car_type">

                            <?php

                            foreach($car_type as $rs) {

                            

                            echo "<option value='".$rs->id."'>".$rs->name."</option>";

                            

                                                     }

                            ?>

                        </select>

                        </div> -->









                   <!--  <div class="form-group ">

                        <label class="control-label" for="shop_id">Car Type</label>

                        <div class="controls">

                        <select id="selectError" class="col-md-3" data-rel="chosen" name="car_type">

                            <?php

                            foreach($car_type as $rs) {

                            

                            echo "<option value='".$rs->id."'>".$rs->name."</option>";

                            

                                                     }

                            ?>

                        </select>

                        </div>

                    </div> -->

                    





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Base Price</label>

                        <input type="number" name="base_price" class="form-control required" placeholder=" Please Enter Base Price">

                    </div>

                                               

                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Kilometre Rate</label>

                        <input type="text" name="km_rate" data-parsley-pattern="^[a-zA-Z\.  \/]+$" class="form-control required" placeholder=" Please Enter the Kilometre Rate">

                    </div>

                    

                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Minute Rate</label>

                        <input type="number" name="min_rate" class="form-control required" placeholder=" Please Enter Minimum Rate">

                    </div>







                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Range</label>

                        <input type="number" name="range" class="form-control required" placeholder=" Please Enter Range">

                    </div>



                   <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Location</label>

                        <input type="text" name="location" id="location" class="form-control required" placeholder="Please Enter Location">

                    </div>



                    

                    

                    <input type="hidden" name="lat" id="lat">

                    <input type="hidden" name="lng" id="lng">

                    <input type="hidden" name="currency" id="currency">

                                 

                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Add Region</button>

                </form>



            </div>

        </div>

    </div>

    



</div>





