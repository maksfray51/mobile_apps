package ru.mirea.poltavets.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class InfoFromWebFragment extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private TextView timeTV;
    private Button btnGet;
    private String host = "time-a.nist.gov"; // или time-a.nist.gov or time-b.nist.gov
    private int port = 13;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_from_web, container, false);
        timeTV = view.findViewById(R.id.timeTV);

        view.findViewById(R.id.getBtn).setOnClickListener(this::updateInfo);
        return view;
    }
    private void updateInfo(View view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String time = "";
        try {
            Socket socket = new Socket(host, port);
            BufferedReader reader = SocketUtils.getReader(socket);
            reader.readLine();
            time = reader.readLine();
            Log.d(TAG, time);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timeTV.setText(time);
    }
}