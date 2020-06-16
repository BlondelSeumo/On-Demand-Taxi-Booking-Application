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
         <th>Source</th>
        <th>Destination</th>
        <th>Customer Name</th>
        <th>Driver</th>
        <th>Booking ID</th>
        <th>Car Type</th>
        <th>Booking Date</th>
        <th>Amount</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
    //print_r($data->driver_name);
   
    foreach($data as $completed) {
        
            //print_r($completed->driver_name);     
    ?>
    <tr>
       <td class="hidden"><?php echo $completed->id; ?></td>
        <td class="center"><?php echo $completed->source; ?></td>
        <td class="center"><?php echo $completed->destination; ?></td>
        <td class="center"><?php echo $completed->name; ?></td>
        <td class="center"><?php echo $completed->driver_name; ?></td>
        <td class="center"><?php echo $completed->booking_id; ?></td>
        
        <td class="center"><?php echo get_carname($completed->car_type); ?></td> 
        <td class="center"><?php if($completed->book_date!='') echo date("d-M-Y",$completed->book_date); ?></td>
        <td class="center"><?php echo $completed->fare; ?></td>
    





            <td class="center">
            <a class="btn btn-success btn-sm view-completed" href="javascript:void(0);" id="view-<?php echo $completed->id; ?>" data-status="<?php echo $completed->status; ?>" data-id="<?php echo $completed->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                View Details
            </a>
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
                    <h3><center>View Completed</center></h3>
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