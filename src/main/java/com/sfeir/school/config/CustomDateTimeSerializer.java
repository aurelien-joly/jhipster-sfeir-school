package com.sfeir.school.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;
import static java.time.temporal.ChronoField.*;

public class CustomDateTimeSerializer extends LocalDateSerializer {

    public static final DateTimeFormatter DEFAULT_FORMATTER = new DateTimeFormatterBuilder()
        .appendValue(DAY_OF_MONTH, 2)
        .appendLiteral('/')
        .appendValue(MONTH_OF_YEAR, 2)
        .appendLiteral('/')
        .appendValue(YEAR, 4)
        .toFormatter();

    private final static List<DateTimeFormatter> formats = Arrays.asList(
        BASIC_ISO_DATE, ISO_LOCAL_DATE, ISO_INSTANT,DEFAULT_FORMATTER
    );

    public CustomDateTimeSerializer() {
        this(DEFAULT_FORMATTER);
    }

    public CustomDateTimeSerializer(DateTimeFormatter dtf) {
        super(dtf);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator g, SerializerProvider provider) throws IOException {
        super.serialize(date, g, provider);
    }

}
