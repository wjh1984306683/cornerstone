package com.cs.base.common.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.cs.base.common.serialize.DatePattern.DATETIME_PATTERN;

/**
 * @author wangjiahao
 * @version 1.0
 * @className OffsetDateTimeJsonDeserialize
 * @since 2018/11/21 12:08 PM
 */
@Slf4j
public class OffsetDateTimeJsonDeserialize extends JsonDeserializer<OffsetDateTime> {
    public static final OffsetDateTimeJsonDeserialize INSTANCE = new OffsetDateTimeJsonDeserialize();
    public static final String COLON = ":";

    @Override
    public OffsetDateTime deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
        return getDeserialized(arg0.getText());
    }

    public OffsetDateTime getDeserialized(String str) {
        if (StringUtils.isNoneEmpty(str)) {
            if (str.contains(COLON)) {
                LocalDateTime loc = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(DATETIME_PATTERN));
                return OffsetDateTime.of(loc, ZoneOffset.of("+08:00"));
            }
            LocalDate date2 = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
            return OffsetDateTime.of(date2, LocalTime.of(0, 0, 0), ZoneOffset.of("+08:00"));
        }
        return null;
    }
}
