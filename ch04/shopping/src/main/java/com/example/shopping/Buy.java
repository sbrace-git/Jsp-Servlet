package com.example.shopping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/buy")
public class Buy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String book = req.getParameter("book");
        if (null != book) {
            HttpSession session = req.getSession();
            List<String> cart = Optional.ofNullable((List<String>) session.getAttribute("cart"))
                    .orElseGet(ArrayList::new);
            cart.add(book);
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect("shopping");
    }
}
