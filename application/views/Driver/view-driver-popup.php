<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Driver</h2>
                </div>
                <div class="box-content">

                    
                    <dl>
                    
                    	

                        <dt>
                        Driver:  <span style="font-weight: normal !important"><?php echo $data->driver_name; ?></span>
                        </dt>
                        <br/>



                        <dt>
                        Car Type:  <span style="font-weight: normal !important"><?php echo get_carname($data->car_id); ?></span>
                        </dt>
                        <br/>


                       


                        <dt>
                        Car Model:  <span style="font-weight: normal !important"><?php echo $data->name; ?></span>
                        </dt>
                        <br/>

                       

                         <dt>
                        License Number:  <span style="font-weight: normal !important"><?php echo $data->license_no; ?></span>
                        </dt>
                        <br/>


                     


                         <dt>
                        Phone:  <span style="font-weight: normal !important"><?php echo $data->phone; ?></span>
                        </dt>
                        <br/>

                       

                          <dt>
                        Email:  <span style="font-weight: normal !important"><?php echo $data->email; ?></span>
                        </dt>
                        <br/>




                          <dt>
                        Address:  <span style="font-weight: normal !important"><?php echo $data->address; ?></span>
                        </dt>
                        <br/>



                          <dt>
                        City:  <span style="font-weight: normal !important"><?php echo $data->city; ?></span>
                        </dt>
                        <br/>




                       
                        <dt>
                        State:  <span style="font-weight: normal !important"><?php echo $data->state; ?></span>
                        </dt>
                        <br/>

                       

                       


                       
                        <dt>
                        Post Code:  <span style="font-weight: normal !important"><?php echo $data->post_code; ?></span>
                        </dt>
                        <br/>

                       

                          <dt>
                        Vehicle Registration Number:  <span style="font-weight: normal !important"><?php echo $data->vehicle_reg_no; ?></span>
                        </dt>
                        <br/>


                          <dt>
                        Is Deaf:  <span style="font-weight: normal !important"><?php echo get_deaf_status($data->id); ?></span>
                        </dt>
                        <br/>





                           <dt>
                        Is Flash Required:  <span style="font-weight: normal !important"><?php echo get_flash($data->id); ?></span>
                        </dt>
                        <br/>
                  
                   

                              <dt>
                        Driver Type:  <span style="font-weight: normal !important"><?php echo get_drivertype($data->id); ?></span>
                        </dt>
                        <br/>

                       

                       

                      
                       <dt>
                       Image
                        </dt>
                       <dd>
                    <ul class="thumbnails gallery">
                    	<li class="thumbnail" data-id="<?php echo $data->image; ?>">
                        	<a style="background:url(<?php echo $data->image; ?>) no-repeat; background-size:200px; width:190px; height:190px;"
                                    href="<?php echo $data->image; ?>"></a>
                        </li>
                    </ul>
                        </dd>   
                        
                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

