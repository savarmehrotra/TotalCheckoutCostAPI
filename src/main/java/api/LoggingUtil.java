package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

    private static Logger logger = LoggerFactory.getLogger(TotalCheckoutCostAPIHandler.class);

    public static void log(String logMessage) {

        logger.info(logMessage);
    }
}