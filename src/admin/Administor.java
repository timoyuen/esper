package admin;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import esperengine.cepConfig.*;
import helper.*;
public class Administor extends HttpServlet
{
	private String methodNewRule = "newRule";
	private String methodViewRules = "viewRules";
	private String methodDeleteRules = "delRules";
	private String methodViewStockCode = "viewStockCode";
	private String methodNewStockCode = "newStockCode";
	private String methodDeleteStockCdoe = "delStockCode";
	private String methodConfirmStockCode = "confirmStockCode";
	private String methodNewInsertEvent = "newInsertEvent";
	// private String pathViewRules = getFullMethodPath(methodViewRules);
	// private String pathViewStockCode = getFullMethodPath(methodViewStockCode);
	// private String pathNewStockCode = getFullMethodPath(methodNewStockCode);
    static Log log = LogFactory.getLog(Administor.class);
	private String method;
	private boolean getDispatcher(String testMethod, HttpServletRequest request, HttpServletResponse response)
														throws IOException, ServletException {
		if (method == null) {
			request.setAttribute("title", methodNewRule);
			request.getRequestDispatcher(getFullMethodPath(methodNewRule)).forward(request, response);
			return true;
		}
		else {
			if (method.equals(testMethod)) {
				request.getRequestDispatcher(getFullMethodPath(method)).forward(request, response);
				return true;
			}
		}
		return false;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		method = request.getParameter("method");
		request.setAttribute("title", method);
		request.setAttribute("curPage", method);
		if (method.equals(methodNewRule)) {
			getNewRule(request, response);
		} else {
			boolean valid = getDispatcher(methodNewRule, request, response) ||
							getDispatcher(methodViewRules, request, response) ||
							getDispatcher(methodViewStockCode, request, response) ||
							getDispatcher(methodNewStockCode, request, response) ||
							getDispatcher(methodNewInsertEvent, request, response);
			if (!valid)
				errors(response);
		}
	}

	public void getNewRule(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		StockInsertEventDAO sied = StockDAOFactory.getStockInsertEventDAOInstance();
		List<StockInsertEventVo> lsiv = sied.getAllInsertEvent();
		request.setAttribute("allEvent", lsiv);
		request.getRequestDispatcher(getFullMethodPath(methodNewRule)).forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		response.setContentType("text/html; charset=utf-8");
		method = request.getParameter("method");
		if (method == null) {
			errors(response);
		} else if (method.equals(methodNewRule)) {
			postNewRule(request, response);
		} else if (method.equals(methodViewRules)) {
			; //postViewRules(request, response);
		} else if (method.equals(methodViewStockCode)) {
			postViewStockCode(request, response);
		} else if (method.equals(methodNewStockCode)) {
			postNewStockCode(request, response);
		} else if (method.equals(methodConfirmStockCode)) {
			postConfirmStockCode(request, response);
		} else if (method.equals(methodNewInsertEvent)) {
			postNewInsertEvent(request, response);
		}
		else {
			errors(response);
		}
	}

	private void postNewRule(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		String epl = request.getParameter("epl");
		String ruleDescription = request.getParameter("description");
		String []argExample = request.getParameterValues("arg_example");
		String []argName = request.getParameterValues("arg_name");
		String []argDescription = request.getParameterValues("arg_description");
		String newEpl = EPLChecker.replaceEPLKey(epl);
		List<String> argExampleList = Helper.getList(argExample);
		StockInsertEventDAO sied = StockDAOFactory.getStockInsertEventDAOInstance();
		List<StockInsertEventVo> lsiv = sied.getAllInsertEvent();
		List<String> eventRelated = new ArrayList<String>();
		List<String> eventRelatedName = new ArrayList<String>();
		for (StockInsertEventVo e : lsiv) {
			if (epl.indexOf(e.getEventName()) >= 0) {
				eventRelated.add(e.getEventId());
				eventRelatedName.add(e.getEventName());
			}
		}
		boolean flag = CepConfig.isEPLValidWithEvent(newEpl, argExampleList, eventRelatedName);
		if (!flag) {
			request.setAttribute("result", "SYNTAX ERROR!");
		} else {
			if (StockDAOFactory.getStockRuleDAOInstance().insert(newEpl, ruleDescription, argExample, argDescription, eventRelated))
				request.setAttribute("result", "SUCCESSFULLY CREATE RULE TEMPLATE");
			else
				request.setAttribute("result", "INSERT TO DATABASE ERROR");
		}
		// request.setAttribute("ruleDescription", ruleDescription);
		request.setAttribute("epl", epl);
		// request.setAttribute("argExample", argExample);
		// request.setAttribute("argName", argName);
		// request.setAttribute("argDescription", argDescription);
		request.getRequestDispatcher(getFullMethodPath(methodNewRule)).forward(request, response);
	}

	private void postViewStockCode(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{

	}
	private void postNewStockCode(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		String stockCodeStr = request.getParameter("stock_code");
		String []stockCodeArray = stockCodeStr.split("\n");
		Set<String> stockCodeSet = new HashSet<String>();
		for (String str : stockCodeArray) {
			stockCodeSet.add(str);
		}
		StockCodeDAO st = StockDAOFactory.getStockCodeDAOInstance();
		List<StockInfo> validList = new ArrayList<StockInfo>();
		List<String> inValidList = new ArrayList<String>();
		List<String> repeatedList = new ArrayList<String>();
		for (String str : stockCodeSet) {
			str = str.trim();
			if (!st.isStockCodeExist(str)) {
				String stockName;
				if ((stockName = StockCode.isStockCodeValid(str)) != null)
					validList.add(new StockInfo(str, stockName));
				else
					inValidList.add(str);
			} else {
				repeatedList.add(str);
			}
		}
		request.setAttribute("repeated", repeatedList);
		request.setAttribute("inValid", inValidList);
		request.setAttribute("valid", validList);
		request.getRequestDispatcher(getFullMethodPath(methodConfirmStockCode)).forward(request,response);
	}

	private void postConfirmStockCode(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		/// NOTICE THAT here I use getParameterValues();
		String [] codeArray = request.getParameterValues("confirm_code");
		// System.out.println(codeArray.length);
		StockCodeDAO st = StockDAOFactory.getStockCodeDAOInstance();
		String errorMsg = null;
		List<StockInfo> dbErrorCode = new ArrayList<StockInfo>();
		String codeName;
		boolean updated = false;
		for (String code : codeArray) {
			code = code.trim();
			if (st.isStockCodeExist(code) || (codeName = StockCode.isStockCodeValid(code)) == null) {
				errorMsg = "YOU MUST BE A BAD GUY";
				break;
			}
			if (!st.insertStockCode(code, codeName)) {
				dbErrorCode.add(new StockInfo(code, codeName));
			} else {
				updated = true;
			}
		}
		if (updated)
			StockCode.initStockCode();
		request.setAttribute("error", errorMsg);
		request.setAttribute("codeError", dbErrorCode);
		request.getRequestDispatcher(getFullMethodPath("stockCodeResult")).forward(request,response);
	}

	private void postNewInsertEvent(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException {
		String epl = request.getParameter("epl");
		String ruleDescription = request.getParameter("description");
		String ruleName = request.getParameter("rule_name");
		String []argExample = request.getParameterValues("arg_example");
		String []argName = request.getParameterValues("arg_name");
		String []argDescription = request.getParameterValues("arg_description");
		String newEpl = EPLChecker.replaceEPLKey(epl);
		List<String> argExampleList = Helper.getList(argExample);
		boolean flag = CepConfig.isEPLValid(newEpl, argExampleList);
		if (!flag) {
			request.setAttribute("result", "SYNTAX ERROR!");
		} else {
			if (StockDAOFactory.getStockInsertEventDAOInstance().insert(newEpl, ruleName, ruleDescription,
			    														argExample, argDescription))
				request.setAttribute("result", "SUCCESSFULLY CREATE EVENT TEMPLATE");
			else
				request.setAttribute("result", "INSERT TO DATABASE ERROR");
		}
		request.setAttribute("ruleDescription", ruleDescription);
		request.setAttribute("epl", epl);
		request.getRequestDispatcher(getFullMethodPath(methodNewInsertEvent)).forward(request, response);
	}

	private void errors(HttpServletResponse response) throws IOException, ServletException {
		log.error("Administor errors");
		response.sendRedirect("jsp/errors");
	}
	private String getFullMethodPath(String method) {
		String pathBase = "/jsp/admin/";
		String pathEnd = ".jsp";
		return pathBase + method + pathEnd;
	}
}