package com.example.book;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpeg");
        ImageIO.write(
                getImage(req.getParameter("name")),
                "JPG",
                resp.getOutputStream()
        );
    }

    private BufferedImage getImage(String name) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(getServletContext().getResourceAsStream("WEB-INF/image/book.jpg"));
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(null, Font.BOLD, 22));
        graphics.drawString(name, 15, 45);
        return bufferedImage;
    }
}
