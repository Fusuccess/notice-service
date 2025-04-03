package com.fusuccess.strategy;

import java.util.Map;

public class PushClient {
    private PushStrategy pushStrategy;
    // 设置策略
    public void setPushStrategy(PushStrategy pushStrategy) {
        this.pushStrategy = pushStrategy;
    }

    // 执行推送
    public boolean executePush(String message, Map<String, String> info) {
        if (pushStrategy == null) {
            throw new IllegalStateException("推送策略未设置");
        }
        return pushStrategy.push(message, info);
    }
}
