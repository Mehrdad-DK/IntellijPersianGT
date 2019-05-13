package com.github.mehrdaddk.http;

import com.github.mehrdaddk.http.HttpParams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;


abstract class AbstractHttpParams implements HttpParams {
    final Map<String, String> params = new HashMap<>();

    @Override
    public HttpParams put(String key, String value) {
        params.put(key, value);
        return this;
    }

    @Override
    public String send2String(String baseUrl) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = send(httpClient, baseUrl);
            return EntityUtils.toString(response.getEntity());
        }
    }

    protected abstract CloseableHttpResponse send(CloseableHttpClient httpClient, String baseUrl) throws Exception;
}
