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
        <th>Driver</th>
        <th>Document</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
  //print_r($data);

	foreach($data as $verified) {
		$label = get_doccolor($verified->status);				 
	?>
    <tr>
        <td class="hidden"><?php echo $verified->id; ?></td>
        <td class="center"><?php echo $verified->driver_name; ?></td>

       <td class="center"><?php echo document_list($verified->type); ?></td>
       <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo $verified->status_rs; ?></span></td>


        <td class="center">
            <a class="btn btn-success btn-sm view-verified" href="javascript:void(0);" data-id="<?php echo $verified->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                View
            </a>
       <!--      <a class="btn btn-info btn-sm" href="<?php echo base_url(); ?>promocode/edit_promocode/<?php echo $promocode->id; ?>">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
           
            <a class="btn btn-danger btn-sm" href="<?php echo base_url(); ?>promocode/delete_promocode/<?php echo $promocode->id; ?>">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
            </a> -->
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
                    <h3><center>Document Status</center></h3>
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