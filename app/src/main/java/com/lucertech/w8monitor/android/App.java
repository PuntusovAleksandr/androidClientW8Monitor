package com.lucertech.w8monitor.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by AleksandrP on 21.09.2016.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = App.class.getSimpleName();

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        Answers.getInstance().logContentView(new ContentViewEvent());

        App.context = this.getApplicationContext();
        registerActivityLifecycleCallbacks(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Answers setup process super easy!")
                .putContentType("Technical documentation")
                .putContentId("article-350"));
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onActivityCreated(Activity mActivity, Bundle mBundle) {

    }

    @Override
    public void onActivityStarted(Activity mActivity) {

    }

    @Override
    public void onActivityResumed(Activity mActivity) {

    }

    @Override
    public void onActivityPaused(Activity mActivity) {

    }

    @Override
    public void onActivityStopped(Activity mActivity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity mActivity, Bundle mBundle) {

    }

    @Override
    public void onActivityDestroyed(Activity mActivity) {

    }
}
