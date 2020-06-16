






<div class="row">
    <div class="box col-md-12">
        <div class="box-inner">
            <div class="box-header well" data-original-title="">
                <h2><i class="glyphicon glyphicon-plus"></i> <?php echo $page_title; ?></h2>

            </div>
            <div class="box-content">
                 <form role="form" method="post" class="validate" enctype="multipart/form-data">
                    
                    
                    <div class="form-group">
                        <label class="control-label" for="name">Code</label>
                        <input type="text" name="code" class="form-control required" placeholder=" Please Enter Code">
                    </div>

                    <div class="form-group">
                        <label class="control-label" for="birthdate">Start Date</label>
                        <input type="text" name="start_date" class="form-control required" data-field="date" placeholder="Please Enter Start Date">
                    </div>

                    <div id="dtBox"></div>
                    <div class="form-group">
                        <label class="control-label" for="birthdate">Expiration Date</label>
                        <input type="text" name="expiration_date" class="form-control required" data-field="date" placeholder="Please Enter Expiration Date">
                    </div>

                    <!-- <div class="form-group">
                        <label class="control-label" for="name">Offer</label>
                        <input type="number" name="off" class="form-control required" placeholder=" Please Enter Offer">
                    </div> -->

                    <div class="form-group">
                        <label class="control-label" for="name">Offer Amount</label>
                        <input type="number" name="off" class="form-control required" placeholder=" Please Enter Offer">
                    </div>
					
					 <div class="form-group">
                        <label class="control-label" for="name">Offer Usage Count</label>
                        <input type="number" name="promo_usage" class="form-control required" placeholder=" Please Enter Offer Usage Count">
                    </div>
					 

                    <br>
    
                    
                    <button type="submit" class="btn btn-custom"><i class="glyphicon glyphicon-plus"></i> Add Promocode</button>
                </form>

            </div>
        </div>
    </div>
    <!--/span-->

</div><!--/row-->




