package ru.mirea.poltavets.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MyLooper myLooper;
    int age = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLooper = new MyLooper();
        myLooper.start();
    }

    public void onClick(View view){
        long endTime = System.currentTimeMillis() + age * 1000L;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this){
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e){ }
            }
        }



        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("work", "entrepreneur");
        bundle.putInt("age", age);
        msg.setData(bundle);
        if (myLooper != null) {
            myLooper.handler.sendMessage(msg);
        }
    }
}