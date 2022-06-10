package ru.mirea.poltavets.mieraprojectnew.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Класс для создания нашей базы данных
@Database(entities = {User.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getDPInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "Users_DataBase")
            .allowMainThreadQueries()
            .build();
        }
        return INSTANCE;
    }
}
