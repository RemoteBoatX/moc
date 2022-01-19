package com.remoteboatx.moc.vrgp.message.util;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class VrgpMessageUtil {

    private VrgpMessageUtil() {
    }

    public static boolean isEmpty(VrgpMessage message) {
        return "{}".equals(message.toJson());
    }
}
