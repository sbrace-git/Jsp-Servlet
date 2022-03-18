package com.example.loginpassword;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;
import java.util.stream.Collectors;

@WebServlet("/captcha")
public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String passwd = new Random().ints(0, 10)
                .limit(4).mapToObj(String::valueOf).collect(Collectors.joining());
        resp.setContentType("image/jpeg");
        ImageIO.write(
                getPasswdImage(passwd),
                "JPG",
                resp.getOutputStream()
        );
    }

    private BufferedImage getPasswdImage(String passwd) {
        BufferedImage bufferedImage = new BufferedImage(60, 25, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(null, Font.BOLD, 16));
        graphics.drawString(passwd, 10, 15);
        return bufferedImage;
    }
}
