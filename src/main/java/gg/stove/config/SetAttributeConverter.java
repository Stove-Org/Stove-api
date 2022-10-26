package gg.stove.config;

import java.io.IOException;
import java.util.Set;
import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SetAttributeConverter implements AttributeConverter<Set<?>, String> {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    @Override
    public String convertToDatabaseColumn(Set attribute) {
        try {
            return mapper.writeValueAsString(attribute != null ? attribute : Set.of());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Set<?> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return Set.of();
        }

        try {
            return mapper.readValue(dbData, Set.class);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
