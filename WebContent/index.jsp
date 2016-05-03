<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TagLife</title>
</head>
<body>
<% java.util.Date d = new java.util.Date(); %>
<h1>
Today's date is <%= d.toString() %> and this jsp page worked!
</h1>
</body>
</html>