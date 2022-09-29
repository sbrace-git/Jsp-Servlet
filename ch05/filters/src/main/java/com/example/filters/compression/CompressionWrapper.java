package com.example.filters.compression;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CompressionWrapper extends HttpServletResponseWrapper {
    private GzipServiceOutputStream gzipServiceOutputStream;

    private PrintWriter printWriter;

    public CompressionWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (null != printWriter) {
            throw new IllegalStateException();
        }
        if (null == gzipServiceOutputStream) {
            gzipServiceOutputStream = new GzipServiceOutputStream(getResponse().getOutputStream());
        }
        return gzipServiceOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (null != gzipServiceOutputStream) {
            throw new IllegalStateException();
        }
        if (null == printWriter) {
            gzipServiceOutputStream = new GzipServiceOutputStream(getResponse().getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(gzipServiceOutputStream, getResponse().getCharacterEncoding());
            printWriter = new PrintWriter(outputStreamWriter);
        }
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (null != printWriter) {
            printWriter.flush();
        } else if (null != gzipServiceOutputStream) {
            gzipServiceOutputStream.flush();
        }
        super.flushBuffer();
    }

    public void finish() throws IOException {
        if (null != printWriter) {
            printWriter.close();
        } else if (null != gzipServiceOutputStream) {
            gzipServiceOutputStream.close();
        }
    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }
}
