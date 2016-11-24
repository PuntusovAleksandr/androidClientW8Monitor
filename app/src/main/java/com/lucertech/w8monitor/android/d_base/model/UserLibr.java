package  com.lucertech.w8monitor.android.d_base.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 14.09.2016.
 */

public class UserLibr extends RealmObject {

    @PrimaryKey
    private String email;
    public String password;

    private String token;
    private int id_server;
    private String created_at;
    private String updated_at;
    private boolean is_imperial;
    private boolean keep_login;
    //    2 - dark, 1 - light
    private int theme;
    private String language;


    private RealmList<Profile> mProfiles;

    public int profileBLE;

    private boolean fullProfile;

    private RealmList<StringRealm> alarms;

    public UserLibr() {
    }

    public boolean isFullProfile() {
        return fullProfile;
    }

    public void setFullProfile(boolean mFullProfile) {
        fullProfile = mFullProfile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String mPassword) {
        password = mPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String mToken) {
        token = mToken;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int mId_server) {
        id_server = mId_server;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        email = mEmail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String mCreated_at) {
        created_at = mCreated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String mUpdated_at) {
        updated_at = mUpdated_at;
    }

    public boolean getIs_imperial() {
        return is_imperial;
    }

    public void setIs_imperial(boolean mIs_imperial) {
        is_imperial = mIs_imperial;
    }

    public boolean getKeep_login() {
        return keep_login;
    }

    public void setKeep_login(boolean mKeep_login) {
        keep_login = mKeep_login;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int mTheme) {
        theme = mTheme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String mLanguage) {
        language = mLanguage;
    }

    public int getProfileBLE() {
        return profileBLE;
    }

    public void setProfileBLE(int mProfileBLE) {
        profileBLE = mProfileBLE;
    }

    public RealmList<Profile> getProfiles() {
        return mProfiles;
    }

    public void setProfiles(RealmList<Profile> mProfiles) {
        this.mProfiles = mProfiles;
    }

    public boolean is_imperial() {
        return is_imperial;
    }

    public boolean isKeep_login() {
        return keep_login;
    }

    public RealmList<StringRealm> getAlarms() {
        return alarms;
    }

    public void setAlarms(RealmList<StringRealm> mAlarms) {
        alarms = mAlarms;
    }
}
