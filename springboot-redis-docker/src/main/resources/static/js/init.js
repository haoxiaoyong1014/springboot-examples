// Edit the date here

$(document).ready(function() {
						   
	$("#countdown").countdown({
				date: "12 December 2015 11:30:00",
				format: "on"
			},
			
			function() {
				// callback function
			});

});	
