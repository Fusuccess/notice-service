package com.fusuccess.module.notice.strategy;

import com.fusuccess.module.notice.config.NoticeConfig;

/**
 * 推送策略接口，定义了推送方法。
 */
public class NoticeClient {
    private NoticeStrategy noticeStrategy;

    // 设置策略
    public void setPushStrategy(NoticeStrategy noticeStrategy) {
        this.noticeStrategy = noticeStrategy;
    }

    // 执行推送
    public boolean executePush(String message, NoticeConfig config) {
        if (noticeStrategy == null) {
            throw new IllegalStateException("推送策略未设置");
        }
        return noticeStrategy.push(message, config);
    }
}
