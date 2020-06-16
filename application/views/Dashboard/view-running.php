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

        <th>Customer Name</th>
        <th>Booking ID</th>
        <th>Driver Name</th>
        <th>Source</th>
        <th>Destination</th>
        <th>Car Type</th>
        <th>Booking Date</th>

    </tr>
    </thead>
    <tbody>
    <?php
   
	foreach($data as $running) {


	?>
    <tr>
        
        <td class="center"><?php echo $running->name; ?></td>
        <td class="center"><?php echo $running->booking_id; ?></td>
        <td class="center"><?php echo $running->driver_name; ?></td>
        <td class="center"><?php echo $running->source; ?></td>
        <td class="center"><?php echo $running->destination; ?></td>

        <td class="center"><?php echo get_carname($running->car_type); ?></td> 
        <!-- <td class="center"><?php echo date("d-M-Y", strtotime($running->book_date)).", ".$running->time; ?></td> -->
        <td class="center"><?php if($running->book_date!='') echo date("d-M-Y",$running->book_date); ?></td>



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
                    <h3><center>View Running Rides</center></h3>
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