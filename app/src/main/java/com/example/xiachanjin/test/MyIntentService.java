package com.example.xiachanjin.test;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xiachanjin on 15-7-21.
 */
public class MyIntentService  extends IntentService {
    private final static String TAG = "xiacj";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyIntentService onCreate thread id:" + Thread.currentThread().getId());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "MyIntentService onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyIntentService onStartCommand thread id:" + Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyIntentService onDestroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "MyIntentService onHandleIntent  START thread id:" + Thread.currentThread().getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "MyIntentService onHandleIntent  END thread id:" + Thread.currentThread().getId());
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "MyIntentService onBind");
        return super.onBind(intent);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        Log.d(TAG, "MyIntentService setIntentRedelivery");
    }
}
