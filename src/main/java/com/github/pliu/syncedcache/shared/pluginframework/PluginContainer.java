package com.github.pliu.syncedcache.shared.pluginframework;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class PluginContainer<T extends Pluggable> {

    private final ConcurrentHashMap<String, TreeMap<Integer, T>> plugins;
    private final int generation;

    public PluginContainer(Map<String, TreeMap<Integer, T>> plugins, int generation) {
        this.plugins = new ConcurrentHashMap<>(plugins);
        this.generation = generation;
    }

    public boolean otherContainerNextGeneration(PluginContainer<T> otherContainer) {
        return this.generation + 1 == otherContainer.generation;
    }

    public T getLatestPlugin(String name) {
        TreeMap<Integer, T> pluginFamily = plugins.getOrDefault(name, new TreeMap<>());
        return pluginFamily.lastEntry().getValue();
    }

    public T getPluginByVersion(String name, Integer version) {
        TreeMap<Integer, T> pluginFamily = plugins.getOrDefault(name, new TreeMap<>());
        return pluginFamily.get(version);
    }
}
