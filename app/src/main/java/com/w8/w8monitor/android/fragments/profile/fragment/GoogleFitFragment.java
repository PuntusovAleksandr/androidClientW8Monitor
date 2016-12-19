package com.w8.w8monitor.android.fragments.profile.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;

import butterknife.ButterKnife;

/**
 * Created by AleksandrP on 19.12.2016.
 */

public class GoogleFitFragment extends Fragment {


    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    private boolean mFromSettings;
    private int markerFrom;

    public GoogleFitFragment() {
    }

    @SuppressLint("ValidFragment")
    public GoogleFitFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_fit, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        // TODO: 19.12.2016 здесь нужен запрос на интеграцию с гугл фит

        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
    }


//    =============================================
//     END        answer from BirthDayListener
//    =============================================

}