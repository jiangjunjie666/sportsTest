package org.example.foundation;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.Class.User;
import org.example.foundation.Login;
import org.example.methods.MethodsUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Register extends JFrame implements ActionListener {

    private JTextField usernameText;
    private JTextField genderText;
    private JPasswordField passwordText;
    private JPasswordField passwordTextTrue;
    private JButton registerBtn;

    public Register() {
        setLayout(null);
        setTitle("注册");
        setSize(400, 400);
        setAlwaysOnTop(true);//设置界面一直处于最上层
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        //组件
        JLabel username = new JLabel("账号：");
        JLabel gender = new JLabel("性别");
        JLabel password = new JLabel("密码：");
        JLabel passwordTrue = new JLabel("确认密码：");
        registerBtn = new JButton("注册");
        registerBtn.addActionListener(this);
        usernameText = new JTextField(15);
        genderText = new JTextField(15);
        passwordText = new JPasswordField(15);
        passwordTextTrue = new JPasswordField(15);
        // 设置字体和背景颜色
        usernameText.setForeground(Color.BLACK);
        passwordText.setForeground(Color.BLACK);
        passwordTextTrue.setForeground(Color.BLACK);
        genderText.setForeground(Color.BLACK);
        usernameText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        genderText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordTextTrue.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //创建装组件的容器
        JPanel usernameJp = new JPanel();
        JPanel genderJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel passwordTrueJp = new JPanel();
        JPanel registerJp = new JPanel();
        //设置容器的位置
        usernameJp.setBounds(30, 40, 300, 40);
        genderJp.setBounds(30, 80, 300, 40);
        passwordJp.setBounds(30, 120, 300, 40);
        passwordTrueJp.setBounds(18, 160, 300, 40);
        registerJp.setBounds(60, 190, 300, 50);

        usernameJp.add(username);
        usernameJp.add(usernameText);
        genderJp.add(gender);
        genderJp.add(genderText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
        passwordTrueJp.add(passwordTrue);
        passwordTrueJp.add(passwordTextTrue);
        registerJp.add(registerBtn);
        //将组件装入GUI
        add(usernameJp);
        add(genderJp);
        add(passwordJp);
        add(passwordTrueJp);
        add(registerJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameText.getText();
        String gender = genderText.getText();
        String password = new String(passwordText.getPassword());
        String passwordTrue = new String(passwordTextTrue.getPassword());
        MethodsUser methodsUser = new MethodsUser();

        ArrayList<User> user = methodsUser.getUserList();//获取用户列表
        boolean found = false;
        found = (!username.equals("") && !gender.equals("") && !password.equals("") && !passwordTrue.equals("") && password.equals(passwordTrue));
        if (found) {
            boolean temp = methodsUser.readUser(username, user);//检测用户名是否重复
            if (temp) {
                String id = "";
                int len = user.toArray().length;
                if(len == 0) id="001";
                else id = "00" + ( Integer.parseInt(user.get(len-1).getId()) + 1);//自动创建id
                user.add(new User(id, username, password, gender,1));//添加新用户
                methodsUser.writeUser(username, password, gender,1, user);//将新用户的数据写入json表中
                JOptionPane.showMessageDialog(this, "注册成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Login();
            } else {
                JOptionPane.showMessageDialog(this, "用户名重复，注册失败", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "注册失败,请正确填写用户名和密码", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
