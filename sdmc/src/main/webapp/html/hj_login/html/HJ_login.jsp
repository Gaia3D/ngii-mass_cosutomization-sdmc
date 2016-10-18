<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String id = request.getParameter("usermail");
String pass = request.getParameter("password");
if(!id.equals("testid")||!pass.equals("testpass")){
	System.out.println("로그인 실패");
}else{
	System.out.println("로그인 성공");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div align = center>
<hr>
<%= id %><br>
<%= pass %>
</div>
</body>
</html>