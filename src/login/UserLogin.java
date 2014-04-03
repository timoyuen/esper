package login;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;

public class UserLogin extends HttpServlet
{
	private String pathBase = "/jsp/login/";
	private String defaultLogin = pathBase + "login.jsp";
	private String successLogin = pathBase + "login_success.jsp";
	private String defaultRegister = pathBase + "register.jsp";
	private String successRegister = pathBase + "register_success.jsp";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String method = request.getParameter("method");
		if (method == null) {
			request.getRequestDispatcher(defaultLogin).forward(request, response);
		} else if (method.equals("register")) {
			request.getRequestDispatcher(defaultRegister).forward(request, response);
		} else {
			response.sendRedirect("errors");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
															throws IOException, ServletException
	{
		String method = request.getParameter("method");
		if (method == null) {
			loginPost(request, response);
		} else if (method.equals("register")) {
			registerPost(request, response);
		} else {
			response.sendRedirect("errors");
		}
	}

	private void loginPost(HttpServletRequest request,HttpServletResponse response)
															throws IOException, ServletException
	{
		List errors = new ArrayList();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		PersonVo pv = new PersonVo();
		pv.setUserName(username);
		pv.setPassword(password);
		pv.setErrors(errors);
		String path = defaultLogin;
		if(pv.loginValidate()) {
			if(PersonDAOFactory.getPersonDAOInstance().isLogin(pv)) {
				// request.setAttribute("name",pv.getName());
				path = successLogin;
			} else {
				errors.add("错误的用户ID及密码！");
			}
		}
		request.setAttribute("errors",errors);
		request.setAttribute("person",pv);
		request.getRequestDispatcher(path).forward(request,response);
	}
	private void registerPost(HttpServletRequest request,HttpServletResponse response)
															throws IOException, ServletException
	{
		List errors = new ArrayList();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("password_again");
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("email");
		PersonVo pv = new PersonVo();
		pv.setUserName(username);
		pv.setPassword(password);
		pv.setPasswordAgain(passwordAgain);
		pv.setTelephone(telephone);
		pv.setEmail(email);
		pv.setErrors(errors);
		if (pv.regValidate()) {
			if (PersonDAOFactory.getPersonDAOInstance().isUserExist(pv)) {
				errors.add("用户重复!");
			} else {
				if (PersonDAOFactory.getPersonDAOInstance().newUser(pv) <= 0) {
					errors.add("插入失败！");
				} else {
					errors.add("注册成功");
				}
			}
		}
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.setAttribute("person", pv);
			request.getRequestDispatcher(defaultRegister).forward(request,response);
		} else {
			response.sendRedirect("management/dashboard");
		}
	}
}