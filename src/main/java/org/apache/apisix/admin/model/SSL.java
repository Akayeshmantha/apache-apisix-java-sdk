package org.apache.apisix.admin.model;

import lombok.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.apisix.common.BaseModel;

@Data
public class SSL extends BaseModel {
    @SerializedName("cert")
    @Expose
    private String cert;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("sni")
    @Expose
    private String sni;
}
