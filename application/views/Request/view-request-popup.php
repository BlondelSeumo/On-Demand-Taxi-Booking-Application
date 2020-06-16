<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Requests</h2>
                </div>
                <div class="box-content">
                    <dl>
                    
                    	<dt>
                        Customer ID
                        </dt>
                        <dd>
                        <?php echo $data->cust_id; ?>
                        </dd>
                  
                         
                        <dt>
                        Driver ID
                        </dt>
                        <dd>
                        <?php echo get_custname($data->cust_id); ?>
                        </dd>

                        <dt>
                       Car Type
                        </dt>
                        <dd>
                        <?php echo get_car_type($data->car_type); ?>
                        </dd>


                        <dt>
                        Source
                        </dt>
                        <dd>
                        <?php echo $data->source; ?>
                        </dd>

                        <dt>
                        Destination
                        </dt>
                        <dd>
                        <?php echo $data->destination; ?>
                        </dd>

         
                       
                   
                        
                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

