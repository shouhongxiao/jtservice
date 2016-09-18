package jtservice.JettyServer;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by shouh on 2016/9/14.
 */
public class NonblockingServletHolder extends ServletHolder {
    private final Servlet servlet;

    public NonblockingServletHolder(Servlet servlet) {
        super(servlet);
        setInitOrder(1);



        this.servlet = servlet;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Servlet getServlet() throws ServletException {
        return servlet;
    }

    @Override
    public void handle(Request baseRequest,
                       ServletRequest request,
                       ServletResponse response) throws ServletException, IOException {
        final boolean asyncSupported = baseRequest.isAsyncSupported();
        if (!isAsyncSupported()) {
            baseRequest.setAsyncSupported(false);
        }
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("begin="+startTime);
            servlet.service(request, response);
            long endTime = System.currentTimeMillis();
            System.out.println("end="+endTime);
            System.out.println((endTime - startTime) + " ms.");
        } finally {
            baseRequest.setAsyncSupported(asyncSupported);
        }
    }
}
