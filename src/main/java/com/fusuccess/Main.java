package com.fusuccess;

import com.fusuccess.common.PropertiesInit;
import com.fusuccess.dingtalk.DingTalk;
import com.fusuccess.dingtalk.DingTalkImpl;
import com.fusuccess.strategy.PushClient;

import java.util.Map;

public class Main {



    public static void main(String[] args) {
        PropertiesInit propertiesInit = new PropertiesInit();
        Map<String, String> propertiesInfo = propertiesInit.getProperties();
        if (propertiesInfo == null) {
            System.err.println("无法获取属性信息");
            return;
        }
        // 创建推送上下文
        PushClient pushClient = new PushClient();

        // 用户选择推送方式 - 这里可以通过配置或用户输入决定
        String pushType = "dingTalk";

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
        boolean result = pushClient.executePush("这是一条测试消息", propertiesInfo);
        System.out.println("推送结果: " + (result ? "成功" : "失败"));
    }

}