package com.lucerotech.aleksandrp.w8monitor.profile.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lucerotech.aleksandrp.w8monitor.R;
import com.lucerotech.aleksandrp.w8monitor.d_base.RealmObj;
import com.lucerotech.aleksandrp.w8monitor.profile.FragmentMapker;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileActivity;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfilePresenter;
import com.lucerotech.aleksandrp.w8monitor.profile.ProfileView;
import com.lucerotech.aleksandrp.w8monitor.utils.ShowImages;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class StateFragment extends Fragment implements RealmObj.StateListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;
    @Bind(R.id.iv_man)
    ImageView iv_man;
    @Bind(R.id.iv_woman)
    ImageView iv_woman;

    public StateFragment(ProfileView mProfileView, ProfilePresenter mPresenter) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_state, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        mPresenter.getStateUser(this);

        return view;
    }


    private void saveDbMan(boolean mB) {
        mPresenter.saveStateUser((mB ? 1 : 2), this);
    }

    @OnClick(R.id.iv_man)
    public void clickNan() {
        saveDbMan(true);
    }

    @OnClick(R.id.iv_woman)
    public void clickWoman() {
        saveDbMan(false);
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.TYPE_BODY);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.onBackPressed();
    }


    //    ==========================================================
//            from StateListener
//    ==========================================================
    @Override
    public void isSave(int state) {
        int srcMan = R.drawable.icon_male_nonactive_dark;
        int srcWoman = R.drawable.icon_female_nonactive_dark;
        switch (state) {
            case -1:        // error
                break;
            case 1:         // man
                srcMan = R.drawable.icon_male_active_dark;
                break;
            case 2:         // woman
                srcWoman = R.drawable.icon_female_active_dark;
                break;
        }
        showPicture(srcMan, srcWoman);
    }

//    ==========================================================
//       END     from StateListener
//    ==========================================================


    private void showPicture(int mSrcMan, int mSrcWoman) {
        ShowImages.showImage(getActivity(), iv_man, mSrcMan);
        ShowImages.showImage(getActivity(), iv_woman, mSrcWoman);
    }

}
