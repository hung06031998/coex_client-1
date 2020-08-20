package com.upit.coex.user.repository.model.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by chien.lx on 3/9/2020.
 */

@Entity
public class Guest {
    @NonNull
    @PrimaryKey
    private String guestEmail;
    private String guestPhoneNumber;
    private String guestName;

    public Guest() {
    }

    @NonNull
    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(@NonNull String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    public void setGuestPhoneNumber(String guestPhoneNumber) {
        this.guestPhoneNumber = guestPhoneNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
