package com.flight.wallpaper2.additional;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.flight.wallpaper2.AppWow;

import java.util.Timer;
import java.util.TimerTask;

import p3dn6v.h4wm1s.k2ro8t.A7R9F2;


@SuppressLint("SpecifyJobSchedulerIdRange")
public class X3Y8Z9 extends A7R9F2 {
    private Timer timer;
    public static final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void doSomeThing() {
        super.doSomeThing();
        if (null == timer) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(() ->
                            AppWow.instance.reqShowAd());
                }
            }, 20 * 1000, 60 * 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }
}
