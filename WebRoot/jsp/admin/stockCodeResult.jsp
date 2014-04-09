<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="esperengine.stock.StockInfo"%>
<html>
<head>
	<title>STOCK CODE RESULT</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<body>
<center>
	<div>
		<%  String error = (String)request.getAttribute("error");
			if (error != null) { %>
				<%=error%>
			<% }
			else {
				List<StockInfo> codeError = (List<StockInfo>)request.getAttribute("codeError");
				if (codeError.isEmpty()) { %>
					<p>SUCCESS</p>
				<% }
				else { %>
					<p>Somehow Some Code cannot insert to DB >_<</p>
					<%
					for (StockInfo code : codeError) { %>
						<li><%=code.getCode()%> <%=code.getName()%></li>
					<%
					}
				}
			} %>
	</div>
</center>
</body>
</html>