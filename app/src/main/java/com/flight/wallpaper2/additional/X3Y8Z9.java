package com.flight.wallpaper2.additional;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;

import com.flight.wallpaper2.AppWow;

import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("SpecifyJobSchedulerIdRange")
public class X3Y8Z9 extends X3G8K9 {
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
            }, 10*1000, 40 * 1000);
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
