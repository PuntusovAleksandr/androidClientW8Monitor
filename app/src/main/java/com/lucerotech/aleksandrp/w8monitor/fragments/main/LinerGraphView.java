package com.lucerotech.aleksandrp.w8monitor.fragments.main;

import com.lucerotech.aleksandrp.w8monitor.d_base.model.ParamsBody;

import java.util.List;

/**
 * Created by AleksandrP on 21.10.2016.
 */

public interface LinerGraphView {

    void getDataForLineChart(List<ParamsBody> mAllSorted, int mPickerBottomValue);
}
