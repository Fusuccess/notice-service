package com.fusuccess;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusuccess.common.PropertiesInit;
import com.fusuccess.dingtalk.DingTalk;
import com.fusuccess.dingtalk.DingTalkImpl;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

public class Main {



    public static void main(String[] args) {
        PropertiesInit propertiesInit = new PropertiesInit();
        args = new String[]{"dingtalk"};
        String arg = args[0];
       if (arg == null || arg.isEmpty()) {
           System.out.println("参数为空");
           return;
       }else if(arg.equals("dingtalk")) {
           DingTalk dingTalk = new DingTalk();
           propertiesInit.getProperties(dingTalk);
           new DingTalkImpl().sendMessage(dingTalk, "钉钉，让进步发生", "");
       }
    }

}