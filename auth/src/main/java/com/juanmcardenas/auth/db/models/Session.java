package com.juanmcardenas.auth.db.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Entity(tableName = "sessions")
public class Session {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private int username;

    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "creationDate")
    private long creationDate;

    @ColumnInfo(name = "expirationDate")
    private long expirationDate;

    public Session(int username, String token, long creationDate, long expirationDate) {
        this.username = username;
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public int getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

}
