<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%

	HttpSession session  = request.getSession();
	String contextRoot = session.getServletContext().getContextPath();
	String userEmail = (String)session.getAttribute("userEmail");
	String userName = (String)session.getAttribute("userName");

	if(userEmail == null){
%>
	<script>
		location.href="<%=contextRoot%>";
	</script>
<%		
	} else {
%>
<!DOCTYPE HTML>
<html lang="ko">

<jsp:include page="../import/head.jsp" >
	<jsp:param value="main" name="page"/>
</jsp:include>

<body>

<div id="HeaderWrap">
<jsp:include page="../import/top.jsp" />
</div>
<!-- END HEADER -->

<div id="wrap">
	<div class="contents">
    	<!-- 위치설정 -->
        <h3>1. 위치를 설정하세요</h3>
        <div class="section" style="height:410px"> 
<jsp:include page="../order/section1.jsp" />
        </div>
        <!-- END 위치설정 -->
        
        
        <h3>2. 자료의 종류를 선택하세요</h3>
        <div class="section">
<jsp:include page="../order/section2.jsp" />
        </div>
        <!-- END 자료의 종류 -->
        
       	<h3>3. 시점을 선택하세요</h3>
        <div class="section">
<jsp:include page="../order/section3.jsp" />
        </div>
        <!-- END 자료의 종류 -->
        
        
    </div>
    <!-- END CONTENTS -->
    
    <div class="ResultSection">
<jsp:include page="../order/dataList.jsp" />
    </div>
    <!-- END RESULT SECTION -->	
</div>
<!-- END WRAP -->  
 
<div class="footer">
<jsp:include page="../import/footer.jsp" />
</div>
<!-- END FOOTER WRAP --> 
 
<!-- END NOTICE WRAP --> 
<div class="layer" style="display:none;">
<jsp:include page="../import/notice.jsp" />
</div>
<!-- END NOTICE WRAP --> 

</body>
</html>
<%	
	}
%>
