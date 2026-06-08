package com.socgen.unibank.platform.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestContext {
    private final Map<String, Object> attributes = new LinkedHashMap<>();

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }
}
