package com.juanmcardenas.auth.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juanmcardenas.auth.db.models.Attempt;

import java.util.List;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface AttemptsDao {

    @Insert
    void insertAttempt(Attempt attempt);

    @Query("SELECT * FROM attempts ORDER BY date DESC LIMIT 100")
    LiveData<List<Attempt>> getAttempts(String uid);

}
