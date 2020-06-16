

<div class="row">

    <div class="box col-md-12">

        <div class="box-inner">

            <div class="box-header well" data-original-title="">

                <h2><i class="glyphicon glyphicon-edit"></i> Edit Region</h2>



            </div>

            <div class="box-content">

                <form role="form" method="post" class="validate" >

                    



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Region Name</label>

                        <input type="text" name="pattern_name" value="<?php echo $data->pattern_name; ?>" class="form-control required" placeholder="Please Enter  Region Name">

                    </div>





                    <!-- <div class="form-group">

                        <label class="control-label" for="name">Car Name</label>

                        <input type="text" name="car_name" value="<?php echo $data->car_name; ?>" class="form-control required" placeholder="">

                    </div> -->



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Car Type</label>

                        <!-- <input type="text" name="car_name" class="form-control required" placeholder=" Please Enter Car Name"> -->

                        <select name="car_type" class="form-control required">

                            <?php

                            foreach ($car_type as $rs) {

                                ?>

                            <option value="<?php echo $rs->id; ?>" <?php if($data->car_type == $rs->id) echo 'selected="SELECTED"'; ?>><?php echo $rs->name; ?></option>

                            <?php } ?>

                        </select>

                    </div>



                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Base Price</label>

                        <input type="number" name="base_price" value="<?php echo $data->base_price; ?>" class="form-control required" placeholder="">

                    </div>



                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Kilometre Rate</label>

                        <input type="number" name="km_rate" value="<?php echo $data->km_rate; ?>" class="form-control required" placeholder="">

                    </div>



                      <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Minute Rate</label>

                        <input type="number" name="min_rate" value="<?php echo $data->min_rate; ?>" class="form-control required" placeholder="">

                    </div>

                    





                    <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Range</label>

                        <input type="number" name="range" value="<?php echo $data->range; ?>" class="form-control required" placeholder="">

                    </div>





                     <div class="form-group" style="width:820px;">

                        <label class="control-label" for="name">Location</label>

                        <input type="text" name="location" id="location" value="<?php echo $data->location; ?>" class="form-control required" placeholder="">

                    </div>



                    



                    

                    <input type="hidden" name="lat" id="lat" value="<?php echo $data->lat; ?>">

                    <input type="hidden" name="lng" id="lng" value="<?php echo $data->lng; ?>">

                    <input type="hidden" name="currency" id="currency" value="<?php echo $data->currency; ?>">





                   <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update Region</button>

                </form>



            </div>

        </div>

    </div>

    <!--/span-->



</div><!--/row-->













