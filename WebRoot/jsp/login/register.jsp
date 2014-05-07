<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<jsp:include  page="../template/header.jsp">
    <jsp:param name="title" value='<%=request.getAttribute("title")%>'/>
</jsp:include>
<body>
<div class="container">
	<h2>USER REGISTER</h2>
	<%
		if(request.getAttribute("errors")!=null) {
			out.print("<ul>");
			List all = (List)request.getAttribute("errors");
			Iterator iter = all.iterator();
			while(iter.hasNext()) { %>
				<li><%=iter.next()%></li>
	<%		}
			out.print("</ul>");
		}
	%>
	<form action="login?method=register" method="post" role='form'>
		<div class="form-group">
			<label>username</label>
			<input type="text" name="username" class='form-control'value="${person.userName}">
		</div>
		<div class="form-group">
			<label>password</label>
			<input type="password" name="password" class='form-control'>
		</div>
		<div class="form-group">
			<label>password again</label>
			<input type="password" name="password_again" class='form-control'>
		</div>
		<div class="form-group">
			<label>email</label>
			<input type="text" name="email" value="${person.email}" class='form-control'>
		</div>
		<div class="form-group">
			<label>telephone</label>
			<input type="text" name="telephone" value="${person.telephone}" class='form-control'>
		</div>
		<div class="form-group">
			<button class="btn btn-default" type='submit'>register</button>
		</div>
	</form>
</div>
</body>
</html>