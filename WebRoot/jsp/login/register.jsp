<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<html>
<head>
	<title>REGISTER</title>
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

	<form action="login?method=register" method="post">
	<table>
		<tr>
			<td colspan="2">USER REGISTER</td>
		</tr>
		<tr>
			<td>Username</td>
			<td><input type="text" name="username" value="${person.userName}"></td>
		</tr>
		<tr>
			<td>password</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td>password again</td>
			<td><input type="password" name="password_again"></td>
		</tr>
		<tr>
			<td>email</td>
			<td><input type="text" name="email" value="${person.email}"></td>
		</tr>
		<tr>
			<td>telephone</td>
			<td><input type="text" name="telephone" value="${person.telephone}"></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="submit" value="Register">
			<input type="reset" value="Reset">
			</td>
		</tr>
	</table>
	</form>
</center>
</body>
</html>