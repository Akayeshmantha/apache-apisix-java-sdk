package com.apiseven.apisix.admin.model.plugin;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Zipkin extends Plugin {
    @SerializedName("endpoint")
    @Expose
    private String endpoint;

    @SerializedName("sample_ratio")
    @Expose
    private Integer sampleRatio;
}