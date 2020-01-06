package com.github.pliu.syncedcache.shared.pluginframework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class CLoader {

    private String manifestKey;

    public CLoader(String manifestKey) {
        this.manifestKey = manifestKey;
    }

    public HashSet<Class> getClassesFromPath(String path) {
        File maybeDir = new File(path);
        HashSet<Class> classes = new HashSet<>();
        if (isDirectory(maybeDir)) {
            File[] files = maybeDir.listFiles((file, name) -> name.endsWith(".jar"));
            for (File file : files) {
                classes.addAll(loadClassesFromJar(file));
            }
        }
        return classes;
    }

    private HashSet<Class> loadClassesFromJar(File jar) {
        String[] classNames = getClassNamesFromManifest(jar);
        HashSet<Class> classes = new HashSet<>();
        try {
            URLClassLoader classloader = new URLClassLoader(new URL[]{new URL("jar:file:" +
                    jar.getAbsolutePath() + "!/")});
            for (String className : classNames) {
                try {
                    Class c = classloader.loadClass(className);
                    classes.add(c);
                } catch (ClassNotFoundException e) {
                    System.out.println(className + " not found");
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: jar:file:" + jar.getAbsolutePath() + "!/");
        }
        return classes;
    }

    private String[] getClassNamesFromManifest(File jar) {
        try {
            JarFile jarFile = new JarFile(jar);
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes attributes = manifest.getMainAttributes();
                String unparsedClassNames = attributes.getValue(this.manifestKey);
                if (unparsedClassNames != null) {
                    return parseClassNames(unparsedClassNames);
                } else {
                    System.out.println("No " + this.manifestKey + " in the manifest of " + jar.getPath());
                    return new String[0];
                }
            } else {
                System.out.println("No manifest in " + jar.getPath());
                return new String[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private static String[] parseClassNames(String unparsedClassNames) {
        String[] classNames = unparsedClassNames.split(",");
        for (int i = 0; i < classNames.length; i++) {
            classNames[i] = classNames[i].trim();
        }
        return classNames;
    }

    private static boolean isDirectory(File file) {
        return file.exists() && file.isDirectory();
    }
}
