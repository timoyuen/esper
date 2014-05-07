<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@page import="esperengine.stock.*" %>
<jsp:include  page="../template/header.jsp">
    <jsp:param name="title" value='<%=request.getAttribute("title")%>'/>
</jsp:include>
<body>
<jsp:include page="userHeadBar.jsp">
    <jsp:param name="curPage" value='<%=request.getAttribute("curPage")%>'/>
</jsp:include>
<div class="container bs-docs-container">
	<% response.sendRedirect("management?method=viewHistory"); %>
</div>
<jsp:include page="../template/footer.jsp"/>
</body>
</html>