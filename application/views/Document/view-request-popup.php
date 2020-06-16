<div class="row">
	<div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Request</h2>
                </div>
                <div class="box-content">
                    <dl>
                    
                    	<dt>
                        Driver Name
                        </dt>
                        <dd>
                        <?php echo get_driver_name($data->driver_id); ?>
                        </dd>
                  
                         
                        <dt>
                        Document Type
                        </dt>
                        <dd>
                        <?php echo get_doc_name($data->id); ?>
                        </dd>

                        <!-- <dt>
                        Expiration date
                        </dt>
                        <dd>
                        <?php echo $data->expiration_date; ?>
                        </dd>


                        <dt>
                        Type
                        </dt>
                        <dd>
                        <?php echo $data->type; ?>
                        </dd>

                        <dt>
                        Offer
                        </dt>
                        <dd>
                        <?php echo $data->off; ?>
                        </dd>

                        <dt>
                        Promotype
                        </dt>
                        <dd>
                        <?php echo $data->promo_type; ?>
                        </dd>
                        -->
                        <br>
                        <br>

                             <dd>
                    <ul class="thumbnails gallery">
                        <li class="thumbnail" data-id="<?php echo $data->image; ?>">
                            <a style="background:url(<?php echo $data->image; ?>) no-repeat; background-size:200px; width:190px; height:190px;"
                                    href="<?php echo $data->image; ?>"></a>
                        </li>
                    </ul>
                        </dd>








                        <td class="center">
            <a class="btn btn-success btn-sm view-approved" style="background:#f44336"  href="javascript:void(0);" data-id="<?php echo $data->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                Approved
            </a>
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp

             <a class="btn btn-success btn-sm view-rejected" style="background:#f44336"  href="javascript:void(0);" data-id="<?php echo $data->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                Rejected
            </a>
           <!--  <a class="btn btn-info btn-sm" href="<?php echo base_url(); ?>data/edit/<?php echo $data->id; ?>">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
           
            <a class="btn btn-danger btn-sm" onclick="return confirm_fun()" href="<?php echo base_url(); ?>data/delete_car/<?php echo $car->id; ?>">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
            </a> -->
        </td>







                   
                        
                    </dl>
                </div>
            </div>
    </div>
	

    
</div>

