package com.apiseven.apisix.admin.model.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LimitReq extends Plugin {
    @SerializedName("rate")
    @Expose
    private Integer rate;

    @SerializedName("burst")
    @Expose
    private Integer burst;

    @SerializedName("rejected_code")
    @Expose
    private Integer rejectedCode;

    @SerializedName("key")
    @Expose
    private String key;
}