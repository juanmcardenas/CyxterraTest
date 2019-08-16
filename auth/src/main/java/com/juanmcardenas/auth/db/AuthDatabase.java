package com.juanmcardenas.auth.db;

import androidx.room.Database;
import androidx.room.TypeConverters;

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
public abstract class AuthDatabase {

    public abstract SessionDao getSessionDao();

    public abstract UsersDao getUsersDao();
}
