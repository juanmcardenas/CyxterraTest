package com.juanmcardenas.auth.db.dao;


import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class DateTimeConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }

}
