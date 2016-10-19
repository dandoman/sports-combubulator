function goToURL(url) {
    window.location.href = url;
}

function createUser() {
	var dialog, form,
 
    username = $( "#username" ),
    email = $( "#email" ),
    password = $( "#password" ),
 	allFields = $( [] ).add( username ).add( email ).add( password ),
 	tips = $( ".validateTips" );
 	
    function updateTips( t ) {
      tips
        .text( t )
        .addClass( "ui-state-highlight" );
      setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
    }
    
    function addUser() {
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
    
 
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "Create an account": addUser,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        form[ 0 ].reset();
        allFields.removeClass( "ui-state-error" );
      }
    });
 
    form = dialog.find( "form" ).on( "submit", function( event ) {
      event.preventDefault();
      addUser();
    });
 
    $( "#create-user" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });
}