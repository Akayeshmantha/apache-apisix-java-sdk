package com.apiseven.apisix.admin.model;

import lombok.Data;
import com.apiseven.apisix.common.BaseModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.Map;

import com.apiseven.apisix.admin.model.plugin.Plugin;

@Data
public class Consumer extends BaseModel{
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("plugins")
    @Expose
    private Map<String, Plugin> plugins;

    @SerializedName("desc")
    @Expose
    private String desc;

}
