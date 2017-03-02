function getAdvice(){
	var id=document.getElementById("stockcode").innerText;
	if(id=="sh000300"){
		return;
	}
	
	
	$.getJSON('getAdvices.jsp',{name:id}, function (data) {
		document.getElementById("advice1").innerText=data[0];
		document.getElementById("advice2").innerText=data[1];
		
		var bindex= $('#range_indate').data().from;
		var sindex= $('#range_outdate').data().from;
		var numindex= $('#range_08').data().from;
		
		var buydate=getBuyDateByID(bindex);
		var selldate=getSellDateByID(sindex);
		var sharenum=getShareNumByID(numindex);
		
		document.getElementById("advice3").innerText=buydate+"买入"+sharenum+"股"
		+document.getElementById("stockname").innerText+","
		+document.getElementById("strategytext1").innerText+"；"
		+document.getElementById("strategytext2").innerText+"；该策略"
		+document.getElementById("strategytext3").innerText+"。";
    });	
	
}