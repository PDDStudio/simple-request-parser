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

import java.io.File;

/**
 * Project : Android IconManager
 * Author : pddstudio
 * Year : 2015
 */
public class SingleApplication {

    public static final String PLAYSTORE_URL_BASE = "https://play.google.com/store/apps/details?id=";

    public String applicationName;
    public String applicationPackage;
    public String applicationHashValue;
    public String applicationLaunchIntent;
    public transient String applicationExportedIconLocation;
    public transient boolean applicationSelected;
    public String applicationExportedIconName;

    static String UNKNOWN = "???";

    public SingleApplication() {
        this.applicationName = UNKNOWN;
        this.applicationPackage = UNKNOWN;
        this.applicationHashValue = UNKNOWN;
        this.applicationLaunchIntent = UNKNOWN;
        this.applicationExportedIconLocation = UNKNOWN;
        this.applicationSelected = false;
    }

    public SingleApplication(String name, String packageName, String hashValue, String launchIntent, String iconLocation, String iconName) {
        this.applicationName = name;
        this.applicationPackage = packageName;
        this.applicationHashValue = hashValue;
        this.applicationLaunchIntent = launchIntent;
        this.applicationExportedIconLocation = iconLocation;
        this.applicationExportedIconName = iconName;
        this.applicationSelected = false;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationPackage() {
        return applicationPackage;
    }

    public void setApplicationPackage(String applicationPackage) {
        this.applicationPackage = applicationPackage;
    }

    public String getApplicationHashValue() {
        return applicationHashValue;
    }

    public void setApplicationHashValue(String applicationHashValue) {
        this.applicationHashValue = applicationHashValue;
    }

    public String getApplicationLaunchIntent() {
        return applicationLaunchIntent;
    }

    public void setApplicationLaunchIntent(String applicationLaunchIntent) {
        this.applicationLaunchIntent = applicationLaunchIntent;
    }

    public void setApplicationExportedIconLocation(String absolutePathToImage) {
        this.applicationExportedIconLocation = absolutePathToImage;
    }

    public String getApplicationExportedIconLocation() {
        return applicationExportedIconLocation;
    }

    public void setApplicationExportedIconName(String iconName) {
        this.applicationExportedIconName = iconName;
    }

    public String getApplicationExportedIconName() {
        return applicationExportedIconName;
    }

    public boolean getIsSelected() {
        return applicationSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.applicationSelected = isSelected;
    }

    public void printAttrs(boolean showAppName, boolean showAppPkg, boolean showLauncherIntent, boolean showIconInfo, boolean showHash) {
        Logger.insertPlaceholder();
        if(showAppName) Logger.log(Logger.LogType.INFO, "Application Name: " + this.getApplicationName());
        if(showAppPkg) {
            Logger.log(Logger.LogType.INFO, "Application PlayStore Url: " + this.getPlaystoreUrl());
            Logger.log(Logger.LogType.INFO, "Application Package: " + this.getApplicationPackage());
        }
        if(showLauncherIntent) Logger.log(Logger.LogType.INFO, "Application Launcher Activity: " + this.getApplicationLaunchIntent());
        if(showIconInfo) Logger.log(Logger.LogType.INFO, "Application Exported Icon Name: " + this.getApplicationExportedIconName() + " || Local Icon-Resource exist: " + (new File(FolderLoader.getCurrent().getWorkDir(), this.getApplicationExportedIconName()).exists() ? "[FOUND FILE]" : "[NOT FOUND]" ));
        if(showHash) Logger.log(Logger.LogType.INFO, "Application Hash-Value: " + this.getApplicationHashValue());
    }

    public String getPlaystoreUrl() {
        return PLAYSTORE_URL_BASE + this.getApplicationPackage();
    }

}


