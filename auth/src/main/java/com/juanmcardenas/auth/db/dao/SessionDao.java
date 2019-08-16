package com.juanmcardenas.auth.db.dao;

import android.se.omapi.Session;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface SessionDao {

    @Insert
    void insertSession(Session session);

    @Query("SELECT * FROM sessions WHERE uid = :uid")
    LiveData<List<Session>> getSessionsByUid(String uid);

    @Query("SELECT * FROM sessions WHERE uid = :uid AND creationDate <= :currentDate AND expirationDate > :currentDate ORDER BY id DESC LIMIT 1" )
    LiveData<Session> getActiveSessionByUid(String uid, long currentDate);

    @Query("DELETE FROM sessions WHERE uid = :uid")
    void deleteUserByUid(String uid);

}
