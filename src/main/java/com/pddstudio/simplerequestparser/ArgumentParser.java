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
public final class ArgumentParser {

    public static final String ARG_PATH_SHORT = "-p";
    public static final String ARG_PATH_LONG = "--path";
    private static final int ARG_PATH_INDEX = 0;
    private static final String ARG_PATH_DESC = "Specify the path you want to use";

    public static final String ARG_HELP_SHORT = "-h";
    public static final String ARG_HELP_LONG = "--help";
    private static final int ARG_HELP_INDEX = 1;
    private static final String ARG_HELP_DESC = "Prints this help page";

    public static final String ARG_VERSION_SHORT = "-v";
    public static final String ARG_VERSION_LONG = "--version";
    private static final int ARG_VERSION_INDEX = 2;
    private static final String ARG_VERSION_DESC = "Prints the current version";

    public static final String ARG_DEBUG_SHORT = "-d";
    public static final String ARG_DEBUG_LONG = "--debug";
    private static final int ARG_DEBUG_INDEX = 3;
    private static final String ARG_DEBUG_DESC = "Forces the program to print log messages with the <LogType.DEBUG> flag. (Ignored by default)";

    public static final String ARG_ABOUT_SHORT = "-a";
    public static final String ARG_ABOUT_LONG = "--about";
    private static final int ARG_ABOUT_INDEX = 4;
    private static final String ARG_ABOUT_DESC = "Prints the about information";

    public static final String ARG_ADVANCED_SHORT = "-adv";
    public static final String ARG_ADVANCED_LONG = "--advanced";
    private static final int ARG_ADVANCED_INDEX = 5;
    private static final String ARG_ADVANCED_DESC = "Allows to configure the output of the parsing result.";

    private static final String[] validArgs = new String[] {
            ARG_PATH_SHORT, ARG_PATH_LONG,
            ARG_HELP_SHORT, ARG_HELP_LONG,
            ARG_VERSION_SHORT, ARG_VERSION_LONG,
            ARG_DEBUG_SHORT, ARG_DEBUG_LONG,
            ARG_ABOUT_SHORT, ARG_ABOUT_LONG,
            ARG_ADVANCED_SHORT, ARG_ADVANCED_LONG
    };

    public static boolean isValidArgument(String arg) {
        for(String valid : validArgs) {
            if(arg.toLowerCase().equals(valid)) {
                Logger.log(Logger.LogType.DEBUG, "found valid argument : " + arg);
                return true;
            }
        }
        return false;
    }

    private static String getHelpPageLine(int argIndex) {
        String shortCmd;
        String longCmd;

        switch (argIndex) {
            default:
            case ARG_HELP_INDEX:
                shortCmd = ARG_HELP_SHORT;
                longCmd = ARG_HELP_LONG;
                break;
            case ARG_ABOUT_INDEX:
                shortCmd = ARG_ABOUT_SHORT;
                longCmd = ARG_ABOUT_LONG;
                break;
            case ARG_VERSION_INDEX:
                shortCmd = ARG_VERSION_SHORT;
                longCmd = ARG_VERSION_LONG;
                break;
            case ARG_PATH_INDEX:
                shortCmd = ARG_PATH_SHORT;
                longCmd = ARG_PATH_LONG;
                break;
            case ARG_DEBUG_INDEX:
                shortCmd = ARG_DEBUG_SHORT;
                longCmd = ARG_DEBUG_LONG;
                break;
            case ARG_ADVANCED_INDEX:
                shortCmd = ARG_ADVANCED_SHORT;
                longCmd = ARG_ADVANCED_LONG;
                break;
        }

        return "[ " + shortCmd + " | " + longCmd + " ]";
    }

    public static void printValidArguments() {
        System.out.println("simple-request-parser arguments: [short command param | long command param] [explanation]");
        System.out.println("#### For active usage:");
        System.out.println(getHelpPageLine(ARG_PATH_INDEX) + " " + ARG_PATH_DESC);
        System.out.println(getHelpPageLine(ARG_ADVANCED_INDEX) + " " + ARG_ADVANCED_DESC);
        System.out.println(getHelpPageLine(ARG_DEBUG_INDEX) + " " + ARG_DEBUG_DESC);
        System.out.println("#### Misc other commands:");
        System.out.println(getHelpPageLine(ARG_HELP_INDEX) + " " + ARG_HELP_DESC);
        System.out.println(getHelpPageLine(ARG_VERSION_INDEX) + " " + ARG_VERSION_DESC);
        System.out.println(getHelpPageLine(ARG_ABOUT_INDEX) + " " + ARG_ABOUT_DESC);
    }
}
