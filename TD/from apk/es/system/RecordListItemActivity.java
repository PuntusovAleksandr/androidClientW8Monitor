/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.NotificationManager
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  android.widget.TableRow
 *  android.widget.TextView
 *  java.io.Serializable
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.system;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import com.lefu.es.constant.UtilConstants;
import com.lefu.es.entity.Records;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.ExitApplication;
import com.lefu.es.system.LoadingActivity;
import com.lefu.es.util.UtilTooth;
import com.lefu.es.view.MyTextView4;
import java.io.Serializable;

public class RecordListItemActivity
extends Activity
implements View.OnClickListener {
    ImageView chaImage = null;
    private Records record = null;
    private TableRow row_phsicalage = null;
    TextView tv_name_title = null;
    TextView tvdetail_bmi = null;
    TextView tvdetail_bmi_title = null;
    TextView tvdetail_bmr = null;
    TextView tvdetail_bmr_title = null;
    TextView tvdetail_bodyfat = null;
    TextView tvdetail_bodyfat_title = null;
    TextView tvdetail_bodywater = null;
    TextView tvdetail_bodywater_title = null;
    TextView tvdetail_bone = null;
    TextView tvdetail_bone_title = null;
    TextView tvdetail_muscle = null;
    TextView tvdetail_muscle_title;
    TextView tvdetail_phsicalage = null;
    TextView tvdetail_phsicalage_title = null;
    TextView tvdetail_visceral = null;
    TextView tvdetail_visceral_title = null;
    MyTextView4 tvdetail_weight = null;
    TextView tvdetail_weight_title;

    private void initResourceRefs() {
        this.chaImage = (ImageView)this.findViewById(2131296358);
        this.tvdetail_weight_title = (TextView)this.findViewById(2131296360);
        this.tvdetail_weight = (MyTextView4)this.findViewById(2131296361);
        this.tvdetail_bmr = (TextView)this.findViewById(2131296375);
        this.tvdetail_bmr_title = (TextView)this.findViewById(2131296374);
        this.tvdetail_bone = (TextView)this.findViewById(2131296363);
        this.tvdetail_bodyfat = (TextView)this.findViewById(2131296365);
        this.tvdetail_bodyfat_title = (TextView)this.findViewById(2131296364);
        this.tvdetail_muscle = (TextView)this.findViewById(2131296367);
        this.tvdetail_phsicalage = (TextView)this.findViewById(2131296378);
        this.tvdetail_phsicalage_title = (TextView)this.findViewById(2131296377);
        this.row_phsicalage = (TableRow)this.findViewById(2131296376);
        this.tvdetail_bone_title = (TextView)this.findViewById(2131296362);
        this.tvdetail_muscle_title = (TextView)this.findViewById(2131296366);
        this.tvdetail_bodywater = (TextView)this.findViewById(2131296369);
        this.tvdetail_bodywater_title = (TextView)this.findViewById(2131296368);
        this.tvdetail_visceral = (TextView)this.findViewById(2131296371);
        this.tvdetail_visceral_title = (TextView)this.findViewById(2131296370);
        this.tvdetail_bmi = (TextView)this.findViewById(2131296373);
        this.tvdetail_bmi_title = (TextView)this.findViewById(2131296372);
        this.tv_name_title = (TextView)this.findViewById(2131296359);
        this.chaImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                RecordListItemActivity.this.finish();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void creatView(Records records) {
        if (records == null) return;
        {
            if (UtilConstants.KITCHEN_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
                this.tv_name_title.setText((CharSequence)records.getRphoto());
                if (UtilConstants.CURRENT_USER != null) {
                    if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB)) {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361819).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.kgToLBoz(records.getRweight())), null);
                        }
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361822).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.kgToFloz(records.getRweight())), null);
                        }
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_G)) {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361815).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.keep2Point(records.getRweight())), null);
                        }
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML)) {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361816).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.keep2Point(records.getRweight())), null);
                        }
                    } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ML2)) {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361817).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((int)UtilTooth.kgToML(records.getRweight())), null);
                        }
                    } else {
                        this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361819).toString());
                        if (this.tvdetail_weight != null) {
                            this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.kgToLBoz(records.getRweight())), null);
                        }
                    }
                } else {
                    this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361815).toString());
                    if (this.tvdetail_weight != null) {
                        this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.keep2Point(records.getRweight())), null);
                    }
                }
                this.tvdetail_bone_title.setText((CharSequence)this.getText(2131361971).toString());
                this.tvdetail_bone.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRbodywater())));
                this.tvdetail_bodyfat_title.setText((CharSequence)this.getText(2131361972).toString());
                if (this.tvdetail_bodyfat != null) {
                    this.tvdetail_bodyfat.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRbodyfat())));
                }
                this.tvdetail_muscle_title.setText((CharSequence)this.getText(2131361975).toString());
                this.tvdetail_muscle.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRbone())));
                this.tvdetail_bodywater_title.setText((CharSequence)this.getText(2131361978).toString());
                if (this.tvdetail_bodywater != null) {
                    this.tvdetail_bodywater.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRmuscle())));
                }
                this.tvdetail_visceral_title.setText((CharSequence)this.getText(2131361981).toString());
                if (this.tvdetail_visceral != null) {
                    this.tvdetail_visceral.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRvisceralfat())));
                }
                this.tvdetail_bmi_title.setText((CharSequence)this.getText(2131361984).toString());
                if (this.tvdetail_bmi != null) {
                    this.tvdetail_bmi.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRbmi())));
                }
                this.tvdetail_bmr_title.setText((CharSequence)this.getText(2131361987).toString());
                if (this.tvdetail_bmr != null) {
                    this.tvdetail_bmr.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getRbmr())));
                }
                this.tvdetail_phsicalage_title.setText((CharSequence)this.getText(2131361990).toString());
                this.tvdetail_phsicalage.setText((CharSequence)String.valueOf((Object)UtilTooth.keep2Point(records.getBodyAge())));
                return;
            } else {
                this.tv_name_title.setText((CharSequence)"");
                if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_KG)) {
                    this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361818).toString());
                    if (this.tvdetail_weight != null) {
                        this.tvdetail_weight.setTexts(String.valueOf((float)records.getRweight()), null);
                    }
                } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_LB)) {
                    this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361820).toString());
                    if (this.tvdetail_weight != null) {
                        this.tvdetail_weight.setTexts(String.valueOf((Object)UtilTooth.kgToLB_new(records.getRweight())), null);
                    }
                } else if (UtilConstants.CHOICE_KG.equals((Object)UtilConstants.UNIT_ST)) {
                    String[] arrstring;
                    this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361821).toString());
                    if (this.tvdetail_weight != null) {
                        this.tvdetail_weight.setTexts(String.valueOf((double)UtilTooth.kgToStLb_B(records.getRweight())), null);
                    }
                    if ((arrstring = UtilTooth.kgToStLbForScaleFat2(records.getRweight()))[1] != null && arrstring[1].indexOf("/") > 0) {
                        this.tvdetail_weight.setTexts(arrstring[0], arrstring[1]);
                    } else {
                        this.tvdetail_weight.setTexts(String.valueOf((Object)arrstring[0]) + (Object)this.getText(2131361894), null);
                    }
                } else {
                    this.tvdetail_weight_title.setText((CharSequence)this.getText(2131361818).toString());
                    if (this.tvdetail_weight != null) {
                        this.tvdetail_weight.setTexts(String.valueOf((float)records.getRweight()), null);
                    }
                }
                if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_KG)) {
                    this.tvdetail_bone_title.setText((CharSequence)this.getText(2131361831).toString());
                    this.tvdetail_muscle_title.setText((CharSequence)this.getText(2131361834).toString());
                    this.tvdetail_bone.setText((CharSequence)String.valueOf((float)records.getRbone()));
                    this.tvdetail_muscle.setText((CharSequence)String.valueOf((float)records.getRmuscle()));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_LB) || UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_FATLB)) {
                    this.tvdetail_bone_title.setText((CharSequence)this.getText(2131361832).toString());
                    this.tvdetail_muscle_title.setText((CharSequence)this.getText(2131361835).toString());
                    this.tvdetail_bone.setText((CharSequence)String.valueOf((Object)UtilTooth.kgToLB(records.getRbone())));
                    this.tvdetail_muscle.setText((CharSequence)String.valueOf((Object)UtilTooth.kgToLB(records.getRmuscle())));
                } else if (UtilConstants.CURRENT_USER.getDanwei().equals((Object)UtilConstants.UNIT_ST)) {
                    this.tvdetail_bone_title.setText((CharSequence)this.getText(2131361833).toString());
                    this.tvdetail_muscle_title.setText((CharSequence)this.getText(2131361836).toString());
                    this.tvdetail_bone.setText((CharSequence)String.valueOf((Object)UtilTooth.kgToLB(records.getRbone())));
                    this.tvdetail_muscle.setText((CharSequence)String.valueOf((Object)UtilTooth.kgToLB(records.getRmuscle())));
                } else {
                    this.tvdetail_bone_title.setText((CharSequence)this.getText(2131361831).toString());
                    this.tvdetail_muscle_title.setText((CharSequence)this.getText(2131361834).toString());
                    this.tvdetail_bone.setText((CharSequence)String.valueOf((float)records.getRbone()));
                    this.tvdetail_muscle.setText((CharSequence)String.valueOf((float)records.getRmuscle()));
                }
                if (records.getBodyAge() > 0.0f) {
                    this.tvdetail_phsicalage.setText((CharSequence)UtilTooth.keep0Point(records.getBodyAge()));
                } else {
                    this.row_phsicalage.setVisibility(8);
                }
                if (this.tvdetail_bmr != null) {
                    this.tvdetail_bmr.setText((CharSequence)String.valueOf((float)records.getRbmr()));
                }
                if (this.tvdetail_bodyfat != null) {
                    this.tvdetail_bodyfat.setText((CharSequence)String.valueOf((float)records.getRbodyfat()));
                }
                if (this.tvdetail_bodywater != null) {
                    this.tvdetail_bodywater.setText((CharSequence)String.valueOf((float)records.getRbodywater()));
                }
                if (this.tvdetail_visceral != null) {
                    this.tvdetail_visceral.setText((CharSequence)String.valueOf((float)records.getRvisceralfat()));
                }
                if (this.tvdetail_bmi == null) return;
                {
                    float f = UtilTooth.countBMI2(records.getRweight(), UtilConstants.CURRENT_USER.getBheigth() / 100.0f);
                    this.tvdetail_bmi.setText((CharSequence)String.valueOf((float)UtilTooth.myround(f)));
                    return;
                }
            }
        }
    }

    public void onClick(View view) {
        view.getId();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setRequestedOrientation(2);
        this.requestWindowFeature(5);
        this.requestWindowFeature(1);
        if (UtilConstants.BABY_SCALE.equals((Object)UtilConstants.CURRENT_SCALE) || UtilConstants.BATHROOM_SCALE.equals((Object)UtilConstants.CURRENT_SCALE)) {
            this.setContentView(2130903048);
        } else {
            this.setContentView(2130903047);
        }
        this.initResourceRefs();
        this.record = (Records)this.getIntent().getSerializableExtra("record");
        this.creatView(this.record);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 4 && keyEvent.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        if (n == 3) {
            if (UtilConstants.serveIntent != null) {
                this.stopService(UtilConstants.serveIntent);
            }
            ((NotificationManager)this.getSystemService("notification")).cancel(0);
            if (LoadingActivity.mainActivty != null) {
                LoadingActivity.mainActivty.finish();
            }
            this.finish();
            ExitApplication.getInstance().exit((Context)this);
        }
        return super.onKeyDown(n, keyEvent);
    }

}

