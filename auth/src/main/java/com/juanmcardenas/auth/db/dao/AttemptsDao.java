package com.juanmcardenas.auth.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juanmcardenas.auth.db.models.Attempt;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Dao
public interface AttemptsDao {

    @Insert
    Completable insertAttempt(Attempt attempt);

    @Query("SELECT * FROM attempts ORDER BY date DESC LIMIT 100")
    Single<List<Attempt>> getAttempts();

}
