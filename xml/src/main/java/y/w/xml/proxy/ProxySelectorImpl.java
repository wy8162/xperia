/**
 * Original: http://docs.oracle.com/javase/6/docs/technotes/guides/net/proxies.html
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

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.lang.Integer;
import java.io.IOException;
import org.apache.log4j.Logger;

public class ProxySelectorImpl extends ProxySelector {
    static Logger log = Logger.getLogger(ProxySelectorImpl.class);
    // Keep a reference on the previous default
    ProxySelector defsel = null;
    boolean useProxy;
        
    /*
     * Inner class representing a Proxy and a few extra data
     */
    class InnerProxy {
        Proxy proxy;
        SocketAddress addr;
        // How many times did we fail to reach this proxy?
        int failedCount = 0;
        
        InnerProxy(InetSocketAddress a) {
                addr = a;
                proxy = new Proxy(Proxy.Type.HTTP, a);
        }
        
        SocketAddress address() {
                return addr;
        }
        
        Proxy toProxy() {
                return proxy;
        }
        
        int failed() {
                return ++failedCount;
        }
    }
        
    /*
     * A list of proxies, indexed by their address.
     */
    HashMap<SocketAddress, InnerProxy> proxies = new HashMap<SocketAddress, InnerProxy>();

    public ProxySelectorImpl(ProxySelector defaultSelector) {
          // Save the previous default
    	  if (defaultSelector == null)
    		   defsel = ProxySelector.getDefault();
    	  else defsel = defaultSelector;
          
          // Get proxy configurations
    	  useProxy = false;
          Properties prop = null;
          try {
        	  prop = ProxyConfiguration.getProperties("/proxy.properties");
        	  if (prop.getProperty("useProxy").equals("true") &&
        		  prop.getProperty("http.proxyHost") != null && !prop.getProperty("http.proxyHost").equals("") &&
        		  prop.getProperty("http.proxyPort") != null && !prop.getProperty("http.proxyPort").equals(""))
        		  useProxy = true;
          } catch (IOException e) {
        	  log.warn("Failed to load proxy configuration file proxy.properties. Proxy is not used.");
          }

          // Populate the HashMap (List of proxies)
          InnerProxy i;
          if ( useProxy ) {
            i = new InnerProxy(new InetSocketAddress( prop.getProperty("http.proxyHost"),
                                                      Integer.parseInt(prop.getProperty("http.proxyPort")) ));
            proxies.put(i.address(), i);
            
			if (prop.getProperty("http.proxyUser") != null && !prop.getProperty("http.proxyUser").equals("") &&
			    prop.getProperty("http.proxyPassword") != null && !prop.getProperty("http.proxyPassword").equals(""))
			{
				Authenticator.setDefault(new ProxyAuthenticator(prop.getProperty("http.proxyUser"),
															    prop.getProperty("http.proxyPassword")));
			}

            
            log.debug("Configuring proxy:" + prop.getProperty("http.proxyHost") + " port=" +
                                              prop.getProperty("http.proxyPort") + " user=" +
                                              prop.getProperty("http.proxyUser") + " password=" +
											  prop.getProperty("http.proxyPassword"));
          } else {
            log.debug("No proxy configured in ProxySelectorImpl");
          }
          // Add more proxies if necessary
    }
          
    /**
     * This is the method that the handlers will call.
     * Returns a List of proxy.
     */
    public java.util.List<Proxy> select(URI uri) {
        log.debug("Called ProxySelector.select(" + uri + ")");
        
        if (!useProxy) return defsel.select(uri);

        // Let's stick to the specs. 
        if (uri == null) {
                throw new IllegalArgumentException("URI can't be null.");
        }
        
        /*
         * If it's a http (or https) URL, then we use our own
         * list.
         */
        String protocol = uri.getScheme();
        if ( ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol)) && proxies.size() >= 1) {
                ArrayList<Proxy> l = new ArrayList<Proxy>();
                for (InnerProxy p : proxies.values()) {
                  l.add(p.toProxy());
                }
                return l;
        }
        
        /*
         * Not HTTP or HTTPS (could be SOCKS or FTP)
         * defer to the default selector.
         */
        if (defsel != null) {
                return defsel.select(uri);
        } else {
                ArrayList<Proxy> l = new ArrayList<Proxy>();
                l.add(Proxy.NO_PROXY);
                return l;
        }
    }
    
    /*
     * Method called by the handlers when it failed to connect
     * to one of the proxies returned by select().
     */
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        log.error("Failed to connect with '" + uri + " '" + sa + "', exception:" + ioe);
        // Let's stick to the specs again.
        if (uri == null || sa == null || ioe == null) {
                throw new IllegalArgumentException("Arguments can't be null.");
        }
        
        if (!useProxy) {
        	defsel.connectFailed(uri, sa, ioe);
        	return;
        }

        /*
         * Let's lookup for the proxy 
         */
        InnerProxy p = proxies.get(sa); 
        if (p != null) {
                /*
                 * It's one of ours, if it failed more than 3 times
                 * let's remove it from the list.
                 */
                if (p.failed() >= 3) proxies.remove(sa);
        } else {
                /*
                 * Not one of ours, let's delegate to the default.
                 */
                if (defsel != null) defsel.connectFailed(uri, sa, ioe);
        }
    }
}