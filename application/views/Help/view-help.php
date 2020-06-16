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
        <th>Heading</th>
        <!-- <th>Content</th> -->
        <!-- <th>Image</th> -->
        <th>Rating</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <?php
   
    foreach($data as $help) {
        //print_r($help->id);
                 
    ?>
    <tr>
        <td class="hidden"><?php echo $help->id; ?></td>
        <td class="center"><?php echo $help->head; ?></td>
        <!-- <td class="center"><?php echo $help->content; ?></td> -->
        <!-- <td class="center"><?php echo $help->image; ?></td> -->

       <!--  <td class="center"><img src="<?php echo $help->image; ?>" width="100px" height="100px"  /></td> -->
    
      <td class="center">
        Likes:
        <?php echo get_likes($help->id); ?>&nbsp;| 
        <!-- <br> -->
        DisLikes:
        <?php echo get_dislikes($help->id); ?></td>  

   



         <td class="center">
            <a class="btn btn-success btn-sm view-help" href="javascript:void(0);" data-id="<?php echo $help->id; ?>">
               <i class="glyphicon glyphicon-zoom-in icon-white"></i> 
                View
            </a>
            <a class="btn btn-info btn-sm" href="<?php echo base_url(); ?>help/edit/<?php echo $help->id; ?>">
                <i class="glyphicon glyphicon-edit icon-white"></i>
                Edit
            </a>
           
            <a class="btn btn-danger btn-sm" onclick="return confirm_fun()" href="<?php echo base_url(); ?>help/delete_help/<?php echo $help->id; ?>">
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
                    <h3><center>View Help</center></h3>
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