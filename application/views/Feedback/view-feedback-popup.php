<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Feedback</h2>
                </div>
                <div class="box-content">
                    <dl>
                    
                    	

                        <dt>
                        Booking ID:  <span style="font-weight: normal !important"><?php echo get_booking_id($data->trip_id); ?></span>
                        </dt>
                        <br/> 

                      

                        <dt>
                        Customer:  <span style="font-weight: normal !important"><?php echo get_custname($data->cust_id); ?></span>
                        </dt>
                        <br/> 

                                           
                         <dt>
                        Driver:  <span style="font-weight: normal !important"><?php echo get_driver_name($data->driver_id); ?></span>
                        </dt>
                        <br/> 
                       


                        <dt>
                        Source:  <span style="font-weight: normal !important"><?php echo get_source($data->cust_id); ?></span>
                        </dt>
                        <br/> 
                       

                      

                          <dt>
                        Destination:  <span style="font-weight: normal !important"><?php echo get_destination($data->id); ?></span>
                        </dt>
                        <br/> 

                     

                          <dt>
                        Rating:  <span style="font-weight: normal !important"><?php  echo $data->rating; ?></span>
                        </dt>
                        <br/>

                     

                         <dt>
                        Good Feedback:  <span style="font-weight: normal !important"><?php echo $data->good_feedback; ?></span>
                        </dt>
                        <br/> 


                      

                          <dt>
                        Bad Feedback:  <span style="font-weight: normal !important"><?php echo $data->bad_feedback; ?></span>
                        </dt>
                        <br/> 

                      

                           <dt>
                        Driver Feedback:  <span style="font-weight: normal !important"><?php echo $data->driver_feedback; ?></span>
                        </dt>
                        <br/> 

                          <dt>
                        Trip Feedback:  <span style="font-weight: normal !important"><?php echo $data->trip_report; ?></span>
                        </dt>
                        <br/> 
                       
                   
                        
                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

