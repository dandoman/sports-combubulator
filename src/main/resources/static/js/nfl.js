$(document).ready(function(){
    $.get( "/api/match", { league: "NFL"} )
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			console.log(arrayItem);
    			$("#matches").append(`<div id="${arrayItem.match.matchId}"><p><strong>${arrayItem.match.visitorTeamName} @ ${arrayItem.match.homeTeamName}</strong>
    			<br>Kickoff: ${new Date(arrayItem.match.matchStartTime).toLocaleString()}
    			<br>${arrayItem.match.visitorTeamName} - Votes to win: <strong>${arrayItem.countAwayWins}</strong> Median predicted score: <strong>${arrayItem.medianAwayScore}</strong>
    			<br>${arrayItem.match.homeTeamName} -  Votes to win: <strong>${arrayItem.countHomeWins}</strong> Median predicted score: <strong>${arrayItem.medianHomeScore}</strong>
    			<br>Your prediction - ${arrayItem.match.visitorTeamName}: <input size="2" type="text" class="visitorScorePrediction" value="${arrayItem.predictedVisitorScore != null ? arrayItem.predictedVisitorScore : ""}"> ${arrayItem.match.homeTeamName}: <input size="2" type="text" class="homeScorePrediction" value="${arrayItem.predictedHomeScore != null ? arrayItem.predictedHomeScore : ""}">
    			<br><button onClick="createOrUpdatePrediction('${arrayItem.match.matchId}', ${arrayItem.predictedWinnerId != null})">${arrayItem.predictedWinnerId != null ? "Update Prediction" : "Submit Prediction"}</button></p></div>`);
    		});
  		}
  	);
});

function createOrUpdatePrediction(matchId, isDefined) {
	if(isDefined) {
		updatePrediction(matchId);
	} else {
		createPrediction(matchId);
	}
}

function createPrediction(matchId) {
    var visitorScore = $('#' + matchId).find(".visitorScorePrediction").val();
    var homeScore = $('#' + matchId).find(".homeScorePrediction").val();
    
  	$.ajax({
    	url: '/api/match/prediction',
    	type: 'post',
    	data: JSON.stringify({
        	matchId: matchId,
        	homeTeamScore: homeScore,
        	visitorTeamScore: visitorScore
    	}),
    	contentType: 'application/json',
    	success: function (data) {
        	location.reload(true);
    	},
    	error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.responseJSON.message);
      	}
	});
}

function updatePrediction(matchId) {
    var visitorScore = $('#' + matchId).find(".visitorScorePrediction").val();
    var homeScore = $('#' + matchId).find(".homeScorePrediction").val();
    
  	$.ajax({
    	url: '/api/match/prediction',
    	type: 'put',
    	data: JSON.stringify({
        	matchId: matchId,
        	homeTeamScore: homeScore,
        	visitorTeamScore: visitorScore
    	}),
    	contentType: 'application/json',
    	success: function (data) {
        	location.reload(true);
    	},
    	error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.responseJSON.message);
      	}
	});
}
