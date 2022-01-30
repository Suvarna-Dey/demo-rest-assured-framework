package org.truelayer.challenge.util;

import org.apache.log4j.Logger;

public class Log {
    private static Logger Log = Logger.getLogger(Log.class.getName());

    public static void startLogging() {
        Log.info("#######################" + "-S---T---A---R---T-" + "######################");
    }

    public static void Logging(String sDescription) {
        Log.info(sDescription);
    }

    public static void endLogging() {

        Log.info("#######################" + "-E---N---D-" + "######################");
    }

    public static void info(String message) {

        Log.info(message);

    }

    public static void warn(String message) {

        Log.warn(message);

    }

    public static void error(String message) {

        Log.error(message);

    }
    public static void fatal(String message) {

        Log.fatal(message);

    }

    public static void debug(String message) {

        Log.debug(message);

    }
}
