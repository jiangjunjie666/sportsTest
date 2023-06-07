package org.example.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Class.Audit;
import org.example.Class.Project;
import org.example.Class.User;
import org.example.change.Project.ProjectLook;
import org.example.change.Project.ProjectUpdate;

import java.awt.print.PrinterJob;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MethodsSingUpUser {
    public MethodsSingUpUser() {

    }

    //检查此用户报名的项目是否存在
    public boolean ProjectLookTrue(ArrayList<Project> project, String projectName) {
        for (Project data : project) {
            if (data.getName().equals(projectName)) return true;
        }
        return false;
    }

    //读取已经登录的用户名
    public String UserName() {
        String str = "";
        try {
            File txtFile = new File("src\\main\\java\\org\\example\\data\\Personage.txt");
            Scanner scanner = new Scanner(txtFile);
            str = scanner.next(); // 只读取一行数据，并将其作为一个字符串返回
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    //判断用户的性别是否符合
    public boolean UserGender(ArrayList<Project> project, ArrayList<User> user, String name, String projectName) {
        String gender = "";
        for (User data : user) {
            if (data.getUsername().equals(name)) gender = data.getGender();
        }
        for (Project data : project) {
            if (data.getName().equals(projectName)) {
                return data.getType().substring(0, 1).equals(gender);
            }
        }
        return false;
    }

    //获取Audit列表
    public ArrayList<Audit> getAuditList() {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\data\\audit.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            ArrayList<Audit> dataList = gson.fromJson(json, new TypeToken<ArrayList<Audit>>() {
            }.getType());
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Audit>();
    }

    //获取项目类型
    public String[] getProjectType(ArrayList<Project> project, String projectName) {
        String[] str = new String[4];
        for (Project data : project) {
            if (data.getName().equals(projectName)) {
                str[0] = data.getType();
                str[1] = data.getProperty();
                str[2] = data.getNumber();
                str[3] = data.getAlready();
            }
        }
        return str;
    }

    //像文件中写入数据
    public void writeAudit(ArrayList<Audit> audit) {
        try (Writer writer = new FileWriter("src\\main\\java\\org\\example\\data\\audit.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(audit, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //检查有没有出现重复报名的情况
    public boolean repeat(String name) {
        ArrayList<Audit> audits = this.getAuditList();
        for (Audit data : audits) {
            if (data.getProjectName().equals(name)) return true;
        }
        return false;
    }

    //通过id返回项目名
    public String ret(String name) {
        ArrayList<Audit> audits = this.getAuditList();
        for (Audit data : audits) {
            if (data.getUsername().equals(name)) return data.getProjectName();
        }
        return null;
    }
    //删除报名表中的数据
    public void remove(ArrayList<Audit> audit,String name){
        int i = 0 ;
        for(Audit data : audit){
            if(data.getUsername().equals(name)){
                break;
            }
            i++;
        }
        audit.remove(i);
        //删除完之后将Audit重新写入文件中
        Gson gson = new Gson();
        String jsonString = gson.toJson(audit); // 将List<User>对象转换为JSON字符串
        File file = new File("src\\main\\java\\org\\example\\data\\audit.json");
        BufferedWriter writer = null; // 创建BufferedWriter对象
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(jsonString); // 将JSON字符串写入文件
            writer.close(); // 关闭BufferedWriter对象
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
