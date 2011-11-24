<%@ page language="java" contentType="text/html; cccc"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CAD_HW5_WEB</title>
</head>
<body onLoad="onLoadProcess();">
	<script type='text/javascript' src='http://www.google.com/jsapi'></script>
	<script type="text/javascript">
		google.load("prototype", "1.7.0.0");
		
		function onLoadProcess() {
			document.getElementById("nums").focus(); 
			getMessage();
		}
		
		function checkMessage( str ) {
			str = str.replace(" ","");
		    var regExp = /^[\d|,]+$/;
		    if (regExp.test(str)) {
		    	var entry = str.split(",");		        
		    	for(var i=0;i<entry.length;i++){
		    		  if (entry[i]=="") {
		    			  return false;
		    		  }
		    	}
		        return true;
			} else {
		        return false;
			}
		}      
		
		function sendMessage(){	
			var n = document.getElementById("nums").value;			
			if (checkMessage(n)==false) {
				alert("請依規則輸入數字組");
			} else {
			    new Ajax.Request(
					"SendMessage", { 
			    	method:'POST',
			    	parameters: "numbers="+n,
			    	onComplete:function(result){ 
			    		addToList(result.responseText);
			    	} 
			    } );     
			    document.getElementById("nums").value = "";
			    document.getElementById("nums").focus();
			}
        } 

		function getMessage() {
			new Ajax.Request(
					"GetMessage", { 
			    	method:'POST',				    	
			    	onComplete:function(result){ 
			    		if (result.responseText!=''){
				    		addToList(result.responseText);
			    		}	
				    	setTimeout("getMessage()", 5000);			    		
			    	} 
			    } );      
        }

        function addToList(mesg) {
        	var div = document.getElementById("dTableDiv");
        	var tbl = document.getElementById("dTable");
        	var row = tbl.insertRow(tbl.rows.length);
        	var cell = row.insertCell(0);
        	cell.innerHTML = mesg;
        	cell.style.color="white";  
        	div.scrollTop = div.scrollHeight
        }                
               
	</script>

	請輸入要處理的數字組，數字間請以 "," 隔開，並按「送出」:
	<br />
	<i>範例: 1,2,33,45,77,99</i>
	<br />
	<br />
	<input type="text" id="nums" name="numbers" style="width: 570px;" />
	<input type="button" value="送出資料" onClick="sendMessage();" />
	<input type="button" value="清理畫面" onClick="location.reload();" />
	<input type="button" value="查詢Log檔" onClick="window.open('ListLogs');" />
	<br/><br/>
	<div id="dTableDiv" style="text-align: left; width: 820px; OVERFLOW: auto; height: 500px; background: black;" >    
	 <table id="dTable" style="background: black; width: 800px;">
	 	
	 </table>
	 </div>
</body>
</html>