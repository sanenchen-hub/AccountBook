package com.sanenchen.UsersManager.tools;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * @author sanenchen
 * @version v1.0
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*创建数据库*/
        String mainSQL = "create table PassWordBook (" +
                        "id integer primary key autoincrement," +
                        "title text," +
                        "user text," +
                        "other text," +
                        "website text," +
                        "createTime text," +
                        "lookTime text," +
                        "checkLove int)";
        db.execSQL(mainSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
