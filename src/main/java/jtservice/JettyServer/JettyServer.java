package jtservice.JettyServer;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import jtservice.utility.MyResourceConfig;
import jtservice.utility.ZkConfigParser;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import java.util.concurrent.BlockingQueue;

/**
 * Created by shouh on 2016/9/14.
 */
public class JettyServer {
    private Server jetty;

    private final ResourceConfig resourceConfig = new MyResourceConfig(ZkConfigParser.getProperty("packages"));

    public JettyServer() {
//        URI baseUri = UriBuilder
//                .fromUri("http://localhost/")
//                .port(port)
//                .build();
        int port = Integer.parseInt(ZkConfigParser.getProperty("port"));
        ThreadPool threadPool = createThreadPool();
        jetty = buildServer(port, threadPool);
    }



    public void start() throws Exception {

        doStart(false);
    }

    public void startAndJoin() throws Exception {

        doStart(true);
    }

    private void doStart(boolean join) throws Exception {

        jetty.start();
        if (join) {
            jetty.join();
        }

    }

    public void stop() throws Exception {

        jetty.stop();
    }


    protected ThreadPool createThreadPool() {
        final BlockingQueue<Runnable> queue = new BlockingArrayQueue(8, 1024, 1024);
        final QueuedThreadPool threadPool = new QueuedThreadPool(1024, 8, 100, queue);
        return threadPool;
    }

    protected Server buildServer(Integer port, ThreadPool threadPool) {
        final Server server = new Server(threadPool);
        //server.addLifeCycleListener(buildSetUIDListener());
        //lifecycle.attach(server);
        final ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setServer(server);
        errorHandler.setShowStacks(false);
        server.addBean(errorHandler);
        server.setStopAtShutdown(true);
        server.setStopTimeout(30l);
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        //AbstractHandler

        //HandlerWrapper
        //ServletHandler
        //WebAppContext
        ServerConnector serverConnector = new ServerConnector(server);
        //serverConnector.setHost();
        serverConnector.setPort(port);
        server.addConnector(serverConnector);
        servletContextHandler.addServlet(new NonblockingServletHolder(servletContainer), "/*");
        //server.setHandler(servletContextHandler);
        HandlerCollection handlerCollection = new HandlerCollection();

        HandlerList list = new HandlerList();
        InstrumentedHandler instrumentedHandler = new InstrumentedHandler();
        instrumentedHandler.setServer(server);
        instrumentedHandler.setHandler(servletContextHandler);

        list.setHandlers(new Handler[]{instrumentedHandler,servletContextHandler});
        //handlerCollection.addHandler(instrumentedHandler);
        //handlerCollection.addHandler(servletContextHandler);

        server.setHandler(list);
        //server.setHandler(handlerCollection);
        return server;
    }
}
