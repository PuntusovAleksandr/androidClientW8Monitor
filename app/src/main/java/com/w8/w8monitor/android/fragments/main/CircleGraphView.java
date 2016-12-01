package com.w8.w8monitor.android.fragments.main;

import com.w8.w8monitor.android.d_base.model.ParamsBody;

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

    void updateUi(ParamsBody mData);
}
