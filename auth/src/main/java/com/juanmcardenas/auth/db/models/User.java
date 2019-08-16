package com.juanmcardenas.auth.db.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "pass")
    private String pass;

    public User(int id, int uid, String username, String pass) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.pass = pass;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
