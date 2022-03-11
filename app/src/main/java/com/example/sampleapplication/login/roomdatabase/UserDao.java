package com.example.sampleapplication.login.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.sampleapplication.login.model.LoginResults;
import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity userEntity);

    @Query("SELECT DISTINCT * FROM table_name")
    LiveData<List<UserEntity>> getValues();

    @Query("DELETE FROM table_name")
    void deleteAll();

    @Query("SELECT COUNT(userphno) FROM table_name")
    LiveData<Integer> getCount();
}
