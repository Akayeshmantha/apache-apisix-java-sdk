package org.apache.apisix.admin.model.plugin;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class KeyAuth extends Plugin {
    @SerializedName("key")
    @Expose	
    private String key;
}