package management;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import login.person.*;
import esperengine.stock.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Error extends HttpServlet
{
	public Error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("error/error.jsp").forward(request, response);
	}
}