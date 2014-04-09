<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="esperengine.stock.StockInfo"%>
<html>
<head>
	<title>CONFIRM STOCK CODE</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<body>
<center>
	<div>
		<p>Repeat stock code, which will be ignored!</p>
		<% 	List<String> repeated = (List<String>)request.getAttribute("repeated");
			if(repeated != null && !repeated.isEmpty()) {
				List<String> all = (List<String>) request.getAttribute("repeated");
				Iterator iter = all.iterator();
				while (iter.hasNext()) { %>
					<li><%=iter.next()%></li>
				<% }
			} else { %>
				<p>NONE</p>
			<% }
		%>
	</div>
	<div>
		<p>invalid stock code input, please check you stock code carefully</p>
		<%  List<String> inValid = (List<String>)request.getAttribute("inValid");
			if(inValid != null && !inValid.isEmpty()) {
				List<String> all = (List<String>) request.getAttribute("inValid");
				Iterator iter = all.iterator();
				while (iter.hasNext()) { %>
					<li><%=iter.next()%></li>
				<% }
			} else { %>
				<p>NONE</p>
			<% }
		%>
	</div>
	<div>
		<p>valid stock code, please recheck to take effect</p>
		<form action="admin?method=confirmStockCode" method="post">
		<%  List<StockInfo> valid = (List<StockInfo>)request.getAttribute("valid");
			if (valid != null && !valid.isEmpty()) {
				List<StockInfo> all = (List<StockInfo>) request.getAttribute("valid");
				for (StockInfo si : all) { %>
					<li>
						<%=si.getCode()%>
						<%=si.getName()%>
						<input type="checkbox" name="confirm_code" value="<%=si.getCode()%>">
					</li>
				<% } %>
				<input type="submit" value="submit">
			<% } else { %>
				<p>NONE</p>
			<% }
		%>
		</form>
	</div>
</center>
</body>
</html>