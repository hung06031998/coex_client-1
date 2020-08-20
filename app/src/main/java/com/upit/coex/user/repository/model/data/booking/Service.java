
package com.upit.coex.user.repository.model.data.booking;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Service implements Serializable {

    @SerializedName("airConditioning")
    private Boolean mAirConditioning;
    @SerializedName("conversionCall")
    private Boolean mConversionCall;
    @SerializedName("drink")
    private Boolean mDrink;
    @SerializedName("other")
    private List<String> mOther;
    @SerializedName("printer")
    private Boolean mPrinter;
    @SerializedName("wifi")
    private Boolean mWifi;

    public Boolean getAirConditioning() {
        return mAirConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        mAirConditioning = airConditioning;
    }

    public Boolean getConversionCall() {
        return mConversionCall;
    }

    public void setConversionCall(Boolean conversionCall) {
        mConversionCall = conversionCall;
    }

    public Boolean getDrink() {
        return mDrink;
    }

    public void setDrink(Boolean drink) {
        mDrink = drink;
    }

    public List<String> getOther() {
        return mOther;
    }

    public void setOther(List<String> other) {
        mOther = other;
    }

    public Boolean getPrinter() {
        return mPrinter;
    }

    public void setPrinter(Boolean printer) {
        mPrinter = printer;
    }

    public Boolean getWifi() {
        return mWifi;
    }

    public void setWifi(Boolean wifi) {
        mWifi = wifi;
    }

}
