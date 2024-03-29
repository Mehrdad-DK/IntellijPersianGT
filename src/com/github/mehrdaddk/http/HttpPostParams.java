package com.github.mehrdaddk.http;

import com.github.mehrdaddk.http.AbstractHttpParams;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpPostParams extends AbstractHttpParams {
    @Override
    protected CloseableHttpResponse send(CloseableHttpClient httpClient, String base) throws Exception {
        List<NameValuePair> formParams = new ArrayList<>();
        for (String key : params.keySet()) {
            String value = params.get(key);
            formParams.add(new BasicNameValuePair(key, value));
        }
        HttpPost request = new HttpPost(base);

        RequestConfig localConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
        request.setConfig(localConfig);
        request.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");        //内容为post
        return httpClient.execute(request);
    }
}
