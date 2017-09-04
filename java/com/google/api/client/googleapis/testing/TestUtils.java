package com.google.api.client.googleapis.testing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TestUtils {
    private static final String UTF_8 = "UTF-8";

    public static Map<String, String> parseQuery(String query) throws IOException {
        Map<String, String> map = new HashMap();
        for (String entry : Splitter.on('&').split(query)) {
            List<String> sides = Lists.newArrayList(Splitter.on('=').split(entry));
            if (sides.size() != 2) {
                throw new IOException("Invalid Query String");
            }
            map.put(URLDecoder.decode((String) sides.get(0), UTF_8), URLDecoder.decode((String) sides.get(1), UTF_8));
        }
        return map;
    }

    private TestUtils() {
    }
}
