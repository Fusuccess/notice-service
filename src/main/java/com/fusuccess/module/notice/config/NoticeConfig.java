package com.fusuccess.module.notice.config;

public class NoticeConfig {
    private String label;
    private DingTalkConfig dingtalk;
    private EmailConfig emailConfig;

    public DingTalkConfig getDingtalk() {
        return dingtalk;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDingtalk(DingTalkConfig dingtalk) {
        this.dingtalk = dingtalk;
    }

    public EmailConfig getEmailConfig() {
        return emailConfig;
    }
    public void setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }
}
