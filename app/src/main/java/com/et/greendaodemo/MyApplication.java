package com.et.greendaodemo;

import android.app.Application;

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
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication getInstance () {
        return instance;
    }
}
