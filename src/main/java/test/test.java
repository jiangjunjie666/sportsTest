package test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IDEA\\javaTest\\classTest\\SchoolSports\\src\\main\\java\\test\\data.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            ArrayList<Data> dataList = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
            }.getType());
            for (Data data : dataList) {
                System.out.println(data.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Data {
    private int id;
    private String name;
    private int age;

    public void setId(int id) {
        this.id = id;
    }

    public Data() {
    }

    public Data(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Data [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}