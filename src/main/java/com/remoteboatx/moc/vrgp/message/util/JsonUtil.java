package com.remoteboatx.moc.vrgp.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

/**
 * Utility class for JSON (de-)serializing.
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    /**
     * Returns a global singleton default {@link ObjectMapper} that should be used whenever the default {@link
     * ObjectMapper} is needed.
     *
     * <b>Please note:</b> Do not configure this {@link ObjectMapper}.
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * Deserializes JSON content from given string to an object of the target class.
     *
     * @throws IllegalArgumentException if the JSON does not comply with the target class.
     * @see ObjectMapper#readValue(String, Class)
     */
    @NonNull
    public static <T> T fromJson(@NonNull String json, Class<T> targetClass) {
        try {
            return getObjectMapper().readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Message does not comply with VRGP message format.");
        }
    }

    /**
     * Serializes an object to JSON.
     *
     * @see ObjectMapper#valueToTree(Object)
     */
    @NonNull
    public static JsonNode toJson(@NonNull Object object) {
        return getObjectMapper().valueToTree(object);
    }

    /**
     * Serializes an object to a JSON string.
     *
     * @return the JSON string or the empty string if the object can not be serialized.
     * @see ObjectMapper#writeValueAsString(Object)
     */
    @NonNull
    public static String toJsonString(@NonNull Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO: Handle JsonProcessingException.
            e.printStackTrace();
            return "";
        }
    }
}
