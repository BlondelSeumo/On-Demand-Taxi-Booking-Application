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
        <th>Name</th>
        <th>Email</th>
        <th>Phone Number</th>
        <th>Status</th>
        <th>No Of Bookings</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
    foreach($data as $customer) {
           
        $label = "success";
        if($customer->status == 1) {
            $rs = "Active";
        }elseif($customer->status == 0){
            $rs = "InActive";
        }
       //print_r($customer->status);
       // $label = get_custcolor($customer->status);
    ?>
    <tr>
        
        <td class="hidden"><?php echo $customer->id; ?></td>
        <td class="center"><?php echo $customer->name; ?></td>
        <td class="center"><?php echo $customer->email; ?></td>
        <td class="center"><?php echo $customer->phone; ?></td>
         <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo $rs; ?></span></td>  
       <!--  <td class="center"><span class="label label-<?php echo $label; ?>"><?php echo $verified->status_rs; ?></span></td> -->
        <td class="center"><?php echo get_bookings($customer->id); ?></td>  


            <td class="center">
            <a class="btn btn-success btn-sm view-customer" href="javascript:void(0);" id="view-<?php echo $customer->id; ?>" data-status="<?php echo $customer->status; ?>" data-id="<?php echo $customer->id; ?>">
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

<div class="modal modal-wide fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3><center>View Customer</center></h3>
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