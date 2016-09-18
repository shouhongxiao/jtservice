package jtservice.JettyServer;

import org.eclipse.jetty.server.AsyncContextState;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shouh on 2016/9/14.
 */
public class InstrumentedHandler extends HandlerWrapper {
    private AsyncListener listener;
    @Override
    protected void doStart() throws Exception {
        super.doStart();
        this.listener = new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                //asyncTimeouts.mark();
                System.out.println("onTimeout");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                event.getAsyncContext().addListener(this);
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
            }

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                final AsyncContextState state = (AsyncContextState) event.getAsyncContext();
                updateResponses(state.getRequest());
//                if (!state.getHttpChannelState().isd) {
//
//                }
            }
        };
    }

    @Override
    public void handle(String path,
                       Request request,
                       HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse) throws IOException, ServletException {



        final long start;
        final HttpChannelState state = request.getHttpChannelState();
        if (state.isInitial()) {
            // new request

            start = request.getTimeStamp();
            System.out.println(start);
        } else {
            // resumed request
            start = System.currentTimeMillis();

        }

        try {
            super.handle(path, request, httpRequest, httpResponse);
        } finally {
            final long now = System.currentTimeMillis();
            final long dispatched = now - start;

//            activeDispatches.dec();
//            dispatches.update(dispatched, TimeUnit.MILLISECONDS);

            if (state.isSuspended()) {
                if (state.isInitial()) {
                    state.addListener(listener);
                }
                //activeSuspended.inc();
            } else if (state.isInitial()) {
                updateResponses(request);
            }
            // else onCompletion will handle it.
        }
    }

    private void updateResponses(ServletRequest servletRequest) {
        if (servletRequest instanceof Request) {
            Request request = (Request) servletRequest;
            final int response = request.getResponse().getStatus() / 100;
            if (response >= 1 && response <= 5) {
                //responses[response - 1].mark();
                System.out.println(response);
            }
            //activeRequests.dec();
            final long elapsedTime = System.currentTimeMillis() - request.getTimeStamp();
            System.out.println(elapsedTime);
            //requests.update(elapsedTime, TimeUnit.MILLISECONDS);
            //requestTimer(request.getMethod()).update(elapsedTime, TimeUnit.MILLISECONDS);
        }
    }
}
