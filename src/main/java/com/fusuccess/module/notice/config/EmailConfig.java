package com.fusuccess.module.notice.config;

public class EmailConfig {
    private String host;       // SMTP服务器地址
    private int port;         // SMTP端口
    private String username;  // 发件人邮箱
    private String password;  // 授权码/密码
    private boolean ssl = true; // 是否启用SSL
    private String from;      // 发件人显示名称
    private String to;  // 收件人邮箱
    private String templateType; // 模板类型


    // Getters and Setters
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isSsl() {
        return ssl;
    }
    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getTemplateType() {
        return templateType;
    }
    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}
