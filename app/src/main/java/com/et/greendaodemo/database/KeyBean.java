package com.et.greendaodemo.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Et on 2018/8/1 16:30
 * email：x313371005@126.com
 */
@Entity
public class KeyBean {
    @Property(nameInDb = "KEYNAME")
    private String keyName;

    @Property(nameInDb = "KEYBALANCE")
    private String keyBalance;

    @Id
    @Property(nameInDb = "KEYID")
    private String keyId;

    public KeyBean(){}

    @Generated(hash = 410225224)
    public KeyBean(String keyName, String keyBalance, String keyId) {
        this.keyName = keyName;
        this.keyBalance = keyBalance;
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyBalance() {
        return keyBalance;
    }

    public void setKeyBalance(String keyBalance) {
        this.keyBalance = keyBalance;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
}
