package com.fusuccess.service;

import com.fusuccess.module.notice.config.NoticeConfig;

public interface NoticeService {
    boolean sendMessage(String pushType, String message, NoticeConfig config);
} 