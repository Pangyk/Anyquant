function search(){
	
	var inputnum=$("#input1-group2").val();
	if(inputnum==""){
		//alert("输入的内容为空");
		$(function(){
    	    toastr.warning('输入的内容为空', '搜索结果：', {
    	    	positionClass: "toast-top-left",
    	        closeButton: false,
    	        progressBar: false,
    	    });
    	  });
		returnFirst();
		//回到最初
		
		return;
	}
	
	
	$.getJSON('getSearchResult.jsp',{name:inputnum}, function (data) {
		var up="<i class=\"fa fa-arrow-up\" style=\"color: red;\"></i>";
        var down="<i class=\"fa fa-arrow-down\" style=\"color: green;\"></i>";
        var i=1;
        
        if (data.length==0){
        	//alert("没找到结果");
        	$(function(){

        	    toastr.error('没找到结果', '搜索结果：', {
        	    	positionClass: "toast-top-left",
        	        closeButton: false,
        	        progressBar: false,
        	       
        	    });
        	  });
        	    
        	//document.getElementById("ul").style.display="block";
        	pageFunc(1);
        }else {
            //alert(data.length/4);
            for (i=1;i<=101;i++){
               var id=1000+i+"";
               if ((i-1)*4<data.length){
            	   if (data[3+(i-1)*4]>=0){
                 	  document.getElementById(id).innerHTML="<td>"+
                 	  data[0+(i-1)*4]+"</td>"+"<td>"+data[1+(i-1)*4]+"</td>"+"<td>"+data[2+(i-1)*4]+"</td>"+"<td>"+up+"</td>";		
                      }else {
                 			document.getElementById(id).innerHTML="<td>"+
                 			data[0+(i-1)*4]+"</td>"+"<td>"+data[1+(i-1)*4]+"</td>"+"<td>"+data[2+(i-1)*4]+"</td>"+"<td>"+down+"</td>";
                 			
                 		}
                   var jqu="tablefunc('"+data[0+(i-1)*4]+"')";
                   var jquid="#"+id;
              	   $(jquid).attr({	
              		   
              		   "onclick" : jqu
                		
              	   });
                	
              	 document.getElementById(id).style.display="table-row";
         		
                }else {
                	document.getElementById(id).style.display="none";

                }
               }
        

           document.getElementById("ul").style.display="none";
   		
        }
		
		
    });
	
}
function entersearch(){
	var event = window.event || arguments.callee.caller.arguments[0];  
    if (event.keyCode == 13)  
       {  
          search();  
       }  
}