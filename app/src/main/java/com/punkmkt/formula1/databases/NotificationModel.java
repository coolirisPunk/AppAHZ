package com.punkmkt.formula1.databases;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by germanpunk on 11/09/15.
 */


@Table(tableName = "Notification",databaseName = AppDatabase.NAME)
public class NotificationModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    private String name;

    @Column
    int active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }




}