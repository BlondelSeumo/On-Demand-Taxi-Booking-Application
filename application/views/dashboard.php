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
$menu = $this->session->userdata('admin');
?>



<div class=" row">
    <div class="col-md-3 col-sm-3 col-xs-6">
        <a data-toggle="tooltip" title="" class="well top-block" href="<?php base_url(); ?>dashboard/running">
            <i class="glyphicon glyphicon-briefcase blue"></i>

            <div>Running Rides</div>
            <div><?php echo $shops; ?></div>
           
        </a>
    </div>
	<?php
	if($menu=='1'){
		?>
    <div class="col-md-3 col-sm-3 col-xs-6">
        <a data-toggle="tooltip" title="" class="well top-block" href="<?php base_url(); ?>customers/view_customer">
            <i class="glyphicon glyphicon-user green"></i>

            <div>Total Customers</div>
            <div><?php echo $customers; ?></div>
        </a>
    </div>

    <div class="col-md-3 col-sm-3 col-xs-6">
        <a data-toggle="tooltip" title="" class="well top-block" href="<?php base_url(); ?>driver/view_driver">
            <i class="glyphicon glyphicon-user yellow"></i>

            <div>Total Drivers</div>
            <!-- <div><?php echo $users; ?></div> -->
            <div><?php echo get_drivers(); ?></div>
        </a>
    </div>
<?php
	}
	?>
    <div class="col-md-3 col-sm-3 col-xs-6">
        <a data-toggle="tooltip" title="" class="well top-block" href="<?php base_url(); ?>booking/view_all">
            <i class="glyphicon glyphicon-book red"></i>

            <div>Total Bookings</div>
            <div><?php echo $bookings; ?></div>
        </a>
    </div>
</div>

