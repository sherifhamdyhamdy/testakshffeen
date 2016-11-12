<%@page import="com.sget.akshef.utils.Performance"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 style="text-align: center;color: green">AkshfFein Services</h1><br/>

<%
	Runtime r = Runtime.getRuntime();
%>

<h3 style="text-align: center;color: gray">Before release memory</h3><br/>

	Total : <%= r.totalMemory() %>
	Max : <%= r.maxMemory() %>
	Free : <%= r.freeMemory() %>

	<%
		Performance.releaseMemory();
	%>
	 
	<br/>
	
	<h3 style="text-align: center;color: gray">After release memory</h3><br/>
	
	Total : <%= r.totalMemory() %>
	Max : <%= r.maxMemory() %>
	Free : <%= r.freeMemory() %>
	
</body>
</html>