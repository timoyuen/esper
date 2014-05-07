<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@page import="esperengine.stock.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Load css for c3 basic styles -->
<link rel="stylesheet" type="text/css" href="css/c3.css">
<!-- Load d3.js and c3.js -->
<script src="js/d3.js" charset="utf-8"></script>
<script src="js/c3.js"></script>
<body>
  <div id="chart"></div>
  <%
      List<Object> allStock = (List<Object>)request.getAttribute("allStockCode");
      EventVo event = (EventVo)request.getAttribute("event");
      int allStockCount = allStock.size();
  %>
<script>
var chart = c3.generate({
  bindto: '#chart',
  data: {
    xs: {
      for (int i = 1; i <= allStockCount; i++) {
        if (i == allStockCount)
          out.print("\'y"+i+"\': \'x"+i+"\'");
        else
          out.print("\'y"+i+"\': \'x"+i+"\',");
      }
    },
    columns: [
   <%
      int stockCount = 1;
      for (Object o : allStock) {
        out.print("[\'x"+stockCount+"\'");
        for (StockInfo si : o) {
          out.print(", "+si.getCurTimeString())
        }
        out.print("]\n,[\'y"+stockCount+"\'");
        for (StockInfo si : o) {
          out.print(", "+si.getCurPrice());
        }
        if (stockCount == allStockCount)
          out.print("]\n");
        else
          out.print("],\n");
      }
    %>
    ]
  }
});
</script>
</body>