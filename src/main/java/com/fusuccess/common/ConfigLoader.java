package com.fusuccess.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusuccess.config.AppConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;


/**
 * 配置加载器，用于加载应用程序的配置文件。
 * author: fusuccess
 * email: fu2000520@gmail.com
 */
public class ConfigLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    public static AppConfig loadConfig(String path) throws IOException {
        try {
            AppConfig appConfig = objectMapper.readValue(new File(path), AppConfig.class);
            logger.info("加载配置: 成功");
            return appConfig;
        } catch (IOException e) {
            logger.error("加载配置文件失败", e);
            throw new RuntimeException(e);
        }
    }
}
