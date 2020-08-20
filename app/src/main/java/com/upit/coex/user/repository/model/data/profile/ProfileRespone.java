
package com.upit.coex.user.repository.model.data.profile;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ProfileRespone extends BaseData implements Serializable {

    @SerializedName("client")
    private Client mClient;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private String mId;
    @SerializedName("typeUser")
    private Boolean mTypeUser;

    public Client getClient() {
        if(mClient == null){
            mClient = new Client();
        }
        return mClient;
    }

    public void setClient(Client client) {
        mClient = client;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getTypeUser() {
        return mTypeUser;
    }

    public void setTypeUser(Boolean typeUser) {
        mTypeUser = typeUser;
    }

}
