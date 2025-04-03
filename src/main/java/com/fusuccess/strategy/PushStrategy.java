package com.fusuccess.strategy;

import java.util.Map;

public interface PushStrategy {
   boolean push(String message, Map<String, String> info);
}
