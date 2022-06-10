package ru.mirea.poltavets.mieraprojectnew;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.net.time.TimeTCPClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class InfoFromNetFragment extends Fragment {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextView timeTV;
    private RequestQueue requestQueue;
    private final String url ="https://timeapi.io/api/Time/current/coordinate?lattitude=55.697980&longitude=37.763324";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_from_net, container, false);
        timeTV = view.findViewById(R.id.timeTV);

        view.findViewById(R.id.getBtn).setOnClickListener(this::updateInfo);
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void updateInfo(View view) {
        requestQueue = Volley.newRequestQueue(requireContext());
        getDateTime(new VolleyCallBack() {
            @Override
            public void onGetDateTime(String date, String time) {
                timeTV.setText(date + "\n" + time);
            }
        });
    }

    // Какой-то индусик грамотно объяснил как сделать парсинг времени
    // https://www.youtube.com/watch?v=d7cTgzy3keM

    public void getDateTime(VolleyCallBack volleyCallBack){
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // date и time это начи объекты в JSON файле, которые хранят
                            // соответственно дату и время (а она хронится по ссылке url)
                            volleyCallBack.onGetDateTime(response.getString("date"),
                                    response.getString("time"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    public interface VolleyCallBack{
        void onGetDateTime(String date, String time);
    }

}
