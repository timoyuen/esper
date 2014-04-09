package register;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import login.person.*;

public class UserRegister extends HttpServlet
{
	private String path_base = "login/";
	private String default_path = path_base + "register.jsp";
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		request.getRequestDispatcher(default_path).forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
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
				if (!PersonDAOFactory.getPersonDAOInstance().newUser(pv)) {
					errors.add("插入失败！");
				} else {
					errors.add("注册成功");
				}
			}
		}
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.setAttribute("person", pv);
			request.getRequestDispatcher(default_path).forward(request,response);
		} else {
			response.sendRedirect("management/dashboard");
		}
	}
}