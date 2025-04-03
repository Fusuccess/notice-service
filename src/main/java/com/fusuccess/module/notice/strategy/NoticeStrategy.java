package com.fusuccess.module.notice.strategy;

import com.fusuccess.module.notice.config.NoticeConfig;

public interface NoticeStrategy {
    boolean push(String message, NoticeConfig config);
}
