package com.example.firstjsp;

import com.example.firstjsp.model.Bookmark;
import com.example.firstjsp.service.BookmarkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/listBookmark")
public class ListBookmark extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<title>观看在线书签</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<table style='text-align:left; width:100%;' border='0'>");
        writer.println("    <tbody>");
        writer.println("        <tr>");
        writer.println("            <td style='background-color: rgb(51,255,255);'>网页</td>");
        writer.println("            <td style='background-color: rgb(51,255,255);'>分类</td>");
        writer.println("        </tr>");

        BookmarkService bookmarkService = (BookmarkService) getServletContext().getAttribute("bookmarkService");
        for (Bookmark bookmark : bookmarkService.getBookmarkList()) {
            writer.println("    <tr>");
            writer.println("        <td><a href='http://" + bookmark.getUrl() + "'>" + bookmark.getTitle() + "</a></td>");
            writer.println("        <td>" + bookmark.getCategory() + "</td>");
            writer.println("    </tr>");
        }

        writer.println("    </tbody>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
        writer.close();
    }
}
