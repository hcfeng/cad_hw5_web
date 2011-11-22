<%@ page language="java" contentType="text/html; cccc"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>CAD_HW5_WEB</title>
</head>
<body>
    <%
    	String showmsg = (String) request.getParameter("showmsg");
    	if (showmsg!=null) {
    %>
    		 <span style="color: red;"><%=showmsg %></span><br/>
    <%		
    	}    else {
    %>
    		 <span style="color: red;">&nbsp;</span><br/>
    <%
    	}
    %>
	
	<form method="post" action="SendQueue" />
	請輸入要處理的數字，若有多個，請以","隔開:<br/>
	<i>範例: 1,2,33,45,77,99</i>
	<br/>
	<br />
	<input type="text" name="numbers" style="width: 400px;" />
	<br />
	<input type="submit" value="送出" />
	</form>
</body>
</html>