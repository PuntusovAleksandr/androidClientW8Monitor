package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views;

import com.lucerotech.aleksandrp.w8monitor.api.model.Measurement;
import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;

import java.util.ArrayList;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public interface MainView {

    void goToSettings();

    void setLineGraph(int mValue);

    void sendProfileToServer();

    void makeUpdateUserSync(UserLibr mEvent);

    void makeUpdateMessurementsSync(ArrayList<Measurement> mData);

    void makeRequestUpdateMeasurement();

    void makeAllUpdateUi();
}
