package ru.mirea.poltavets.mireaproject;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeSensors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeSensors extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = MainActivity.class.getSimpleName();
    View view = getView();
    TextView magneticText = (TextView) view.findViewById(R.id.textViewMagnetic);
    TextView pressureText = (TextView) view.findViewById(R.id.textViewPressure);
    TextView stationaryText = (TextView) view.findViewById(R.id.textViewStationary);

    public ThreeSensors() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ThreeSensors.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeSensors newInstance(String param1) {
        ThreeSensors fragment = new ThreeSensors();
        Bundle args = new Bundle();
        args.putString(TAG, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Инициализируем 3 датчика
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor stationarySensor = sensorManager.getDefaultSensor(Sensor.TYPE_STATIONARY_DETECT);

        // Теперь нужно зарегестрировать listener'ы
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, stationarySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_player, container, false);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_MAGNETIC_FIELD:
                float valueMagnetic = sensorEvent.values[0];
                magneticText.setText("Магнитное поле: " + valueMagnetic);

            case Sensor.TYPE_PRESSURE:
                float valuePressure = sensorEvent.values[0];
                pressureText.setText("Давление: " + valuePressure);

            case Sensor.TYPE_STATIONARY_DETECT:
                float valueStationary = sensorEvent.values[0];
                stationaryText.setText("Магнитное поле: " + valueStationary);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}