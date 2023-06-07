package org.example.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Class.User;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class MethodsUser {
    public MethodsUser() {

    }

    //抽离公共方法放置此处
    //获取用户的所有信息
    public ArrayList<User> getUserList() {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\data\\user.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            ArrayList<User> dataList = gson.fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    //检查有没有用户名出现重复的情况
    public boolean readUser(String username, ArrayList<User> user) {
        for (User data : user) {
            if (data.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    //将用户信息写入json文件中(注册用户)
    public void writeUser(String username, String password, String gender, int status, ArrayList<User> user) {
        try (Writer writer = new FileWriter("src\\main\\java\\org\\example\\data\\user.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(user, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //遍历用户状态有多少是正常的
    public int statusTrue(ArrayList<User> user) {
        int len = 0;
        for (User data : user) {
            if (data.getStatus() != 0) len++;
        }
        return len;
    }

    //查找某个用户所在的位置
    public int indexUser(ArrayList<User> user, String username) {
        int index = 0;
        for (User data : user) {
            if (data.getUsername().equals(username)) return index;
            else index++;
        }
        return -1;//异常未找到用户
    }

    //检查有没有此用户
    public boolean checkUser(ArrayList<User> user, String username) {
        for (User data : user) {
            if (data.getUsername().equals(username)) return true;//存在
        }
        return false;//不存在
    }

    //修改用户数据
    public boolean updateUser(ArrayList<User> user, String jsonString) {
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
}
