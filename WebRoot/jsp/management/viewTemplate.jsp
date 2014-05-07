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
		<%
		List<StockRuleVo> ls = (List<StockRuleVo>)request.getAttribute("ruleList");
		if (ls != null && !ls.isEmpty()) { %>
			<table class='table table-hover'>
				<thead>
					<tr><th>Template</th><th>Description</th></tr>
				</thead>
				<tbody>
				<%	for (StockRuleVo sv : ls) { %>
					<tr>
						<td>
							<a href="management?method=viewDetail&id=<%=sv.getEplId()%>"><%=sv.getEplStr()%></a>
						</td>
						<td>
							<span><%=sv.getEplDescription()%></span>
						</td>
					</tr>
				<%	} %>
				</tbody>
			</table>
		<%
		} else {
			out.println("<p>EMPTY</p>");
		}
		%>
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
			<li><a href="management?method=viewTemplate&page=<%=curRulePage-1%>">&laquo;</a></li>
		<% }
		for (i = 1; i <= pageCount; i++) { %>
			  <li <% if (i == curRulePage) out.println("class='active'"); %>><a href="management?method=viewTemplate&page=<%=i%>">1</a></li>
		<% }
		if (curRulePage == pageCount) { %>
			<li class='disabled'><a href="#">&raquo;</a></li>
		<% } else { %>
			<li><a href="management?method=viewTemplate&page=<%=curRulePage+1%>">&laquo;</a></li>
		<% } %>
		</ul>
	</div>
</div>
<jsp:include page="../template/footer.jsp"/>
</body>
</html>