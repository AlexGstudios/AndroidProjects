    package com.example.toserver;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private String TAG = "db.class";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ip.db";

    //table: static values
    public static final String TABLE_NAME = "ip_table";
    public static final String COLUMN_NAME_VALUE_TITLE = "ip_value";
    public static final String COLUMN_NAME_VALUE_CONTENT = "ip_content";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE "
                + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_VALUE_TITLE + " INTEGER, "
                + COLUMN_NAME_VALUE_CONTENT + " TEXT);";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean add(int title, String content){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_VALUE_TITLE, title);
        cv.put(COLUMN_NAME_VALUE_CONTENT, content);

        long id = db.insert(TABLE_NAME, null, cv);

        if(id == -1){
            Log.d(TAG, "add: error ");
            return false;
        }else{
            Log.d(TAG, "add: complete");
            return true;
        }
    }

    public ArrayList<IpBean> getIp(ArrayList<IpBean> ipBeans){

        SQLiteDatabase db = this.getWritableDatabase();

        String all = "SELECT " + COLUMN_NAME_VALUE_TITLE + ", " + COLUMN_NAME_VALUE_CONTENT + " FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(all, null);

        if(cursor.moveToFirst()){

            while (cursor.moveToNext()){

                String title = cursor.getString(0);

                String content = cursor.getString(1);

                IpBean ipBean = new IpBean();

                ipBean.setIpValue(title);
                ipBean.setIpContent(content);

                ipBeans.add(ipBean);

            }
            cursor.close();
            db.close();
        }

        return ipBeans;
    }

    public ArrayList<DbObject> table(ArrayList<DbObject> arrObj){

        SQLiteDatabase db = this.getWritableDatabase();

        String all = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(all, null);

        if(cursor.moveToFirst()){

            while(cursor.moveToNext()){

                DbObject obj = new DbObject(cursor.getInt(1), cursor.getString(2));

                obj.setID(cursor.getInt(0));

                arrObj.add(obj);
            }

            cursor.close();
            db.close();
        }

        return arrObj;
    }

    public void delete() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_NAME);
    }
}
