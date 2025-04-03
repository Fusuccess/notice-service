package com.fusuccess.config;

public class UserPushConfig {
    private String label;
    private DingTalkConfig dingtalk;

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
}
