package y.w.webapp.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * MyWebAppInitializer
 *
 * Needs Servlet 3.0-capable server, like Apache Tomcat 7 or higher. No need for web.xml.
 *
 * Under the covers, AbstractAnnotationConfigDispatcherServletInitializer creates
 * both a DispatcherServlet and a ContextLoaderListener. The @Configuration
 * classes returned from getServletConfigClasses() will define beans for
 * DispatcherServlet’s application context. Meanwhile, the @Configuration class’s returned
 * getRootConfigClasses() will be used to configure the application context created by
 * ContextLoaderListener.
 *
 * The interface implemented by AbstractAnnotationConfigDispatcherServletInitializer is WebApplicationInitializer, which
 * is recognized by SpringServletContainerInitializer, which in turn implements ServletContaierInitializer, which is
 * then recognized by Servlet 3.0+ container.
 *
 * To achieve that, file META-INF/services/javax.servlet.ServletContainerInitializer must contain
 * "org.springframework.web.SpringServletContainerInitializer".
 *
 * @author ywang
 * @date 8/14/2019
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    /**
     * Set the ApplicationContext for the whole web app. It's thr root
     * contexnt.
     *
     * ContextLoaderListene will load this application context.
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }

    /**
     * Set the WebApplicationContext.
     *
     * DispatcherServlet will create and load this web application context.
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[]{WebApplicationContextConfig.class};
    }

    /**
     * Map DispatcherServlet to "/". Servlet mapping specifies the Servlet to be
     * invoked by the container.
     *
     * All the controller request mappings are relative to Servlet mapping. Request
     * mappings decide the handler method to be called.
     *
     * @return
     */
    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }
}
