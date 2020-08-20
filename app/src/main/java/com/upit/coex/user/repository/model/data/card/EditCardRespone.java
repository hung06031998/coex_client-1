
package com.upit.coex.user.repository.model.data.card;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EditCardRespone {

    @SerializedName("name")
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
