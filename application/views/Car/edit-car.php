
<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
              
                <h2><i class="glyphicon glyphicon-edit"></i> <?php echo $page_title; ?></h2>
 
            </div>
            <div class="box-content" style="width:820px;">
                <form role="form" method="post" class="validate" enctype="multipart/form-data">
                    

                  <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Car Type</label>
                        <input type="text" name="name" value="<?php echo $data->name; ?>" class="form-control required" placeholder="Please Enter Car Type">
                    </div>

            
                      <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Maximum Seat</label>
                        <input type="number" name="max_seat" value="<?php echo $data->max_seat; ?>" class="form-control required" placeholder="Please Enter Maximum Seat">
                    </div>
                

                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="image">Image</label>
                        <div class="form-control ">
                        <input type="file" name="image" class="" size="20" />
                        </div>
                       <div>
                       <!-- <img src="<?php echo $data->image; ?>" width="100px;" height="100px" /> -->
                       <img src="<?php echo base_url($data->image); ?>" width="100px;" height="100px" />
                       </div>
                    </div>

                    
                   
                    
                    <button  type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update Car</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->


