package ru.mirea.poltavets.mieraprojectnew.RoomDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Класс для работы с БД
@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Insert
    void insertUser(User...users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User")
    void deleteAll();
}
