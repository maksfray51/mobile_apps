package ru.mirea.poltavets.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        mediaPlayer = MediaPlayer.create(this, MusicPlayer.song);
        mediaPlayer.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaPlayer.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}