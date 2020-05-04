package main.java;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClasspathInspector {
    static boolean DEBUG = false;

    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }

    public static List<Class<?>> getClassesFromJarFile(File path) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        log("getClassesFromJarFile: Getting classes for " + path);

        try {
            if (path.canRead()) {
                JarFile jar = new JarFile(path);
                Enumeration<JarEntry> en = jar.entries();
                while (en.hasMoreElements()) {
                    JarEntry entry = en.nextElement();

                    if (entry.getName().endsWith("class") && entry.getName().toLowerCase().contains("test")) {
                        String className = fromFileToClassName(entry.getName());
                        log("\tgetClassesFromJarFile: found " + className);
                        Class<?> claz = Class.forName(className);
                        classes.add(claz);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read classes from jar file: " + path, e);
        }

        return classes;
    }


    private static void log(String pattern, final Object... args) {
        if (DEBUG)
            System.out.printf(pattern + "\n", args);
    }

}