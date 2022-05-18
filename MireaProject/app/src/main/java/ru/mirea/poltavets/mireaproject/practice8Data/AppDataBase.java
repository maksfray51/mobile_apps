package ru.mirea.poltavets.mireaproject.practice8Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Subjects.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase{
    public abstract SubjectsDao subjectsDao();
}