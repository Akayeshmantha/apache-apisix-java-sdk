package org.apache.apisix.admin.model.plugin;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class LimitCount extends Plugin {
    @SerializedName("count")
    @Expose	
    private Integer count;

    @SerializedName("time_window")
    @Expose    
    private Integer timeWindow;

    @SerializedName("rejected_code")
    @Expose    
    private Integer rejectedCode;

    @SerializedName("key")
    @Expose    
    private String key;
}