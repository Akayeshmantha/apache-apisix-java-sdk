package org.apache.apisix.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.apisix.admin.model.response.Item;
import org.apache.apisix.common.exception.ApisixSDKExcetion;
import org.apache.apisix.common.http.Connection;
import org.apache.apisix.common.profile.Credential;
import org.apache.apisix.common.profile.HttpProfile;
import org.apache.apisix.common.profile.Profile;


public abstract class BaseClient {

    public static final int HTTP_OK = 200;
    public static final int HTTP_NOT_OK = 400;
    public static final String SDK_VERSION = "0.1.0";

    private Profile profile;
    private Credential credential;
    private String endpoint;
    private String sdkVersion;
    private String apiVersion;
    public Gson gson;

    public BaseClient(Profile profile) {
        this.credential = profile.getCredential();
        this.profile = profile;
        this.endpoint = profile.getEndpoint();
        this.sdkVersion = BaseClient.SDK_VERSION;
        this.apiVersion = profile.getVersion();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public Profile getProfile() {
        return this.profile;
    }

    protected String doRequest(String reqMethod, String path)  throws ApisixSDKExcetion {
        Response okRsp = doRequest(reqMethod, path, "");
        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKExcetion(strResp, String.valueOf(okRsp.code()));
        }

        return strResp;
    }

    protected String doRequest(BaseModel model, String reqMethod, String path)  throws ApisixSDKExcetion {
        String strParam = gson.toJson(model);
        Response okRsp = doRequest(reqMethod, path, strParam);

        String strResp = null;
        try {
            strResp = okRsp.body().string();
        } catch (IOException e) {
            throw new ApisixSDKExcetion(e.getClass().getName() + "-" + e.getMessage());
        }

        if (okRsp.code() >= BaseClient.HTTP_NOT_OK) {
            throw new ApisixSDKExcetion(strResp, String.valueOf(okRsp.code()));
        }

        return strResp;
    }

    private Response doRequest(String reqMethod, String path, String param)
            throws ApisixSDKExcetion {

        String contentType = "application/json; charset=utf-8";

        Connection conn =
                new Connection(
                        this.profile.getHttpProfile().getConnTimeout(),
                        this.profile.getHttpProfile().getReadTimeout(),
                        this.profile.getHttpProfile().getWriteTimeout());


        String url = this.profile.getHttpProfile().getProtocol() + this.endpoint + path;

        Builder hb = new Headers.Builder();
        hb.add("Content-Type", contentType)
                .add("Host", this.endpoint)
                .add("X-API-Version", this.apiVersion)
                .add("X-SDK-RequestClient", this.sdkVersion);

        String token = this.credential.getToken();

        if (token != null && !token.isEmpty()) {
            hb.add("X-API-KEY", token);
        }

        Headers headers = hb.build();

        if (reqMethod.equals(HttpProfile.REQ_GET)) {
            return conn.getRequest(url + "?" + param, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_POST)) {
            return conn.postRequest(url, param, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_DELETE)) {
            return conn.deleteRequest(url, headers);
        } else if (reqMethod.equals(HttpProfile.REQ_PUT)) {
            return conn.putRequest(url, param, headers);
        } else {
            throw new ApisixSDKExcetion("Method only support (GET, POST, PUT, DELETE)");
        }
    }

    protected <T extends BaseModel> List<T> arrangeMulti(List<Item<T>> list){
        Item<T> item;
        T model;
        List<T> result = new ArrayList<>();

        for(int i=0; i<list.size();i++){
            item = list.get(i);
            model = item.value;
            result.add(model);
        }

        return result;
    }


}

