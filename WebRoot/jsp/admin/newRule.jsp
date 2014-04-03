<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<html>
<head>
	<title>NEW RULE</title>
</head>
<body>
<center>
	<form action="admin?method=newRule" method="post">
		<p>New Rules</p>
		<label for="">desciption</label>
		<textarea name="description" id="" cols="30" rows="10" placeholder="say something about this rule"></textarea>
		<p>You need replace </p>
		<input type="text" name="rule"/>
		<td>password</td>
		<td><input type="password" name="password" value="${person.password}"></td>
		<td colspan="2">
		<input type="submit" value="submit">
	</form>
</center>
</body>
</html>