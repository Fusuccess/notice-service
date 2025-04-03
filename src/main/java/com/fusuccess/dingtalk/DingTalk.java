package com.fusuccess.dingtalk;

public class DingTalk {
    private String label;
    private String custom_robot_token;
    private String secret;
    private String message;
    private String userIds;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setCustom_robot_token(String custom_robot_token) {
        this.custom_robot_token = custom_robot_token;
    }

    public String getCustom_robot_token() {
        return custom_robot_token;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
    public String getUserIds() {
        return userIds;
    }
}
