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
	<div>
		<h1>Manage MY Templates</h1>
		<% List<RuleSubscriptionVo> lrs = (List<RuleSubscriptionVo>) request.getAttribute("ruleSub");
			if (lrs == null) { %>
				<div class="alert alert-warning">You have not subscribe <strong>ANYTHING</strong></div>
			<% } else { %>
				<table class='table table-hover'>
					<thead>
						<tr>
							<td>Subscribe Time</td>
							<td>Rule Descprition</td>
							<td>Action</td>
						</tr>
					</thead>
					<tbody>
				<%
				for (RuleSubscriptionVo rs : lrs) { %>
					<tr>
						<td><%=rs.getCreateTime()%></td>
						<td><%=rs.getRuleDescription()%></td>
						<td>
							<a href="management?method=updateSubscription&subid=<%=rs.getSubId()%>">EDIT</a>
							<a href="management?method=viewGraph&subid=<%=rs.getSubId()%>">GRAPH</a>
							<a href="management?method=deleteSub&subid=<%=rs.getSubId()%>">DELETE</a>
						</td>
					</tr>
				<% } %>
				</tbody>
				</table>
			<% } %>
	</div>
	<div>
		<ul class="pagination">
		<%
		int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());
		int curRulePage = Integer.parseInt(request.getAttribute("curRulePage").toString());
		int i;
		if (curRulePage == 1) { %>
			<li class='disabled'><a href="#">&laquo;</a></li>
		<% } else { %>
			<li><a href="management?method=viewHistory&page=<%=curRulePage-1%>">&laquo;</a></li>
		<% }
		for (i = 1; i <= pageCount; i++) { %>
			  <li <% if (i == curRulePage) out.println("class='active'"); %>><a href="management?method=viewHistory&page=<%=i%>">1</a></li>
		<% }
		if (curRulePage == pageCount) { %>
			<li class='disabled'><a href="#">&raquo;</a></li>
		<% } else { %>
			<li><a href="management?method=viewHistory&page=<%=curRulePage+1%>">&laquo;</a></li>
		<% } %>
		</ul>
	</div>
</div>
<jsp:include page="../template/footer.jsp"/>
</body>
</html>