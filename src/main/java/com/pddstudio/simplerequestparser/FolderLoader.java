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

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : Android IconManager
 * Author : pddstudio
 * Year : 2015
 */
public class FolderLoader {

    private static FolderLoader folderLoader;

    private final String sysPath;
    private final File workDir;
    private final File configFile;
    private final List<SingleApplication> singleApplicationList;

    private FolderLoader(String path) {
        this.sysPath = path;
        this.workDir = new File(path);
        this.configFile = new File(workDir, SimpleRequestParser.CONFIG_FILE);
        this.singleApplicationList = new ArrayList<>();
    }

    protected static FolderLoader load(String path) {
        folderLoader = new FolderLoader(path);
        return folderLoader;
    }

    protected static FolderLoader getCurrent() {
        return folderLoader;
    }

    public boolean folderExist() {
        return workDir.exists() && workDir.isDirectory();
    }

    public boolean configExist() {
        return configFile.exists() && configFile.isFile();
    }

    public File getWorkDir() {
        return workDir;
    }

    public void parseConfig(boolean printOutput) {
        singleApplicationList.clear();
        if(configFile.exists() && configFile.isFile()) {
            try {
                List<String> configList = FileUtils.readLines(configFile);
                if(configList == null) {
                    Logger.log(Logger.LogType.ERROR, "configList<> is null!", Logger.ERR);
                } else if(configList.isEmpty()) {
                    Logger.log(Logger.LogType.WARNING, "configList<> is empty.");
                } else {
                    Logger.log(Logger.LogType.INFO, "loaded " + configList.size() + " lines from config.");
                    for (String jsonLine : configList) {
                        Gson gson = new Gson();
                        SingleApplication singleApplication = gson.fromJson(jsonLine, SingleApplication.class);
                        if (singleApplication == null) {
                            Logger.log(Logger.LogType.ERROR, "unable to create POJO object from config line.", Logger.ERR);
                        } else {
                            if (printOutput) {
                                System.out.println();
                                Logger.log(Logger.LogType.INFO, "Application Name: " + singleApplication.getApplicationName());
                                Logger.log(Logger.LogType.INFO, "Application PlayStore Url: " + singleApplication.getPlaystoreUrl());
                                Logger.log(Logger.LogType.INFO, "Application Package: " + singleApplication.getApplicationPackage());
                                Logger.log(Logger.LogType.INFO, "Application Launcher Activity: " + singleApplication.getApplicationLaunchIntent());
                                Logger.log(Logger.LogType.INFO, "Application Exported Icon Name: " + singleApplication.getApplicationExportedIconName() + " || Local Icon-Resource exist: " + (new File(workDir, singleApplication.getApplicationExportedIconName()).exists() ? "[FOUND FILE]" : "[NOT FOUND]" ));
                                Logger.log(Logger.LogType.INFO, "Application Hash-Value : " + singleApplication.getApplicationHashValue());
                            }
                            singleApplicationList.add(singleApplication);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<SingleApplication> parseConfigAndCreateList(boolean printOutput) {
        parseConfig(printOutput);
        return singleApplicationList;
    }

    public boolean validateSystemValues() {
        if (folderExist()) {
            Logger.log(Logger.LogType.INFO, "FOUND SYSTEM FOLDER [" + workDir.getAbsolutePath() + "] : YES");
        } else {
            Logger.log(Logger.LogType.ERROR, "FOUND SYSTEM FOLDER [" + workDir.getAbsolutePath() + "] : NO", Logger.ERR);
        }
        if (configExist()) {
            Logger.log(Logger.LogType.INFO, "FOUND CONFIG [" + configFile.getAbsolutePath() + "] : YES");
        } else {
            Logger.log(Logger.LogType.ERROR, "FOUND CONFIG [" + configFile.getAbsolutePath() + "] : NO", Logger.ERR);
        }
        return folderExist() && configExist();
    }


}
