package com.fusuccess.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig {
    private String codeVersion;
    private String propertiesVersion;
    private String codeAuthor;
    private UserPushConfig userPush;

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

    @JsonProperty("UserPush")
    public UserPushConfig getUserPush() {
        return userPush;
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
    @JsonProperty("UserPush")
    public void setUserPush(UserPushConfig userPush) {
        this.userPush = userPush;
    }
}
