$(document).ready(function(){
    $.get( "/api/match", { league: "NFL"} )
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			$("#matches").append(`<div id="${arrayItem.match.matchId}"><p><strong>${arrayItem.match.visitorTeamName} @ ${arrayItem.match.homeTeamName}</strong>
    			<br>Kickoff: ${new Date(arrayItem.match.matchStartTime).toLocaleString()}
    			<br>${arrayItem.match.visitorTeamName} - Votes to win: <strong>${getHiddenOrActualValue(arrayItem,arrayItem.countAwayWins)}</strong> Median predicted score: <strong>${getHiddenOrActualValue(arrayItem,arrayItem.medianAwayScore)}</strong>
    			<br>${arrayItem.match.homeTeamName} -  Votes to win: <strong>${getHiddenOrActualValue(arrayItem,arrayItem.countHomeWins)}</strong> Median predicted score: <strong>${getHiddenOrActualValue(arrayItem,arrayItem.medianHomeScore)}</strong>
    			<br>Your prediction - ${arrayItem.match.visitorTeamName}: <input size="2" type="text" class="visitorScorePrediction" value="${arrayItem.predictedVisitorScore != null ? arrayItem.predictedVisitorScore : ""}"> ${arrayItem.match.homeTeamName}: <input size="2" type="text" class="homeScorePrediction" value="${arrayItem.predictedHomeScore != null ? arrayItem.predictedHomeScore : ""}">
    			<br><button onClick="createOrUpdatePrediction('${arrayItem.match.matchId}', ${arrayItem.predictedWinnerId != null})">${arrayItem.predictedWinnerId != null ? "Update Prediction" : "Submit Prediction"}</button></p></div>`);
    		});
  		}
  	);
});

function getHiddenOrActualValue(arrayItem, value) {
	return (arrayItem.predictedWinnerId != null) ? value : "Hidden";
}

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
    var homeScoreInt = parseInt(homeScore, 10);
    var visitorScoreInt = parseInt(visitorScore, 10);
    if(isNaN(homeScoreInt) || isNaN(visitorScoreInt)) {
    	alert("Provided scores are not integers");
    	return;
    }
    homeScore = '' + homeScoreInt;
    visitorScore = '' + visitorScoreInt;
    
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
        	var url = "/api/match/" + matchId;
    		$.get(url) 
    			.done(function( arrayItem ) {
    				$("#matches").find('#' + matchId).find("p")
    				.replaceWith(`<p><strong>${arrayItem.match.visitorTeamName} @ ${arrayItem.match.homeTeamName}</strong>
    				<br>Kickoff: ${new Date(arrayItem.match.matchStartTime).toLocaleString()}
    				<br>${arrayItem.match.visitorTeamName} - Votes to win: <strong>${arrayItem.countAwayWins}</strong> Median predicted score: <strong>${arrayItem.medianAwayScore}</strong>
    				<br>${arrayItem.match.homeTeamName} -  Votes to win: <strong>${arrayItem.countHomeWins}</strong> Median predicted score: <strong>${arrayItem.medianHomeScore}</strong>
    				<br>Your prediction - ${arrayItem.match.visitorTeamName}: <input size="2" type="text" class="visitorScorePrediction" value="${arrayItem.predictedVisitorScore != null ? arrayItem.predictedVisitorScore : ""}"> ${arrayItem.match.homeTeamName}: <input size="2" type="text" class="homeScorePrediction" value="${arrayItem.predictedHomeScore != null ? arrayItem.predictedHomeScore : ""}">
    				<br><button onClick="createOrUpdatePrediction('${arrayItem.match.matchId}', ${arrayItem.predictedWinnerId != null})">${arrayItem.predictedWinnerId != null ? "Update Prediction" : "Submit Prediction"}</button></p>`)
    			}
  			);
    	},
    	error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.responseJSON.message);
      	}
	});
}

function updatePrediction(matchId) {
    var visitorScore = $('#' + matchId).find(".visitorScorePrediction").val();
    var homeScore = $('#' + matchId).find(".homeScorePrediction").val();
    var homeScoreInt = parseInt(homeScore, 10);
    var visitorScoreInt = parseInt(visitorScore, 10);
    if(isNaN(homeScoreInt) || isNaN(visitorScoreInt)) {
    	alert("Provided scores are not integers");
    	return;
    }
    homeScore = '' + homeScoreInt;
    visitorScore = '' + visitorScoreInt;
    
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
    		var url = "/api/match/" + matchId;
    		$.get(url) 
    			.done(function( arrayItem ) {
    				$("#matches").find('#' + matchId).find("p")
    				.replaceWith(`<p><strong>${arrayItem.match.visitorTeamName} @ ${arrayItem.match.homeTeamName}</strong>
    				<br>Kickoff: ${new Date(arrayItem.match.matchStartTime).toLocaleString()}
    				<br>${arrayItem.match.visitorTeamName} - Votes to win: <strong>${arrayItem.countAwayWins}</strong> Median predicted score: <strong>${arrayItem.medianAwayScore}</strong>
    				<br>${arrayItem.match.homeTeamName} -  Votes to win: <strong>${arrayItem.countHomeWins}</strong> Median predicted score: <strong>${arrayItem.medianHomeScore}</strong>
    				<br>Your prediction - ${arrayItem.match.visitorTeamName}: <input size="2" type="text" class="visitorScorePrediction" value="${arrayItem.predictedVisitorScore != null ? arrayItem.predictedVisitorScore : ""}"> ${arrayItem.match.homeTeamName}: <input size="2" type="text" class="homeScorePrediction" value="${arrayItem.predictedHomeScore != null ? arrayItem.predictedHomeScore : ""}">
    				<br><button onClick="createOrUpdatePrediction('${arrayItem.match.matchId}', ${arrayItem.predictedWinnerId != null})">${arrayItem.predictedWinnerId != null ? "Update Prediction" : "Submit Prediction"}</button></p>`)
    			}
  			);
    	},
    	error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.responseJSON.message);
      	}
	});
}
