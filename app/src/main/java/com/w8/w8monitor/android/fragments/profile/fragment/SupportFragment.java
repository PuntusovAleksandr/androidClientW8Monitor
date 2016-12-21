package com.w8.w8monitor.android.fragments.profile.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.w8.w8monitor.android.R;
import com.w8.w8monitor.android.activity.ProfileActivity;
import com.w8.w8monitor.android.activity.interfaces.presentts.ProfilePresenter;
import com.w8.w8monitor.android.fragments.FragmentMapker;
import com.w8.w8monitor.android.utils.ValidationText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AleksandrP on 21.12.2016.
 */

public class SupportFragment extends Fragment {


    private ProfileActivity mActivity;
    private ProfilePresenter mPresenter;

    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;

    @Bind(R.id.ll_send_question)
    RelativeLayout iv_register_ok;

    @Bind(R.id.et_description_text)
    EditText et_description_text;
    @Bind(R.id.et_email_register)
    EditText et_email_register;

    @SuppressLint("ValidFragment")
    public SupportFragment(ProfilePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        ButterKnife.bind(this, view);
        mActivity = (ProfileActivity) getActivity();

        return view;
    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {
        mActivity.setSettingsFragment(FragmentMapker.SETTINGS_FRAGMENT, 0);
    }

    @OnClick(R.id.ll_send_question)
    public void iv_register_okClick() {

        String email = et_email_register.getText().toString();
        String text = et_description_text.getText().toString();
        int length = text.length();
        boolean emailBoolean = ValidationText.checkValidEmail(email);
        if (length > 3 && emailBoolean) {
            mActivity.sendQuestion(email, text);
        } else {
            if (!emailBoolean) {
                Toast.makeText(mActivity, R.string.fill_email, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, R.string.fill_question, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
