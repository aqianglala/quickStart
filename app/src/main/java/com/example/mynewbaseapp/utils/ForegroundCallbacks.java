package com.example.mynewbaseapp.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zy1584 on 2017-6-15.
 */

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final long CHECK_DELAY = 500;
    public static final String TAG = ForegroundCallbacks.class.getName();

    public interface Listener {
        public void onBecameForeground();

        public void onBecameBackground();
    }

    public interface AppCloseListener{
        public void onClose();
    }

    private static ForegroundCallbacks instance;
    private boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private ArrayList<AppCloseListener> closeListeners = new ArrayList<>();
    private Runnable check;

    public static ForegroundCallbacks init(Application application) {
        if (instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Application application) {
        if (instance == null) {
            init(application);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Context ctx) {
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application) appCtx);
            }
            throw new IllegalStateException(
                    "Foreground is not initialised and " +
                            "cannot obtain the Application object");
        }
        return instance;
    }

    public static ForegroundCallbacks get() {
        if (instance == null) {
            throw new IllegalStateException(
                    "Foreground is not initialised - invoke " +
                            "at least once with parameterised init/get");
        }
        return instance;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void addAppCloseListener(AppCloseListener listener){
        closeListeners.add(listener);
    }
    public void removeAppCloseListener(AppCloseListener listener){
        closeListeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;
        if (check != null)
            handler.removeCallbacks(check);
        if (wasBackground) {
            Logger.t(TAG).e("went foreground");
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    Logger.t(TAG).e("Listener threw exception!: " + exc);
                }
            }
        } else {
            Logger.t(TAG).e("still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        if (check != null)
            handler.removeCallbacks(check);
        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    Logger.t(TAG).e("went background");
                    for (Listener l : listeners) {
                        try {
                            l.onBecameBackground();
                        } catch (Exception exc) {
                            Logger.t(TAG).e("Listener threw exception!: " + exc);
                        }
                    }
                } else {
                    Logger.t(TAG).e("still foreground");
                }
            }
        }, CHECK_DELAY);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityCollector.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityCollector.removeActivity(activity);
        if (ActivityCollector.size() == 0){
            for (AppCloseListener l : closeListeners) {
                l.onClose();
            }
        }
    }
}
