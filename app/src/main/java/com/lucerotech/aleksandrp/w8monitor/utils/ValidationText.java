package com.lucerotech.aleksandrp.w8monitor.utils;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class ValidationText {

    private static final int REGULAR_PASS =          8;
    private static final String REGULAR_MAIL =       "^[a-z0-9](\\.?[a-z0-9_-]){0,}@[a-z0-9-]+\\.([a-z]{1,6}\\.)?[a-z]{2,15}$";

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
