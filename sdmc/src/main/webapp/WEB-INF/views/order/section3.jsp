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

        	<h4 class="hide">시점</h4>
            <input type="text" class="search" size="20" placeholder="최신생성자료(기본)" id="datePicker" disabled="disabled"/>
            <!-- <button type="button" class="ico cal">검색</button> -->
            <label><input id="checkLatestVersion" type="checkbox"> 최신버전으로 받기</label>
            <!-- <button id="addDate" type="button" class="ico add">추가</button> -->
