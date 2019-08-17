package y.w;

import y.w.spring.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * This class is needed for tests to load configurations.
 *
 * Must stay in this package so that it is visible to all the
 * tests under this package.
 */
@SpringBootApplication
@ImportResource("classpath:/spring/springbeans.xml")
@Import({ SpringConfig.class })
public class J8SpringTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(J8SpringTestApplication.class, args);
	}
}
