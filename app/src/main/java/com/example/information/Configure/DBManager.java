package com.example.information.Configure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/9/14.
 */
//对数据库的数据进行操作的类
public class DBManager {
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;
    private String table_history="sql_regester";
    
    public DBManager(Context context){
        myDatabaseHelper=new MyDatabaseHelper(context);
        db=myDatabaseHelper.getWritableDatabase();
    }
    //插入数据的数据 合适的id
    public  int countid(){
        return db.query(table_history, null, null, null, null, null, null).getCount()+1;
    }
  //添加历史数据
    public void adddata(int id,String songid,int durtion,String songlink,String songname,String songartist,String picuri){
        ContentValues cv=new ContentValues();
        cv.put("id",id);
        cv.put("songid",songid);
        cv.put("durtion",durtion);
        cv.put("songname",songname);
        cv.put("picuri",picuri);
        cv.put("songartist",songartist);
        cv.put("songlink",songlink);
        db.insert(table_history,"id",cv);
    }
//查找歌曲是否有播放过 有的话true 否则 false
    public boolean search_songid(String songid) {
        boolean result = false;

        Cursor c = db.rawQuery("select * from " + table_history, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
           if (c.getString(1).equals(songid))
           {
               result =true;
               break;
           }
        }
        c.close();
        return result;
    }
    //展示播放记录的时候 需要展示歌名和歌手
    public List<Map<String,Object>> getlist_songart(){
        List<Map<String,Object>> list_songart= new ArrayList<Map<String, Object>>();
        Cursor c =db.rawQuery("select * from "+table_history,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("songname",c.getString(3));
            map.put("songartist",c.getString(5));
            list_songart.add(map);
        }
        return  list_songart;
    }
    //返回歌曲的id 数组
    public String[] receiveids(){
        int size=countid()-1;
        String[] songids=new String[size];
        int count=0;
        Cursor c=db.rawQuery("select * from "+table_history,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            songids[count]=c.getString(1);
            count++;
        }
        return  songids;
    }
    //根据songid 找到其对应的id返回
    public  int returnid(String songid){
        int id=0;
        Cursor c=db.rawQuery("select * from "+table_history,null);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getString(1).equals(songid)){
                id=c.getInt(0);
                break;
            }
        }
        return id;
    }
    //根据id 返回播放的链接
    public String returnsonglink(String songid){
        String songlink=null;
        Cursor c=db.rawQuery("select * from "+table_history,null);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getString(1).equals(songid)){
               songlink=c.getString(6);
                break;
            }
        }
        return songlink;
    }
    //根据id 找uri

    public String returnuri(int id){
        String uri=null;
        Cursor c=db.rawQuery("select * from "+table_history,null);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            if (c.getInt(0)==id){
                uri=c.getString(4);
                break;
            }
        }
        return uri;
    }

    public void closedb(){
        db.close();
    }
}
