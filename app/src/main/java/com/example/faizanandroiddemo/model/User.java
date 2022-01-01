package com.example.faizanandroiddemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String firstName;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "celcius")
    public String celcius;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCelcius() {
        return celcius;
    }

    public void setCelcius(String celcius) {
        this.celcius = celcius;
    }
}
