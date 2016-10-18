<%@page import="kr.ngii.pilot.sdmc.util.StringUtil"%>
<%@page import="java.net.URLDecoder"%>
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
	
	System.out.println(request.getParameter("login"));
	System.out.println(request.getParameter("code"));
	
	String userEmail = (String)session.getAttribute("userEmail");
	String userName = (String)session.getAttribute("userName");
	
	if(!StringUtil.isEmpty(userEmail))
		userEmail = URLDecoder.decode(userEmail, "UTF-8");
	
	if(!StringUtil.isEmpty(userName))
		userName = URLDecoder.decode(userName, "UTF-8");
	
	String error = request.getParameter("error");
	
	String debug = request.getParameter("debug");
%>


<!DOCTYPE HTML>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>맞춤형 공간정보제공시스템</title>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />
<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta name="format-detection" content="telephone=no" />
<link href="<%=contextRoot%>common/css/style.css" type="text/css" rel="stylesheet" />
<script src="<%=contextRoot%>common/js/jquery-1.12.3.js" ></script>
<script>

<c:if test="${param.error == 'access_denied'}">
$(document).ready(function(){
	alert("'SDMC' 애플리케이션에 대한 엑세스 권한 부여를 동의하지 않으면\n 로그인이 불가능합니다.");
});
</c:if>

<c:if test="${param.login == 'success'}">
$(document).ready(function(){
	if(!("" == "<%=userEmail%>" || "null" == "<%=userEmail%>") ){
		alert("<%=userEmail%>님 환영합니다.");
		goPage();
	}
});
</c:if>

function goPage(url){

	if(!url){
		url = 'order/searchPage';
	}

	//alert(url);
	$("#frm input").val(url);
	$("form#frm")[0].submit();

}

function goLogin(url){
	
	window.open(url);
}

</script>
</head>

<body style="overflow:hidden">

<form id="frm" name="frm" action="<%=contextRoot%>viewPage.ngii" method="post">
	<input type="hidden" name="nextPageName" value="" />
</form>

<div id="LoginWrap">
	<div class="loginTitle">
    	맞춤형 공간정보제공시스템
    </div>
    <fieldset>
        <p>
        	<input type="text" size="16" placeholder="이메일"> @
            <input type="text" size="24">
        </p>
        <p>
        	<input type="password" size="51" placeholder="패스워드">
        </p>
        <p>
        	<button type="button" class="login">로그인</button>
        </p>
    </fieldset>
	<p class="failed">
    	! 입력하신 정보가 맞지않습니다.
    </p>
    
    <p>
    	아직 회원이 아니십니까? 
        <a href="#" onclick="goPage('main/index_regist');">회원가입 하기</a>
    </p>
</div>
<!-- END WRAP --> 


<<<<<<< HEAD
=======
	// 구글+ 로그인 연계....
	function snsLogin(sns){
		var uri = "<%=contextRoot%>login.ngii?social="+ sns;
		

<% if ("true".equals(debug)){ %>
		// 로그인 회피를 위하여 
		// 로그인 회피 대신 이름과 email을 입력하여 넘어가야 하므로 직접 입력하고 넘어갈 수 있도록 처리 (직접 입력한다는 점을 제외하면 프로세스는 동일하다!!)
		var email = encodeURIComponent(prompt("이메일 주소를 입력해 주세요"));
		location.href = uri + "&debug=true&email=" + email;
<% } else {		%>
		location.href = uri;
<% } %>

	};

	// 구글+ 로그인 연계....

	function idLogin(){
		var uri = "/sdmc/signin.ngii";
		location.href=uri;
	}
</script>
>>>>>>> branch 'master' of https://github.com/Gaia3D/ngii-mass_customization-sdmc.git
</body>
</html>
