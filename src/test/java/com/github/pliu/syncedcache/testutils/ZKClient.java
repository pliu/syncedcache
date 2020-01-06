package com.github.pliu.syncedcache.testutils;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZKClient {
    private ZooKeeper zk;
    private CountDownLatch connSignal = new CountDownLatch(0);

    public ZKClient(String host) {
        try {
            zk = new ZooKeeper(host, 3000, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState() == KeeperState.SyncConnected) {
                        connSignal.countDown();
                    }
                }
            });
            connSignal.await();
        } catch (Exception e) {

        }
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    public void createNode(String path, byte[] data) throws Exception
    {
        zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public Object getZNodeData(String path)
            throws Exception {
        byte[] b = null;
        b = zk.getData(path, null, null);
        return new String(b, "UTF-8");
    }

    public void updateNode(String path, byte[] data) throws Exception
    {
        zk.setData(path, data, zk.exists(path, true).getVersion());
    }

    public void deleteNode(String path) throws Exception
    {
        zk.delete(path,  zk.exists(path, true).getVersion());
    }
}
