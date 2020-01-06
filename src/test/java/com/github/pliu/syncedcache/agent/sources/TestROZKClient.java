package com.github.pliu.syncedcache.agent.sources;

import com.github.pliu.syncedcache.testutils.ZKClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

@RunWith(JUnit4.class)
public class TestROZKClient {

    @Test
    public void test1() throws Exception {
        System.out.println("lol");
        ROZKClient client = new ROZKClient("localhost:2181", "/test");
        ZKClient testClient = new ZKClient("localhost:2181");
        System.out.println(testClient.getZNodeData("/test/lol"));
    }

}
