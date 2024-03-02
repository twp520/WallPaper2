package com.flight.wallpaper2.additional;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.flight.wallpaper2.R;

import p3dn6v.h4wm1s.k2ro8t.W3mI6o;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class X3G8K9 extends W3mI6o {

    @Override
    public void doSomeThing() {
        showNotification();
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification =
                new NotificationCompat.Builder(this, " ")
                        .setContentTitle("")
                        .setContentText("")
                        .setSmallIcon(p3dn6v.h4wm1s.k2ro8t.R.mipmap.ic_alphe)
                        .setVibrate(new long[0])
                        .setSound(null)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCustomContentView(new RemoteViews(getPackageName(),
                                R.layout.layout_at_one_px))
                        .build();

        //Android 8.0 以上需包添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(" ", " ",
                    NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setShowBadge(false);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setSound(null, null);
            manager.createNotificationChannel(notificationChannel);
        }
        startForeground(100, notification);
    }
}
