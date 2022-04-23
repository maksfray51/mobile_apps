package ru.mirea.poltavets.practice6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private SharedPreferences preferences;
    final String SAVED_TEXT = "saved_text";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        preferences = getPreferences(MODE_PRIVATE);
    }
    public void onSaveText(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        // Сохранение значения по ключу SAVED_TEXT
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }
    public void onLoadText(View view) {
        // Загрузка значения по ключу SAVED_TEXT
        String text = preferences.getString(SAVED_TEXT, "Empty");
        textView.setText(text);
    }
}