package ru.mirea.poltavets.mieraprojectnew;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecorderFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private MediaRecorder mediaRecorder;
    private File audioFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recorder, container, false);

        Button startRecordButton = view.findViewById(R.id.button_start_recording);
        Button stopRecordButton = view.findViewById(R.id.button_stop_recording);
        Button playBackStart = view.findViewById(R.id.button_playback_start);
        Button playBackStop = view.findViewById(R.id.button_playback_stop);


        // инициализация объекта MediaRecorder
        mediaRecorder = new MediaRecorder();

        // проверка наличия разрешений на выполнение аудиозаписи и сохранения на карту памяти
        boolean isWork = hasPermissions(requireActivity().getApplicationContext(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }

        // Следим за нажатием на кнопку начала записи
        startRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startRecordButton.setEnabled(false);
                    stopRecordButton.setEnabled(true);
                    stopRecordButton.requestFocus();
                    startRecording();
                } catch (Exception e) {
                    Log.e(TAG, "Caught io exception " + e.getMessage());
                }
            }
        });

        // Следим за нажатием на кнопку конца записи
        stopRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecordButton.setEnabled(true);
                stopRecordButton.setEnabled(false);
                startRecordButton.requestFocus();
                stopRecording();
                processAudioFile();

            }
        });

        // Следим за нажатием на кнопку воспроизведения
        playBackStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().startService(new Intent(getActivity(), PlayerService.class));
            }
        });


        // Следим за нажатием на кнопку остановки воспроизведения
        playBackStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().stopService(new Intent(getActivity(), PlayerService.class));
            }
        });
        return view;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_PERMISSION) {
//            // permission granted
//            isWork = grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
//        }
//    }


    // Начало записи
    private void startRecording() throws IOException {
        // проверка доступности sd - карты
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            // выбор источника звука
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // выбор формата данных
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // выбор кодека
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {

                // Создаём директорию для файла на устройстве
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_audio");
                if (!myDir.exists()) {myDir.mkdirs();}

                // Создаём файл
                String fileName = "audio1.3gp";
                audioFile = new File(myDir, fileName);

                // Чтобы видеть наш новый файл в gallery view
                requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getActivity(), "Recording started!", Toast.LENGTH_SHORT).show();


        }
    }

    // Завершение записи
    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(getActivity(), "You are not recording right now!", Toast.LENGTH_SHORT).show();
        }
    }

    // Процесс записи файла
    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = requireActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);

        // оповещение системы о новом файле
        // https://stackoverflow.com/questions/24072489/java-lang-securityexception-permission-denial-not-allowed-to-send-broadcast-an
        requireActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }
}