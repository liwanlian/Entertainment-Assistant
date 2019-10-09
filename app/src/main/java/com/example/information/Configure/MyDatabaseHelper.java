package com.example.information.Configure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/9/14.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="sqlite.db";
    private static final int database_version=1;
   private String table_history="sql_regester";
  
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, database_version);
    }

    //调用父类构造器
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + "if not exists "+ table_history + " ("
        + "id integer primary key,"
                        + "songid varchar,"
                        +"durtion int,"
                        + "songname varchar,"
                        + "picuri varchar,"
                        + "songartist varchar,"
                        + "songlink varchar)"
        );//picuri
      
    }

//当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if  exists "+table_history);
 
    this.onCreate(db);
}



}
