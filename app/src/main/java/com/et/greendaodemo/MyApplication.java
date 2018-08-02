package com.et.greendaodemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.et.greendaodemo.database.DaoMaster;
import com.et.greendaodemo.database.DaoSession;
import com.et.greendaodemo.database.MySQLiteOpenHelper;

/**
 * Created by Et on 2018/8/1 15:32
 * email：x313371005@126.com
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "key_db",null);
        daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();

//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        DaoMaster.DevOpenHelper key_db = new DaoMaster.DevOpenHelper(this, "key_db", null);
//        SQLiteDatabase writableDatabase = key_db.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        DaoMaster daoMaster = new DaoMaster(writableDatabase);
//        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication getInstance () {
        return instance;
    }
}
