package com.remoteboatx.moc.message.util;

import com.remoteboatx.moc.message.VrgpMessage;

public class VrgpMessageUtil {

    private VrgpMessageUtil() {
    }

    public static boolean isEmpty(VrgpMessage message) {
        return "{}".equals(message.toJson());
    }
}
