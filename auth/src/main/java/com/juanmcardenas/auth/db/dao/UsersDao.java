package com.juanmcardenas.auth.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juanmcardenas.auth.db.models.User;

import java.util.List;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface UsersDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE uid = :uid")
    LiveData<User> getUserByUid(String uid);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM users WHERE uid = :uid")
    void deleteUserByUid(String uid);


}
