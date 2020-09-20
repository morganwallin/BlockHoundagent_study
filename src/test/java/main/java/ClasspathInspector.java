package main.java;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClasspathInspector {

    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }

    public static List<Class<?>> getClassesFromJarFile(File path) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            if (path.canRead()) {
                JarFile jar = new JarFile(path);
                Enumeration<JarEntry> en = jar.entries();
                while (en.hasMoreElements()) {
                    JarEntry entry = en.nextElement();

                    if (entry.getName().endsWith("class") && entry.getName().toLowerCase().contains("test")) {
                        String className = fromFileToClassName(entry.getName());
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

    public static List<Class<?>> getClassesInPackage(String packageName) {
        String path = packageName.replace(".", File.separator);
        List<Class<?>> classes = new ArrayList<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );


        String name;
        for (String classpathEntry : classPathEntries) {

                try {

                    File directory = new File(classpathEntry + File.separatorChar + path);
                    if(!directory.isDirectory()) {
                        continue;
                    }
                    Collection<File> files = FileUtils.listFiles(directory, null, true);
                    if(files.size() == 0) {
                        continue;
                    }
                    for (File file : files) {
                        name = file.getName();
                        name = name.replace("/", File.separator);
                        if (name.endsWith(".class")) {
                            if(file.getPath().contains(path)) {
                                String classPath = file.getPath().substring(file.getPath().indexOf(path));
                                classPath = classPath.substring(0, classPath.length()-6);
                                Class<?> className = Class.forName(classPath.replace(File.separator, "."));
                                System.out.println(className);
                                classes.add(className);
                            }

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        //}

        return classes;
    }

}