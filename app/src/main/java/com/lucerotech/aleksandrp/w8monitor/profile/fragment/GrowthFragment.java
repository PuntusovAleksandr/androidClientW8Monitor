package com.lucerotech.aleksandrp.w8monitor.profile.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrowthFragment extends Fragment {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

    public GrowthFragment(ProfileView mProfileView, ProfilePresenter mPresenter) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_growth, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        return view;
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.CONNECT_BLE);
    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY);
    }

}
