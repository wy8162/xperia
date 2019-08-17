import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * WebServletContainerInitializer
 *
 * Need to create a file named y.w.web.config.WebServletContainerInitializer in META-INF, which
 * has contents of y.w.web.config.WebServletContainerInitializer. This will Bootstrap the Application
 * Using a WebServletContainerInitializer
 *
 * @author ywang
 * @date 8/6/2019
 */
public class WebServletContainerInitializer extends SpringServletContainerInitializer
{
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
    {
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();

        // Define a Servlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

        // Servlet is named "webservlet"
        ServletRegistration.Dynamic webRegistration =
                servletContext.addServlet("webservlet", dispatcherServlet);

        webRegistration.setLoadOnStartup(1);

        // Default root
        webRegistration.addMapping("/");
    }
}
