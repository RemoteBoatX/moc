package com.remoteboatx.moc.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VrgpMessageTest {

    private final static String TIME_MESSAGE_TEMPLATE = "{\"time\":{\"sent\":%d,\"received\":%d}}";

    @Test
    void testEmptyMessageToJson() {
        Assertions.assertEquals("{}", new VrgpMessage().toJson());
    }

    @Test
    void testEmptyMessageFromJson() {
        Assertions.assertFalse(VrgpMessage.fromJson("{}").hasTime());
    }

    @Test
    void testTimeMessageToJson() {
        final long sent = 1234;
        final long received = 2345;

        Assertions.assertEquals(getJsonTimeMessage(sent, received), getTimeMessage(sent,
                received).toJson());
    }

    @Test
    void testTimeMessageFromJson() {
        final long sent = 1234;
        final long received = 2345;

        final VrgpMessage message = VrgpMessage.fromJson(getJsonTimeMessage(sent, received));

        Assertions.assertEquals(sent, message.getTime().getSent());
        Assertions.assertEquals(received, message.getTime().getReceived());
    }

    private VrgpMessage getTimeMessage(long sent, long received) {
        return new VrgpMessage().withTime(
                new LatencyMessage().withSent(sent).withReceived(received));
    }

    private String getJsonTimeMessage(long sent, long received) {
        return String.format(TIME_MESSAGE_TEMPLATE, sent, received);
    }
}
