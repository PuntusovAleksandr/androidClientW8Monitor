package  com.w8.w8monitor.android.d_base.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 05.10.2016.
 */

public class AlarmModel extends RealmObject {

    private int idAlarm;

    @PrimaryKey
    private String time;

    private String email;

    private boolean isAm;

    public AlarmModel() {
    }

    public boolean isAm() {
        return isAm;
    }

    public void setAm(boolean mAm) {
        isAm = mAm;
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int mIdAlarm) {
        idAlarm = mIdAlarm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String mTime) {
        time = mTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mEmail) {
        email = mEmail;
    }
}
