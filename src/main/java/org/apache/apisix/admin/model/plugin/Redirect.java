package org.apache.apisix.admin.model.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Redirect extends Plugin {
    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("ret_code")
    @Expose
    private Integer retCode;
}
