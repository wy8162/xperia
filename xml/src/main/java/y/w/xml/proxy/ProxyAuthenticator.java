/**
 * Original: http://docs.oracle.com/javase/6/docs/technotes/guides/net/proxies.html
 * Reference: http://docs.oracle.com/javase/6/docs/technotes/guides/net/proxies.html
 *
 * To use this class:
 *      ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
 *      ProxySelector.setDefault(ps);
 *      Authenticator.setDefault(new ProxyAuthenticator("yang_wang", "xxxx"));
 *      ...
 *      URL url = new URL("http://java.sun.com/index.html");
 *      URLConnection conn = url.openConnection();
 *      InputStream in = conn.getInputStream();
 */
package y.w.xml.proxy;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.apache.log4j.Logger;

public class ProxyAuthenticator extends Authenticator {
    private String userName, password;

    static Logger log = Logger.getLogger(ProxyAuthenticator.class);

    public ProxyAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     *  Default constructor will try to use System properties http.proxyUser and http.proxyPassword.
     */
    public ProxyAuthenticator() {
        if ( System.getProperty("http.proxyUser") != null && !System.getProperty("http.proxyUser").equals("") &&
             System.getProperty("http.proxyPassword") != null && !System.getProperty("http.proxyPassword").equals("") ) {
        
            log.debug("Configuring proxy user name and password from system property:" + System.getProperty("http.proxyUser"));
            this.userName = System.getProperty("http.proxyUser");
            this.password = System.getProperty("http.proxyPassword");
        } else {
            log.debug("No proxy user name and password configured");
            userName = null;
            password = null;
        }
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password.toCharArray());
    }
    
    /**
     * If you need pass through an authenticating proxy, you need an authenticator
     */
    /*
    public static void main(String[] args) {
        String username = System.getProperty("proxy.authentication.username");
        String password = System.getProperty("proxy.authentication.password");

        if (username != null && !username.equals("")) {
            Authenticator.setDefault(new ProxyAuthenticator(username, password));
        }
        // here your JVM will be authenticated
    }
    */
}