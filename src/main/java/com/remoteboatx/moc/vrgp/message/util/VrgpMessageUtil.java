package com.remoteboatx.moc.vrgp.message.util;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;

/**
 * Utility class for {@link VrgpMessage}s.
 */
public class VrgpMessageUtil {

    private VrgpMessageUtil() {
    }

    /**
     * Checks, if a {@link VrgpMessage} is empty.
     */
    public static boolean isEmpty(VrgpMessage message) {
        return "{}".equals(message.toJson());
    }
}
