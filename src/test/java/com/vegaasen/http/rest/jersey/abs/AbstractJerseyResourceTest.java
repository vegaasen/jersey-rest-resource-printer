package com.vegaasen.http.rest.jersey.abs;

import com.vegaasen.http.jetty.container.variant.JettyContainer;
import com.vegaasen.http.jetty.model.JettyArguments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public abstract class AbstractJerseyResourceTest {

    protected JettyContainer jettyContainer;
    protected JettyArguments arguments;

    @Before
    public void setUp() throws Exception {
        arguments = new JettyArguments();
        arguments.setWebAppResourceFolder("webapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");
        arguments.setControlServlet(null);
        jettyContainer = new JettyContainer();
    }

    @After
    public void tearDown() throws Exception {
        jettyContainer.stopServer();
        jettyContainer.stopControlServer();
        System.out.println("Server stopped.");
    }

    @Test
    public void shouldJustStartServletContainer() {
        startServer();
        assertIsRunning();
    }

    public void startServer() {
        jettyContainer.startServer(arguments);
    }

    protected void assertIsRunning() {
        final boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
    }

    protected void assertControlServerIsRunning() {
        final boolean running = jettyContainer.isControlServerRunning();
        assertNotNull(running);
        assertTrue(running);
    }


}
