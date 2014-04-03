<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<html>
<head>
	<title>NEW STOCK CODE</title>
</head>
<body>
<center>
	<form action="admin?method=newStockCode" method="post">
		<p>New STOCK CODE</p>
		<p>You can register several stock codes at a time</p>
		<p>
			<span>eg: sh601006</span>
			<br />
			<span>sh601001</span>
			<br />
			<span>seperate by newline</span>
		</p>
		<textarea name="stock_code" id="" cols="30" rows="20" placeholder="input stock codes here..."></textarea>
		<input type="submit" value="submit" />
	</form>
</center>
</body>
</html>