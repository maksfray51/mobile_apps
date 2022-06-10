package ru.mirea.poltavets.mieraprojectnew;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.AutofillService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ru.mirea.poltavets.mieraprojectnew.RoomDataBase.AppDataBase;
import ru.mirea.poltavets.mieraprojectnew.RoomDataBase.User;
import ru.mirea.poltavets.mieraprojectnew.databinding.ActivityAddNewUserDbactivityBinding;
import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentDataBaseBinding;

public class AddNewUserDBActivity extends AppCompatActivity {
    private static final String TAG = AddNewUserDBActivity.class.getSimpleName();
    private ActivityAddNewUserDbactivityBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNewUserDbactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveNewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Клик на кнопку " + binding.saveNewUserBtn.getText().toString()
                        +" произошёл сто пудово");

                // Сохраняем нового пользователя
                saveNewUser(binding.userName.getText().toString(),
                        binding.userSurname.getText().toString(),
                        getApplicationContext());

                Toast.makeText(getApplicationContext(),
                        "You've saved user: " + binding.userName.getText().toString()
                                + binding.userSurname.getText().toString(),
                        Toast.LENGTH_LONG).show();

                Log.i(TAG, "Юзер с именем: " + binding.userName.getText().toString()
                        + "\nИ фамилией: " + binding.userSurname.getText().toString()
                        + " сохранился");

            }
        });
    }

    private void saveNewUser(String name, String surname, Context context){
        AppDataBase db = AppDataBase.getDPInstance(getApplicationContext());

        // Смотрим размер базы данных до сохранения
        List<User> usersList = db.userDao().getAllUsers();
        int previousUsersListSize = usersList.size();

        Log.i("UsersList Size = ", String.valueOf(previousUsersListSize));

        User user = new User();
        user.firstName = name;
        user.lastName = surname;
        db.userDao().insertUser(user);


        finish();

        // Смотрим размер БД после сохранения
        usersList = db.userDao().getAllUsers();
        int newUsersListSize = usersList.size();
        Log.i("UsersList Size = ", String.valueOf(newUsersListSize));

        if ((previousUsersListSize < newUsersListSize)
                && (usersList.get(newUsersListSize - 1).firstName.equals(name))
                && (usersList.get(newUsersListSize - 1).lastName.equals(surname))){
            Log.i("Save Succeed", "Юзер сохранён. Его данные:\nИмя: "
                + usersList.get(usersList.size()-1).firstName
                + "\nФамилия:" + usersList.get(usersList.size()-1).lastName);

            Toast.makeText(getApplicationContext(),
                    "You've saved user: " + name + surname,
                    Toast.LENGTH_LONG).show();
        } else {
            Log.e("Save Unsuccessful", "Юзер не сохранился");

            Toast.makeText(getApplicationContext(),
                    "ERROR. Unsuccessful " + name + surname,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}