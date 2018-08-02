package com.et.greendaodemo.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Et on 2018/8/1 15:12
 * email：x313371005@126.com
 */
@Entity
public class UserBean {

    @Property(nameInDb = "USERNAME")
    private String userName;

    @Property(nameInDb = "USERBALANCE")
    private String userBalance;

    @Id
    @Property(nameInDb = "USERID")
    private String userId;

    @Property(nameInDb = "UPDATEID")
    private String updateId;

    @Generated(hash = 753973164)
    public UserBean(String userName, String userBalance, String userId,
            String updateId) {
        this.userName = userName;
        this.userBalance = userBalance;
        this.userId = userId;
        this.updateId = updateId;
    }

    public UserBean(){
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdateId() {
        return this.updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}
