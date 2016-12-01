package com.w8.w8monitor.android.utils;

import static com.w8.w8monitor.android.utils.STATICS_PARAMS.REGULAR_MAIL;
import static com.w8.w8monitor.android.utils.STATICS_PARAMS.REGULAR_PASS;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class ValidationText {

    /**
     * check validation email address
     *
     * @param email
     * @return
     */
    public static boolean checkValidEmail(String email) {
        return email.matches(REGULAR_MAIL);
    }


    public static boolean checkLenghtPassword(String password) {
        return password.length() > REGULAR_PASS;
    }

}
