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
        <th hidden="true">Id</th>
        <th>Name</th>
        <th>Car Type</th>
        <th> Car Model</th>
        
        
        <th>License Number</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Address</th>
        <th>Rating</th>
        <th>Status</th>
        <th>Vehicle Reg Number</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
   
    foreach($data as $driver) {
        //print_r($driver->id);
        $label = "success";
        if($driver->status == 1) {
            $rs = "Active";
        }elseif($driver->status == 0){
            $rs = "InActive";
        }
                 
    ?>
    <tr>
        <td hidden="true">$driver->id</td>
        <td class="center"><?php echo $driver->driver_name; ?></td>
        <td class="center"><?php echo get_carname($driver->car_id); ?></td> 
        <td class="center"><?php echo $driver->name; ?></td>
        <!-- <td class="center"><?php echo $driver->car_id; ?></td> -->
        
        
        <td class="center"><?php echo $driver->license_no; ?></td>
        <td class="center"><?php echo $driver->phone; ?></td>
        <td class="center"><?php echo $driver->email; ?></td>
        <td class="center"><?php echo $driver->address; ?></td>

        <td class="center"><?php echo get_driverrating($driver->id); ?></td> 
        <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo $rs; ?></span></td>  
        <td class="center"><?php echo $driver->vehicle_reg_no; ?></td>
        
        <td class="center">
            <a class="btn btn-success btn-sm view-driver" href="javascript:void(0);" data-id="<?php echo $driver->id; ?>">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                View
            </a>
            <a class="btn btn-info btn-sm" href="<?php echo base_url(); ?>driver/edit/<?php echo $driver->id; ?>">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
           
            <a class="btn btn-danger btn-sm" onclick="return confirm_fun()" href="<?php echo base_url(); ?>driver/delete_driver/<?php echo $driver->id; ?>">
                <i class="glyphicon glyphicon-trash icon-white"></i>
                Delete
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
                    <h3><center>View Driver</center></h3>
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