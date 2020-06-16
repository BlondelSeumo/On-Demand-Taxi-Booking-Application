<?php
if($this->session->flashdata('message')) {
  $message = $this->session->flashdata('message');

  ?>
<div class="alert alert-<?php echo $message['class']; ?>">
<button class="close" data-dismiss="alert" type="button">×</button>
<?php echo $message['message']; ?>
</div>
<?php
}
?>
<div class="row">
    <div class="box col-md-12">
    <div class="box-inner">
    <div class="box-header well" data-original-title="">
        <h2><i class="glyphicon glyphicon-eye-open"></i> <?php echo $page_title; ?></h2>
    </div>
    <div class="box-content">
    <table class="table table-striped table-bordered bootstrap-datatable datatable responsive">
    <thead>
    <tr>

        <th class="hidden">Id</th>
        <th>ID</th>
        <th>Source</th>
        <th>Destination</th>
        <th>Customer</th>
        <th>Driver</th>        
        <th>Car Type</th>
        <th>Pattern Name</th> 
        <th>Booking Date</th>
        <th>Amount</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
	foreach($data as $all) {
        //print_r($all->pattern_id);

        $label = get_color($all->status);

        /*-$label = "success";
        if($all->status == "Booked") {
            $label = "info";
        }*/
		
			//	 print_r($all->source);
	?>
    <tr>

        <td class="hidden"><?php echo $all->id; ?></td>
        <td class="center"><?php echo $all->booking_id; ?></td> 
        <td class="center"><?php echo $all->source; ?></td>
        <td class="center"><?php echo $all->destination; ?></td>
        <td class="center"><?php echo $all->name; ?></td>
        <td class="center"><?php echo $all->driver_name; ?></td>
        
        <td class="center"><?php echo get_carname($all->car_type); ?></td> 
        <td class="center"><?php echo get_patternname($all->pattern_id); ?></td> 
        <td class="center"><?php if($all->book_date!='') echo date("d-M-Y",$all->book_date); ?></td>
        <!-- <td class="center"><?php echo "&#8377".$all->fare; ?></td> -->
        <td class="center"><?php echo $all->fare; ?></td>
        <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo get_status($all->status); ?></span></td>
        
        <td class="center">
            <a class="btn btn-success btn-sm view-all" href="javascript:void(0);" id="view-<?php echo $all->id; ?>" data-status="<?php echo $all->status; ?>" data-id="<?php echo $all->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                View Details
            </a>
        </td>




        </td>
    </tr>
    <?php
	}
	?>
    </tbody>
    </table>
    </div>
    </div>
    </div>
    <!--/span-->

    </div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3><center>View All Bookings</center></h3>
                </div>
                <div class="modal-body">
                    <p class="text-center"><img src="<?php echo base_url(); ?>assets/images/ajax-loader-4.gif" /></p>
                </div>
                <div class="modal-footer">
                    <a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
                </div>
            </div>
        </div>
    </div>