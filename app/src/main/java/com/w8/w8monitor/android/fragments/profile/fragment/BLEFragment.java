package com.w8.w8monitor.android.fragments.profile.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.activity.interfaces.views.ProfileView;
import com.w8.w8monitor.android.ble.BluetoothHandler;
import com.w8.w8monitor.android.d_base.RealmObj;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.utils.SettingsApp;
import com.w8.w8monitor.android.utils.ShowImages;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.w8.w8monitor.android.activity.ProfileActivity.MARKER_MAIN;
import static com.w8.w8monitor.android.utils.FontsTextView.getFontRobotoLight;


/**
 * A simple {@link Fragment} subclass.
 */
public class BLEFragment extends Fragment implements
        BLEFragmentView,
        RealmObj.ProfileBLeListener,
        BluetoothHandler.onResultScanDevice {
//        ContextDialogListBtDevices.RefreshInterface {

    private ProfileActivity mActivity;

    private ProfileView mProfileView;
    private ProfilePresenter mPresenter;

    public static final int PROFILE_1 = 1;
    public static final int PROFILE_2 = 2;

    private int markerFrom;

    private boolean mFromSettings;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;
    @Bind(R.id.profile_ble1)
    ImageView profile_ble1;
    @Bind(R.id.profile_ble2)
    ImageView profile_ble2;
    @Bind(R.id.iv_ble_connect)
    ImageView iv_ble_connect;

    @Bind(R.id.tv_title_fragment)
    TextView tv_title_fragment;
    @Bind(R.id.tv_turn_ble)
    TextView tv_turn_ble;

    @Bind(R.id.ll_buttons_profile)
    LinearLayout ll_buttons_profile;

    // BLE
    private BluetoothHandler mBluetoothHandler;
    private boolean supportEnable;

    public BLEFragment() {
    }

    public BLEFragment(ProfileView mProfileView, ProfilePresenter mPresenter, int markerFrom, boolean mFromSettings) {
        this.mProfileView = mProfileView;
        this.mPresenter = mPresenter;
        this.markerFrom = markerFrom;
        this.mFromSettings = mFromSettings;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ProfileActivity) getContext();
        mActivity.initListenerBleFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ble, container, false);
        ButterKnife.bind(this, view);
        setDefaultUI();

        mPresenter.getProfileBLE(this);
        tv_title_fragment.setText(R.string.user_profile_scale);
        tv_title_fragment.setTypeface(getFontRobotoLight());
        tv_turn_ble.setTypeface(getFontRobotoLight());

        // hide button back
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void setDefaultUI() {
        tv_turn_ble.setVisibility(View.VISIBLE);
        ll_buttons_profile.setVisibility(View.INVISIBLE);
        iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        if (SettingsApp.getInstance().isThemeDark()) {
            iv_ble_connect.setImageResource(R.drawable.scale_nonactive_dark);
        } else {
            iv_ble_connect.setImageResource(R.drawable.scale_nonactive_light);
        }
    }


    private void setConnectUI() {
        tv_turn_ble.setVisibility(View.INVISIBLE);
        ll_buttons_profile.setVisibility(View.VISIBLE);
        if (markerFrom == MARKER_MAIN) {
            iv_toolbar_next_press.setVisibility(View.INVISIBLE);
        } else
            iv_toolbar_next_press.setVisibility(View.VISIBLE);
        if (SettingsApp.getInstance().isThemeDark()) {
            iv_ble_connect.setImageResource(R.drawable.scale_active_dark);
        } else {
            iv_ble_connect.setImageResource(R.drawable.scale_active_light);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        checkStatusBLE();
    }

    //    ==================================================================
//        BLE    check and set bluetooth connection
//    ==================================================================
    private void checkStatusBLE() {
        mBluetoothHandler = new BluetoothHandler(mActivity, this);
        supportEnable = mBluetoothHandler.checkSupport();
    }

    @Override
    public void showListDevisesDialog() {
        setConnectUI();
    }


    //    ==================================================================
//            onClick
//    ==================================================================
    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {
        mPresenter.setFullSettings(this);

    }

    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        if (mFromSettings) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else
            mActivity.setEnterProfileDataFragment(FragmentMapker.USER_GROWTH, false);
    }

    @OnClick(R.id.profile_ble1)
    public void clickProfile1() {
        setProfileBLE(PROFILE_1);
    }

    @OnClick(R.id.profile_ble2)
    public void clickProfile2() {
        setProfileBLE(PROFILE_2);
    }

    @OnClick(R.id.tv_turn_ble)
    public void clickRefreshConnection() {
        supportEnable = mBluetoothHandler.checkSupport();
    }

    private void setProfileBLE(int mProfile) {
        mPresenter.setProfileBLE(mProfile, this);
    }


//    ==================================================================
//      END      onClick
//    ==================================================================
//     from ProfileBLeListener
//    ==================================================================

    @Override
    public void isProfileBLE(int prfileBle) {
        int src1 = 0;
        int src2 = 0;
        if (SettingsApp.getInstance().isThemeDark()) {
            src1 = R.drawable.profile_nonactive_dark;
            src2 = R.drawable.profile_nonactive_dark;
            switch (prfileBle) {
                case -1:        // error
                    break;
                case PROFILE_1:         // man
                    src1 = R.drawable.profile_active_dark;
                    break;
                case PROFILE_2:         // woman
                    src2 = R.drawable.profile_active_dark;
                    break;
            }
        } else {
            src1 = R.drawable.profile_nonactive_light;
            src2 = R.drawable.profile_nonactive_light;
            switch (prfileBle) {
                case -1:        // error
                    break;
                case PROFILE_1:         // man
                    src1 = R.drawable.profile_active_light;
                    break;
                case PROFILE_2:         // woman
                    src2 = R.drawable.profile_active_light;
                    break;
            }
        }
        showPicture(src1, src2);
    }

    @Override
    public void isOkFullSettings(boolean mIsOkFullSettings) {
        if (markerFrom == MARKER_MAIN) {
            mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
        } else {
            mPresenter.goToMainActivity();
            mActivity.finish();
        }
    }


//    ==================================================================
//      END       from ProfileBLeListener
//    ==================================================================

    private void showPicture(int mSrc1, int mSrc2) {
        ShowImages.showImage(getActivity(), profile_ble1, mSrc1);
        ShowImages.showImage(getActivity(), profile_ble2, mSrc2);
    }


    //    ==================================================================
//      START       from onResultScanDevice
//    ==================================================================
    public void scanOk(boolean mEnabled) {
        supportEnable = mEnabled;
        showListDevisesDialog();
    }


//    //    ==================================================================
////      START       from RefreshInterface
////    ==================================================================
//    @Override
//    public void onRefresh() {
//        listBtDevices.updayteListDev(mBluetoothHandler.getDeviceListAdapter());
//    }
//
//    @Override
//    public void onClickItem(String mItem) {
//        mBluetoothHandler.connect(mItem, mActivity);
//        setConnectUI();
//    }


}
