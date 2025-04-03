package com.fusuccess.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PropertiesInit {

    public Map<String,String> getProperties() {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "/Users/a1234/Documents/properties/properties.json";
        try {
            // 读取 JSON 文件到 Map
            Map<String, Object> jsonMap = objectMapper.readValue(new File(path), Map.class);
            // Bug 修复：修正拼写错误，从 Map 中获取 properties 对象
            Map<String, Object> propertiesMap = (Map<String, Object>) jsonMap.get("properties");
            // 确保 propertiesMap 不为空
            if (propertiesMap == null) {
                System.err.println("properties 字段不存在于 JSON 文件中");
                return null;
            }
            // 从 propertiesMap 中获取 dingtalk 对象
            return (Map<String, String>) propertiesMap.get("dingtalk");
        } catch (IOException e) {
            System.err.println("读取属性文件时出错: " + e.getMessage());
        }
        return null;
    }
}
