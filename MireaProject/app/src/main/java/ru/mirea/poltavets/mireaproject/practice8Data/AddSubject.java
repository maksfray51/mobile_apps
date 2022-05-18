package ru.mirea.poltavets.mireaproject.practice8Data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.poltavets.mireaproject.MainActivity;
import ru.mirea.poltavets.mireaproject.R;

public class AddSubject extends AppCompatActivity {
    private EditText subjectName;
    private EditText subjectLectName;
    private Button button;

    private AppDataBase db;
    private SubjectsDao subjectsDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects_view);

        db = App.getInstance().getDatabase();
        subjectsDao = db.subjectsDao();

        subjectName = findViewById(R.id.editSubjectName);
        subjectLectName = findViewById(R.id.editLectorName);

        button = findViewById(R.id.btnSaveSubject);
        button.setOnClickListener(this::saveBtn);
    }

    public void saveBtn(View view) {

        Subjects subjects = new Subjects();
        subjects.name = subjectName.getText().toString();
        subjects.lecturersName = subjectLectName.getText().toString();

        subjectsDao.insert(subjects);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
