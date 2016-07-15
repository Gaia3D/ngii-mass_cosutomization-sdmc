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
	<jsp:param value="mypg" name="page"/>
</jsp:include>

<body>

<div id="HeaderWrap">
<jsp:include page="../import/top.jsp" />
</div>
<!-- END HEADER -->

<div id="wrap">
	<h2>나의 자료목록</h2>
    <div class="ListWrap">
<jsp:include page="../mypg/orderList.jsp" />
    </div>
    <!-- END LIST -->
    
    <div class="view">
<jsp:include page="../mypg/orderDetail.jsp" />
    </div>
    <!-- END VIEW -->
</div>
<!-- END WRAP -->  
 
<div class="footer">
<jsp:include page="../import/footer.jsp" />
</div>
<!-- END FOOTER WRAP --> 

</body>
</html>
<%	
	}
%>
