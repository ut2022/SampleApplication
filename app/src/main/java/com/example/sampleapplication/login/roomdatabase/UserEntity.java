package com.example.sampleapplication.login.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_name")
public class UserEntity  implements Serializable {

//    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="email")
    String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getUserphno() {
        return userphno;
    }

    public void setUserphno(String userphno) {
        this.userphno = userphno;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    @ColumnInfo(name = "username")
    String username;

    @PrimaryKey@NonNull
    @ColumnInfo(name="userphno")
    String userphno;

    @ColumnInfo(name="userpassword")
    String userpassword;

    public UserEntity(String username,String userEmail,String userphno,String userpassword){
        this.username=username;
        this.userEmail=userEmail;
        this.userpassword=userpassword;
        this.userphno=userphno;
    }
}

