package com.fusuccess.module.notice.strategy;

import com.fusuccess.module.notice.config.NoticeConfig;

/**
 * 推送策略接口，定义了推送方法。
 */
public interface NoticeStrategy {
    boolean push(String message, NoticeConfig config);
}
