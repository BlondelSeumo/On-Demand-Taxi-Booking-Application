
$( "form.validate" ).submit(function( event ) {

	var access = true;
	$(this).find('.required').each(function() {
		var v = $(this).val();
		if((v.replace(/\s+/g, '')) == '') {
			access = false;
			$(this).parents(".form-group").addClass("has-error");
		}
		else {
			var n = $(this).attr("name");
			if(n == "phone_no") {
				var phoneno = /^\d{10}$/;
				if(v.match(phoneno)) {
					$(this).parents(".form-group").removeClass("has-error");
				}
				else {
					access = false;
					$(this).parents(".form-group").addClass("has-error");
				}
			}
			else {
				$(this).parents(".form-group").removeClass("has-error");
			}
		}
	});
	if(access) {
		return;
	}
	else {
		$("body").animate({ scrollTop: $('.has-error').offset().top - 50 }, "slow");
	}
	event.preventDefault();
	
});

$( "form.validate .required" ).change(function( event ) {
	var v = $(this).val();
	if((v.replace(/\s+/g, '')) == '') {
		$(this).parents(".form-group").addClass("has-error");
	}
	else {
		$(this).parents(".form-group").removeClass("has-error");
	}
});
// View Shop Details
$(function() {
	

$('#pick-map').click(function (e) {
        e.preventDefault();
        $('#mapModal').modal('show');
});
	
$('#mapModal').on('shown.bs.modal', function () {
	load_map();
	//google.maps.event.trigger(map, 'resize')
});

$('.select-location').click(function (e) {
	$('#latitude').val($('#pick-lat').val());
	$('#longitude').val($('#pick-lng').val());
	$('#mapModal').modal('hide');
});





$('.thumbnails').on('click', '.gallery-delete', function (e) {
    e.preventDefault();
    //get image id
    var id = $(this).parents('.thumbnail').data("id");
	var data = {id:id};
	var url = config_url+'shop/delete_gallery_image';
	var result = post_ajax(url, data);
	if(result != 'error') {
		$(this).parents('.thumbnail').fadeOut();
	}
    });
    
 $('.view-customer').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'customer/view_single_customer';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	
	reload_gallery();
	
});

  $('.view-completed').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'booking/view_single_completed';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	
	reload_gallery();
	
});






$('.view-car').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'car/view_single_car';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	//reload_gallery();
});



$('.view-driver').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'driver/view_single_driver';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});




$('.view-customer').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'customers/view_single_customer';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-promocode').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'promocode/view_single_promocode';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-pattern').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'pattern/view_single_pattern';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-document').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'document/view_single_document';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});







$('.view-verified').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'document/view_single_verified';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
	reload_action();
});

function reload_action(){
	$("#approve_click").on('click',function(){
		var id = $(this).data('id');
		var data = {id:id,status:2};
		var url = config_url+'document/update_doc_status';
		var result = post_ajax(url, data);		
		alert('Document Approved successfully');
		window.location.reload();
	})

	$("#reject_click").on('click',function(){
		var id = $(this).data('id');
		var data = {id:id,status:3};
		var url = config_url+'document/update_doc_status';
		var result = post_ajax(url, data);
		alert('Document Rejected');
		window.location.reload();
	})
}





$('.view-requestdoc').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'document/view_single_request';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	//reload_gallery();
	call_action();
});


function call_action(){
	$('.view-approved').on("click", function() {
		alert('sdfsdfsdfds');
		var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
		$('#myModal .modal-body').html(loader);
		$('#myModal').modal({show:true});
		var id = $(this).data('id');
		var data = {id:id};
		var url = config_url+'document/view_single_approved';
		var result = post_ajax(url, data);
		$('#myModal .modal-body').html(result);
		reload_gallery();
	});	
}








$('.view-feedback').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'feedback/view_single_feedback';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});


$('.view-request').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'request/view_single_request';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-all').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'booking/view_single_all';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-bookings').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var status = $(this).data('status');
	if(status == "Booked") {
		$("#complete-booking").show();
		
	}
	else {
		$("#complete-booking").hide();
	}
	$("#complete-booking").attr("data-id",id);
	var data = {id:id};
	var url = config_url+'booking/view_booking_details';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	setTimeout(function() {
		add_services();
	},1000);
	
});

$('#complete-booking').on("click", function() {
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'booking/complete_booking_details';
	var result = post_ajax(url, data);
	location.reload();
	
	
});



$('.view-cancelled').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'booking/view_single_cancelled';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});



$('.view-onprocess').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'booking/view_single_onprocess';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	reload_gallery();
});









$('.view-help').on("click", function() {
	var loader = '<p class="text-center"><img src="'+config_url+'/assets/images/ajax-loader-4.gif" /></p>';
	$('#myModal .modal-body').html(loader);
	$('#myModal').modal({show:true});
	var id = $(this).data('id');
	var data = {id:id};
	var url = config_url+'help/view_single_help';
	var result = post_ajax(url, data);
	$('#myModal .modal-body').html(result);
	//reload_gallery();
});

});


function post_ajax(url, data) {
	var result = '';
	$.ajax({
        type: "POST",
        url: url,
		data: data,
		success: function(response) {
			result = response;
		},
		error: function(response) {
			result = 'error';
		},
		async: false
		});
		
		return result;
}

function add_services() {
	$('.btn-minimize').on("click",function (e) {
        e.preventDefault();
        var $target = $(this).parent().parent().next('.box-content');
        if ($target.is(':visible')) $('i', $(this)).removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        else                       $('i', $(this)).removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
        $target.slideToggle();
    });
	
	$('#add_new_services').on("click",function (e) {
        e.preventDefault();
		var data = $('#new_services_form').serialize();
		if(data=='') {
			$('.new_service_message').show();
		}
		else {
			$('.new_service_message').hide();
			data = data+"&booking_id="+$(this).data("id")+"&shop_id="+$(this).data("shop");
			var url = config_url+'booking/add_new_services';
			var result = post_ajax(url, data);
			if(result == "success") {
				sessionStorage.load_booking = $(this).data("id");
				location.reload();
			}
		}
    });
}

function reload_gallery() {
	
	$('.thumbnail a').colorbox({
        rel: 'thumbnail a',
        transition: "elastic",
        maxWidth: "95%",
        maxHeight: "95%",
        slideshow: true
    });
	
}

function remove_shop_service() {
	$('.remove_services').on("click", function() {
		var id = $(this).data("id");
		var data = {id:id};
		var url = config_url+'shop/remove_shop_service';
		var result = post_ajax(url, data);
		if(result != 'Error') {
			$(this).parents('.row').first().remove();
		}
	});
	
}

function load_map() {
	var map = new google.maps.Map(document.getElementById('map_canvas'), {
						zoom: 7,
						center: new google.maps.LatLng(35.137879, -82.836914),
						mapTypeId: google.maps.MapTypeId.HYBRID
					});
					
	var myMarker = new google.maps.Marker({
		position: new google.maps.LatLng(9.369, 76.803),
		draggable: true
	});
	
	var latitude = document.getElementById('pick-lat');
	var longitude = document.getElementById('pick-lng');
	
	google.maps.event.addListener(myMarker, 'dragend', function (evt) {
		document.getElementById('current').innerHTML = '<p>Marker dropped: Current Lat: ' + evt.latLng.lat().toFixed(3) + ' Current Lng: ' + evt.latLng.lng().toFixed(3) + '</p>';
		latitude.value = evt.latLng.lat().toFixed(3);
		longitude.value = evt.latLng.lng().toFixed(3);
	});
	
	google.maps.event.addListener(myMarker, 'dragstart', function (evt) {
		document.getElementById('current').innerHTML = '<p>Currently dragging marker...</p>';
	});
	
	map.setCenter(myMarker.position);
	myMarker.setMap(map);
}