<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>

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
      int allStockCount = allStock.size();
  %>
<script>
var chart = c3.generate({
    data: {
      x: 'x',
      columns: [
      <%
          int stockCount = 1;
          List<Date> xAxis = new ArrayList<Date>();
          for (Object oo : allStock) {
            List<StockInfo> o = (List<StockInfo>)oo;
            if (stockCount == 1) {
              out.print("[\'x\'");
              for (StockInfo si : o) {
                out.print(", \'"+si.getCurTimeString()+"\'");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                xAxis.add(dateFormat.parse(si.getCurTimeString()));
              }
              out.print("],\n");
            }
            out.print("[\'"+o.get(0).getCode()+"\'");
            for (StockInfo si : o) {
              out.print(", "+si.getCurPrice());
            }
            if (stockCount == allStockCount)
              out.print("]\n");
            else
              out.print("],\n");
            stockCount++;
          }
      %>
      ]
    },
    axis: {
      x: {
          type: 'timeseries',
          tick: {
              format: '%Y-%m-%d %H:%M:%S'
          }
      }
    }
});
</script>
<%
    List<EventVo> eventList = (List<EventVo>)request.getAttribute("eventList");
    if (eventList == null) {
      out.print("<script>alert('Oops');</script>");
    } else if (eventList.size() == 1) {
      EventVo ev = eventList.get(0);
      List<Object> lo = ev.getNewEvent();
      out.print("<table class='table table-hover'>");
      int i = 0;
      for (Object o : lo) {
        Map<String, Object> map = (Map<String, Object>) o;
        if (i == 0) {
          out.print("<thead><tr>");
          for (String key : map.keySet()) {
            out.print("<td>"+key+"</td>");
          }
          out.print("</tr></thead><tbody>");
        } else {
          out.print("<tr>");
          for (String key : map.keySet()) {
            out.print("<td>"+map.get(key)+"</td>");
          }
          out.print("</tr>");
        }
      }
      out.print("</tbody></table>");
    } else {
      out.print("<script>setTimeout(function() {");
      for (EventVo ev : eventList) {
        List<Object> lo = ev.getNewEvent();
        Date createDate = ev.getCreateTime();
        int i = 0;
        int maxXAxis = xAxis.size();
        int index = -1;
        for (Date dateStr : xAxis) {
          int res = createDate.compareTo(dateStr);
          if (res == 0) {
            index = i;
            break;
          } else if (i != maxXAxis - 1) {
            int res_b = createDate.compareTo(xAxis.get(i + 1));
            if (res_b == 0) {
              index = i + 1;
              break;
            } else if (res_b < 0) {
              index = i;
              break;
            }
          }
          i++;
        }
        if (index == -1)
          continue;
        for (Object o : lo) {
          Map<String, Object> map = (Map<String, Object>)o;
          String code = (String)map.get("code");
          out.print("chart.select(\"["+code+"]\", ["+index+"]);\n");
        }
      }
      out.print("}, 1000);</script>");
    }
%>
</body>
</html>