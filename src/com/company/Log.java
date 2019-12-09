package com.company;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public static Logger LOGGER = setUpLogger();

    public static Logger setUpLogger() {
        Logger logger = Logger.getLogger("MyLog");

        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(System.getProperty("user.dir") + "LogFile.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        //logger.setUseParentHandlers(false);

        return logger;
    }
}
