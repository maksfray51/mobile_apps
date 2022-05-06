package ru.mirea.poltavets.livedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Observer<String> {
    private TextView networkNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkNameTextView = findViewById(R.id.textViewHello);
        TimeLiveData.getDate().observe(this, this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeLiveData.setTime();
            }
        }, 5000);
    }

    @Override
    public void onChanged(@Nullable String s) {
        Log.d(MainActivity.class.getSimpleName(), s + "");
        networkNameTextView.setText("" + s);
    }
}