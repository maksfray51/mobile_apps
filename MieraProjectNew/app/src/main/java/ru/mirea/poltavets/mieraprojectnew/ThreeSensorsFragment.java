package ru.mirea.poltavets.mieraprojectnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentThreeSensorsBinding;

public class ThreeSensorsFragment extends Fragment implements SensorEventListener {
    private FragmentThreeSensorsBinding binding;
    private SensorManager sensorManager;
    private Sensor magneticSensor, temperatureSensor, pressureSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentThreeSensorsBinding.inflate(inflater, container, false);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        // Инициализируем 3 датчика
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);



        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Теперь нужно зарегестрировать listener'ы
        // SensorManager.SENSOR_DELAY_NORMAL — частота обновления по умолчанию
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent sensorEvent) {

        TextView magneticText = binding.textViewMagnetic;
        TextView temperatureText = binding.textViewTemperature;
        TextView pressureText = binding.textViewPressure;

        // Обрабатываем магнитное поле
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float valueMagneticX1 = sensorEvent.values[0];
            float valueMagneticY1 = sensorEvent.values[1];
            float valueMagneticY2 = sensorEvent.values[2];
            magneticText.setText("Магнитное поле: "
                    + "\n\t\tось X (поперечная): " + valueMagneticX1
                    + "\n\t\tось Y (продольная): " + valueMagneticY1
                    + "\n\t\tось Y (вертикальная): " + valueMagneticY2);
        }

        // Обрабатываем температуру:
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            float valueTemperature = sensorEvent.values[0];
            temperatureText.setText("Температура в градусах по Цельсию: " + valueTemperature);
        }

       // Обрабатываем бездействие
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
            float valuePressure = sensorEvent.values[0];
            pressureText.setText("Pressure: " + valuePressure);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}