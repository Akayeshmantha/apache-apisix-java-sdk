package com.apiseven.apisix.admin.model.plugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicAuth extends Plugin {
    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;

}
