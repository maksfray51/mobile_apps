package ru.mirea.poltavets.mieraprojectnew.RoomDataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Класс нашей сущности
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int UserId;

    @ColumnInfo(name = "name")
    public String firstName;

    @ColumnInfo(name = "surname")
    public String lastName;
}
