package org.example.Class;

public class User {
    private String id;
    private String username;
    private String password;
    private String gender;
    private int status;//状态为1代表正常。状态为0代表删除
    private String pro1 = "未报名";
    private String pro2 = "未报名";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPro1() {
        return pro1;
    }

    public void setPro1(String pro1) {
        this.pro1 = pro1;
    }

    public String getPro2() {
        return pro2;
    }

    public void setPro2(String pro2) {
        this.pro2 = pro2;
    }


    public User(String id, String username, String password, String gender, int status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.status = status;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
