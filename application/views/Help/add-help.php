<script src="//tinymce.cachefly.net/4.2/tinymce.min.js"></script>
<script>tinymce.init({selector:'textarea'});</script>
<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo $page_title; ?></h2>

            </div>
            <div class="box-content">
                 <form role="form" method="post" class="validate" enctype="multipart/form-data">
                          
                    <div class="form-group">
                        <label class="control-label" for="name">Heading</label>
                        <input type="text" name="head" class="form-control required" placeholder="Please Enter The Heading">
                    </div>
                    


                    <div class="form-group">
                        <label class="control-label" for="description">Content</label>
                        <textarea name="content" placeholder="Description" rows="3" class="form-control required"></textarea>
                    </div>

                   <!--  
                    <div class="form-group">
                        <label class="control-label" for="image">Icon</label>
                        <input type="file" multiple name="image" class="required" size="20" />
                    </div> -->

                    <div class="form-group">
                        <label class="control-label" for="image">Image</label>
                        <input type="file" multiple name="image" id="image" class="required" class size="20" />
                         <div id="image_req" style="color: red"></div>
                       
                    </div>



                   <!--    <div class="form-group">
                        <label class="control-label" for="image">Icon</label>
                        <input type="file" multiple name="image" id="image" class="required"class size="20" />
                        <div id="image_req" style="color: red"></div>
                       
                    </div>
                    -->
                   
                    
                    <button type="submit" class="btn btn-custom new_driver"><i class="glyphicon glyphicon-plus"></i> Add Help</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->


