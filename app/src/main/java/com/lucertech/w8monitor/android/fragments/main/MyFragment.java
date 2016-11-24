package com.lucertech.w8monitor.android.fragments.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucertech.w8monitor.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lucertech.w8monitor.android.utils.FontsTextView.getFontRobotoLight;
import static com.lucertech.w8monitor.android.utils.STATICS_PARAMS.MAX_VALUE_PICKER;

public class MyFragment extends Fragment {

    @Bind(R.id.text)
    TextView text;

    private Context context;
    private int pos;
    private float scale;
    private int mCountPage;

    public MyFragment(Context mContext, int mPos, float mScale, int mCountPage) {
        context = mContext;
        pos = mPos;
        scale = mScale;
        this.mCountPage = mCountPage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf, container, false);
        ButterKnife.bind(this, view);
        if (container == null) {
            return null;
        }

        if (mCountPage == MAX_VALUE_PICKER) {
            String[] stringArray = getResources().getStringArray(R.array.select_weight_params);
            text.setText(stringArray[pos]);
        } else {
            String[] stringArray = getResources().getStringArray(R.array.select_weight_params_top);
            text.setText(stringArray[pos]);
        }
        text.setTypeface(getFontRobotoLight());

        return view;
    }

    @Override
    public boolean getUserVisibleHint() {
        boolean userVisibleHint = super.getUserVisibleHint();
        if (!userVisibleHint) {
            text.setAlpha(0.5f);
        }
        return userVisibleHint;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!visible && text != null) {
            text.setAlpha(0.5f);
        }
    }

    boolean visible;

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        visible = menuVisible;
        if (!menuVisible && text != null) {
            text.setAlpha(0.5f);
        } else if (text != null) {
            text.setAlpha(1.0f);
        }
    }
}
