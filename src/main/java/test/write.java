package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class write {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\IDEA\\javaTest\\classTest\\SchoolSports\\src\\main\\java\\test\\data.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<Data>>() {
            }.getType();
            List<Data> dataList = gson.fromJson(json, type);
            System.out.println(dataList);
            dataList.add(new Data(4, "Alice", 27)); // 添加一条新数据
            // 更新 id 为 2 的数据
            for (Data data : dataList) {
                if (data.getId() == 2) {
                    data.setName("Lucy");
                    data.setAge(35);
                }
            }
            writeToJsonFile(dataList, "D:\\IDEA\\javaTest\\classTest\\SchoolSports\\src\\main\\java\\test\\data.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToJsonFile(List<Data> dataList, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
