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
       <!--  <th>Customer ID</th> -->
        <th>Customer</th>
        
        <th>Source</th>
        <th>Destination</th>
        <th>Car Type</th>
        
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <?php
   
	foreach($data as $request) {
		$label = get_reqcolor($request->status);
				 
	?>
    <tr>
        <td class="hidden"><?php echo $request->id; ?></td>
        <!-- <td class="center"><?php echo $request->cust_id; ?></td> -->
        <td class="center"><?php echo get_custname($request->cust_id); ?></td>
        <!-- <td class="center"><?php echo get_car_type($request->car_type); ?></td> -->
        <td class="center"><?php echo $request->source; ?></td>
        <td class="center"><?php echo $request->destination; ?></td>
        <td class="center"><?php echo get_car_type($request->car_type); ?></td>
        <!-- <td class="center"><?php echo $request->status; ?></td> -->


        <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo get_reqstatus($request->status); ?></span></td>
        
        <!-- <td class="center">
            <a class="btn btn-success btn-sm view-request" href="javascript:void(0);" data-id="<?php echo $request->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                View
            </a>
  
        </td> -->
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
                    <h3><center>View Request</center></h3>
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