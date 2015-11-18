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

import javax.xml.parsers.FactoryConfigurationError;
import java.io.Console;
import java.util.*;

/**
 * Project : Android IconManager
 * Author : pddstudio
 * Year : 2015
 */
public class SimpleRequestParser {

    public static final String CONFIG_FILE = "config.json";
    public static final String PROJECT_NAME = "Simple Request Parser [simple-request-parser]";

    private FolderLoader folderLoader;
    private final HashMap<Integer, String> tmpArgsList;
    private final String[] inputArgs;
    private final boolean advancedMode;
    private final Console console;

    public static void main(String[] args) {
        SimpleRequestParser simpleRequestParser = new SimpleRequestParser(args);
    }

    public SimpleRequestParser(String[] args) {
        this.tmpArgsList = new HashMap<>();
        this.inputArgs = args;
        this.console = System.console();
        for(int i = 0; i < args.length; i++) {
            if(ArgumentParser.isValidArgument(args[i])) tmpArgsList.put(i, args[i]);
        }
        if(tmpArgsList.isEmpty()) showHelp(true);
        //check if help is an argument
        if(tmpArgsList.containsValue(ArgumentParser.ARG_HELP_SHORT) || tmpArgsList.containsValue(ArgumentParser.ARG_HELP_LONG)) showHelp(true);
        //check if version is an argument
        if(tmpArgsList.containsValue(ArgumentParser.ARG_VERSION_SHORT) || tmpArgsList.containsValue(ArgumentParser.ARG_VERSION_LONG)) showVersion(true);
        //check if about is an argument
        if(tmpArgsList.containsValue(ArgumentParser.ARG_ABOUT_SHORT) || tmpArgsList.containsValue(ArgumentParser.ARG_ABOUT_LONG)) showAbout(true);
        //check if debug is forced before parsing other actions
        if(tmpArgsList.containsValue(ArgumentParser.ARG_DEBUG_SHORT) || tmpArgsList.containsValue(ArgumentParser.ARG_DEBUG_LONG)) Logger.forceDebug();
        //check for advanced argument before parsing path value
        if(tmpArgsList.containsValue(ArgumentParser.ARG_ADVANCED_SHORT) || tmpArgsList.containsValue(ArgumentParser.ARG_ADVANCED_LONG)) this.advancedMode = true;
        else this.advancedMode = false;
        //check if path argument is given
        if(tmpArgsList.containsValue(ArgumentParser.ARG_PATH_SHORT)) checkPathArgument(ArgumentParser.ARG_PATH_SHORT);
        if(tmpArgsList.containsValue(ArgumentParser.ARG_PATH_LONG)) checkPathArgument(ArgumentParser.ARG_PATH_LONG);
    }

    public void showHelp(boolean exitAfter) {
        ArgumentParser.printValidArguments();
        if(exitAfter) System.exit(0);
    }

    public void showVersion(boolean exitAfter) {
        HelperUtils.printVersion(this.getClass());
        if(exitAfter) System.exit(0);
    }

    public void showAbout(boolean exitAfter) {
        HelperUtils.printAbout(this.getClass());
        if(exitAfter) System.exit(0);
    }

    private void checkPathArgument(String arg) {
        Logger.clearConsole();
        Logger.log(Logger.LogType.INFO,"=======================================================");
        Logger.log(Logger.LogType.INFO, "===  " + PROJECT_NAME.toUpperCase() + "  ===");
        Logger.log(Logger.LogType.INFO,"=======================================================");
        int position = getArgPosition(arg);
        int paramPosition = ++position;
        if(position < 0 || position > inputArgs.length) {
            Logger.log(Logger.LogType.ERROR, "failed to get position for argument [" + arg + "]", Logger.ERR);
            System.exit(-1);
        } else if(paramPosition < 0 || paramPosition > inputArgs.length){
            Logger.log(Logger.LogType.ERROR, "failed to get position for argument parameter [" + arg + "::" + paramPosition + "]");
            System.exit(-1);
        } else {
            String pathVar = inputArgs[paramPosition];
            folderLoader = FolderLoader.load(pathVar);
            if(folderLoader.validateSystemValues()) {
                //for testing only
                if(advancedMode) {
                    if(console == null) {
                        Logger.log(Logger.LogType.ERROR, "UNABLE TO FIND SYSTEM CONSOLE. CAN'T CREATE CUSTOM CONFIGURATION. SKIPPING!", Logger.ERR);
                        folderLoader.parseConfig(true);
                    } else {
                        configureAdvancedOutput();
                    }
                } else {
                    folderLoader.parseConfig(true);
                }
            } else {
                System.exit(-1);
            }
        }
    }

    private int getArgPosition(String arg) {
        String[] valArray = tmpArgsList.values().toArray(new String[tmpArgsList.values().size()]);
        for(int i = 0; i < valArray.length; i++) {
            if(valArray[i].equals(arg)) return i;
        }
        return -1;
    }

    private void configureAdvancedOutput() {
        List<SingleApplication> applicationList = folderLoader.parseConfigAndCreateList(false);
        Logger.insertPlaceholder();
        Logger.log(Logger.LogType.INFO, "== ADVANCED CONFIGURATION ==");
        Logger.log(Logger.LogType.INFO, "Loaded "  + applicationList.size() + " item(s) from the given configuration.");
        Logger.log(Logger.LogType.INFO, "Preparing for custom configuration...");
        Logger.insertPlaceholder();

        /*
        Configuration for displaying several information about the parsed config file
        - Application Name
        - Application Package
        - Application Launcher Intent
        - Application Icon information
        - Application Hash Value
         */

        boolean showApp;
        boolean showAppValid;

        do {
            String showAppName = console.readLine("Display Application Name [y/n] ? ");
            if(showAppName.toLowerCase().equals("y") || showAppName.toLowerCase().equals("yes")) {
                showApp = true;
                showAppValid = true;
            } else if(showAppName.toLowerCase().equals("n") || showAppName.toLowerCase().equals("no")) {
                showApp = false;
                showAppValid = true;
            } else {
                Logger.log(Logger.LogType.WARNING, "Please answer with [y/Y] or [n/N]. (Short and Long version supported)");
                showApp = false;
                showAppValid = false;
            }
        } while (!showAppValid);
        Logger.log(Logger.LogType.DEBUG, "Display Application Name set to -> [" + showApp + "]");

        boolean showAppPackage;
        boolean showAppPackageValid;

        do {
            String showPkgName = console.readLine("Display Application Package Name [y/n] ? ");
            if(showPkgName.toLowerCase().equals("y") || showPkgName.toLowerCase().equals("yes")) {
                showAppPackage = true;
                showAppPackageValid = true;
            } else if(showPkgName.toLowerCase().equals("n") || showPkgName.toLowerCase().equals("no")) {
                showAppPackage = false;
                showAppPackageValid = true;
            } else {
                Logger.log(Logger.LogType.WARNING, "Please answer with [y/Y] or [n/N]. (Short and Long version supported)");
                showAppPackage = false;
                showAppPackageValid = false;
            }
        } while (!showAppPackageValid);
        Logger.log(Logger.LogType.DEBUG, "Display Application Package Name set to -> [" + showAppPackage + "]");

        boolean showLaunchIntent;
        boolean showLaunchIntentValid;

        do {
            String showIntent = console.readLine("Display Application Launcher Intent [y/n] ? ");
            if(showIntent.toLowerCase().equals("y") || showIntent.toLowerCase().equals("yes")) {
                showLaunchIntent = true;
                showLaunchIntentValid = true;
            } else if(showIntent.toLowerCase().equals("n") || showIntent.toLowerCase().equals("no")) {
                showLaunchIntent = false;
                showLaunchIntentValid = true;
            } else {
                Logger.log(Logger.LogType.WARNING, "Please answer with [y/Y] or [n/N]. (Short and Long version supported)");
                showLaunchIntent = false;
                showLaunchIntentValid = false;
            }
        } while (!showLaunchIntentValid);
        Logger.log(Logger.LogType.DEBUG, "Display Application Launcher Intent set to -> [" + showLaunchIntent + "]");

        boolean showIcon;
        boolean showIconValid;

        do {
            String showIco = console.readLine("Display Icon Information [y/n] ? ");
            if(showIco.toLowerCase().equals("y") || showIco.toLowerCase().equals("yes")) {
                showIcon = true;
                showIconValid = true;
            } else if(showIco.toLowerCase().equals("n") || showIco.toLowerCase().equals("no")) {
                showIcon = false;
                showIconValid = true;
            } else {
                Logger.log(Logger.LogType.WARNING, "Please answer with [y/Y] or [n/N]. (Short and Long version supported)");
                showIcon = false;
                showIconValid = false;
            }
        } while (!showIconValid);
        Logger.log(Logger.LogType.DEBUG, "Display Icon Information set to -> [" + showIcon + "]");

        boolean showHash;
        boolean showHashValid;

        do {
            String showHashVal = console.readLine("Display Application Hash-Value [y/n] ? ");
            if(showHashVal.toLowerCase().equals("y") || showHashVal.toLowerCase().equals("yes")) {
                showHash = true;
                showHashValid = true;
            } else if(showHashVal.toLowerCase().equals("n") || showHashVal.toLowerCase().equals("no")) {
                showHash = false;
                showHashValid = true;
            } else {
                Logger.log(Logger.LogType.WARNING, "Please answer with [y/Y] or [n/N]. (Short and Long version supported)");
                showHash = false;
                showHashValid = false;
            }
        } while (!showHashValid);
        Logger.log(Logger.LogType.DEBUG, "Display Application Hash-Value set to -> [" + showHash + "]");

        Logger.insertPlaceholder();
        Logger.log(Logger.LogType.INFO, "Configuration complete. Type [show] to display the results");

        boolean show = false;

        do {
            String inp = console.readLine();
            if(inp.toLowerCase().equals("show")) show = true;
        } while (!show);

        if(!showApp && !showAppPackage && !showLaunchIntent && !showIcon && !showHash) {
            Logger.log(Logger.LogType.INFO, "All outputs set to false. Nothing to show here! >.<");
        } else {
            Logger.clearConsole();
            for(SingleApplication singleApplication : applicationList) {
                singleApplication.printAttrs(showApp, showAppPackage, showLaunchIntent, showIcon, showHash);
            }
        }
    }

}
