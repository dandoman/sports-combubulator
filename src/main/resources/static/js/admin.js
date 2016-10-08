$(document).ready(function(){
    $.get( "/api/admin/teams", { league: "NFL"} )
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			$(".awayTeamSelector").append(`<option value="${arrayItem.id}">${arrayItem.name}</option>`);
    			$(".homeTeamSelector").append(`<option value="${arrayItem.id}">${arrayItem.name}</option>`);
    		});
  		}
  	);
  	jQuery('#datetimepicker').datetimepicker();
});

function createMatch() {
    var awayTeamId = $(".createMatch").find(".awayTeamSelector").val();
    var homeTeamId = $(".createMatch").find(".homeTeamSelector").val();
    var date = $('#datetimepicker').datetimepicker('getValue');
    
  	$.ajax({
    	url: '/api/admin/match',
    	type: 'post',
    	data: JSON.stringify({
        	homeTeamId: homeTeamId,
        	awayTeamId: awayTeamId,
        	startTime: date,
        	league: 'NFL'
    	}),
    	contentType: 'application/json',
    	success: function (data) {
        	alert("Success");
    	},
    	error: function (xhr, ajaxOptions, thrownError) {
    		if(typeof responseJSON !== 'undefined' && typeof responseJSON.message !== 'undefined') {
	    		alert(xhr.responseJSON.message);
    		} else {
    			alert("Error");
    		}
      	}
	});
}