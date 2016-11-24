package  com.lucertech.w8monitor.android.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class UserApiData {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("is_imperial")
    @Expose
    private boolean is_imperial;

    @SerializedName("keep_login")
    @Expose
    private boolean keep_login;

    @SerializedName("theme")
    @Expose
    private int theme;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("profiles")
    @Expose
    private List<ProfileApi> mProfileApis;

    @SerializedName("alarms")
    @Expose
    private List<String> alarms;

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
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

    public List<ProfileApi> getProfileApis() {
        return mProfileApis;
    }

    public boolean is_imperial() {
        return is_imperial;
    }

    public void setIs_imperial(boolean mIs_imperial) {
        is_imperial = mIs_imperial;
    }

    public boolean isKeep_login() {
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

    public void setProfileApis(List<ProfileApi> mProfileApis) {
        this.mProfileApis = mProfileApis;
    }

    public List<String> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<String> mAlarms) {
        alarms = mAlarms;
    }
}
