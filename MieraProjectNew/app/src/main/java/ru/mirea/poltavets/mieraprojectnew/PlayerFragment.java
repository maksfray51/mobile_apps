package ru.mirea.poltavets.mieraprojectnew;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ru.mirea.poltavets.mieraprojectnew.PlayerService;

public class PlayerFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Button playBtn;
    private Button stopBtn;


    public PlayerFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        playBtn = view.findViewById(R.id.player_play_button);
        stopBtn = view.findViewById(R.id.player_stop_button);

        mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.breathing);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopPlay();
            }
        });


        // Вешаем обработчик событий на кнопку play
        playBtn.setOnClickListener(this::onClickPlayMusic);

        // Вешаем обработчик событий на кнопку pause
        stopBtn.setOnClickListener(this::onClickStopMusic);

        return view;
    }

    private void onClickPlayMusic(View view) {
        mediaPlayer.start();
        playBtn.setEnabled(false);
        stopBtn.setEnabled(true);

//        PlayerService playerService = new PlayerService();
//        playerService.startService(new Intent(getActivity(), ru.mirea.poltavets.mieraprojectnew.PlayerService.class));
    }

    private void onClickStopMusic(View view){
        stopPlay();
//        requireActivity().stopService(new Intent(getActivity(), ru.mirea.poltavets.mieraprojectnew.PlayerService.class));
    }

    private void stopPlay(){
        mediaPlayer.stop();
        stopBtn.setEnabled(false);
        playBtn.setEnabled(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            stopPlay();
        }
    }
}