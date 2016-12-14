/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.NotificationManager
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.webkit.JsResult
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.TextView
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Method
 */
package com.lefu.es.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lefu.es.constant.UtilConstants;
import com.lefu.es.system.LoadingActivity;

public class HelpActivity
extends Activity {
    TextView tvTitle;
    WebView weView;

    public void onBackPressed() {
        this.finish();
    }

    public void onClickBack(View view) {
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LoadingActivity.isPad) {
            this.setRequestedOrientation(2);
        } else {
            this.setRequestedOrientation(1);
        }
        this.setContentView(2130903051);
        this.tvTitle = (TextView)this.findViewById(2131296267);
        this.weView = (WebView)this.findViewById(2131296381);
        this.weView.getSettings().setJavaScriptEnabled(true);
        this.weView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.weView.getSettings().setSupportZoom(true);
        this.weView.getSettings().setBuiltInZoomControls(true);
        this.weView.setInitialScale(200);
        this.weView.setWebChromeClient((WebChromeClient)new MyWebChromeClient());
        this.weView.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
                HelpActivity.this.weView.loadUrl("file:///android_asset/help.html");
                return false;
            }
        });
        this.weView.loadUrl("file:///android_asset/help.html");
        if (Integer.parseInt((String)Build.VERSION.SDK) >= 11) {
            WebSettings webSettings = this.weView.getSettings();
            Object[] arrobject = new Object[]{false};
            this.setZoomControlGoneX(webSettings, arrobject);
        } else {
            this.setZoomControlGone((View)this.weView);
        }
        this.weView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    default: 
                }
                return false;
            }
        });
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n == 3) {
            ((NotificationManager)this.getSystemService("notification")).cancel(0);
        }
        return super.onKeyDown(n, keyEvent);
    }

    protected void onResume() {
        super.onResume();
    }

    public void openIntentate(View view) {
        Intent intent = new Intent();
        intent.setData(Uri.parse((String)UtilConstants.homeUrl));
        intent.setAction("android.intent.action.VIEW");
        this.startActivity(intent);
    }

    /*
     * Exception decompiling
     */
    public void setZoomControlGone(View var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:394)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:446)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2859)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:805)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // com.njlabs.showjava.processor.JavaExtractor$1.run(JavaExtractor.java:100)
        // java.lang.Thread.run(Thread.java:841)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void setZoomControlGoneX(WebSettings var1_1, Object[] var2_2) {
        var3_3 = var1_1.getClass();
        var5_4 = new Class[var2_2.length];
        var6_5 = 0;
        var7_6 = var2_2.length;
        do lbl-1000: // 2 sources:
        {
            if (var6_5 >= var7_6) {
                var8_7 = var3_3.getMethods();
                var9_8 = 0;
lbl10: // 2 sources:
                if (var9_8 < var8_7.length) break;
                return;
            }
            var5_4[var6_5] = var2_2[var6_5].getClass();
            break;
        } while (true);
        {
            catch (Exception var4_13) {
                var4_13.printStackTrace();
                return;
            }
        }
        {
            ++var6_5;
            ** while (true)
        }
        var10_9 = var8_7[var9_8].getName().equals((Object)"setDisplayZoomControls");
        ** if (!var10_9) goto lbl31
lbl-1000: // 1 sources:
        {
            try {
                var12_10 = var8_7[var9_8];
                var13_11 = new Object[]{false};
                var12_10.invoke((Object)var1_1, var13_11);
                return;
            }
            catch (Exception var11_12) {
                var11_12.printStackTrace();
                return;
            }
        }
        {
        }
lbl31: // 1 sources:
        ++var9_8;
        ** GOTO lbl10
    }

    class MyWebChromeClient
    extends WebChromeClient {
        MyWebChromeClient() {
        }

        public boolean onJsAlert(WebView webView, String string2, String string3, JsResult jsResult) {
            jsResult.cancel();
            return true;
        }
    }

}

