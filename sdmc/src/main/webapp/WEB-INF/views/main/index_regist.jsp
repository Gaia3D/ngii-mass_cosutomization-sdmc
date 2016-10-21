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
<script src="<%=contextRoot%>common/js/common.js" ></script>
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

<form id="frm" name="frm" action="<%=contextRoot%>signup.ngii" method="post">
	<input type="hidden" name="nextPageName" value="" />
<div id="JoinWrap">
	<div class="loginTitle">
    	맞춤형 공간정보제공시스템 회원가입
    </div>
    <fieldset>
        <p>
        	<input type="text" name = "email" id = "email1" size="31" placeholder="이메일"> @
            <input type="text" name = "email2" id = "email2" size="31">
        </p>
        <p>
            <input type="password" name = "password" id = "password1" size="31" placeholder="패스워드">
            <input type="password" name = "password2" id = "password2" size="31" placeholder="패스워드 확인">
        </p>
    </fieldset>
    
    <fieldset>
        <p style="display:none;">
        	<input type="text" name = "name" id ="name" size="31" value="none" placeholder="이름">
        </p>
        <p>
            <input type="text" maxlength="3" name="tel1" id="tel1" size="10" placeholder="연락처"> -
            <input type="text" maxlength="4" name="tel2" id="tel2" size="10"> -
            <input type="text" maxlength="4" name="tel3" id="tel3" size="10">
        </p>
    </fieldset>

    <textarea id="agreementrule2" rows="10" cols="93">
  가. 개인정보의 수집 및 이용 목적 : 제도 및 시스템 개선 관련 각종 연구용역 및 설문조사, 통계분석
 
  나. 개인정보 필수 수집 항목 : 이메일, 비밀번호, 휴대폰번호
 
  다. 개인정보의 보유 및 이용기간 : 2년(사이트 폐쇄시 즉시 폐기)
 
  라. 개인정보 수집방법 : 국토지리정보원은 별도의 개인정보를 수집하지 않습니다. 다만 자료 다운로드 주체의 식별을 위하여 개인정보를 일정기간 보존하게 됩니다.
        
  마. 정보주체의 권리(동의거부권) : 본 홈페이지 이용자는 개인정보 수집·이용을 거부할 권리가 있으나, 필수항목 수집을 거부할 경우 회원가입이 불가능합니다.
       
  바. 기타 : 기타 개인정보의 수집, 처리, 이용에 대한 사항은 국토지리정보원 개인정보보호방침을 참조하시기 바랍니다.
    </textarea>
    <p>
    	<label>
        	<input type="checkbox" name ="checkbox" id = "checkbox"> 개인수집 및 이용에 동의합니다.
        </label>
    </p>
    <p class="btnCenter">
    	<button type="button" class="join" onclick = "btn_js_confirm_click();">회원가입</button>
        <button type="button" class="cancle" onclick="goPage('main/index_id');">취소</button>
    </p>
    
</div>
<!-- END WRAP --> 
</form>
</body>
</html>


