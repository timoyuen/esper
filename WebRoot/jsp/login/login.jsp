<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<html>
<head>
	<title>LOGIN</title>
</head>
<body>
<center>
	<%
		if(request.getAttribute("errors")!=null)
		{
			List all = (List)request.getAttribute("errors");
			Iterator iter = all.iterator();
			while(iter.hasNext()) {
	%>
				<li><%=iter.next()%>
	<%
			}
		}
	%>

	<form action="login" method="post">
	<table>
		<tr>
			<td colspan="2">User Login</td>
		</tr>
		<tr>
			<td>Username</td>
			<td><input type="text" name="username" value="${person.userName}"></td>
		</tr>
		<tr>
			<td>password</td>
			<td><input type="password" name="password" value="${person.password}"></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="submit" value="Login">
			<input type="reset" value="Reset">
			</td>
		</tr>
	</table>
	</form>
</center>
</body>
</html>