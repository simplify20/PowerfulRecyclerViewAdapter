package com.steve.creact.processor.core;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * A Wrapper for Messager
 * Created by Administrator on 2016/4/11.
 */
public class Logger {
    public static boolean logOn = false;
    public static Logger getInstance(Messager messager) {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance != null)
                    return instance;
                instance = new Logger(messager);
            }
        }
        return instance;
    }

    private static Messager actual;
    private static volatile Logger instance;
    private Logger(Messager actual) {
        this.actual = actual;
    }

    public void log(Diagnostic.Kind kind, String message) {
        if (logOn && actual != null) {
            actual.printMessage(kind, message);
        }
    }
}
