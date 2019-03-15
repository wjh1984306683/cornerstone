package com.cs.base.common.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.cs.base.common.serialize.DatePattern.DATETIME_PATTERN;

/**
 * @author wangjiahao
 * @version 1.0
 * @className OffsetDateTimeJsonSerialize
 * @since 2018/11/21 12:06 PM
 */
public class OffsetDateTimeJsonSerialize extends JsonSerializer<OffsetDateTime> {

    public static final OffsetDateTimeJsonSerialize INSTANCE = new OffsetDateTimeJsonSerialize();

    @Override
    public void serialize(OffsetDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(getSerializeStr(dateTime));
    }

    public String getSerializeStr(OffsetDateTime dateTime) {
        if (null == dateTime) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
    }
}
