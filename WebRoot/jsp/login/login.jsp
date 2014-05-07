<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<jsp:include  page="../template/header.jsp">
    <jsp:param name="title" value='<%=request.getAttribute("title")%>'/>
</jsp:include>
<body>
<div class="container">
	<%
		if(request.getAttribute("errors")!=null)
		{
			List all = (List)request.getAttribute("errors");
			Iterator iter = all.iterator();
			while(iter.hasNext()) {
	%>
				<li><%=iter.next()%></li>
	<%
			}
		}
	%>

	<form action="login" method="post" role='form'>
		<h2>User Login</h2>
		<div class="form-group">
			<label for="username">User Name</label>
			<input type="text" name="username" id='username' value="${person.userName}" placeholder='USER NAME' class='form-control'>
		</div>
		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" name="password" value="${person.password}" placeholder='password' class='form-control'>
		</div>
		<button type="submit" class="btn btn-default">Submit</button>
	</form>
</div>
</body>
</html>