package cc.openhome;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FragmentMainServlet extends HttpServlet {

    @Override
    public void init() {
        System.out.println("fragment-main FragmentMainServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write(getServletName());
    }
}
