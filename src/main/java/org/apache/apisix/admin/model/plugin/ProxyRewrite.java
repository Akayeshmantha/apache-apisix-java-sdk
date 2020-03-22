package org.apache.apisix.admin.model.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ProxyRewrite extends Plugin {
    @SerializedName("scheme")
    @Expose
    private String scheme;

    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("regex_uri")
    @Expose
    private String regexUri;

    @SerializedName("host")
    @Expose
    private String host;

    @SerializedName("headers")
    @Expose
    private Map<String, String> headers;


}
