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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Project : Android IconManager
 * Author : pddstudio
 * Year : 2015
 */
public final class HelperUtils {

    private static final String MANIFEST_SRP_VERSION = "SRP-Version";
    private static final String MANIFEST_SRP_YEAR = "SRP-Year";
    private static final String MANIFEST_SRP_LICENSE_NAME = "SRP-License-Name";
    private static final String MANIFEST_SRP_DEV_NAME = "SRP-Developer-Name";
    private static final String MANIFEST_SRP_DEV_URL = "SRP-Developer-Url";
    private static final String MANIFEST_SRP_DEV_EMAIL = "SRP-Developer-Email";
    private static final String MANIFEST_SRP_DEV_GIT_NAME = "SRP-Dev-Git-Name";
    private static final String MANIFEST_SRP_DEV_GIT_URL = "SRP-Dev-Git-Url";

    private HelperUtils() {}

    public static void printVersion(Class<?> className) {
        File jarName;
        try {
            jarName = getClassSource(className);
            if(jarName == null) {
                Logger.log(Logger.LogType.ERROR, "UNABLE TO GET CLASS SOURCE FOR [" + className.getName() + "]", Logger.ERR);
            } else {
                JarFile jarFile = new JarFile(jarName);
                Attributes jarAttributes = jarFile.getManifest().getMainAttributes();

                if (jarAttributes == null) {
                    Logger.log(Logger.LogType.ERROR, "GIVEN ATTRIBUTES ARE NULL<>", Logger.ERR);
                } else {
                    boolean foundVersion = false;
                    for (Object key : jarAttributes.keySet()) {
                        if(key.toString().equals(MANIFEST_SRP_VERSION)) {
                            System.out.println(SimpleRequestParser.PROJECT_NAME + " Version: " + jarAttributes.get(key));
                            foundVersion = true;
                        }
                    }
                    if(!foundVersion) {
                        Logger.log(Logger.LogType.WARNING, "UNABLE TO FIND VERSION IN MANIFEST.");
                    }
                }
            }
        } catch (IOException e) {
            Logger.log(Logger.LogType.ERROR, "UNABLE TO GET MANIFEST FROM RESOURCES!\n" + e.getMessage(), Logger.ERR);
            e.printStackTrace();
        }
    }

    public static void printAbout(Class<?> className) {
        File jarName;
        try {
            jarName = getClassSource(className);
            if(jarName == null) {
                Logger.log(Logger.LogType.ERROR, "UNABLE TO GET CLASS SOURCE FOR [" + className.getName() + "]", Logger.ERR);
            } else {
                JarFile jarFile = new JarFile(jarName);
                Attributes jarAttributes = jarFile.getManifest().getMainAttributes();
                if(jarAttributes == null) {
                    Logger.log(Logger.LogType.ERROR, "GIVEN ATTRIBUTES ARE NULL<>", Logger.ERR);
                } else {
                    System.out.println();
                    System.out.println(SimpleRequestParser.PROJECT_NAME);
                    System.out.println("(c) " + getValueFromAttr(jarAttributes, MANIFEST_SRP_YEAR)
                            + " | " + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_NAME)
                            + " (" + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_GIT_NAME)
                            + ")");
                    System.out.println();
                    System.out.println("Version: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_VERSION));
                    System.out.println();
                    System.out.println("Developed by: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_NAME));
                    System.out.println("E-Mail: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_EMAIL));
                    System.out.println("Website: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_URL));
                    System.out.println("Github: @" + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_GIT_NAME));
                    System.out.println("Github Url: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_DEV_GIT_URL));
                    System.out.println();
                    System.out.println("Licensed under: " + getValueFromAttr(jarAttributes, MANIFEST_SRP_LICENSE_NAME));
                    System.out.println();
                }
            }
        } catch (IOException e) {
            Logger.log(Logger.LogType.ERROR, "UNABLE TO GET MANIFEST FROM RESOURCES!\n" + e.getMessage(), Logger.ERR);
            e.printStackTrace();
        }
    }

    public static File getClassSource(Class<?> className) {
        String classResource = className.getName().replace(".", "/") + ".class";
        ClassLoader classLoader = className.getClassLoader();
        if(classLoader == null) classLoader = HelperUtils.class.getClassLoader();
        URL url = null;
        if(classLoader == null) {
            url = ClassLoader.getSystemResource(classResource);
        } else {
            url = classLoader.getResource(classResource);
        }
        if(url != null) {
            String urlString = url.toString();
            int split;
            String jarName;
            if(urlString.startsWith("jar:file:")) {
                split = urlString.indexOf("!");
                jarName = urlString.substring(4, split);
                return new File(fromUri(jarName));
            } else if(urlString.startsWith("file:")) {
                split = urlString.indexOf(classResource);
                jarName = urlString.substring(0, split);
                return new File(fromUri(jarName));
            }
        }
        return null;
    }

    private static String fromUri(String uri) {
        URL url = null;
        try
        {
            url = new URL(uri);
        }
        catch(MalformedURLException emYouEarlEx)
        {
            // Ignore malformed exception
        }
        if(url == null || !("file".equals(url.getProtocol())))
        {
            throw new IllegalArgumentException("Can only handle valid file: URIs");
        }
        StringBuffer buf = new StringBuffer(url.getHost());
        if(buf.length() > 0)
        {
            buf.insert(0, File.separatorChar).insert(0, File.separatorChar);
        }
        String file = url.getFile();
        int queryPos = file.indexOf('?');
        buf.append((queryPos < 0) ? file : file.substring(0, queryPos));

        uri = buf.toString().replace('/', File.separatorChar);

        if(File.pathSeparatorChar == ';' && uri.startsWith("\\") && uri.length() > 2
                && Character.isLetter(uri.charAt(1)) && uri.lastIndexOf(':') > -1)
        {
            uri = uri.substring(1);
        }
        String path = decodeUri(uri);
        return path;
    }

    private static String decodeUri(String uri) {
        if(uri.indexOf('%') == -1)
        {
            return uri;
        }
        StringBuffer sb = new StringBuffer();
        CharacterIterator iter = new StringCharacterIterator(uri);
        for(char c = iter.first(); c != CharacterIterator.DONE; c = iter.next())
        {
            if(c == '%')
            {
                char c1 = iter.next();
                if(c1 != CharacterIterator.DONE)
                {
                    int i1 = Character.digit(c1, 16);
                    char c2 = iter.next();
                    if(c2 != CharacterIterator.DONE)
                    {
                        int i2 = Character.digit(c2, 16);
                        sb.append((char) ((i1 << 4) + i2));
                    }
                }
            }
            else
            {
                sb.append(c);
            }
        }
        String path = sb.toString();
        return path;
    }

    private static Object getValueFromAttr(Attributes attributes, String manifestValue) {
        for(Object key : attributes.keySet()) {
            if(key.toString().equals(manifestValue)) return attributes.get(key);
        }
        return null;
    }

}
