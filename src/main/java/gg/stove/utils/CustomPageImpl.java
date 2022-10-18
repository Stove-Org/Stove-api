package gg.stove.utils;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class CustomPageImpl<T> extends PageImpl<T> {

    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @JsonCreator(mode = Mode.PROPERTIES)
    public CustomPageImpl(
        @JsonProperty("content") List<T> content,
        @JsonProperty("number") int page,
        @JsonProperty("size") int size,
        @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }
}
