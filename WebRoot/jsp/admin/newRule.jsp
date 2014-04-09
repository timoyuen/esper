<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
  	<link rel="stylesheet" type="text/css" href="">
  	<script type='text/javascript'src='js/jquery.1.8.0.js'></script>
</head>
<body>
<center>
	<form action="admin?method=newRule" method="post">
		<p>New Rules--check syntax</p>
			<%
				String result = (String)request.getAttribute("result");
				if (result != null) { %>
				<p>
					<%=result%>
				</p>
			<%	}
			%>
		<p>desciption</p>
		<p>
		<textarea name="description" id="" cols="30" rows="10">
			${ruleDescription}
		</textarea>
		</p>
		<span>
			We collect several features of stock information, and offer some for you.
			$CODE => sock code,
	 		$NAME => stock name,
	 		$TOPENPRICE => today's opening price,
	 		$YOPENPRICE => yesterday's opening price,
	 		$CPRICE => current price,
	 		$TMAXPRICE => today's maximum price,
	 		$TMINPRICE => today's minimum price,
	 		$DEALCOUNT => today's deal count,
	 		$DEALMONEY => today's deal money,
			$B1PRICE   => today's buy one price
			$B2PRICE   => today's buy two price
			$B3PRICE   => ...
		   	$B4PRICE   => ...
		   	$B5PRICE   => ...
		   	$S1PRICE   => today's sell one price
		   	$S2PRICE   => ...
		   	$S3PRICE   => ...
		   	$S4PRICE   => ...
		   	$S5PRICE   => ...
			$B1COUNT   => today's buy one count
			$B2COUNT   => ...
			$B3COUNT   => ...
			$B4COUNT   => ...
			$B5COUNT   => ...
			$S1COUNT   => ...
			$S2COUNT   => ...
			$S3COUNT   => ...
			$S4COUNT   => ...
			$S5COUNT   => ...

			NOTICE:
			- YOU HAVE TO END THE RULE WITH a "newline"
			- PARAMETER SHOULD BE LIKE THIS "$CODE=?"

		</span>
		<p>
		<textarea name="epl" id="" cols="30" rows="5">${epl}</textarea>
		</p>
		<p>
		<button id="add_args">add args</button>
		</p>
		<div id='arg_table'>
			<%
				String [] argDescription = (String [])request.getAttribute("argDescription");
				String [] argExample = (String [])request.getAttribute("argExample");
				String [] argName = (String [])request.getAttribute("argName");
				if (argDescription != null && argExample != null && argName != null &&
					argDescription.length > 0 && argExample.length > 0 && argName.length > 0) { %>
					<table>
						<tr>
							<td>args counter</td><td>$ARG_NAME</td><td>args description</td><td>args example</td>
						</tr>
					<%  int i = 0;
					for (i = 0; i < argDescription.length; i++) { %>
							<tr>
								<td>ARG <%=i%></td>
								<td><%=argName[i]%><input type='hidden' name='arg_name' value="<%=argName[i]%>"></td>
								<td><input type='text' name='arg_description' value="<%=argDescription[i]%>"></td>
								<td><input type='text' name='arg_example' value="<%=argExample[i]%>"></td>
							</tr>
					<%	} %>
					</table>
			<%	}
			%>
		</div>
		<input type="submit" id="check_syntax" value="check syntax" onclick="check_syntax()">
	</form>
	<script src='js/rule_check.js'></script>
	<script text='text/javascript'>
	$('#add_args').click(function() {
		$('#arg_table').empty();
		var epl = $("[name='epl']").val();
		var match = findMatch(epl);
		if (!match) {
			alert("NO ARGS ERROR");
			return false;
		}
		var table = "<table><tr><td>args counter</td><td>$ARG_NAME</td><td>args description</td><td>args example</td></tr>";

		for (var i = 0; i < match.length; i++) {
			var arg_name = match[i].substring(0, match[i].length-2);
			table += "<tr><td>ARG "+i+"</td><td>"+arg_name+"<input type='hidden' name='arg_name' value='"+arg_name+"'></td><td><input type='text' name='arg_description'/></td><td><input type='text' name='arg_example'></td></tr>";
		}
		table += "</table>";
		$('#arg_table').append(table);
		return false;
	})
	function check_empty(name, errorMsg) {
		var flag = false;
		$("[name='"+name+"']").each(function() {
			if ($(this).val() == "") {
				flag = true;
				$(this).after("<span class='add_error'>"+errorMsg+"</span")
			}
		})
		return flag;
	}
	function check_syntax() {
		var flag = check_empty('arg_description', "description cannot be empty");
		flag = check_empty('arg_example', "example cannot be empty") || flag;
		return !flag;
	}
	</script>
</center>
</body>
</html>