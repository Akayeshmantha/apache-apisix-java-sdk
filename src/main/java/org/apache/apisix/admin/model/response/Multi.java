package org.apache.apisix.admin.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Multi<T> {
    @SerializedName("nodes")
    @Expose
    public List<Item<T>> nodes;
}
