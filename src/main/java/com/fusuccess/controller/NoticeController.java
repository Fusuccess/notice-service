package com.fusuccess.controller;

import com.fusuccess.common.ConfigLoader;
import com.fusuccess.config.AppConfig;
import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.service.NoticeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private static final Logger logger = LogManager.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String pushType,
                                       @RequestParam String message) {
        try {
            // 加载配置
            String path = "/Users/a1234/Documents/properties/properties.json";
            AppConfig appConfig = ConfigLoader.loadConfig(path);
            NoticeConfig config = appConfig.getUserPush();

            // 参数验证
            if (pushType == null || pushType.isEmpty()) {
                return ResponseEntity.badRequest().body("推送类型不能为空");
            }
            if (message == null || message.isEmpty()) {
                return ResponseEntity.badRequest().body("推送内容不能为空");
            }

            // 发送消息
            boolean result = noticeService.sendMessage(pushType, message, config);

            Map<String, Object> response = new HashMap<>();
            response.put("success", result);
            response.put("message", result ? "消息推送成功" : "消息推送失败");

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("加载配置文件失败", e);
            return ResponseEntity.internalServerError().body("加载配置文件失败");
        } catch (Exception e) {
            logger.error("消息推送失败", e);
            return ResponseEntity.internalServerError().body("消息推送失败: " + e.getMessage());
        }
    }
} 