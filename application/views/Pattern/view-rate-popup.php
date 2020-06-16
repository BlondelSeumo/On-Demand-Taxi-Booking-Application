<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Rate </h2>
                </div>
                <div class="box-content">
                    <dl>
                    
                    	


                            <dt>
                        Region Name:  <span style="font-weight: normal !important"><?php echo $data->pattern_name; ?></span>
                        </dt>
                        <br/>
                         
                     

                      <dt>
                        Car Name:  <span style="font-weight: normal !important"><?php echo get_carname($data->car_type); ?></span>
                        </dt>
                        <br/>

                        <!--       <dt>
                        Car Name:  <span style="font-weight: normal !important"><?php echo $data->car_type; ?></span>
                        </dt>
                        <br/> -->

                      


                            <dt>
                        Base Price:  <span style="font-weight: normal !important"><?php echo $data->base_price; ?></span>
                        </dt>
                        <br/>

                       


                            <dt>
                        Kilometre Rate:  <span style="font-weight: normal !important"><?php echo $data->km_rate; ?></span>
                        </dt>
                        <br/>

                    

                              <dt>
                        Minute Rate:  <span style="font-weight: normal !important"><?php echo $data->min_rate; ?></span>
                        </dt>
                        <br/>

                    

                               <dt>
                        Location:  <span style="font-weight: normal !important"><?php echo $data->location; ?></span>
                        </dt>
                        <br/>
                        

                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

