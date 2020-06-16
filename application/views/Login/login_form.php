<style>
    .logo{text-align:center;margin-bottom:20px;}
    .logo img{width:120px;height:auto;}
    </style>
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
    
        <div class="well col-md-5 center login-box">
        <div class="col-md-12 center login-header" style="padding-top:0px; height:130px;">
            <div class="logo">
                <img src="<?php echo base_url().get_profile_image(); ?>" alt="">
            </div>
            
        </div>
        	<?php if(validation_errors()) { ?>
            <div class="alert alert-info">
                <?php echo validation_errors(); ?>
            </div>
          	<?php } ?>
            <form class="form-horizontal validate" action="" method="post">
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                        <input type="text" name="username" class="form-control required" placeholder="Username">
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input type="password" name="password" class="form-control required" placeholder="Password">
                    </div>
                    <div class="clearfix"></div>

                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-custom">Login</button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div>


