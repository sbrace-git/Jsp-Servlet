package cc.openhome.response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/mime")
public class MimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        if ("pdf".equalsIgnoreCase(file)) {
            file = "pdf.pdf";
        } else if ("docx".equalsIgnoreCase(file)) {
            file = "docx.docx";
        } else if ("xlsx".equalsIgnoreCase(file)) {
            file = "xlsx.xlsx";
        } else if ("html".equalsIgnoreCase(file)) {
            file = "html.html";
        } else if ("pptx".equalsIgnoreCase(file)) {
            file = "pptx.pptx";
        } else if ("jpg".equalsIgnoreCase(file)) {
            file = "jpg.jpg";
        } else {
            resp.setContentType("text/plain; charset=UTF-8");
            resp.getWriter().print("file name error");
        }
        ServletContext servletContext = getServletContext();
        String fileRealPath = servletContext.getRealPath("WEB-INF/file/" + file);

        String mimeType = servletContext.getMimeType(fileRealPath);
        resp.setContentType(mimeType);
        Files.copy(Paths.get(fileRealPath), resp.getOutputStream());
    }
}
