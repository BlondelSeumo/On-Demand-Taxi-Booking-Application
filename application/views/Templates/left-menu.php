<?php  
$menu = $this->session->userdata('admin');
?>
<div class="col-sm-2 col-lg-2 admin-menu">
  <div class="sidebar-nav">
    <div class="nav-canvas">
      <div class="nav-sm nav nav-stacked">
      </div>
      <ul class="nav nav-pills nav-stacked main-menu">
        <li class="nav-header">Main Menu
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>dashboard">
            <i class="glyphicon glyphicon-dashboard">
            </i>
            <span> Dashboard
            </span>
          </a> 
        </li>
        <li class="accordion">
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-book">
            </i>
            <span> Bookings
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>booking/view_all">All Bookings
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>booking/view_completed">Completed
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>booking/view_onprocess">On Processing
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>booking/view_cancelled">Cancelled
              </a>
            </li>
          </ul>
        </li> 
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>Customers/view_customer">
            <i class="glyphicon glyphicon-user">
            </i>
            <span> Customers
            </span>
          </a> 
        </li>
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-user">
            </i>
            <span> Drivers
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>driver/create">Create
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>driver">View
              </a>
            </li>
          </ul>
        </li> 
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-briefcase">
            </i>
            <span> Car Type
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>car/create">Create
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>car">View
              </a>
            </li>
          </ul>
        </li> 
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-briefcase"><i class="fa fa-map-marker"></i>
            </i>
            <span> Region
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>pattern/create">Create
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>pattern">View
              </a>
            </li>
          </ul>
        </li> 
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-barcode">
            </i>
            <span> Promocode
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>promocode/create">Create
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>promocode">View
              </a>
            </li>
          </ul>
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>document">
            <i class="glyphicon glyphicon-ok">
            </i>
            <span> Document
            </span>
          </a> 
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>feedback">
            <i class="glyphicon glyphicon-comment">
            </i>
            <span> Feedback
            </span>
          </a> 
        </li>
        <li class="accordion">
          <a href="#">
            <i class="glyphicon glyphicon-user">
            </i>
            <span> Help
            </span>
          </a> 
          <ul class="nav nav-pills nav-stacked">
            <li>
              <a href="<?php echo base_url();?>help/create">Create
              </a>
            </li>
            <li>
              <a href="<?php echo base_url(); ?>help">View
              </a>
            </li>
          </ul>
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>request">
            <i class="glyphicon glyphicon-download">
            </i>
            <span> Request
            </span>
          </a> 
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>settings">
            <i class="glyphicon glyphicon-cog">
            </i>
            <span> Settings
            </span>
          </a> 
        </li>
        <li>
          <a class="ajax-link" href="<?php echo base_url(); ?>profile">
            <i class="glyphicon glyphicon-user">
            </i>
            <span> Profile
            </span>
          </a> 
        </li>
      </ul>
    </div>
  </div>
</div>
