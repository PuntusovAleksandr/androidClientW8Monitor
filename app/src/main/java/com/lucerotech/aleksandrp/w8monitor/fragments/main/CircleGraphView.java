package com.lucerotech.aleksandrp.w8monitor.fragments.main;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;

/**
 * Created by AleksandrP on 04.10.2016.
 */

public interface CircleGraphView {

    void hideTextEnableBLE();

    void showTextConnectionBLE();

    void setDefaultUI();

    void showParams(float[] mMassParams, long mTime, boolean mSync);

    void showMessageError();

    void showDataCircle(int mI, ParamsBody mLast, ParamsBody mPreLast, float[] mMassParams);

    void deleteValuesInTextShows();

    void updateUi();
}
