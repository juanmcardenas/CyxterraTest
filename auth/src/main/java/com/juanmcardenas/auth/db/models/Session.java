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

    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "creationDate")
    private long creationDate;

    @ColumnInfo(name = "expirationDate")
    private long expirationDate;

    public Session(int uid, String token, long creationDate, long expirationDate) {
        this.uid = uid;
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }
}
