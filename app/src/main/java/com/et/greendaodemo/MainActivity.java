package com.et.greendaodemo;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.et.greendao.greendaodemo.R;
import com.et.greendaodemo.database.DaoSession;
import com.et.greendaodemo.database.KeyBean;
import com.et.greendaodemo.database.UserBean;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Et";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void insert(View view) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        daoSession.insert(new UserBean("william", "110", "13", "new data"));
        daoSession.insert(new KeyBean("key william", "110", "12"));
        query(view);
    }

    public void delete(View view) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        daoSession.delete(new UserBean("william", "110", "13", "new data"));
        daoSession.delete(new KeyBean("key william", "110", "12"));
        query(view);
    }

    public void update(View view) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        daoSession.update(new UserBean("william111", "110", "12", "new data"));
        daoSession.update(new KeyBean("key william111", "110", "12"));
    }

    public void query(View view) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        UserBean load = daoSession.load(UserBean.class, "12");
        UserBean load3 = daoSession.load(UserBean.class, "13");
        if (load != null) {
            Log.d(TAG, "query id = 12, name: " + load.getUserName() + " updateId : " + load.getUpdateId());
        } else {
            Log.d(TAG, "no query result for id = 12");
        }
        if (load3 != null) {
            Log.d(TAG, "query id = 13, name: " + load3.getUserName() + " updateId : " + load3.getUpdateId());
        } else {
            Log.d(TAG, "no query result for id = 13");
        }


        KeyBean load2 = daoSession.load(KeyBean.class, "12");
        if (load2 != null) {
            Log.d(TAG, "key query id = 12, name: " + load2.getKeyName());
        } else {
            Log.d(TAG, "key no query result");
        }
    }
}
