package y.w.springdata;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Descriptions
 *
 * @author ywang
 */
@Component
public class AppCommandRunner implements CommandLineRunner
{
    private static final Log logger = LogFactory.getLog(AppCommandRunner.class);

    public AppCommandRunner()
    {
    }

    @Override
    public void run(String... args) throws Exception
    {
        logger.info("Run command to run or start tasks...");
    }
}
