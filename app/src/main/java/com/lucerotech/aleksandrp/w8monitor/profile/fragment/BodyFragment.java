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

import static com.lucerotech.aleksandrp.w8monitor.R.id.iv_man;
import static com.lucerotech.aleksandrp.w8monitor.R.id.iv_toolbar_back_press;
import static com.lucerotech.aleksandrp.w8monitor.R.id.iv_woman;

public class BodyFragment extends Fragment implements RealmObj.BodyListener {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    public static final int BODY_TYPE_1 = 1;
    public static final int BODY_TYPE_2 = 2;
    public static final int BODY_TYPE_3 = 3;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

    @Bind(R.id.iv_type1)
    ImageView iv_type1;
    @Bind(R.id.iv_type2)
    ImageView iv_type2;
    @Bind(R.id.iv_type3)
    ImageView iv_type3;

    public BodyFragment(ProfileView mProfileView, ProfilePresenter mPresenter) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_body, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        mPresenter.getBodyUser(this);

        return view;
    }

    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.DATA_BIRTHDAY);
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.setEnterProfileDataFragment(FragmentMapker.SELECT_STATE);
    }

    @OnClick(R.id.iv_type1)
    public void clickType1() {
        setBody(BODY_TYPE_1);
    }


    @OnClick(R.id.iv_type2)
    public void clickType2() {
        setBody(BODY_TYPE_2);
    }

    @OnClick(R.id.iv_type3)
    public void clickType3() {
        setBody(BODY_TYPE_3);
    }


    private void setBody(int mBodyType) {
        mPresenter.setBody(mBodyType, this);
    }
    //    ==================================================
//            answer from BodyListener
//    ==================================================
    @Override
    public void isBody(int body) {
        int resIcon1 = R.drawable.type_1_nonactive_dark;
        int resIcon2 = R.drawable.type_2_nonactive_dark;
        int resIcon3 = R.drawable.type_3_nonactive_dark;
        switch (body) {
            case -1:
                break;
            case BODY_TYPE_1:
                resIcon1 = R.drawable.type_1_active_dark;
                break;
            case BODY_TYPE_2:
                resIcon2 = R.drawable.type_2_active_dark;
                break;
            case BODY_TYPE_3:
                resIcon3 = R.drawable.type_3_active_dark;
                break;
        }

        showPicture(resIcon1, resIcon2, resIcon3);
    }


//    ==========================================================
//       END     from StateListener
//    ==========================================================


    private void showPicture(int mResIcon1, int mResIcon2, int mResIcon3) {
        ShowImages.showImage(getActivity(), iv_type1, mResIcon1);
        ShowImages.showImage(getActivity(), iv_type2, mResIcon2);
        ShowImages.showImage(getActivity(), iv_type3, mResIcon3);
    }
}
