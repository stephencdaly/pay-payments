package uk.gov.pay.payments.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class MicrosecondPrecisionInstantSerializer extends JsonSerializer<Instant> {

    public static final DateTimeFormatter MICROSECOND_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendInstant(6)
                    .toFormatter(Locale.ENGLISH);
    
    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(MICROSECOND_FORMATTER.format(value));  
    }
}

