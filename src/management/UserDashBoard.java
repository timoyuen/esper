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
public class UserDashBoard extends HttpServlet
{
    static Log log = LogFactory.getLog(UserDashBoard.class);
	private String method = null;
	private static final String methodViewTemplate = "viewTemplate";
	private static final String methodViewDetail = "viewDetail";
	private static final String methodMakeRUle = "makeRule";
	private HttpSession session = null;

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
		checkLogin();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		method = request.getParameter("method");
		if (method.equals(methodViewTemplate)) {
			getViewTemplate();
		} else if (method.equals(methodViewDetail)) {
			getViewDetail();
		}
		// boolean valid = getDispatcher(methodViewTemplate, request, response) ||
		// 				getDispatcher(methodViewRules, request, response) ||
		// 				getDispatcher(methodViewStockCode, request, response) ||
		// 				getDispatcher(methodNewStockCode, request, response);
		// if (!valid)
			// Error(request, response);
	}

	private void getViewTemplate() throws IOException, ServletException {
		String pageS = request.getParameter("page");
		int page = 1; /// start from 1
		if (pageS != null)
			page = Integer.parseInt(pageS);
		StockRuleDAO sd = StockDAOFactory.getStockRuleDAOInstance();
		List<StockRuleVo> ls = sd.getAllRules(page);
		int pageCount = sd.getRulePageCount();
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("curPage", page);
		request.setAttribute("ruleList", ls);
		request.getRequestDispatcher(getFullMethodPath(methodViewTemplate)).forward(request, response);
	}

	private void getViewDetail() throws IOException, ServletException {
		String id = request.getParameter("id");
		StockRuleDAO sd = StockDAOFactory.getStockRuleDAOInstance();
		request.setAttribute("rule", sd.getRuleWithId(id));
		request.getRequestDispatcher(getFullMethodPath(methodViewDetail)).forward(request, response);
	}
	private void checkLogin() throws IOException, ServletException {
		session = request.getSession();
		if ((PersonVo)session.getAttribute("user") != null)
			return true;
		response.sendRedirect("login");
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		request = req;
		response = res;
		checkLogin();
		method = request.getParameter("method");
		if (method == null) {
			new Error(request, response);
		} else if (method.equals(methodMakeRUle)) {
			postMakeRule();
		} else {
			new Error(request, response);
		}
	}

	private void postMakeRule() throws IOException, ServletException {
		String [] args = (String []) request.getParameterValues("user_args");
		String id = (String) request.getParameter("id");
		StockRuleDAO sd = StockDAOFactory.getStockRuleDAOInstance();
		int rs = sd.insertUserRule(id, args, (PersonVo)session.getAttribute("user"));
		if (rs > 0) {
			EsperEngine.ruleInserted(rs);
			request.setAttribute("result", "INSERT DATABASE SUCCESSFULLY");
		} else {
			request.setAttribute("result", "INSERT DATABASE ERROR");
		}
		request.getRequestDispatcher(getFullMethodPath(methodMakeRUle)).forward(request, response);
	}
	private String getFullMethodPath(String method) {
		String pathBase = "/jsp/management/";
		String pathEnd = ".jsp";
		return pathBase + method + pathEnd;
	}
}