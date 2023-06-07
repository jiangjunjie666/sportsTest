package org.example.change.User;

import org.example.Class.User;
import org.example.Manage.ShowUser;
import org.example.foundation.InterfaceMan;
import org.example.methods.MethodsUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserAdd extends JFrame implements ActionListener {
    private JTextField usernameText;
    private JTextField passwordText;
    private JTextField genderText;
    private JButton addUser;

    public UserAdd() {
        setTitle("新增用户");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置布局
        JLabel username = new JLabel("账号：");
        JLabel gender = new JLabel("性别：");
        JLabel password = new JLabel("密码：");

        usernameText = new JTextField(15);
        genderText = new JTextField(15);
        passwordText = new JTextField(15);

        addUser = new JButton("添加用户");
        addUser.addActionListener(this);//监听按钮事件
        //容器
        JPanel usernameJp = new JPanel();
        JPanel genderJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel addBtnJp = new JPanel();
        //设置位置
        usernameJp.setBounds(25, 00, 300, 40);
        genderJp.setBounds(25, 40, 300, 40);
        passwordJp.setBounds(25, 80, 300, 40);
        addBtnJp.setBounds(40, 120, 300, 40);
        //
        usernameJp.add(username);
        usernameJp.add(usernameText);
        genderJp.add(gender);
        genderJp.add(genderText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
//        passwordJp.add(addUser);
        addBtnJp.add(addUser);
        //
        add(usernameJp);
        add(genderJp);
        add(passwordJp);
        add(addBtnJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameText.getText();
        String gender = genderText.getText();
        String password = passwordText.getText();
        boolean temp = (!username.equals("") && !password.equals("") && !gender.equals(""));
        MethodsUser methodsUser = new MethodsUser();//方法构造类
        ArrayList<User> user = methodsUser.getUserList();//获取用户列表
        if (temp) {
            temp = methodsUser.readUser(username, user);//检测用户名是否重复
            if (temp) {
                int len = user.toArray().length;
                String id = "";
                if (len == 0) id = "001";
                else id = "00" + (Integer.parseInt(user.get(len - 1).getId()) + 1);//自动创建id
                user.add(new User(id, username, password, gender, 1));//添加新用户
                methodsUser.writeUser(username, password, gender, 1, user);//将新用户的数据写入json表中
                JOptionPane.showMessageDialog(this, "注册成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new ShowUser();
            } else {
                JOptionPane.showMessageDialog(this, "用户名重复，添加失败", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "注册失败,请正确填写用户名,性别和密码", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
