package com.github.pliu.syncedcache;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.threadPool;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;


public class SyncedCache {
    public static void main(String[] args) {
        int maxThreads = 12;
        int timeoutMillis = 1000;
        threadPool(maxThreads, maxThreads, timeoutMillis);
        Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        port(8080);


        get("/", (request, response) -> "Test");
    }
}
