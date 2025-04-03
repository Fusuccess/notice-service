package com.fusuccess.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fusuccess.module.notice.config.NoticeConfig;

public class AppConfig {
    private String codeVersion;
    private String propertiesVersion;
    private String codeAuthor;
    private NoticeConfig noticeConfig;

    @JsonProperty("code_version")
    public String getCodeVersion() {
        return codeVersion;
    }

    @JsonProperty("properties_version")
    public String getPropertiesVersion() {
        return propertiesVersion;
    }

    @JsonProperty("code_author")
    public String getCodeAuthor() {
        return codeAuthor;
    }

    @JsonProperty("noticeConfig")
    public NoticeConfig getUserPush() {
        return noticeConfig;
    }

    @JsonProperty("code_version")
    public void setCodeVersion(String codeVersion) {
        this.codeVersion = codeVersion;
    }

    @JsonProperty("properties_version")
    public void setPropertiesVersion(String propertiesVersion) {
        this.propertiesVersion = propertiesVersion;
    }

    @JsonProperty("code_author")
    public void setCodeAuthor(String codeAuthor) {
        this.codeAuthor = codeAuthor;
    }

    @JsonProperty("noticeConfig")
    public void setUserPush(NoticeConfig noticeConfig) {
        this.noticeConfig = noticeConfig;
    }
}
