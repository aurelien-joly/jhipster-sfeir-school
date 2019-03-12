package com.sfeir.school.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.*;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;
import static java.time.temporal.ChronoField.*;

public class CustomDateTimeDeserializer extends JSR310DateTimeDeserializerBase<LocalDate> {


    private static final DateTimeFormatter DEFAULT_FORMATTER = new DateTimeFormatterBuilder()
        .appendValue(DAY_OF_MONTH, 2)
        .appendLiteral('/')
        .appendValue(MONTH_OF_YEAR, 2)
        .appendLiteral('/')
        .appendValue(YEAR, 4)
        .toFormatter();

    private final static List<DateTimeFormatter> formats = Arrays.asList(
        BASIC_ISO_DATE, ISO_LOCAL_DATE, ISO_INSTANT,DEFAULT_FORMATTER
    );

    public CustomDateTimeDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public CustomDateTimeDeserializer(DateTimeFormatter dtf) {
        super(LocalDate.class, dtf);
    }

    @Override
    protected JsonDeserializer<LocalDate> withDateFormat(final DateTimeFormatter dtf) {
        return new LocalDateDeserializer(dtf);
    }

    @Override
    public LocalDate deserialize(final JsonParser p,
                                 final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String localDate = p.getValueAsString();
        for (DateTimeFormatter format : formats) {
            try {
                System.out.println("Parsing "+localDate + "with "+ format);
                return LocalDate.parse(localDate, format);
            } catch (DateTimeParseException e) {}
        }
        throw new IllegalArgumentException("Unrecognized date " + localDate);
    }
}
