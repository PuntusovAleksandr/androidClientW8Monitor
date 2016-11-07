package com.lucerotech.aleksandrp.w8monitor.help;

/**
 * Created by AleksandrP on 27.10.2016.
 */

public interface HelpView {

    void goToBackMainActivity();

    void goToNextFragment(String mScreen);

    void goToBackFragment(String mScreen);

    void finishHelp();
}
