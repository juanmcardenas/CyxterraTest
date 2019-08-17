package com.juanmcardenas.auth.db.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
@Entity(tableName = "attempts")
public class Attempt {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "result")
    private String result;

    public Attempt(long date, String result) {
        this.date = date;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

}
