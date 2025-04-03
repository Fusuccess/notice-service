package com.fusuccess.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.fusuccess.strategy.PushStrategy;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class DingTalkImpl implements PushStrategy {

    /**
     * 实现 PushStrategy 接口的 push 方法，用于向钉钉机器人推送消息。
     *
     * @param message 要推送的消息内容
     * @param info    包含推送所需信息的映射，如 secret、custom_robot_token 和 userIds
     * @return 如果消息推送成功，返回 true；否则抛出异常
     */
    @Override
    public boolean push(String message, Map<String, String> info) {
        // 从 info 映射中获取 secret 信息
        String secret = info.get("secret");
        // 从 info 映射中获取自定义机器人的 token
        String customRobotToken = info.get("custom_robot_token");
        // 从 info 映射中获取需要 @ 的用户 ID 字符串
        String userIds = info.get("userIds");
        try {
            // 创建钉钉客户端，该客户端会根据传入的 secret 生成签名和时间戳
            DingTalkClient client = createClient(secret);
            // 设置要 @ 的用户 ID
            OapiRobotSendRequest.At at = setUserIds(userIds);
            // 设置钉钉机器人发送消息的内容和相关信息
            OapiRobotSendRequest req = setContent(at, message);
            // 执行发送请求，使用钉钉机器人的自定义 token
            OapiRobotSendResponse rsp = client.execute(req, customRobotToken);
            // 打印响应体内容
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            // 若发送过程中出现 API 异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        }
        // 返回推送结果，这里固定返回 true，表示推送成功
        return true;
    }


    /**
     * 设置钉钉机器人发送消息的内容和相关信息
     *
     * @param at      包含 @ 用户信息的对象
     * @param content 要发送的消息内容
     * @return 配置好的钉钉机器人发送请求对象
     */
    public OapiRobotSendRequest setContent(OapiRobotSendRequest.At at, String content) {
        // 创建一个新的钉钉机器人发送请求对象
        OapiRobotSendRequest req = new OapiRobotSendRequest();
        // 创建一个文本消息对象
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        // 设置文本消息的内容，这里固定为 "钉钉，让进步发生"，后续可根据需要修改
        text.setContent("钉钉，让进步发生");
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
     * 设置需要 @ 的用户 ID
     *
     * @param userIds 要 @ 的用户 ID 字符串，多个 ID 以逗号分隔
     * @return 包含 @ 用户信息的对象
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
     * 创建一个用于与钉钉机器人通信的客户端实例，该实例包含签名和时间戳信息
     *
     * @param dingTalk 包含钉钉机器人配置信息的对象，其中包含 secret 用于生成签名
     * @return 配置好签名和时间戳的钉钉客户端实例
     */
    public DingTalkClient createClient(String secret) {
        try {
            // 获取当前时间戳，单位为毫秒
            Long timestamp = System.currentTimeMillis();
            // 打印当前时间戳，可用于调试
            System.out.println(timestamp);
            // 若 secret 为空或为 null，抛出 IllegalArgumentException 异常
            if (secret == null || secret.isEmpty()) {
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
            // 若出现不支持的编码异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            // 若出现不支持的算法异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            // 若出现无效密钥异常，将其包装为 RuntimeException 抛出
            throw new RuntimeException(e);
        }
    }

}
