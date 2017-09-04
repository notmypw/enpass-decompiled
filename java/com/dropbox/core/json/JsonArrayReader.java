package com.dropbox.core.json;

import com.dropbox.core.util.Collector;
import com.dropbox.core.util.Collector.ArrayListCollector;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.util.List;

public class JsonArrayReader<T, L> extends JsonReader<L> {
    public final Collector<T, ? extends L> collector;
    public final JsonReader<? extends T> elementReader;

    public JsonArrayReader(JsonReader<? extends T> elementReader, Collector<T, ? extends L> collector) {
        this.elementReader = elementReader;
        this.collector = collector;
    }

    public static <T> JsonArrayReader<T, List<T>> mk(JsonReader<? extends T> elementReader) {
        return new JsonArrayReader(elementReader, new ArrayListCollector());
    }

    public static <T, L> JsonArrayReader<T, L> mk(JsonReader<? extends T> elementReader, Collector<T, ? extends L> collector) {
        return new JsonArrayReader(elementReader, collector);
    }

    public L read(JsonParser parser) throws JsonReadException, IOException {
        return read(this.elementReader, this.collector, parser);
    }

    public static <T, L> L read(JsonReader<? extends T> elementReader, Collector<T, ? extends L> collector, JsonParser parser) throws JsonReadException, IOException {
        JsonReader.expectArrayStart(parser);
        int index = 0;
        while (!JsonReader.isArrayEnd(parser)) {
            try {
                collector.add(elementReader.read(parser));
                index++;
            } catch (JsonReadException ex) {
                throw ex.addArrayContext(index);
            }
        }
        parser.nextToken();
        return collector.finish();
    }
}
