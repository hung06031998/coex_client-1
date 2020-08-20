
package com.upit.coex.user.repository.model.data.register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegisterRequest  {

    @SerializedName("client")
    private Client mClient;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("typeUser")
    private Boolean mTypeUser;

    public RegisterRequest( String mEmail, String mPassword, Boolean mTypeUser,Client mClient) {
        this.mClient = mClient;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mTypeUser = mTypeUser;
    }

    public Client getClient() {
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

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Boolean getTypeUser() {
        return mTypeUser;
    }

    public void setTypeUser(Boolean typeUser) {
        mTypeUser = typeUser;
    }

}
