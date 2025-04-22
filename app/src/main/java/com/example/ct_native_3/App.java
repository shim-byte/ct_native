package com.example.ct_native_3;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

public class App extends Application {

    private static CleverTapAPI cleverTapDefaultInstance;

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
//        initializeCleverTap();
    }

//    private void initializeCleverTap() {
//        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
//        if (cleverTapDefaultInstance != null) {
//            cleverTapDefaultInstance.enablePersonalization();
//            cleverTapDefaultInstance.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
//        }
//    }
//
//    public static CleverTapAPI getCleverTapInstance() {
//        return cleverTapDefaultInstance;
//    }
}
