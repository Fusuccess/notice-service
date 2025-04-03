package com.fusuccess;

import com.fusuccess.common.ConfigLoader;
import com.fusuccess.config.AppConfig;
import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.module.notice.impl.dingtalk.DingTalkImpl;
import com.fusuccess.module.notice.strategy.NoticeClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main {



    public static void main(String[] args) {

        try {
            String path = "/Users/a1234/Documents/properties/properties.json";
            AppConfig appConfig = ConfigLoader.loadConfig(path);
            NoticeConfig config = appConfig.getUserPush();

            // 创建推送上下文
            NoticeClient noticeClient = new NoticeClient();

            // 根据用户选择设置策略
            String pushType = args[0]; // 可以从配置或用户输入获取
            // 根据选择设置策略
            switch (pushType) {
                case "dingTalk":
                    noticeClient.setPushStrategy(new DingTalkImpl());
                    break;
                case "sms":
//                pushClient.setPushStrategy(new SmsPushStrategy());
                    break;
                case "email":
//                pushClient.setPushStrategy(new EmailPushStrategy());
                    break;
                default:
                    throw new IllegalArgumentException("不支持的推送类型: " + pushType);
            }

            // 执行推送
            boolean result = noticeClient.executePush("这是一条测试消息", config);
            System.out.println("推送结果: " + (result ? "成功" : "失败"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}