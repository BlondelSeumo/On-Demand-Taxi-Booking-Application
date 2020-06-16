<script src="//tinymce.cachefly.net/4.2/tinymce.min.js"></script>

<script>tinymce.init({selector:'textarea'});</script>



<div class="row">

    <div class="box col-md-12">

        <div class="box-inner">

            <div class="box-header well" data-original-title="">

                <h2><i class="glyphicon glyphicon-edit"></i> <?php echo $page_title; ?></h2>



            </div>

            <div class="box-content">

                <form role="form" method="post" class="validate" enctype="multipart/form-data">







                    <div class="form-group">

                        <label class="control-label" for="name">Heading</label>

                        <input type="text" name="head" value="<?php echo $data->head; ?>" class="form-control required" placeholder="Please Enter The Heading">

                    </div>



                     <div class="form-group">

                        <label class="control-label" for="description">Content</label>

                        <textarea name="content" placeholder="Description" rows="3" class="form-control required"><?php echo $data->content; ?></textarea>

                    </div>

                    

                    <div class="form-group">

                        <label class="control-label" for="profile_pic">Icon</label>

                        <div class="form-control ">

                        <input type="file" name="image" class="" size="20" />

                        </div>

                       <div>

                       <!-- <img src="<?php echo $data->image; ?>" width="100px;" height="100px" /> -->

                        <img src="<?php echo base_url($data->image); ?>" width="100px;" height="100px" style="border:1px solid #000" />

                       </div>

                    </div>

                    

                    



                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update Help</button>

                </form>



            </div>

        </div>

    </div>

    <!--/span-->



</div><!--/row-->





