package com.github.mehrdaddk.http;


public interface HttpParams {
    String send2String(String baseUrl) throws Exception;

    HttpParams put(String key, String value);
}
