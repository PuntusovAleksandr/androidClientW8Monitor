package com.lucertech.w8monitor.android.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by AleksandrP on 22.11.2016.
 */

public class ObjectMeasurement {

    @SerializedName("measurements")
    @Expose
    private ArrayList<Measurement> measurements;

    public ObjectMeasurement() {
    }

    public ArrayList<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ArrayList<Measurement> mMeasurements) {
        this.measurements = mMeasurements;
    }
}
