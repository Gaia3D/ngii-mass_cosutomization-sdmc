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

            <h4 style="width:320px" class="h">
            	행정구역 선택
                <button id="addSelection" type="button" class="ico add">추가</button>
            </h4>
            <div class="division">
                <h5 class="hide">시,도 선택</h5>
                <ul id="sido" style="width:110px;"></ul>
                <h5 class="hide">군,구 선택</h5>
                <ul id="sgg"></ul>
                <h5 class="hide">지역선택</h5>
                <ul id="umd"></ul>
            </div>
            
            
            <h4>도로명</h4>
            <input type="text" class="search" size="35" placeholder="도로명을 입력하세요" />
            <button type="button" class="ico sea">검색</button>
            
            <h4>도엽명, 도엽번호</h4>
            <input type="text" id="dyCode" class="search" size="35" placeholder="도엽번호를 입력하세요" />
            <button id="searchDy" type="button" class="ico sea" onclick="goDoyeupSearch();return false;">검색</button>
            <button id="addSelectionDy" type="button" class="ico add">추가</button>

            
            <div class="MapWrap">
                <h4 class="hide"> 지도에서 선택</h4>
<jsp:include page="../import/map.jsp" />
            </div>
            <!-- END MapWrap --> 
