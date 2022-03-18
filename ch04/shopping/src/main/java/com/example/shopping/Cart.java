package com.example.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@WebServlet("/cart.view")
public class Cart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> cart = (List<String>) req.getSession().getAttribute("cart");
        if (null == cart) {
            resp.sendRedirect("shopping");
        }
        Map<String, Long> books = cart.stream().collect(groupingBy(String::toString, counting()));
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>購物車</title></head><body>");
        out.printf("已採購  %s 本書籍 <br><br>", cart.size());
        out.println("<table style='text-align: left; width: 193px; height: 74px;' border='1' cellpadding='2' cellspacing='2'><tbody>");

        books.forEach((book, count) -> {
            out.println("<tr>");
            out.printf("<td style='width: 109px;'><img style='width: 89px; height: 120px;' alt='' src='images/%s.jpg'><br></td>", book);
            out.printf("<td style='width: 232px;text-align: center;'>共 %s 本<br></td></tr>",  count);
        });

        out.println("</tbody>");
        out.println("</table>");
        out.println("<br>");
        out.println("</body></html>");

    }
}
