package com.fusuccess;

import com.fusuccess.common.ConfigLoader;
import com.fusuccess.config.AppConfig;
import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.module.notice.impl.dingtalk.DingTalkImpl;
import com.fusuccess.module.notice.impl.email.EmailImpl;
import com.fusuccess.module.notice.strategy.NoticeClient;
import com.fusuccess.untils.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final Logger logger = LogManager.getLogger(ConfigLoader.class);

    public static void main(String[] args) {

        try {
            logger.info("北京时间: "+ DateUtils.getBeijingTime());
            logger.info("执行推送: "+ Arrays.toString(args));

            if (args.length < 2) {
                logger.error("缺少推送类型或推送内容 [pushType, message] 例如: [dingTalk, 测试消息]");
                return;
            }

            // 根据用户选择设置策略
            String pushType = args[0]; // 可以从配置或用户输入获取
            String message = args[1]; // 可以从配置或用户输入获取

            if (pushType == null || pushType.isEmpty()) {
                logger.error("推送类型不能为空");
                throw new IllegalArgumentException("推送类型不能为空");
            }
            if (message == null || message.isEmpty()) {
                logger.error("推送内容不能为空");
                throw new IllegalArgumentException("推送内容不能为空");
            }

            String path = "/Users/a1234/Documents/properties/config.json";
            AppConfig appConfig = ConfigLoader.loadConfig(path);
            NoticeConfig config = appConfig.getUserPush();

            // 创建推送上下文
            NoticeClient noticeClient = new NoticeClient();
            logger.info("推送类型: " + (pushType.equals("dingTalk")? "钉钉" : (pushType.equals("sms")? "短信" : "邮箱")));
            // 根据选择设置策略
            switch (pushType) {
                case "dingTalk":
                    noticeClient.setPushStrategy(new DingTalkImpl());
                    break;
                case "sms":
//                pushClient.setPushStrategy(new SmsPushStrategy());
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

        } catch (IOException e) {
            logger.error("加载配置文件失败", e);
            throw new RuntimeException(e);
        }
    }

}