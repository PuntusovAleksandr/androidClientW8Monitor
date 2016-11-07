package com.lucerotech.aleksandrp.w8monitor.help.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucerotech.aleksandrp.w8monitor.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lucerotech.aleksandrp.w8monitor.utils.FontsTextView.getFontRobotoLight;

public class FragmentScreenBase extends Fragment {


    @Bind(R.id.tv_title_help)
    TextView tv_title_help;
    @Bind(R.id.tv_description_screen)
    TextView tv_description_screen;


    @Bind(R.id.iv_toolbar_back_press)
    ImageView iv_toolbar_back_press;
    @Bind(R.id.iv_toolbar_next_press)
    ImageView iv_toolbar_next_press;

    public FragmentScreenBase() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_screen_base, container, false);
        ButterKnife.bind(this, view);

        tv_title_help.setTypeface(getFontRobotoLight());
        tv_description_screen.setTypeface(getFontRobotoLight());

        setTextsInView("SCREEN", "Description");

        return view;
    }

    public void setTextsInView(String title, String description) {
        tv_title_help.setText(Html.fromHtml(title));
        tv_description_screen.setText(Html.fromHtml(description));
    }

    //    =====================================
//            onClick
//    =====================================
    @OnClick(R.id.iv_toolbar_next_press)
    public void clickNextFragment() {

    }


    @OnClick(R.id.iv_toolbar_back_press)
    public void clickBackFragment() {

    }
}
