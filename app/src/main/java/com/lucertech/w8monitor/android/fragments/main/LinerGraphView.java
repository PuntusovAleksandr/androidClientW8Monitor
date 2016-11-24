package com.lucertech.w8monitor.android.fragments.main;

import com.lucertech.w8monitor.android.d_base.model.ParamsBody;

import java.util.List;

/**
 * Created by AleksandrP on 21.10.2016.
 */

public interface LinerGraphView {

    void getDataForLineChart(List<ParamsBody> mAllSorted, int mPickerBottomValue);
}
