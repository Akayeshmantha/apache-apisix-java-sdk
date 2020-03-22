package org.apache.apisix.admin.model;

import lombok.Data;
import org.apache.apisix.common.BaseModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.Map;

@Data
public class Upstream extends BaseModel{
    @SerializedName("nodes")
    @Expose
    private Map<String, Integer> nodes;
     
    @SerializedName("type")
    @Expose
    private String type;
   
    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("retries")
    @Expose
    private Integer retries;

    @SerializedName("timeout")
    @Expose
    private Integer timeout;

    @SerializedName("desc")
    @Expose
    private String desc;

}
