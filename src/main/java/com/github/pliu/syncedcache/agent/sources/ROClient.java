package com.github.pliu.syncedcache.agent.sources;

import com.github.pliu.syncedcache.agent.handlers.Handler;

public interface ROClient {

    boolean registerHandler(Handler handler);
}
