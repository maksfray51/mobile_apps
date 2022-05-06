package ru.mirea.poltavets.mireaproject.practice8Data;

import androidx.room.PrimaryKey;

public class Subjects {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String lecturersName;
}