/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.database.sqlite.SQLiteDatabase
 *  android.graphics.Bitmap
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.lefu.es.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lefu.es.constant.UtilConstants;
import com.lefu.es.constant.imageUtil;
import com.lefu.es.db.DBOpenHelper;
import com.lefu.es.entity.UserModel;
import com.lefu.es.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserlistviewAdapter
extends BaseAdapter {
    private Bitmap bitmap;
    private Context cont;
    private LayoutInflater inflater;
    private boolean isEdit;
    private int resource;
    private int selectedPosition = -1;
    private UserModel user;
    public List<UserModel> users = new ArrayList();
    private UserService uservice;

    public UserlistviewAdapter(Context context, int n, List<UserModel> list) {
        this.cont = context;
        this.resource = n;
        this.users = list;
        this.inflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    static /* synthetic */ void access$2(UserlistviewAdapter userlistviewAdapter, UserService userService) {
        userlistviewAdapter.uservice = userService;
    }

    public int getCount() {
        if (this.users == null || this.users.size() == 0) {
            return 0;
        }
        return this.users.size();
    }

    public Object getItem(int n) {
        if (this.users == null || this.users.size() == 0) {
            return null;
        }
        return this.users.get(n);
    }

    public int getItemID(int n) {
        return ((UserModel)this.users.get(n)).getId();
    }

    public long getItemId(int n) {
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(final int n, View view, ViewGroup viewGroup) {
        ViewCache viewCache = new ViewCache();
        View view2 = this.inflater.inflate(this.resource, null);
        viewCache.photo = (ImageView)view2.findViewById(2131296298);
        viewCache.nameView = (TextView)view2.findViewById(2131296541);
        viewCache.deletimg = (ImageView)view2.findViewById(2131296542);
        this.user = (UserModel)this.users.get(n);
        if (this.user != null) {
            this.bitmap = null;
            viewCache.nameView.setText((CharSequence)this.user.getUserName());
            if (this.user.getPer_photo() != null && !"".equals((Object)this.user.getPer_photo())) {
                this.bitmap = imageUtil.getBitmapfromPath(this.user.getPer_photo());
                viewCache.photo.setImageBitmap(this.bitmap);
            }
        }
        if (!this.isEdit) {
            viewCache.deletimg.setVisibility(8);
        } else {
            viewCache.deletimg.setVisibility(0);
        }
        viewCache.deletimg.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                try {
                    if (UserlistviewAdapter.this.uservice == null) {
                        UserlistviewAdapter.access$2(UserlistviewAdapter.this, new UserService(UserlistviewAdapter.this.cont));
                    }
                    int n3 = UserlistviewAdapter.this.getItemID(n);
                    UserlistviewAdapter.this.uservice.delete(n3);
                    UserlistviewAdapter.this.users.remove(n);
                    UserlistviewAdapter.this.notifyDataSetChanged();
                    if (UtilConstants.CURRENT_USER != null && UtilConstants.CURRENT_USER.getId() == n3) {
                        UtilConstants.CURRENT_USER = null;
                    }
                    if (UserlistviewAdapter.this.users == null || UserlistviewAdapter.this.users.size() == 0) {
                        Intent intent = new Intent("ACTION_NO_USER");
                        UserlistviewAdapter.this.cont.sendBroadcast(intent);
                    }
                    int n2 = new UserService(UserlistviewAdapter.this.cont).getMaxGroup();
                    SQLiteDatabase sQLiteDatabase = new DBOpenHelper(UserlistviewAdapter.this.cont).getReadableDatabase();
                    Object[] arrobject = new Object[]{n2, UtilConstants.SELECT_SCALE};
                    sQLiteDatabase.execSQL("update user u set max(number) = ? where scaletype = ?", arrobject);
                    sQLiteDatabase.close();
                    return;
                }
                catch (Exception var2_7) {
                    var2_7.printStackTrace();
                    return;
                }
            }
        });
        return view2;
    }

    public void setEdit(boolean bl) {
        this.isEdit = bl;
    }

    public void setSelectedPosition(int n) {
        this.selectedPosition = n;
    }

}

