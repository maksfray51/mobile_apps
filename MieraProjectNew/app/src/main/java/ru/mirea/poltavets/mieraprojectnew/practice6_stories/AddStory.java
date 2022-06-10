package ru.mirea.poltavets.mieraprojectnew.practice6_stories;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.poltavets.mieraprojectnew.R;

public class AddStory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_story);

        String name, age;
        name = findViewById(R.id.storyName).toString();
        age = findViewById(R.id.storyAge).toString();

        Intent intent = new Intent(this, HistoryFragment.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        startActivity(intent);
    }
}
