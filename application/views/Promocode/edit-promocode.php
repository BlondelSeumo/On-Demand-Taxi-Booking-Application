
<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-edit"></i> <?php echo $page_title; ?></h2>

            </div>
            <div class="box-content" style="width:820px;">
                <form role="form" method="post" class="validate" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="control-label" for="name">Code</label>
                        <input type="text" name="code" value="<?php echo $data->code; ?>" class="form-control required" placeholder="Please Enter Code">
                    </div>


                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Start Date</label>
                        <input type="text" name="start_date" value="<?php echo $data->start_date; ?>" class="form-control required" data-field="date" placeholder="Please Enter Start Date">
                    </div>

                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Expiration Date</label>
                        <input type="text" name="expiration_date" value="<?php echo $data->expiration_date; ?>" class="form-control required" data-field="date" placeholder="Please Enter Expiration Date">
                    </div>
                    <div id="dtBox"></div>



                    <div class="form-group" style="width:820px;">
                        <label class="control-label" for="name">Offer Amount</label>
                        <input type="number" name="off" value="<?php echo $data->off; ?>" class="form-control required" placeholder="Please Enter Offer">
                    </div>
					
					<div class="form-group">
                        <label class="control-label" for="name">Offer Usage Count</label>
                        <input type="number" name="promo_usage" value="<?php echo $data->promo_usage; ?>" class="form-control required" placeholder=" Please Enter Offer Usage Count">
                    </div>


                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Update Promocode</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->


