<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	// context root를 찾기 위한 프로세스... (context가 변경되더라도 바로 적용할 수 있도록)
	HttpSession session  = request.getSession();
	String contextRoot = session.getServletContext().getContextPath();
	
	// windows와 linux의 context 결과값은 서로 다르다!! 맨뒤의 "/"를 일괄적으로 붙이기 위하여 조건문으로 확실히 한다!!
	if(!"/".equals(contextRoot.substring(contextRoot.length()-1, contextRoot.length())) ){
		contextRoot += "/";
	}
%>

<head>

<title>맞춤형 공간정보제공시스템</title>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta name="format-detection" content="telephone=no" />

<link href="<%=contextRoot%>common/css/style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="<%=contextRoot%>common/js/jqui/jquery-ui.css">

<c:if test="${param.page == 'main'}">
<link rel="stylesheet" href="<%=contextRoot%>common/js/leaflet/leaflet.css" />
<link rel="stylesheet" href="<%=contextRoot%>common/js/leaflet/locationfilter.css" />
</c:if>

<jsp:include page="./scripts.jsp" >
	<jsp:param value="${param.page}" name="page"/>
</jsp:include>
<!-- 
<c:out value="${param.page}" />
 -->
</head>
