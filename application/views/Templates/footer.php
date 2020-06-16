	<hr>
  <!--   <footer class="row">
        <p class="col-md-9 col-sm-9 col-xs-12 copyright">&copy; <a href="#" target="_blank"> La Taxi </a> 2016</p>
 
    </footer> -->
<!--/.fluid-container-->

<script src="<?php echo base_url();?>/assets/js/custom-script.js"></script>

<!-- external javascript -->

<script src="<?php echo base_url();?>/assets/js/bootstrap.min.js"></script>
<!-- data table plugin -->
<script src='<?php echo base_url();?>/assets/js/jquery.dataTables.min.js'></script>
<!-- select or dropdown enhancer -->
<script src="<?php echo base_url();?>/assets/js/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="<?php echo base_url();?>/assets/js/jquery.colorbox-min.js"></script>
<!-- tour plugin -->
<script src="<?php echo base_url();?>/assets/js/bootstrap-tour.min.js"></script>
<!-- rating plugin -->
<script src="<?php echo base_url();?>/assets/js/jquery.raty.min.js"></script>
<!-- datepicker -->
<script src="<?php echo base_url();?>/assets/js/DateTimePicker.js"></script>
<!-- application script for Charisma demo -->
<script src="<?php echo base_url();?>/assets/js/charisma.js"></script>
<script src="<?php echo base_url();?>/assets/js/bootbox.js"></script>
<!--<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB1br9lwKFyEpCnS5elLan_90CCsYeak6I&libraries=places" type="text/javascript"></script> -->

<script src="http://maps.googleapis.com/maps/api/js?key=<?php echo get_key(); ?>&libraries=places" type="text/javascript"></script> 


 <script>
            var autocomplete = new google.maps.places.Autocomplete($("#location")[0], {});

            google.maps.event.addListener(autocomplete, 'place_changed', function() {
                var place = autocomplete.getPlace();

                var label_address  = place.adr_address;

                //label_address.find('span').attr('class="country-name"', '');

var lat = place.geometry.location.lat();
var lng = place.geometry.location.lng();
$('#lat').val(lat);
$('#lng').val(lng);
var latlng;
latlng = new google.maps.LatLng(lat, lng);

new google.maps.Geocoder().geocode({'latLng' : latlng}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
        if (results[1]) {
            var country = null, countryCode = null, city = null, cityAlt = null;
            var c, lc, component;
            for (var r = 0, rl = results.length; r < rl; r += 1) {
                var result = results[r];
                if (!country && result.types[0] === 'country') {
                    country = result.address_components[0].long_name;
                    countryCode = result.address_components[0].short_name;
                }

                if (country) {
                    break;
                }
            }

        }
    }
    var data = {county:country};
    var url = config_url+'Pattern/get_currency';
	var result = post_ajax(url, data);
	$('#currency').val(result);
});















            });

function confirm_fun(){
    var a = confirm("Are sure to delete this record?");
    return a;
}

$('.new_driver').on('click',function(){
    //alert($('#image').val());
    if($('#image').val()==''){
        $('#image_req').html('Image field is mandatory');
    } else {
        $('#image_req').html('');
    }
})

        </script>

