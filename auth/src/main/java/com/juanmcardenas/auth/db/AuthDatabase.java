package com.juanmcardenas.auth.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.juanmcardenas.auth.db.dao.AttemptsDao;
import com.juanmcardenas.auth.db.dao.DateTimeConverter;
import com.juanmcardenas.auth.db.dao.SessionDao;
import com.juanmcardenas.auth.db.dao.UsersDao;
import com.juanmcardenas.auth.db.models.Attempt;
import com.juanmcardenas.auth.db.models.Session;
import com.juanmcardenas.auth.db.models.User;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Database(entities = {Session.class, User.class, Attempt.class}, version = 1)
@TypeConverters({DateTimeConverter.class})
public abstract class AuthDatabase extends RoomDatabase {

    private static AuthDatabase INSTANCE = null;

    private AuthDatabase() {
    }

    public static AuthDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AuthDatabase.class, "auth_db")
                    .build();
        }
        return INSTANCE;
    }

    public abstract SessionDao getSessionDao();

    public abstract UsersDao getUsersDao();

    public abstract AttemptsDao getAttemptsDao();
}
