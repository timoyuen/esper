<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<html>
<head>
	<title>LOGIN RESULT</title>
</head>
<body>
<center>
	<%  String result = (String)request.getAttribute("result");
		if (result.equals("SUCCESS")) {
			out.println("<h2>WELCOME! WE ARE NOW REDIRECTING YOU TO YOUR DASHBOARD</h2>"); %>
			<script type='text/javascript'>
				setTimeout(function() { window.location.href="management?method=viewTemplate"; }, 500);
			</script>
	<%  }
	%>
</center>
</body>
</html>