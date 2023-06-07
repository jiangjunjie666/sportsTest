package org.example.methods;

import com.google.gson.Gson;
import org.example.Class.Project;
import org.example.Class.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MethodsSingUp {
    public MethodsSingUp() {

    }

    //检查用户有没有报名此项目
    public boolean InspectSingUp(ArrayList<User> user, String username, String projectName) {
        for (User data : user) {
            //查找到此用户查看有没有报名此项目
            if ((data.getPro1().equals(projectName) && data.getUsername().equals(username)) || (data.getPro2().equals(projectName) && data.getUsername().equals(username))) {
                return true;
            }
        }
        return false;
    }

    //查找此项目有没有剩余名额
    public boolean InspectProject(ArrayList<Project> project, String projectName) {
        for (Project data : project) {
            if (data.getName().equals(projectName) && data.getAlready().equals("0")) {
                return false;
            }
        }
        return true;
    }

    //判断报名者的性别符不符合要求
    public boolean InspectGender(ArrayList<User> user, String projectName, String username) {
        for (User data : user) {
            if (data.getUsername().equals(username) && data.getGender().equals(projectName.substring(0, 1))) {
                //截取项目名首字对比gender判断性别
                return true;
            }
        }
        return false;
    }

    //判断此用户已经报名了几个项目，每个人最多报名二个项目
    public int InspectNum(ArrayList<User> user, String username) {
        int num = 0;
        for (User data : user) {
            if (data.getUsername().equals(username) && data.getPro1().equals("未报名")) {
                num++;
            }
            if (data.getUsername().equals(username) && data.getPro2().equals("未报名")) {
                num++;
            }
        }
        return num;
    }

    //修改用户数据，给用户添加报名项目
    public boolean updateUser(String jsonString) {
        File file = new File("src\\main\\java\\org\\example\\data\\user.json");
        BufferedWriter writer = null; // 创建BufferedWriter对象
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonString); // 将JSON字符串写入文件
            writer.close(); // 关闭BufferedWriter对象
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    //修改项目数据，更新项目剩余名额
    public boolean updateProject(ArrayList<Project> project, String projectName) {
        for (Project data : project) {
            if (data.getName().equals(projectName)) {//匹配对应的项目名
                data.setAlready(Integer.toString(Integer.parseInt(data.getAlready()) - 1));//更新剩余的项目余额
            }
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(project); // 将List<User>对象转换为JSON字符串
        File file = new File("src\\main\\java\\org\\example\\data\\project.json");
        BufferedWriter writer = null; // 创建BufferedWriter对象
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonString); // 将JSON字符串写入文件
            writer.close(); // 关闭BufferedWriter对象
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}

