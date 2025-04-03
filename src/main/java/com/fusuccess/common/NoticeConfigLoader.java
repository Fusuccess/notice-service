package com.fusuccess.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusuccess.config.AppConfig;

import java.io.File;
import java.io.IOException;

public class NoticeConfigLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AppConfig loadConfig(String path) throws IOException {
        return objectMapper.readValue(new File(path), AppConfig.class);
    }
}
