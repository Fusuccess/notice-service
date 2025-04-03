package com.fusuccess;

import com.fusuccess.common.PushConfigLoader;
import com.fusuccess.config.AppConfig;
import com.fusuccess.config.UserPushConfig;
import com.fusuccess.dingtalk.DingTalkImpl;
import com.fusuccess.strategy.PushClient;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        try {
            String path = "/Users/a1234/WorkSpace/ideaProject/dingtalk/src/main/resources/properties.json";
            AppConfig appConfig = PushConfigLoader.loadConfig(path);
            UserPushConfig config = appConfig.getUserPush();

            // 创建推送上下文
            PushClient pushClient = new PushClient();

            // 根据用户选择设置策略
            String pushType = "dingTalk"; // 可以从配置或用户输入获取
            // 根据选择设置策略
            switch (pushType) {
                case "dingTalk":
                    pushClient.setPushStrategy(new DingTalkImpl());
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
            boolean result = pushClient.executePush("这是一条测试消息", config);
            System.out.println("推送结果: " + (result ? "成功" : "失败"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}