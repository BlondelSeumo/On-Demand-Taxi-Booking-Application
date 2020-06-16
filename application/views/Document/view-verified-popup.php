<div class="row">
    <div class="box col-md-12">
            <div class="box-inner">
                <div class="box-header well" data-original-title="">
                    <h2><i class="glyphicon glyphicon-th"></i> Document</h2>
                </div>
                <div class="box-content">
                    <dl>
                   <!--  <?php print_r($data); ?> -->
                        <dt>
                        Driver:  <span style="font-weight: normal !important"><?php echo get_driver_name($data->driver_id); ?></span>
                        </dt>
                        <br/>                  
                         
                        <dt>
                        <b>Document</b>: <span style="font-weight: normal !important"><?php echo document_list($data->type); ?></span>
                        </dt> 
                        <br>


                             <dd>
                    <ul class="thumbnails gallery">
                        <li class="thumbnail" data-id="<?php echo $data->image; ?>">
                            <a style="background:url(<?php echo $data->image; ?>) no-repeat; background-size:400px; width:400px; height:300px;"
                                    href="<?php echo $data->image; ?>"></a>
                        </li>
                    </ul>
                        </dd> 

            <?php

            if($data->status==1){?>

                    <table><tr><td class="center"> &nbsp;&nbsp;&nbsp;&nbsp;
            <a class="btn btn-success btn-sm view-approved" style="background:"  href="javascript:void(0);" data-id="<?php echo $data->id; ?>" id="approve_click">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                Approved
            </a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

             <a class="btn btn-success btn-sm view-rejected" style="background:#f44336"  href="javascript:void(0);" data-id="<?php echo $data->id; ?>"  id="reject_click">
                <i class="glyphicon glyphicon-zoom-in icon-white"></i>
                Rejected
            </a></td></tr></table>
            <?php } else {
                echo $data->status==2?"<strong>Document Approved</strong>":"<strong>Document Rejected</strong>";

            } ?>
                       
                   
                        
                    </dl>
                </div>
            </div>
    </div>
    

    
</div>

