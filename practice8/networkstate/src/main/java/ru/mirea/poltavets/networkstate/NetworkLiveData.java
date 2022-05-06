package ru.mirea.poltavets.networkstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

public class NetworkLiveData extends LiveData<String> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private static NetworkLiveData instance;
    static NetworkLiveData getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkLiveData(context.getApplicationContext());
        }
        return instance;
    }
    private NetworkLiveData(Context context) {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.context = context;
    }
    // Отслеживание изменения состояния сети осществляется через BroadcastReceiver. Приизменении состояния генерируется
    // сообщение для всех слушателей в системе.
    private void prepareReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    boolean isConnected = activeNetwork.isConnectedOrConnecting();
                    setValue(Boolean.toString(isConnected));
                }
                else
                    setValue("false");
            }
        };
        context.registerReceiver(broadcastReceiver, filter);
    }
    @Override
    protected void onActive() {
        prepareReceiver(context);
    }
    @Override
    protected void onInactive() {
        context.unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
}
