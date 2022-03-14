package com.example.sessionapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/questionnaire")
public class Questionnaire extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }


    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("</head>");
        writer.println("<body>");

        String page = req.getParameter("page");
        writer.println("<form action='questionnaire' method='post'>");
        if ("page1".equals(page)) {
            page1(writer);
        } else if ("page2".equals(page)) {
            page2(req, writer);
        } else if ("page3".equals(page)) {
            page3(req, writer);
        }
        writer.println("</form>");
        writer.println("</body>");
        writer.println("</html>");
    }

    private void page1(PrintWriter writer) {
        writer.println("問題一：<input type='text' name='p1q1'/><br/>");
        writer.println("問題二：<input type='text' name='p1q2'/><br/>");
        writer.println("<button type='submit' name='page' value='page2'>送出</button><br/>");
    }

    private void page2(HttpServletRequest request, PrintWriter writer) {
        String p1q1 = request.getParameter("p1q1");
        String p1q2 = request.getParameter("p1q2");

        HttpSession session = request.getSession();
        session.setAttribute("p1q1",p1q1);
        session.setAttribute("p1q2",p1q2);

        writer.println("問題三：<input type='text' name='p2q1'/><br/>");
//        writer.printf("<input type='hidden' name='p1q1' value='%s'>%n", p1q1);
//        writer.printf("<input type='hidden' name='p1q2' value='%s'>%n", p1q2);

        writer.println("<button type='submit' name='page' value='page3'>送出</button><br/>");
    }

    private void page3(HttpServletRequest request, PrintWriter writer) {
        HttpSession session = request.getSession();
        writer.println(session.getAttribute("p1q1") + "<br/>");
        writer.println(session.getAttribute("p1q2") + "<br/>");
        writer.println(request.getParameter("p2q1") + "<br/>");
        session.invalidate();
    }
}
