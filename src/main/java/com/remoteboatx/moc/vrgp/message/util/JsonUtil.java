package com.remoteboatx.moc.vrgp.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    @NonNull
    public static <T> T fromJson(@NonNull String json, Class<T> targetType) {
        try {
            return OBJECT_MAPPER.readValue(json, targetType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Message does not comply with VRGP message format.");
        }
    }

    @NonNull
    public static String toJson(@NonNull Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO: Handle JsonProcessingException.
            e.printStackTrace();
            return "";
        }
    }
}
