package com.apiseven.apisix.admin.model;

import lombok.Data;
import com.apiseven.apisix.common.BaseModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apiseven.apisix.admin.model.plugin.Plugin;

@Data
public class Route extends BaseModel{

    @SerializedName("uri")
    @Expose
    private String uri;

    @SerializedName("uris")
    @Expose
    private List<String> uris;

    @SerializedName("plugins")
    @Expose
    private Map<String, Plugin> plugins;

    @SerializedName("upstream")
    @Expose
    private Upstream upstream;

    @SerializedName("upstream_id")
    @Expose
    private String upstreamId;

    @SerializedName("service_id")
    @Expose
    private String ServiceId;

    @SerializedName("service_protocol")
    @Expose
    private String serviceProtocol;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("host")
    @Expose
    private String host;
    
    @SerializedName("hosts")
    @Expose
    private List<String> hosts;

    @SerializedName("remote_addr")
    @Expose
    private String remote_addr;
    
    @SerializedName("remote_addrs")
    @Expose
    private List<String> remote_addrs;

    @SerializedName("methods")
    @Expose
    private List<String> methods;

    @SerializedName("priority")
    @Expose
    private Integer priority;
    
    @SerializedName("filter_func")
    @Expose
    private String filter_func;


    public void toMap(HashMap<String, String> map) {
    }
}
