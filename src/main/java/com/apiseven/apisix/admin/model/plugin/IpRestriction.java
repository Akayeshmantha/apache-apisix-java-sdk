package com.apiseven.apisix.admin.model.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IpRestriction extends Plugin {
    @SerializedName("whitelist")
    @Expose
    private List<String> whiteList;

    @SerializedName("blacklist")
    @Expose
    private List<String> blackList;
}
