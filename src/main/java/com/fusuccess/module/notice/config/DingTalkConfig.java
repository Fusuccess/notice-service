package com.fusuccess.module.notice.config;

public class DingTalkConfig {
    private String customRobotToken;
    private String secret;
    private String userIds;

    public String getCustomRobotToken() {
        return customRobotToken;
    }

    public void setCustomRobotToken(String customRobotToken) {
        this.customRobotToken = customRobotToken;
    }

    public String getSecret() { return secret; }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
