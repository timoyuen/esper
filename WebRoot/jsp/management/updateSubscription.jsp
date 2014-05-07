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
		<% if (request.getAttribute("result") != null) {
			out.println("<div class='alert alert-warning'>"+request.getAttribute("result")+"</div>");
		} %>
		<div class="alert alert-info">
			<div><strong>RULE DESCIRPTION: </strong></div>
			<div>${oneRuleSub.ruleDescription}</div>
			<div><strong>RULE EPL:</strong></div>
			<div>${oneRuleSub.epl}</div>
		</div>
		<form action="management?method=updateSubscription&subid=${oneRuleSub.subId}" method="post" role='form'>
			<input type="hidden" name='subid' value="${oneRuleSub.subId}">
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

			RuleSubscriptionVo srv = (RuleSubscriptionVo)request.getAttribute("oneRuleSub");
			if (srv != null) {
				List<StockInsertEventVo> le = (List<StockInsertEventVo>) request.getAttribute("allEvent");
				for (StockInsertEventVo e : le) { %>
					<tr><td>Argument For <%=e.getEventName()%></td><td>Description: <%=e.getEventDescription()%></td></tr>
					<%
					List<String> desc = e.getEventArgsDescription();
					List<String> exp = e.getEventArgsExample();
					int i = 0;
					for (String d : desc) { %>
						<tr><td><%=d%></td><td><%=exp.get(i)%></td><td><input type="text" class='form-control' name="<%=e.getEventName()%>_event_args"></td></tr>
					<%
						i++;
					}
				}
				List<String> ruleArgsDescription = srv.getRuleArgsDescription();
				List<String> ruleArgsExample = srv.getRuleArgsExample();
				List<String> userArgs = srv.getUserArgs();
				int i = 0;
				for (String description: ruleArgsDescription) { %>
					<tr><td><%=description%></td><td><%=ruleArgsExample.get(i)%></td><td><input class='form-control'type="text" name="user_args" value="<%=userArgs.get(i)%>"></td></tr>
				<%	i++;
				 } %>
				<button type='submit' class='btn btn-default'>Update</button>
				<div class="radio">
				  <label>
					<input type="radio" id="prio_1" name="priority" value="0" checked>
				    INFO
				  </label>
				</div>
				<div class="radio">
				  <label>
					<input type="radio" id="prio_2" name="priority" value="1">
				    IMPORTANT
				  </label>
				</div>
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