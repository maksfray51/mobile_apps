package ru.mirea.poltavets.mireaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class activityForStory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_story);

        String name, age;
        name = findViewById(R.id.storyName).toString();
        age = findViewById(R.id.storyAge).toString();

        Intent intent = new Intent(this, HistoryFragment.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        startActivity(intent);
    }
}