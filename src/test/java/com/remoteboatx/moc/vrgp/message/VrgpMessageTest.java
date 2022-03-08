package com.remoteboatx.moc.vrgp.message;

import com.remoteboatx.moc.message.vrgp.LatencyMessage;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VrgpMessageTest {

    private static final String TIME_MESSAGE_TEMPLATE = "{\"time\":{\"sent\":%d,\"received\":%d}}";

    private static final String BYE_MESSAGE = "{\"bye\":{\"over\":true}}";

    @Test
    void testEmptyMessageToJson() {
        Assertions.assertEquals("{}", new VrgpMessage().toJson());
    }

    @Test
    void testEmptyMessageFromJson() {
        Assertions.assertNull(VrgpMessage.fromJson("{}").getTime());
        Assertions.assertNull(VrgpMessage.fromJson("{}").getBye());
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

        Assertions.assertNotNull(message.getTime());
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

    @Test
    void testByeMessageToJson() {
        Assertions.assertEquals(BYE_MESSAGE, new VrgpMessage().withBye().toJson());
    }

    @Test
    void testByeMessageFromJson() {
        Assertions.assertNotNull(VrgpMessage.fromJson(BYE_MESSAGE).getBye());
    }
}
