package com.fusuccess.service.impl;

import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.module.notice.impl.dingtalk.DingTalkImpl;
import com.fusuccess.module.notice.impl.email.EmailImpl;
import com.fusuccess.module.notice.strategy.NoticeClient;
import com.fusuccess.service.NoticeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
    private static final Logger logger = LogManager.getLogger(NoticeServiceImpl.class);

    @Override
    public boolean sendMessage(String pushType, String message, NoticeConfig config) {
        try {
            logger.info("推送类型: " + (pushType.equals("dingTalk") ? "钉钉" : (pushType.equals("sms") ? "短信" : "邮箱")));
            
            // 创建推送上下文
            NoticeClient noticeClient = new NoticeClient();
            
            // 根据选择设置策略
            switch (pushType) {
                case "dingTalk":
                    noticeClient.setPushStrategy(new DingTalkImpl());
                    break;
                case "sms":
                    // pushClient.setPushStrategy(new SmsPushStrategy());
                    break;
                case "email":
                    noticeClient.setPushStrategy(new EmailImpl());
                    break;
                default:
                    logger.error("不支持的推送类型: " + pushType);
                    throw new IllegalArgumentException("不支持的推送类型: " + pushType);
            }

            // 执行推送
            boolean result = noticeClient.executePush(message, config);
            logger.info("推送结果: " + (result ? "成功" : "失败"));
            logger.info("推送内容: " + message);
            
            return result;
        } catch (Exception e) {
            logger.error("消息推送失败", e);
            throw new RuntimeException("消息推送失败", e);
        }
    }
} 