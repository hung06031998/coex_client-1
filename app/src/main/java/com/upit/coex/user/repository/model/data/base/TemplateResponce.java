
package com.upit.coex.user.repository.model.data.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TemplateResponce {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("description")
    @Expose
    private String description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
