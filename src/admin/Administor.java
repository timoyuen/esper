package admin;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;

public class Administor extends HttpServlet
{
	private String pathBase = "/jsp/admin/";
	private String pathEnd = ".jsp";
	private String methodNewRule = "newRule";
	private String methodViewRules = "viewRules";
	private String methodViewStockCode = "viewStockCode";
	private String methodNewStockCode = "newStockCode";

	private String pathNewRule = pathBase + methodNewRule + pathEnd;
	// private String pathViewRules = pathBase + methodViewRules + pathEnd;
	// private String pathViewStockCode = pathBase + methodViewStockCode + pathEnd;
	// private String pathNewStockCode = pathBase + methodNewStockCode + pathEnd;

	private method;

	private getDispatcher(String testMethod, HttpServletRequest request, HttpServletResponse response) {
		if (method == null) {
			request.getRequestDispatcher(pathNewRule).forward(request, response);
			return true;
		}
		else {
			if (method.equals(testMethod)) {
				request.getRequestDispatcher(pathBase + method + pathEnd).forward(request, response);
				return true;
			}
		}
		return false;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		method = request.getParameter("method");
		boolean valid = getDispatcher(methodNewRule, request, response) ||
						getDispatcher(methodViewRules, request, response) ||
						getDispatcher(methodViewStockCode, request, response) ||
						getDispatcher(methodNewStockCode, request, response) ||
		if (!valid)
			errors(response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		method = request.getParameter("method");
		if (method == null) {
			errors(response);
		} else if (method.equals(methodNewRule)) {
			postNewRule(request, response);
		} else if (method.equals(methodViewRules)) {
			postViewRules(request, response);
		} else if (method.equals(methodViewStockCode)) {
			postViewStockCode(request, response);
		} else if (method.equals(methodNewStockCode)) {
			postNewStockCode(request, response);
		} else {
			errors(response);
		}
	}

	private void postNewRule(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{

	}
	private void postViewRules(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{

	}
	private void postViewStockCode(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{

	}
	private void postNewStockCode(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		request.getParameter("stock_code");
	}

	private void errors(HttpServletResponse response) {
		System.out.println("Administor errors");
		response.sendRedirect("errors");
	}
}