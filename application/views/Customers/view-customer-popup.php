<div class="row">
  <div class="box col-md-12">
    <div class="box-inner">
      <div class="box-header well" data-original-title="">
        <h2>
          <i class="glyphicon glyphicon-th">
          </i> Customer Details
        </h2>
      </div>
      <div class="box-content">
        <dl>
          <dt>
            Customer:  
            <span style="font-weight: normal !important">
              <?php echo $data->name; ?>
            </span>
          </dt>
          <br/>
          <dt>
            No of bookings:  
            <span style="font-weight: normal !important">
              <?php echo get_bookings($data->id); ?>
            </span>
          </dt>
          <br/>
          <dt>
            Phone Number:  
            <span style="font-weight: normal !important">
              <?php echo $data->phone; ?>
            </span>
          </dt>
          <br/>
          <dt>
            Email address:  
            <span style="font-weight: normal !important">
              <?php echo $data->email; ?>
            </span>
          </dt>
          <br/>
          <dt>
            Promocode:  
            <span style="font-weight: normal !important">
              <?php echo $data->promocode; ?>
            </span>
          </dt>
          <br/>
          
          <dt>
            Image
          </dt>
          <dd>
            <ul class="thumbnails gallery">
              <li class="thumbnail" data-id="<?php echo $data->image; ?>">
                <a style="background:url(<?php echo $data->image; ?>) no-repeat; background-size:200px; width:190px; height:190px;"
                   href="<?php echo $data->image; ?>">
                </a>
              </li>
            </ul>
          </dd> 
        </dl>
      </div>
    </div>
  </div>
</div>
