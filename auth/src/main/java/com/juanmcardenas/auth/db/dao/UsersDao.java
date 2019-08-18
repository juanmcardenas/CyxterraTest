package com.juanmcardenas.auth.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juanmcardenas.auth.db.models.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface UsersDao {

    @Insert
    Completable insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    Single<User> getUserByUsername(String username);

    @Query("SELECT * FROM users")
    Single<List<User>> getAllUsers();

    @Query("DELETE FROM users WHERE username = :username")
    Completable deleteUserByUsername(String username);


}
