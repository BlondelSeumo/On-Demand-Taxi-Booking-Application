<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Cancelled Booking </h2>
                </div>
                <div class="box-content">
                    <dl>
           

                          <dt>
                        Source:  <span style="font-weight: normal !important"><?php echo $data->source; ?></span>
                        </dt>
                        <br/>



                        <dt>
                        Destination:  <span style="font-weight: normal !important"><?php echo $data->destination; ?></span>
                        </dt>
                        <br/>   




                          <dt>
                        Booking ID:  <span style="font-weight: normal !important"><?php echo $data->booking_id; ?></span>
                        </dt>
                        <br/> 



                        

                         <dt>
                        Customer:  <span style="font-weight: normal !important"><?php echo get_custname($data->user_id); ?></span>
                        </dt>
                        <br/> 


                         <dt>
                        Driver:  <span style="font-weight: normal !important"><?php echo get_driver_name($data->driver_id); ?></span>
                        </dt>
                        <br/> 

                     
                      <dt>
                         Car Type:  <span style="font-weight: normal !important"><?php echo get_carname($data->car_type); ?></span>
                        </dt>
                        <br/> 



                        <dt>
                        Booking Date:  <span style="font-weight: normal !important"><?php if($data->book_date!='') echo date("d-M-Y",$data->book_date); ?></span>
                        </dt>
                        <br/> 

                        <dt>
                         Amount:  <span style="font-weight: normal !important"><?php echo $data->fare; ?></span>
                        </dt>
                        <br/> 

                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

