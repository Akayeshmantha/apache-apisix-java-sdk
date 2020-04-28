package com.apiseven.apisix.common.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.util.concurrent.TimeUnit;
import java.io.IOException;

import com.apiseven.apisix.common.exception.ApisixSDKExcetion;

public class Connection {

    private OkHttpClient client;

    public Connection(Integer connTimeout, Integer readTimeout, Integer writeTimeout) {
        this.client = new OkHttpClient();
        this.client.setConnectTimeout(connTimeout, TimeUnit.SECONDS);
        this.client.setReadTimeout(readTimeout, TimeUnit.SECONDS);
        this.client.setWriteTimeout(writeTimeout, TimeUnit.SECONDS);
    }


    public Response doRequest(Request request) throws ApisixSDKExcetion {
        Response response = null;
        try {
            response = this.client.newCall(request).execute();
        } catch (IOException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }
        return response;
    }


    public Response getRequest(String url, Headers headers) throws ApisixSDKExcetion {
        Request request = null;
        try {
            request = new Request.Builder().url(url).headers(headers).get().build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response putRequest(String url, String body, Headers headers)
            throws ApisixSDKExcetion {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request =
                    new Request.Builder()
                            .url(url)
                            .put(RequestBody.create(contentType, body))
                            .headers(headers)
                            .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response postRequest(String url, String body, Headers headers)
            throws ApisixSDKExcetion {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        Request request = null;
        try {
            request =
                    new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(contentType, body))
                            .headers(headers)
                            .build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

    public Response deleteRequest(String url, Headers headers) throws ApisixSDKExcetion {
        Request request = null;
        try {
            request = new Request.Builder().url(url).headers(headers).delete().build();
        } catch (IllegalArgumentException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        return this.doRequest(request);
    }

}
