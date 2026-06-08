package com.socgen.unibank.platform.springboot.config;

public class ProxyEndpoints {
    private final Class<?> endpointType;

    private ProxyEndpoints(Class<?> endpointType) {
        this.endpointType = endpointType;
    }

    public static ProxyEndpoints create(Class<?> endpointType) {
        return new ProxyEndpoints(endpointType);
    }

    public Class<?> getEndpointType() {
        return endpointType;
    }
}
