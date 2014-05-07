<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
hello
<% if (request.getAttribute("error") != null) {
	response.sendRedirect("login");
} %>