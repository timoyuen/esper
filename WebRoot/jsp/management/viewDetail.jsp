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
<div class="container">
	<div>
		<div class="alert alert-info">
			<div><strong>RULE DESCIRPTION: </strong></div>
			<div>${rule.eplDescription}</div>
			<div><strong>RULE EPL:</strong></div>
			<div>${rule.eplStr}</div>
		</div>
		<form action="management?method=makeSubscription" method="post" role='form'>
			<table class="table table-hover">
				<thead>
					<tr>
						<td>ARG DESCRIPTION</td>
						<td>ARG EXAMPLE</td>
						<td>YOUR ARGS</td>
					</tr>
				</thead>
			<tbody>
			<%
			StockRuleVo srv = (StockRuleVo)request.getAttribute("rule");
			if (srv != null) {
				List<StockInsertEventVo> le = (List<StockInsertEventVo>) request.getAttribute("allEvent");
				for (StockInsertEventVo e : le) { %>
					<tr><td>Argument For <%=e.getEventName()%></td><td>Description: <%=e.getEventDescription()%></td></tr>
					<%
					List<String> desc = e.getEventArgsDescription();
					List<String> exp = e.getEventArgsExample();
					int i = 0;
					for (String d : desc) { %>
						<tr><td><%=d%></td><td><% out.print(exp.get(i)); %></td><td><input type="text" class='form-control' name="<% out.print(e.getEventName());%>_event_args"></td></tr>
					<%
						i++;
					}
				}
				%>
				<tr><td>Argument for This Complex Event</td></tr>
				<%
				List<String> ruleArgsDescription = srv.getRuleArgsDescription();
				List<String> ruleArgsExample = srv.getRuleArgsExample();
				int i = 0;
				for (String description: ruleArgsDescription) { %>
					<tr><td><%=description%></td><td><%=ruleArgsExample.get(i)%></td><td><input class='form-control'type="text" name="user_args"></td></tr>
				<%	i++;
				 } %>
				<input type="hidden" value="${rule.eplId}" name="id">
				<tr>
					<td>
						<div class="radio">
						  <label>
							<input type="radio" id="prio_1" name="priority" value="0" checked>
						    <strong>INFO</strong>
						  </label>
						</div>
						<div class="radio">
						  <label>
							<input type="radio" id="prio_2" name="priority" value="1">
						    <strong>IMPORTANT</strong>
						  </label>
						</div>
					</td>
				</tr>
				<tr>
					<td><button type='submit' class='btn btn-default'>Submit</button></td>
				</tr>
			<%
			} else {
				out.println("CANNOT FIND THIS RULE");
			} %>

			</tbody>
			</table>
		</form>
	</div>
</div>
</body>
</html>