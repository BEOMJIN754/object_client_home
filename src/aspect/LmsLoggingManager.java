package aspect;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LmsLoggingManager {

	private static final Logger logger = Logger.getLogger("ServerLogger");
    private static final LmsLoggingManager serverLogger = new LmsLoggingManager();
    private FileHandler logFileHandler;

    private LmsLoggingManager() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String fileDate = dateFormat.format(new Date());
            String logDir = "logs";
            String logFileName = logDir + "/log_" + fileDate + ".txt";

            File dir = new File(logDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            logFileHandler = new FileHandler(logFileName, true);
            logFileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(logFileHandler);

            logger.setLevel(Level.INFO);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
    public static LmsLoggingManager getLogger() {
        return serverLogger;
    }
    public void log(Level level, String message) {
        logger.log(level, message);
    }
}
