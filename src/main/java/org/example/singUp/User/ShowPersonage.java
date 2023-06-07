package org.example.singUp.User;

import org.example.Class.User;
import org.example.methods.MethodsSingUpUser;
import org.example.methods.MethodsUser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ShowPersonage extends JFrame {
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    public ShowPersonage(){
        setTitle("用户个人信息展示");
        setAlwaysOnTop(true);
        setBounds(100, 100, 500, 300);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        Object[] columnNames = {"ID", "gender", "username", "password", "project1", "project2"};
        Object[][] rowData = {};
        rowData = getUser();
        System.out.println(rowData[0][0]);
        if (rowData[0][0] == null) {
            JOptionPane.showMessageDialog(this, "读取用户信息失败", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            model = new DefaultTableModel(rowData, columnNames);//设置模型
            table = new JTable(model);//引用模型，或table.setModel(model);
            //设置文本居中对齐
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, cellRenderer);
            JScrollPane sc = new JScrollPane(table);//设置滚动条
            getContentPane().add(sc, BorderLayout.CENTER);
            final JPanel panel = new JPanel();//内部默认流布局
            getContentPane().add(panel, BorderLayout.SOUTH);
            setVisible(true);
        }
    }
    public Object[][] getUser(){
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> dataList = methodsUser.getUserList();
        Object[][] newUser = new Object[1][6];
        //读取已经登录的用户名
        MethodsSingUpUser methodsSingUpUser =new MethodsSingUpUser();
        String str = methodsSingUpUser.UserName();
        //筛选用户数据
        for(User data : dataList){
            if(data.getUsername().equals(str)){
                newUser[0][0] = data.getId();
                newUser[0][1] = data.getGender();
                newUser[0][2] = data.getUsername();
                newUser[0][3] = data.getPassword();
                newUser[0][4] = data.getPro1();
                newUser[0][5] = data.getPro2();
            }
        }
        return newUser;
    }
}
