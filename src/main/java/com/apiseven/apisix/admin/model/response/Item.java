package com.apiseven.apisix.admin.model.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Item<T> {
    @SerializedName("value")
    @Expose
    public T value;
}