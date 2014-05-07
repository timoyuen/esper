package management;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import esperengine.stock.*;
import error.*;
import login.person.*;
import esperengine.EsperEngine;
import esperengine.StockEsperInstance;
public class UserDashBoard extends HttpServlet
{
    static Log log = LogFactory.getLog(UserDashBoard.class);
	private String method = null;
	private static final String methodViewTemplate = "viewTemplate";
	private static final String methodViewDetail = "viewDetail";
	private static final String methodMakeSubscription = "makeSubscription";
	private static final String methodViewHistory = "viewHistory";
	private static final String methodUpdateSubscription = "updateSubscription";
	private static final String methodViewGraph = "viewGraph";
	private static final String errorOccur = "errorOccur";
	private HttpSession session = null;
	final static int itemPerPage = 10;
	// private static final String methodViewTemplate = "viewTemplate";
	// private static final String methodViewTemplate = "viewTemplate";
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	private boolean getDispatcher(String testMethod, HttpServletRequest request, HttpServletResponse response)
														throws IOException, ServletException {
		if (method == null) {
			request.getRequestDispatcher(getFullMethodPath(methodViewTemplate)).forward(request, response);
			return true;
		}
		else {
			if (method.equals(testMethod)) {
				request.setAttribute("title", method);
				request.getRequestDispatcher(getFullMethodPath(method)).forward(request, response);
				return true;
			}
		}
		return false;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		request = req;
		response = res;
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		if (!checkLogin())
			return;

		method = request.getParameter("method");
		request.setAttribute("title", method);
		request.setAttribute("curPage", method);
		if (method.equals(methodViewTemplate)) {
			getViewTemplate();
		} else if (method.equals(methodViewDetail)) {
			getViewDetail();
		} else if (method.equals(methodViewHistory)) {
			getViewHistory();
		} else if (method.equals(methodUpdateSubscription)) {
			getUpdateSubscription();
		} else if (method.equals(methodViewGraph)) {
			getViewGraph();
		}
		return;
	}

	private void getViewTemplate() throws IOException, ServletException {
		String pageS = request.getParameter("page");
		int page = 1; /// start from 1
		if (pageS != null)
			page = Integer.parseInt(pageS);
		StockRuleDAO sd = StockDAOFactory.getStockRuleDAOInstance();
		List<StockRuleVo> ls = sd.getAllRulesWithPage(page, itemPerPage);
		int pageCount = sd.getAllRulesCount();
		request.setAttribute("pageCount", (pageCount + itemPerPage - 1) / itemPerPage);
		request.setAttribute("curRulePage", page);
		request.setAttribute("ruleList", ls);
		request.getRequestDispatcher(getFullMethodPath(methodViewTemplate)).forward(request, response);
		return;
	}

	private void getViewDetail() throws IOException, ServletException {
		String id = request.getParameter("id");
		StockRuleDAO sd = StockDAOFactory.getStockRuleDAOInstance();
		request.setAttribute("rule", sd.getRuleWithId(id));
		request.getRequestDispatcher(getFullMethodPath(methodViewDetail)).forward(request, response);
	}

	private void getViewHistory() throws IOException, ServletException {
		PersonVo myUser = (PersonVo)session.getAttribute("user");
		String pages = request.getParameter("page");
		int curPage = 1;
		if (pages != null)
			curPage = Integer.parseInt(pages);
		RuleSubscriptionDAO rs = StockDAOFactory.getRuleSubscriptionDAOInstance();
		List<RuleSubscriptionVo> ruleSub = rs.getUserEPLWithUserNameAndPage(myUser.getUserName(),
		                                                                    curPage, itemPerPage);
		int allItems = rs.getAllUserEPLWithUserName(myUser.getUserName());
		request.setAttribute("ruleSub", ruleSub);
		request.setAttribute("pageCount", (allItems + itemPerPage - 1) / itemPerPage);
		request.setAttribute("curRulePage", curPage);
		request.getRequestDispatcher(getFullMethodPath(methodViewHistory)).forward(request, response);
	}

	private void getUpdateSubscription() throws IOException, ServletException {
		int subId = Integer.parseInt((String)request.getParameter("subid"));
		showSubscriptionWithSubId(subId);
	}

	private List<String> findStockCodeInEPL(String epl, List<String> args) {
		int start = -1;
		List<String> stockCode = new ArrayList<String>();
		while (true) {
			start = epl.indexOf("code=", start + 1);
			if (start == -1)
				break;
			String substr = epl.substring(0, start);
			int count = 0;
			for (int i = 0; i < substr.length(); i++) {
				String sub = substr.substring(i, i + 1);
				if (sub.equals("?"))
					count++;
			}
			stockCode.add(args.get(count));
		}
		return stockCode;
	}

	private List<String> findStockCodeInSubscription(String sid) {
		EventDAO ed = StockDAOFactory.getEventDAOInstance();
		List<EventVo> leo = ed.getAllEventWithSubId(sid);
		Set<String> ret = new HashSet<String>();
		for (EventVo ev : leo) {
			List<Object> lo = ev.getNewEvent();
			for (Object o : lo) {
				Map<String, Object> map = (Map<String, Object>) o;
				ret.add((String)map.get("code"));
			}
		}
		List<String> listRet = new ArrayList<String>();
		for (String s : ret)
			listRet.add(s);
		return listRet;
	}
	private void getViewGraph() throws IOException, ServletException {
		String eventid = (String)request.getParameter("eventid");
		String sid = (String)request.getParameter("subid");
		List<EventVo> lev = new ArrayList<EventVo>();
		EventDAO rs = StockDAOFactory.getEventDAOInstance();
		if (eventid != null) {
			lev.add(rs.getEventWithEventId(eventid));
		} else {
			lev = rs.getAllEventWithSubId(sid);
		}
		List<String> stcList = findStockCodeInSubscription(sid);
		List<Object> lo = null;
		StockDetailDAO std = StockDAOFactory.getStockDetailInstance();
		if (stcList.size() == 0) {
			lo = std.getAllStockDetail();
		} else {
			lo = std.getStockDetailWithStockCode(stcList);
		}
		log.info("B "+lev);

		request.setAttribute("allStockCode", lo);
		request.setAttribute("eventList", lev);
		request.getRequestDispatcher(getFullMethodPath(methodViewGraph)).forward(request, response);
	}

	private void showSubscriptionWithSubId(int subId) throws IOException, ServletException {
		RuleSubscriptionDAO rs = StockDAOFactory.getRuleSubscriptionDAOInstance();
		RuleSubscriptionVo rsv = rs.getEPLWithSubId(subId);
		request.setAttribute("oneRuleSub", rsv);
		request.getRequestDispatcher(getFullMethodPath(methodUpdateSubscription)).forward(request, response);
	}

	private boolean checkLogin() throws IOException, ServletException {
		session = request.getSession();
		System.out.println((PersonVo)session.getAttribute("user"));
		if ((PersonVo)session.getAttribute("user") == null) {
			request.setAttribute("error", true);
			request.getRequestDispatcher(getFullMethodPath(errorOccur)).forward(request, response);
			return false;
			// response.sendRedirect("login");
		}
		return true;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
															throws IOException, ServletException
	{
		request = req;
		response = res;
		if (!checkLogin())
			return;
		method = request.getParameter("method");
		request.setAttribute("title", method);
		request.setAttribute("curPage", method);
		if (method == null) {
			new Error(request, response);
		} else if (method.equals(methodMakeSubscription)) {
			postMakeSubscription();
		} else if (method.equals(methodUpdateSubscription)) {
			postUpdateSubscription();
		} else {
			new Error(request, response);
		}
	}

	private void postMakeSubscription() throws IOException, ServletException {
		String [] args = (String []) request.getParameterValues("user_args");
		String id = (String) request.getParameter("id");
		RuleSubscriptionDAO sd = StockDAOFactory.getRuleSubscriptionDAOInstance();
		String priority = (String) request.getParameter("priority");
		int rs = sd.insertUserSubsciption(id, args, (PersonVo)session.getAttribute("user"), priority);
		if (rs > 0) {
			StockEsperInstance sei = (StockEsperInstance)(EsperEngine.getInstance("Stock"));
			sei.insertNewSub(rs);
			request.setAttribute("result", "INSERT DATABASE SUCCESSFULLY");
		} else {
			request.setAttribute("result", "INSERT DATABASE ERROR");
		}
		showSubscriptionWithSubId(rs);
	}

	/// need delete old subscription
	private void postUpdateSubscription() throws IOException, ServletException {
		String [] args = (String []) request.getParameterValues("user_args");
		String id = (String) request.getParameter("subid");
		RuleSubscriptionDAO sd = StockDAOFactory.getRuleSubscriptionDAOInstance();
		String priority = (String) request.getParameter("priority");
		int rs = sd.updateUserSubscription(id, args, (PersonVo)session.getAttribute("user"), priority);
		int subid = Integer.parseInt(id);
		// if (rs > 0) {
			StockEsperInstance sei = (StockEsperInstance)(EsperEngine.getInstance("Stock"));
			sei.updateOldSub(subid);
			request.setAttribute("result", "UPDATE DATABASE SUCCESSFULLY");
		// } else {
			// request.setAttribute("result", "INSERT DATABASE ERROR");
		// }
		showSubscriptionWithSubId(subid);

	}
	private String getFullMethodPath(String method) {
		String pathBase = "/jsp/management/";
		String pathEnd = ".jsp";
		return pathBase + method + pathEnd;
	}

}