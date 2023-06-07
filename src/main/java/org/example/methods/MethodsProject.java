package org.example.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Class.Project;

import java.io.*;
import java.util.ArrayList;

public class MethodsProject {
    public MethodsProject() {
    }

    //获取所有项目信息
    public ArrayList<Project> getProjectList() {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\data\\project.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            ArrayList<Project> dataList = gson.fromJson(json, new TypeToken<ArrayList<Project>>() {
            }.getType());
            return dataList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //检查是否有此项目
    public boolean readProject(String name, ArrayList<Project> project) {
        for (Project data : project) {
            if (data.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    //遍历项目状态有多少是正常的
    public int statusTrue(ArrayList<Project> project) {
        int len = 0;
        for (Project data : project) {
            if (data.getStatus() != 0) len++;
        }
        return len;
    }
    //将项目信息写入json文件中(注册用户)
    public void writeProject(String name, String type, String property, String number, int status, ArrayList<Project> project) {
        try (Writer writer = new FileWriter("src\\main\\java\\org\\example\\data\\project.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(project, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //查找某个项目所在的位置
    public int indexProject(ArrayList<Project> project, String name) {
        int index = 0;
        for (Project data : project) {
            if (data.getName().equals(name)) return index;
            else index++;
        }
        return -1;//异常未找到用户
    }
    //修改项目数据
    public boolean updateProject(ArrayList<Project> project, String jsonString) {
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
