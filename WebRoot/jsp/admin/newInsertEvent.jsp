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
	<h2>New Insert Event!</h2>
	<form action="admin?method=newInsertEvent" method="post" role='form'>
			<%
				String result = (String)request.getAttribute("result");
				if (result != null) { %>
				<p>
					<%=result%>
				</p>
			<%	}
			%>
		<div class="form-group">
			<label>desciption</label>
			<textarea name="description" id="" rows="5" class='form-control'>${ruleDescription}</textarea>
		</div>
		<div class="form-group">
			<label for="">rule name</label>
			<input type="text" name="rule_name" class='form-control'>
		</div>
		<div class="form-group">
			<label for="epl-lang">Event EPL</label>
			<p class="help-block">
				<p>
					<strong>NOTICE:</strong>
					You can create Event HERE, please describe your ARGUMENT in DETAIL.
				</p>
			</p>
		</div>
		<div class="form-group">
			<textarea name="epl" id="" class='form-control' rows="5">${epl}</textarea>
		</div>
		<div id='arg_table'>
			<%
				String [] argDescription = (String [])request.getAttribute("argDescription");
				String [] argExample = (String [])request.getAttribute("argExample");
				String [] argName = (String [])request.getAttribute("argName");
				if (argDescription != null && argExample != null && argName != null &&
					argDescription.length > 0 && argExample.length > 0 && argName.length > 0) { %>
					<table class='table table-hover'>
						<thead>
						<tr>
							<td>args counter</td><td>$ARG_NAME</td><td>args description</td><td>args example</td>
						</tr>
						</thead>
						<tbody>
					<%  int i = 0;
					for (i = 0; i < argDescription.length; i++) { %>
							<tr>
								<td>ARG <%=i%></td>
								<td><%=argName[i]%><input type='hidden' name='arg_name' value="<%=argName[i]%>"></td>
								<td><input type='text' name='arg_description' class='form-control' value="<%=argDescription[i]%>"></td>
								<td><input type='text' name='arg_example' class='form-control' value="<%=argExample[i]%>"></td>
							</tr>
					<%	} %>
						</tbody>
					</table>
			<%	}
			%>
		</div>
		<div class='form-group'>
			<button id="add_args" class='btn btn-info' type='button'>add args</button>
			<button type="submit" id="check_syntax" class='btn btn-primary'>check syntax</button>
		</div>
	</form>
	<jsp:include page="../template/footer.jsp"/>
	<script src='js/rule_check.js'></script>
	<script text='text/javascript'>
	var added_arg = false;
	$('#add_args').click(function() {
		added_arg = true;
		$('#arg_table').empty();
		var epl = $("[name='epl']").val();
		var match = findMatch(epl);
		if (!match) {
			alert("ARGS ERROR");
			return false;
		}
		var table = "<table class='table table-hover'><thead><tr><td>ARG COUNTER</td><td>$ARG_NAME</td><td>ARG DESCRIPTION</td><td>ARG EXAMPLE</td></tr></thead><tbody>";

		for (var i = 0; i < match.length; i++) {
			var arg_name = match[i]; //.substring(0, match[i].length-2);
			table += "<tr><td>ARG "+i+"</td><td>"+arg_name+"<input type='hidden' name='arg_name' value='"+arg_name+"'></td><td><input type='text' name='arg_description' class='form-control'/></td><td><input type='text' name='arg_example' class='form-control'></td></tr>";
		}
		table += "</tbody></table>";
		$('#arg_table').append(table);
		return false;
	})
	function check_empty(name, errorMsg) {
		var flag = false;
		$("[name='"+name+"']").each(function() {
			$(this).parent().children().remove('.add_error');
			if ($(this).val() == "") {
				flag = true;
				$(this).after("<div class='add_error alert alert-danger'>"+errorMsg+"</div")
			}
		})
		return flag;
	}
	$('#check_syntax').click(function() {
		if (!added_arg) {
			alert("add ARGS first");
			return false;
		}
		var flag = check_empty('arg_description', "description cannot be empty");
		flag = check_empty('arg_example', "example cannot be empty") || flag;
		return !flag;
	})
	</script>
</div>
</body>
</html>