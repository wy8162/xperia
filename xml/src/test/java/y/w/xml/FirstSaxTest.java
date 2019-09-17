package y.w.xml;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import y.w.xml.proxy.ProxySelectorImpl;

public class FirstSaxTest {
	static Logger log = Logger.getLogger(FirstSaxTest.class);
    @Before public void setUpTestEnvironment() {
        ProxySelectorImpl ps = new ProxySelectorImpl(ProxySelector.getDefault());
        ProxySelector.setDefault(ps);
    }
    
    @After public void cleanUpTestEnvironment() {
    }

    @Test public void doParserTest() {
    	log.info("Creating FirstSax...");
		FirstSax fs = new FirstSax();
		
		assertNotNull(fs);
		
		log.info("Parsing books.xml...");
		InputStream in = ClassLoader.class.getResourceAsStream("/books.xml");
		assertNotNull(in);
		
		fs.parse(in);
		
		try {
			in.close();
		} catch (IOException e) {}
		
		log.info("Parsing fib.xml...");
		in = ClassLoader.class.getResourceAsStream("/fib.xml");
		assertNotNull(in);
		
		fs.parse(in);
    }
    
    @Test public void doValidatorTest() {
    	log.info("Creating FirstSax...This XInclude test is supposed to fail because of no XInclude DTD given.");
		FirstSax fs = new FirstSax();
		
		assertNotNull(fs);

		String systemId = ClassLoader.class.getResource("/xincludetest.xml").getFile();
        log.info("File: " + systemId);
		InputStream in;
		try {
			in = new FileInputStream(new File(systemId));
		} catch (FileNotFoundException e) {
			log.error("Failed to open file /xincludetest.xml");
			e.printStackTrace();
			return;
		}
		assertNotNull(in);
		InputSource is = new InputSource(in);
		
		// This is very important for XInclude to find the resources relative to this system ID.
		is.setSystemId(systemId);
		
		fs.validXmlAgainstDTD(is);
		
		try {
			in.close();
		} catch (IOException e) {}
    }
}
    