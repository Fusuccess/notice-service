package com.fusuccess.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusuccess.config.AppConfig;
import com.fusuccess.config.UserPushConfig;

import java.io.File;
import java.io.IOException;

public class PushConfigLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AppConfig loadConfig(String path) throws IOException {
        // 直接映射到 UserPushConfig 类
        return objectMapper.readValue(new File(path), AppConfig.class);
    }
}
