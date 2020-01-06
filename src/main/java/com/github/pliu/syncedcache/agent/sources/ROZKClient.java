package com.github.pliu.syncedcache.agent.sources;

import com.github.pliu.syncedcache.agent.handlers.Handler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

import java.util.concurrent.Semaphore;

public class ROZKClient {

    private CuratorFramework client;
    private TreeCache cache;

    private static TreeCacheListener router = (curatorFramework, treeCacheEvent) -> {
        switch (treeCacheEvent.getType()) {
            case NODE_ADDED: {
                System.out.println("TreeNode added: "
                        + ZKPaths
                        .getNodeFromPath(treeCacheEvent.getData().getPath())
                        + ", value: "
                        + new String(treeCacheEvent.getData().getData()));
                break;
            }
            case NODE_UPDATED: {
                System.out.println("TreeNode changed: "
                        + ZKPaths
                        .getNodeFromPath(treeCacheEvent.getData().getPath())
                        + ", value: "
                        + new String(treeCacheEvent.getData().getData()));
                break;
            }
            case NODE_REMOVED: {
                System.out
                        .println("TreeNode removed: "
                                + ZKPaths.getNodeFromPath(treeCacheEvent.getData()
                                .getPath()));
                break;
            }
            default:
                System.out
                        .println("Other event: " + treeCacheEvent.getType().name());
        }
    };

    public ROZKClient(String zkConnString, String path) {
        try {
            Semaphore ready = new Semaphore(0);


            client = CuratorFrameworkFactory.newClient(zkConnString, new BoundedExponentialBackoffRetry(1000, 10000, 3));
            client.start();

            cache = new TreeCache(client, path);
            cache.start();

            TreeCacheListener readyListener = (curatorFramework, treeCacheEvent) -> {
                if (treeCacheEvent.getType() == TreeCacheEvent.Type.INITIALIZED) {
                    ready.release();
                }
            };
            cache.getListenable().addListener(readyListener);

            ready.acquire();

            cache.getListenable().addListener(router);
        } catch (Exception e) {

        }
    }
}
