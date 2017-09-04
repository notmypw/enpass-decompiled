package com.google.api.client.json.rpc2;

import com.google.api.client.util.Beta;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

@Beta
public class JsonRpcRequest extends GenericData {
    @Key
    private Object id;
    @Key
    private final String jsonrpc = "2.0";
    @Key
    private String method;
    @Key
    private Object params;

    public String getVersion() {
        return "2.0";
    }

    public Object getId() {
        return this.id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getParameters() {
        return this.params;
    }

    public void setParameters(Object parameters) {
        this.params = parameters;
    }

    public JsonRpcRequest set(String fieldName, Object value) {
        return (JsonRpcRequest) super.set(fieldName, value);
    }

    public JsonRpcRequest clone() {
        return (JsonRpcRequest) super.clone();
    }
}
