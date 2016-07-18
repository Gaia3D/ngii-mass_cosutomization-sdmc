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



<!-- 공지 LAYER -->
    <h6 class="info">
        알려드립니다.
    </h6>
    <p class="text">
    	현대백화점카드,현대S카드를 소지하신 회원님은 수강신청시 입회비(5천원)가 면제되며 카드를 소지하지 않은 분들은 5천원이 추가되오니 입회비 면제를 원하시면 인터넷으로 회원가입후 내방하여 주시기 바랍니다.
    </p>
    <p class="layerbtn">
    	<button type="button" onclick="closeNotice();">닫기</button>
    </p>