<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<jsp:include  page="../template/header.jsp">
    <jsp:param name="title" value='<%=request.getAttribute("title")%>'/>
</jsp:include>
<body>
<jsp:include page="adminHeadBar.jsp">
    <jsp:param name="curPage" value='<%=request.getAttribute("curPage")%>'/>
</jsp:include>
	<div class="container">
		<h2>New STOCK CODE</h2>
		<form action="admin?method=newStockCode" method="post" role='form'>
			<p>You can register several stock codes at a time</p>
			<div>
				<span>eg: </span>
				<pre>sh601006
sh601001</pre>
				<p>seperate by <strong>newline</strong></p>
			</div>
			<div class='form-group'>
			<textarea name="stock_code" id="" class='form-control' rows="10" placeholder="input stock codes here..."></textarea>
			</div>
			<button type='submit' class='btn btn-default'>Submit</button>
		</form>
	</div>
</body>
</html>