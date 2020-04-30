package com.apiseven.apisix.admin.model;

import lombok.Data;
import com.apiseven.apisix.common.BaseModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.Map;

import com.apiseven.apisix.admin.model.plugin.Plugin;

@Data
public class Service extends BaseModel{
    @SerializedName("plugins")
    @Expose
    private Map<String, Plugin> plugins;

    @SerializedName("upstream")
    @Expose
    private Upstream upstream;

    @SerializedName("upstream_id")
    @Expose
    private String upstreamId;

    @SerializedName("desc")
    @Expose
    private String desc;

}
