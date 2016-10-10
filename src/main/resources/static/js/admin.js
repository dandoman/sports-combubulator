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
  	$.get( "/api/admin/match/incomplete", { league: "NFL"} )
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			$(".matchSelector").append(`<option value="${arrayItem.matchId}">${arrayItem.visitorTeamName} @ ${arrayItem.homeTeamName} - ${new Date(arrayItem.matchStartTime).toLocaleString()}</option>`);
    		});
  		}
  	);
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

function updateScore() {
    var matchId = $(".updateScore").find(".matchSelector").val();
    var awayScore = $("#awayScore").val();
    var homeScore = $("#homeScore").val();
    var homeScoreInt = parseInt(homeScore, 10);
    var awayScoreInt = parseInt(awayScore, 10);
    
  	$.ajax({
    	url: '/api/admin/match/score',
    	type: 'post',
    	data: JSON.stringify({
        	matchId: matchId,
        	finalAwayScore: awayScoreInt,
        	finalHomeScore: homeScoreInt
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