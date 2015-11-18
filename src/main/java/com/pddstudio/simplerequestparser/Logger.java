/*
 * Copyright (c) 2015 - Android IconManager - Patrick J (pddstudio)
 * Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.pddstudio.simplerequestparser;

/**
 * Project : Android IconManager
 * Author : pddstudio
 * Year : 2015
 */
public class Logger {

    public enum LogType {
        INFO("INFO"),
        WARNING("WARNING"),
        ERROR("ERROR"),
        DEBUG("DEBUG");


        private String typeName;
        LogType(String name) {
            this.typeName = name;
        }

        public String getLoggerPrefix() {
            return "[" + typeName + "]";
        }
    }

    public static final int OUT = 0;
    public static final int ERR = 1;

    private static Logger logger;
    private static boolean forceDebug = false;

    private Logger() {}

    private static synchronized void logMessage(String msg, int outCh) {
        if(logger == null) logger = new Logger();
        if(outCh == ERR) {
            System.err.println(msg);
        } else {
            System.out.println(msg);
        }
    }

    public static void insertPlaceholder() {
        logMessage("", OUT);
    }

    public static void forceDebug() {
        forceDebug = true;
    }

    public static void log(String customPrefix, String message) {
        logMessage("[" + customPrefix + "] : " + message, OUT);
    }

    public static void log(String customPrefix, String message, int outputChannel) {
        logMessage("[" + customPrefix + "] : " + message, outputChannel);
    }

    public static void log(LogType logType, String message) {
        if(logType.equals(LogType.DEBUG) && forceDebug) {
            logMessage(logType.getLoggerPrefix() + " : " + message, OUT);
        } else if(!logType.equals(LogType.DEBUG)){
            logMessage(logType.getLoggerPrefix() + " : " + message, OUT);
        }
    }

    public static void log(LogType logType, String message, int outputChannel) {
        if(logType.equals(LogType.DEBUG) && forceDebug) {
            logMessage(logType.getLoggerPrefix() + " : " + message, outputChannel);
        } else if(!logType.equals(LogType.DEBUG)) {
            logMessage(logType.getLoggerPrefix() + " : " + message, outputChannel);
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

}
