package cc.openhome;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Hello
 */
@WebServlet(value = "/hello", loadOnStartup = 0)
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init() {
		System.out.println("fragment-main Hello init");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.getWriter().append(getServletName());
	}

}
