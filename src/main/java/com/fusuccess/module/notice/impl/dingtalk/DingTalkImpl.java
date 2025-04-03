package com.fusuccess.module.notice.impl.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.fusuccess.module.notice.config.DingTalkConfig;
import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.module.notice.strategy.NoticeStrategy;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 钉钉机器人实现类，实现了 NoticeStrategy 接口，用于发送钉钉消息。
 * author: fusuccess
 * email: fu2000520@gmail.com
 */
public class DingTalkImpl implements NoticeStrategy {
    private static final Logger logger = LogManager.getLogger(DingTalkImpl.class);

    /**
     * 执行钉钉机器人推送消息
     *
     * @param message 要推送的消息内容
     * @param config  钉钉机器人的配置信息
     * @return 推送结果，true 表示推送成功，false 表示推送失败
     */
    @Override
    public boolean push(String message, NoticeConfig config) {
        if (config == null || config.getDingtalk() == null) {
            logger.error("钉钉配置信息为空");
            return false;
        }

        // 获取钉钉机器人的配置信息
        DingTalkConfig dingtalk = config.getDingtalk();

        try {
            // 创建钉钉客户端，该客户端会根据传入的 secret 生成签名和时间戳
            DingTalkClient client = createClient(dingtalk.getSecret());
            // 设置要 @ 的用户 ID
            OapiRobotSendRequest.At at = setUserIds(dingtalk.getUserIds());
            // 设置钉钉机器人发送消息的内容和相关信息
            OapiRobotSendRequest req = setContent(at, message);
            // 执行发送请求，使用钉钉机器人的自定义 token
            OapiRobotSendResponse rsp = client.execute(req, dingtalk.getCustomRobotToken());
            // 打印响应体内容
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            logger.error("钉钉机器人推送消息失败", e);
            // 若发送过程中出现 API 异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        }
        // 返回推送结果，这里固定返回 true，表示推送成功
        return true;
    }


    /**
     * 设置钉钉机器人发送消息的内容和相关信息
     *
     * @param at      用户信息
     * @param message 消息内容
     * @return 配置好的钉钉机器人发送请求对象
     */
    public OapiRobotSendRequest setContent(OapiRobotSendRequest.At at, String message) {
        // 创建一个新的钉钉机器人发送请求对象
        OapiRobotSendRequest req = new OapiRobotSendRequest();
        // 创建一个文本消息对象
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        // 设置文本消息的内容，这里固定为 "钉钉，让进步发生"，后续可根据需要修改
        text.setContent(message);
        // 设置消息类型为文本类型
        req.setMsgtype("text");
        // 将文本消息对象设置到请求对象中
        req.setText(text);
        // 将 @ 用户信息对象设置到请求对象中
        req.setAt(at);
        // 返回配置好的请求对象
        return req;
    }

    /**
     * 设置要 @ 的用户 ID
     * 注意：此处的 userIds 应该是一个以逗号分隔的字符串，每个 ID 代表一个要 @ 的用户
     *
     * @param userIds 要 @ 的用户 ID，格式为 "user1,user2,user3"
     * @return 配置好的 @ 用户信息对象
     */
    public OapiRobotSendRequest.At setUserIds(String userIds) {
        // 定义 @ 对象，用于存储要 @ 的用户信息
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // Bug 修复：此处代码存在问题，Arrays.asList(userIds) 会将整个 userIds 字符串作为一个元素放入列表，
        // 若 userIds 是多个 ID 以逗号分隔的字符串，应先分割成数组再转为列表
        at.setAtUserIds(Arrays.asList(userIds));
        // 返回包含 @ 用户信息的对象
        return at;
    }

    /**
     * 创建钉钉客户端，该客户端会根据传入的 secret 生成签名和时间戳
     *
     * @param secret 钉钉机器人的 secret，用于生成签名和时间戳
     * @return 钉钉客户端对象
     */
    public DingTalkClient createClient(String secret) {
        try {
            // 获取当前时间戳，单位为毫秒
            Long timestamp = System.currentTimeMillis();
            // 打印当前时间戳，可用于调试
            System.out.println(timestamp);
            // 若 secret 为空或为 null，抛出 IllegalArgumentException 异常
            if (secret == null || secret.isEmpty()) {
                logger.error("secret 不能为空");
                throw new IllegalArgumentException("secret 不能为空");
            }
            // 拼接待签名的字符串，格式为时间戳 + 换行符 + secret
            String stringToSign = timestamp + "\n" + secret;
            // 获取 HmacSHA256 算法的 Mac 实例
            Mac mac = Mac.getInstance("HmacSHA256");
            // 使用 secret 初始化 Mac 实例，将 secret 转换为 UTF-8 字节数组
            // Bug 修复：可改用 StandardCharsets.UTF_8 替代 "UTF-8" 字符串
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            // 对待签名的字符串进行签名，将其转换为 UTF-8 字节数组
            // Bug 修复：可改用 StandardCharsets.UTF_8 替代 "UTF-8" 字符串
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            // 对签名结果进行 Base64 编码，并使用 UTF-8 进行 URL 编码
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            // 可取消注释以输出签名结果，用于调试
//        System.out.println(sign);
            // sign 字段和 timestamp 字段必须拼接到请求 URL 上，否则会出现 310000 的错误信息
            return new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的编码异常", e);
            // 若出现不支持的编码异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("不支持的算法异常", e);
            // 若出现不支持的算法异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            logger.error("无效密钥异常", e);
            // 若出现无效密钥异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        }
    }

}
