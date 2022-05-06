package ru.mirea.poltavets.mireaproject.practice8Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectsDao {
    @Query("SELECT * FROM AppDataBase")
    LiveData<List<Subjects>> getAllDisciplines();
    @Insert
    void insert(Subjects disciplines);
    @Update
    void update(Subjects disciplines);
    @Delete
    void delete(Subjects disciplines);
}
