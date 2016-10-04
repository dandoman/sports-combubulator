$(document).ready(function(){
    $.get( "/api/match", { league: "NFL"} )
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			console.log(arrayItem);
    			$("#matches").append(`<div id="${arrayItem.match.matchId}"><p><strong>${arrayItem.match.visitorTeamName} @ ${arrayItem.match.homeTeamName}</strong>
    			<br>Kickoff: ${new Date(arrayItem.match.matchStartTime).toLocaleString()}
    			<br>${arrayItem.match.visitorTeamName} - Votes to win: <strong>${arrayItem.countAwayWins}</strong> Median predicted score: <strong>${arrayItem.medianAwayScore}</strong>
    			<br>${arrayItem.match.homeTeamName} -  Votes to win: <strong>${arrayItem.countHomeWins}</strong> Median predicted score: <strong>${arrayItem.medianHomeScore}</strong>
    			<br>Your prediction - ${arrayItem.match.visitorTeamName}: <input size="2" type="text" name="visitorScorePrediction" value="${arrayItem.predictedVisitorScore != null ? arrayItem.predictedVisitorScore : ""}"> ${arrayItem.match.homeTeamName}: <input size="2" type="text" name="homeScorePrediction" value="${arrayItem.predictedHomeScore != null ? arrayItem.predictedHomeScore : ""}">
    			<br><button>${arrayItem.predictedWinnerId != null ? "Update Prediction" : "Submit Prediction"}</button></p></div>`);
    		});
  		}
  	);
});