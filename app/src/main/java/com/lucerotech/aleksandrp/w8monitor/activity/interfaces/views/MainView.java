package com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.UserLibr;

/**
 * Created by AleksandrP on 26.09.2016.
 */

public interface MainView {

    void goToSettings();

    void setLineGraph(int mValue);

    void sendProfileToServer();

    void makeMessurementsSync(UserLibr mEvent);
}
