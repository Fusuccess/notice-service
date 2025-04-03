package com.fusuccess.strategy;

import com.fusuccess.config.UserPushConfig;

import java.util.Map;

public interface PushStrategy {
   boolean push(String message, UserPushConfig config);
}
