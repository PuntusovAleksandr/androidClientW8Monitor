package com.lucerotech.aleksandrp.w8monitor.fragments.profile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;
import com.lucerotech.aleksandrp.w8monitor.api.service.ApiService;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.activity.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.presentts.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.activity.interfaces.views.ProfileView;
import com.lucerotech.aleksandrp.w8monitor.utils.FragmentMapker;
import com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS;
import com.lucerotech.aleksandrp.w8monitor.utils.SettingsApp;
import com.shawnlin.numberpicker.NumberPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucerotech.aleksandrp.w8monitor.api.constant.ApiConstants.PROFILE;
import static com.lucerotech.aleksandrp.w8monitor.activity.ProfileActivity.MARKER_MAIN;
import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRobotoLight;
import static com.lucerotech.aleksandrp.w8monitor.utils.InternetUtils.checkInternetConnection;
import static com.lucerotech.aleksandrp.w8monitor.utils.STATICS_PARAMS.SERVICE_JOB_ID_TITLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrowthFragment extends Fragment implements
        RealmObj.HeightListener,
        RealmObj.ProfileFirstStartBLeListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private boolean mFromSettings;
    private int markerFrom;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;
    @Bind(R.id.iv_metric)
    ImageView iv_metric;

    @Bind(R.id.np_height_metric)
    public NumberPicker npHeightMetric;
    @Bind(R.id.np_height_foot)
    public NumberPicker npHeightFoot;
    @Bind(R.id.np_height_inch)
    public NumberPicker npHeightInch;

    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;
    @Bind(R.id.tv_text_cm)
    TextView tv_text_cm;

    private float FOOT_IN_CM = 30.48f;
    private float INCH_IN_CM = 2.54f;


    private Intent serviceIntent;

    public GrowthFragment() {
    }

    public GrowthFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_growth, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        setHeight();

        tv_title_fragment.setText(R.string.set_height);
        tv_title_fragment.setTypeface(getFontRobotoLight());
        tv_text_cm.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        }
        setDefoultViews();

        serviceIntent = new Intent(getActivity(), ApiService.class);

        return view;
    }

    private void setDefoultViews() {
        boolean metric = SettingsApp.getInstance().getMetric();
        if (metric) {
            tv_text_cm.setVisibility(View.VISIBLE);
            npHeightMetric.setVisibility(View.VISIBLE);
            npHeightFoot.setVisibility(View.INVISIBLE);
            npHeightInch.setVisibility(View.INVISIBLE);
            iv_metric.setVisibility(View.INVISIBLE);
        } else {
            npHeightFoot.performClick();
            npHeightInch.performClick();
        }
    }


    private void setHeight() {
        mPresenter.setHeight(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        saveData();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void saveData() {
        String height = "";
        if (SettingsApp.getInstance().getMetric()) {
            height = String.valueOf(npHeightMetric.getValue());
        } else {
            float value = (float) npHeightFoot.getValue() * FOOT_IN_CM +
                    (float) npHeightInch.getValue() * INCH_IN_CM;
            height = String.valueOf(value);
        }
        mPresenter.saveHeight(height, this);
    }

    @Subscribe
    public void onEvent(UpdateUiEvent mEvent) {
        if (mEvent.isSucess()) {
            if (mEvent.getId() == UpdateUiEvent.PROFILE) {
                saveData();
                goToNext();
            }
        } else {
            Toast.makeText(getActivity(), ((String) mEvent.getData()), Toast.LENGTH_SHORT).show();
        }
        System.out.println(mEvent.getData().toString());
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        if (checkInternetConnection() &&
                !SettingsApp.getInstance().getUserName().equalsIgnoreCase(STATICS_PARAMS.TEST_USER)) {
            serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, PROFILE);
            getActivity().startService(serviceIntent);
        } else {
            goToNext();
        }
    }

    private void goToNext() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mPresenter.setFullSettings(this);
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY, false);
    }


    //    =============================================
//             answer from HeightListener
//    =============================================

    @Override
    public void isHeight(String mHeight) {
        float height = 170;
        if (!mHeight.isEmpty()) {
            height = Float.parseFloat(mHeight);
        }
        final float finalHeight = height;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (SettingsApp.getInstance().getMetric()) {
                    npHeightMetric.setValue((int) finalHeight);
                } else {
                    float inchs = finalHeight / INCH_IN_CM;

                    int foot = (int) (inchs / 12);
                    int inch = (int) (inchs - (foot * 12));
                    npHeightFoot.setValue(foot);              // foot
                    npHeightInch.setValue(inch);     // inch
                }
            }
        });
    }

    //    =============================================
//     END        answer from HeightListener
//    =============================================
//         answer from ProfileFirstStartBLeListener
//    =============================================
    @Override
    public void isOkFullSettings(boolean mIsOkFullSettings) {
        mPresenter.goToMainActivity();
        mActivity.finish();
    }

}
