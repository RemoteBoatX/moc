package com.remoteboatx.moc.vrgp.message.util;

import com.remoteboatx.moc.vrgp.message.Streams;

import java.util.ArrayList;
import java.util.List;

public class StreamsUtil {

    private StreamsUtil() {
    }

    public static List<String> getAvailableStreams(Streams streams) {
        // TODO: Maybe use Java reflections here.
        final List<String> result = new ArrayList<>();

        if (streams.getConning()) {
            result.add("conning");
        }
        if (streams.getCamera()) {
            result.add("camera");
        }

        return result;
    }
}
