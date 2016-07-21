<%@page import="java.net.URLDecoder"%>
<%@page import="kr.ngii.pilot.sdmc.util.StringUtil"%>
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
	
	String userEmail = (String)session.getAttribute("userEmail");
	String userName = (String)session.getAttribute("userName");
	
	if(!StringUtil.isEmpty(userEmail))
		userEmail = URLDecoder.decode(userEmail, "UTF-8");
	
	if(!StringUtil.isEmpty(userName))
		userName = URLDecoder.decode(userName, "UTF-8");


%>
    <div class="header">

        <h1><span onclick="goPage('main/main');return false;">맞춤형 공간정보제공시스템</span></h1>
        <p class="gnb">
            <a href="#" id="navMypg" class="new" onclick="goPage('main/myPage');return false;">나의 자료목록</a>
        </p>
        <!-- 로그아웃 -->
        <p class="login">
        	<span><%=userEmail %></span>님이 접속하셨습니다.
            <button type="button" class="small" onclick="logout();return false;">로그아웃</button>
        </p>
        <!-- 로그인 
        <p class="login">
            <button type="button" class="small">로그인</button>
        </p>
        -->
    </div>
    
