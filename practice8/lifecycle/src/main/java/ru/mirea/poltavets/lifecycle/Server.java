package ru.mirea.poltavets.lifecycle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class Server  implements LifecycleObserver {
    private String TAG = "lifecycle";
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void connect() {
        Log.d(TAG,"connect to web-server");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.d(TAG,"create");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        Log.d(TAG,"pause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.d(TAG,"resume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void any() {
        Log.d(TAG,"any");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disconnect() {
        Log.d(TAG,"disconnect");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.d(TAG,"destroy");
    }
}
