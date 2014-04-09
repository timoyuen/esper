<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@page import="esperengine.stock.*" %>
<html>
<head>
	<title>VIEW TEMPLATE</title>
</head>
<body>
<center>
	<form action="management/method=search">
		<input type="text" name="tag" />
		<input type="submit" value="search" />
	</form>
	<div>
		<%
		List<StockRuleVo> ls = (List<StockRuleVo>)request.getAttribute("ruleList");
		if (ls != null && !ls.isEmpty()) {
			for (StockRuleVo sv : ls) { %>
			<p>
				<a href="management?method=viewDetail&id=<%=sv.getEplId()%>"><%=sv.getEplStr()%></a>
				<span><%=sv.getEplDescription()%></span>
			</p>
		<%	}
		} else {
			out.println("<p>EMPTY</p>");
		}
		%>
	</div>
	<div>
	<%
		int pageCount = Integer.parseInt(request.getAttribute("pageCount").toString());
		int curPage = Integer.parseInt(request.getAttribute("curPage").toString());
		int i;
		for (i = 1; i <= pageCount; i++) {
			if (curPage == i) { %>
				<span><%=i%></span>
			<% } else { %>
				<span><a href="management?method=viewTemplate&page=<%=i%>"><%=i%></a></span>
			<% }
		}
	%>
	</div>
</center>
</body>
</html>