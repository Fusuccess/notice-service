package com.fusuccess.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusuccess.dingtalk.DingTalk;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PropertiesInit {

    public void getProperties(DingTalk dingTalk) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "/Users/a1234/Documents/properties/properties.json";
        try {
            // 直接将 JSON 文件反序列化为包含 DingTalk 对象的 Map
            Map<String, Object> jsonMap = objectMapper.readValue(new File(path), Map.class);
            // Bug 修复：修正拼写错误，从 Map 中获取 properties 对象
            Map<String, Object> propertiesMap = (Map<String, Object>) jsonMap.get("properties");
            // 从 properties 对象中获取 dingtalk 对象
            Map<String, String> dingtalkMap = (Map<String, String>) propertiesMap.get("dingtalk");

            dingTalk.setCustom_robot_token(dingtalkMap.get("custom_robot_token"));
            dingTalk.setSecret(dingtalkMap.get("secret"));
            // 将 DingTalk 对象的 Map 转换为 DingTalk 类的实例 (如果需要)  这里可以不用转换，直接使用dingtalkMap即可
//            dingTalk = objectMapper.convertValue(dingtalkMap, DingTalk.class);
        } catch (IOException e) {
            System.err.println("读取属性文件时出错: " + e.getMessage());
        }
    }
}
