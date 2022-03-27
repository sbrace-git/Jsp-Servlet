package com.example.filters;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipServiceOutputStream extends ServletOutputStream {

    private ServletOutputStream servletOutputStream;

    private GZIPOutputStream gzipOutputStream;

    public GzipServiceOutputStream(ServletOutputStream servletOutputStream) throws IOException {
        this.servletOutputStream = servletOutputStream;
        this.gzipOutputStream = new GZIPOutputStream(servletOutputStream);
    }

    @Override
    public boolean isReady() {
        return servletOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        servletOutputStream.setWriteListener(writeListener);
    }

    @Override
    public void write(int b) throws IOException {
        gzipOutputStream.write(b);
    }

    public GZIPOutputStream getGzipOutputStream() {
        return gzipOutputStream;
    }

    @Override
    public void close() throws IOException {
        gzipOutputStream.close();
    }

    @Override
    public void flush() throws IOException {
        gzipOutputStream.flush();
    }

    public void finish() throws IOException {
        gzipOutputStream.finish();
    }
}
