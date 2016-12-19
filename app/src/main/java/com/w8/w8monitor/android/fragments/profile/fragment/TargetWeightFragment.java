package com.w8.w8monitor.android.fragments.profile.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.RegisterUser;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.utils.SettingsApp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.w8.w8monitor.android.fragments.profile.fragment.StateFragment.MAN;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;

/**
 * Created by AleksandrP on 19.12.2016.
 */

public class TargetWeightFragment extends Fragment implements
        RealmObj.TargetWeightListener {


    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private boolean mFromSettings;
    private int markerFrom;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;


    @Bind(R.id.years_pld)
    public NumberPicker years_pld;


    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;

    private boolean metric;
    private RegisterUser mRegisterUser;

    public TargetWeightFragment() {
    }

    @SuppressLint("ValidFragment")
    public TargetWeightFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings, RegisterUser mRegisterUser) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
        this.mRegisterUser = mRegisterUser == null ? new RegisterUser() : mRegisterUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        metric = SettingsApp.getInstance().getMetric();

        setDefoultViews();
        setDataPicker();

        tv_title_fragment.setText(R.string.target_weight);
        tv_title_fragment.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        }
        return view;
    }


    private void setDataPicker() {
        int weightUser = 0;
        try {
            weightUser = mRegisterUser.getTargetWeight();
            if (weightUser == 0) {
                weightUser = Integer.parseInt(null);
            }
            if (metric)
                weightUser = (int) (weightUser * 0.453592f);
        } catch (Exception mE) {
            mE.printStackTrace();


//        if () // TODO: 19.12.2016  UFLJ СОЗДАТЬ ПОЛУЧЕНИЕ ОТ ЮЗЕРА С ФБ
            if (metric) {
                if (mRegisterUser.getGender() == MAN) {
                    weightUser = 82;
                } else {
                    weightUser = 54;
                }
            } else {
                if (mRegisterUser.getGender() == MAN) {
                    weightUser = 180;
                } else {
                    weightUser = 120;
                }
            }
        }
        years_pld.setValue(weightUser);
    }

    private void setDefoultViews() {
        if (metric) {
            years_pld.setMaxValue(250);
        } else {
            years_pld.setMaxValue(400);
        }
    }


    @Override
    public void onStop() {
        int weight = years_pld.getValue();
        if (metric) {
            weight = (int) (years_pld.getValue() / 0.453592f);
        }
        mRegisterUser.setTargetWeight(weight);
        mPresenter.saveTargetWeight(
                String.valueOf(weight),
                this);
        super.onStop();
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.GOOGLE_FIT, false, mRegisterUser);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH, false, mRegisterUser);
    }

    //=============================================
//        from TargetWeightListener
//=============================================
    @Override
    public void isTargetWeight(String date) {
        years_pld.setValue(Integer.parseInt(date));
    }
}
