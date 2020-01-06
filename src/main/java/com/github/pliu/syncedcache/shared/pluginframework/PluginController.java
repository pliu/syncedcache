package com.github.pliu.syncedcache.shared.pluginframework;

import java.util.HashSet;

public class PluginController<T extends Pluggable> {

    private Class<T> clazz;
    private HashSet<String> watchedPaths;
    private PluginContainer<T> plugins;

    public PluginController(Class<T> clazz) {
        this.clazz = clazz;
        watchedPaths = new HashSet<>();
    }

    public void addWatchedPath(String path) {
        watchedPaths.add(path);
    }

    public void removeWatchedPath(String path) {
        watchedPaths.remove(path);
    }

    public synchronized boolean updateContainer(PluginContainer<T> newPlugins) {
        if (plugins.otherContainerNextGeneration(newPlugins)) {
            plugins = newPlugins;
            return true;
        }
        return false;
    }


}
