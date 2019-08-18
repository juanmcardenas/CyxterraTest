package com.juanmcardenas.auth.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juanmcardenas.auth.db.models.Session;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface SessionDao {

    @Insert
    Completable insertSession(Session session);

    @Query("SELECT * FROM sessions WHERE username = :username")
    Single<List<Session>> getSessionsByUsername(String username);

    @Query("SELECT * FROM sessions WHERE username = :username AND creationDate <= :currentDate AND expirationDate > :currentDate ORDER BY id DESC LIMIT 1" )
    Single<Session> getActiveSessionByUid(String username, long currentDate);

    @Query("DELETE FROM sessions WHERE username = :username")
    Completable deleteUserByUid(String username);

}
