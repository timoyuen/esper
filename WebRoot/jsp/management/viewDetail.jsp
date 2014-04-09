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
		<p>
			RULE DESCIRPTION: ${rule.eplDescription}
			RULE EPL: ${rule.eplStr}
		</p>
		<form action="management?method=makeRule" method="post">
			<table>
				<tr>
					<td>ARG DESCRIPTION</td>
					<td>ARG EXAMPLE</td>
					<td>YOUR ARGS</td>
				</tr>
			<%
			StockRuleVo srv = (StockRuleVo)request.getAttribute("rule");
			System.out.println(srv);
			if (srv != null) {
				List<String> ruleArgsDescription = srv.getRuleArgsDescription();
				List<String> ruleArgsExample = srv.getRuleArgsExample();
				int i = 0;
				for (String description: ruleArgsDescription) { %>
					<tr><td><%=description%></td><td><%=ruleArgsExample.get(i)%></td><td><input type="text" name="user_args"></td></tr>
				<%	i++;
				 } %>
				<input type="text" value="${rule.eplId}" name="id">
				<input type="submit" value="submit">
			<%
			} else {
				out.println("CANNOT FIND THIS RULE");
			} %>
			</table>
		</form>
	</div>
	<div>
	</div>
</center>
</body>
</html>