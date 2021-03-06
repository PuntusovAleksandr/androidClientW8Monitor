package com.w8.w8monitor.android.fragments.profile.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.d_base.model.RegisterUser;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.shawnlin.numberpicker.NumberPicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_SETTINGS;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;


/**
 * A simple {@link Fragment} subclass.
 */
public class BirthdayFragment extends Fragment implements RealmObj.BirthDayListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private boolean mFromSettings;
    private int markerFrom;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

//    @Bind(R.id.np_day)
//    public NumberPicker npDay;
//    @Bind(R.id.np_month)
//    public NumberPicker npMonth;
//    @Bind(R.id.np_year)
//    public NumberPicker npYear;

    @Bind(R.id.years_pld)
    public NumberPicker years_pld;


    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;

    private RegisterUser mRegisterUser;

    public BirthdayFragment() {
    }

    @SuppressLint("ValidFragment")
    public BirthdayFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings, RegisterUser mRegisterUser) {
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


        setDate();

        tv_title_fragment.setText(R.string.set_ago);
        tv_title_fragment.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN || markerFrom == MARKER_SETTINGS) {
            iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void setDate() {
        mPresenter.setDateBirthDay(this);
    }


    @Override
    public void onStop() {
        mRegisterUser.setAge(years_pld.getValue());
        mPresenter.saveDateBirthDay(
                String.valueOf(years_pld.getValue()),
//                npDay.getValue() + "/" +
//                        npMonth.getValue() + "/" +
//                        npYear.getValue(),
                this);
        super.onStop();
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH, false, mRegisterUser);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        if (markerFrom == MARKER_SETTINGS) {
            getActivity().finish();
            return;
        }
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY, false, mRegisterUser);
    }


    //    =============================================
//             answer from BirthDayListener
//    =============================================
    @Override
    public void isBirthDay(String date) {
//        int day = 1, month = 1, year = 2000;
//        if (!date.isEmpty()) {
//            String[] strings = date.split("/");
//            day = Integer.parseInt(strings[0]);
//            month = Integer.parseInt(strings[1]);
//            year = Integer.parseInt(strings[2]);
//        }
//        npDay.setValue(day);
//        npMonth.setValue(month);
//        npYear.setValue(year);
        years_pld.setValue(Integer.parseInt(date));

    }

//    =============================================
//     END        answer from BirthDayListener
//    =============================================
}
