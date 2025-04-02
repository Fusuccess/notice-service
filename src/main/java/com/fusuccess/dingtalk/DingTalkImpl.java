package com.fusuccess.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DingTalkImpl {
    public static final String CUSTOM_ROBOT_TOKEN = "b481f0fad8d29d4360499b0675049d9bb3c6ca13c9349d1850f0ab6ba65d56a5";
    public static final String SECRET = "SECde4866b6ba992eac729644554d5e384a43e488f4a126f1f7538a858f64201338";

    public void sendMessage(String content, String userIds) {
        try {
            DingTalkClient client = createClient();
            OapiRobotSendRequest.At at = setUserIds(userIds);
            OapiRobotSendRequest req = setContent(at, content);
            OapiRobotSendResponse rsp = client.execute(req, CUSTOM_ROBOT_TOKEN);
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public OapiRobotSendRequest setContent(OapiRobotSendRequest.At at, String content) {
        OapiRobotSendRequest req = new OapiRobotSendRequest();
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("钉钉，让进步发生");
        //设置消息类型
        req.setMsgtype("text");
        req.setText(text);
        req.setAt(at);
        return req;
    }

    public OapiRobotSendRequest.At setUserIds(String userIds) {
        //定义 @ 对象
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtUserIds(Arrays.asList(userIds));
        return at;
    };

    public DingTalkClient createClient() {
        try {
        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String secret = SECRET;
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        System.out.println(sign);
        //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
        return new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign="+sign+"&timestamp="+timestamp);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
