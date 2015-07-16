package com.example.xiachanjin.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by xiachanjin on 15-7-16.
 */
public class MyService extends Service {
    public static final String TAG = "xiacj MyService";
    public MyBinder binder = new MyBinder();

    class MyBinder extends Binder {
        public void onMyBinderExec() {
            Log.d(TAG, "onMyBinderExec");
        }
    };

    private  IMyAidlInterface.Stub mBinder= new IMyAidlInterface.Stub() {
        @Override
        public String toUpperCase(String str) throws RemoteException {
            if(str != null) {
                return str.toUpperCase();
            }

            return null;
        }

        @Override
        public int plus(int a, int b) throws RemoteException {
            return a+b;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: thread id=" + Thread.currentThread().getId() + "; process id="
                + android.os.Process.myPid());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onbind");
        //return binder;
        return mBinder;
    }
}
