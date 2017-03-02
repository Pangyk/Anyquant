function  updateSlider(){
	var slidernor = $("#range_03").data("ionRangeSlider");
	var id=document.getElementById("stockcode").innerText;
	
	$.getJSON('getSliderData.jsp',{name:id}, function (data) {
		slidernor.update({
			 min: data[2],
		        max: data[3],
		        from: data[0],
		        to: data[1]
		});
    });
}

function initslider(){
$(function() {
	$("#range_08").ionRangeSlider(
			{
				grid : true,
				from : 5,
				values : [ 100, 500, 1000, 3000, 5000, 10000, 30000, 50000,
						80000, 100000, 200000 ],
				onFinish : function(obj) { 
					sliderdone();
				}
			});

	var indatearr = getBuyindate();

	$("#range_indate").ionRangeSlider({
		grid : false,
		from : 5,
		values : indatearr,
		onFinish : function(obj) {
			sliderdone();
		}

	});
	var selldate = getSelldate();

	$("#range_outdate").ionRangeSlider({
		grid : false,
		from : 5,
		values : selldate,
		onFinish : function(obj) {
			sliderdone();
		}
	});
	
	
	
	var id=document.getElementById("stockcode").innerText;
	$.getJSON('getSliderData.jsp',{name:id}, function (data) {
		$("#range_03").ionRangeSlider({
	        type: "double",
	        grid: true,
	        min: data[2],
	        max: data[3],
	        from: data[0],
	        to: data[1],
	        postfix: "元",
	        onFinish : function(obj) { 
				normalslider();
			}
	    });

		
    });
	

});
}
