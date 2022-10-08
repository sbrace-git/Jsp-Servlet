package com.example.async;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

@WebServlet(name = "asyncServlet", value = "/asyncServlet",
        // 在 service 方法执行完成之后，释放容器所分配的线程
        asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // AsyncServlet current thread name = http-nio-8080-exec-1
        printCurrentThreadName();
        response.setContentType("text/html; charset=UTF-8");
        AsyncContext asyncContext = request.startAsync();
        // AsyncServlet current thread name = http-nio-8080-exec-1
        printCurrentThreadName();

        printServletRequest(request);
        printServletResponse(response);

        doAsync(asyncContext);
    }

    private void doAsync(AsyncContext asyncContext) {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // AsyncServlet current thread name = ForkJoinPool.commonPool-worker-1
            printCurrentThreadName();
            ServletRequest request = asyncContext.getRequest();
            printServletRequest(request);
            String resource = request.getParameter("resource");
            return String.format("%s back finally ... XD", resource);
        }).thenApplyAsync(String::toUpperCase).thenAcceptAsync(resource -> {
//            if (true) {
//                asyncContext.dispatch("/asyncNumber");
//                return;
//            }
            // AsyncServlet current thread name = ForkJoinPool.commonPool-worker-1
            printCurrentThreadName();
            try {
                ServletResponse response = asyncContext.getResponse();
                printServletResponse(response);
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>" + resource + "</h1>");
                out.println("</body></html>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncContext.complete();
        });
    }

    private void printServletRequest(ServletRequest servletRequest) {
        System.out.printf("AsyncServlet servletRequest = %s%n", servletRequest);
    }

    private void printServletResponse(ServletResponse servletResponse) {
        System.out.printf("AsyncServletServletResponse = %s%n", servletResponse);

    }

    private void printCurrentThreadName() {
        System.out.printf("AsyncServlet current thread name = %s%n", Thread.currentThread().getName());
    }
}