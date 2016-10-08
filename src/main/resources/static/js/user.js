$(document).ready(function(){
    $.get( "/api/user/predictions")
  		.done(function( data ) {
    		data.forEach(function(arrayItem){
    			$("#predictions").append(`<div id="${arrayItem.matchData.match.matchId}"><p><strong>${arrayItem.matchData.match.visitorTeamName} @ ${arrayItem.matchData.match.homeTeamName}</strong>
    			<br>Match Date: ${new Date(arrayItem.matchData.match.matchStartTime).toDateString()}
    			<br>Your Prediction: ${arrayItem.matchData.match.visitorTeamName}:${arrayItem.matchData.predictedVisitorScore} ${arrayItem.matchData.match.homeTeamName}:${arrayItem.matchData.predictedHomeScore}
    			<br>Concensus Score: ${arrayItem.matchData.match.visitorTeamName}:${arrayItem.matchData.medianAwayScore} ${arrayItem.matchData.match.homeTeamName}:${arrayItem.matchData.medianHomeScore}
    			<br>${(arrayItem.awayFinalScore !== null && arrayItem.homeFinalScore != null) ? "Actual Score: <strong>" + arrayItem.matchData.match.visitorTeamName + ":" + arrayItem.awayFinalScore + " " + arrayItem.matchData.match.homeTeamName + ":" + arrayItem.homeFinalScore + "</strong>" : ""}</p></div>`);
    		});
  		}
  	);
});