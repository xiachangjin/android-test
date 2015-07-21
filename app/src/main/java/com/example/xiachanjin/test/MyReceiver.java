package com.example.xiachanjin.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by xiachanjin on 15-7-21.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("xiacj", "MyReceiver: onReceive thread id=" + Thread.currentThread().getId()
        + " thread name:" + Thread.currentThread().getName());
    }
}