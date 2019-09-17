package y.w.xml;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class FirstDOMTest {
	static Logger log = Logger.getLogger(FirstDOMTest.class);
    @Before public void setUpTestEnvironment() {
    }
    
    @After public void cleanUpTestEnvironment() {
    }

    @Test public void doTestJobs() {
    	log.info("Creating FirstDOM...");
		FirstDOM fd = new FirstDOM();
		
		assertNotNull(fd);
		
		log.info("creating books.xml...");
		fd.createDocument();

		log.info("Parsing fib.xml...");
		InputStream in = ClassLoader.class.getResourceAsStream("/books.xml");
		assertNotNull(in);
		
		fd.parse(in);
		try {
			in.close();
		} catch (IOException e) {}
    }
}
    