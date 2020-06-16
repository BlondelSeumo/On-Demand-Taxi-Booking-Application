
<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo $page_title; ?></h2>

            </div>
            <div class="box-content">
                 <form role="form" method="post" class="validate" enctype="multipart/form-data">
                    



                      <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Car Type</label>
                        <input type="text" name="name" class="form-control required" placeholder=" Please Enter Car Type">
                    </div>


                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Maximum Seats</label>
                        <input type="number" name="max_seat" class="form-control required" placeholder=" Please Enter Maximum seats">
                    </div>


                       <div class="form-group">
                        <label class="control-label" for="image">Image</label>
                        <input type="file" multiple name="image" id="image" class="required"class size="20" />
                        <div id="image_req" style="color: red"></div>
                       
                    </div>

         
                    
                    <button type="submit" class="btn btn-custom new_driver"><i class="glyphicon glyphicon-plus"></i> Add Car</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->


