package com.apiseven.apisix.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class K8sDeploymentInfo {
    @SerializedName("namespace")
    @Expose
    private String namespace;

    @SerializedName("deploy_name")
    @Expose
    private String deployName;

    @SerializedName("service_name")
    @Expose
    private String serviceName;

    @SerializedName("port")
    @Expose
    private Integer port;

    @SerializedName("backend_type")
    @Expose
    private String backendType;

}
