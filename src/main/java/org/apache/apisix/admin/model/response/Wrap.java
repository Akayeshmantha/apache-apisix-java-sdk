package org.apache.apisix.admin.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wrap<T> {
    @SerializedName("node")
    @Expose
    public T node;
}
