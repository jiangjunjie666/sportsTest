package org.example.change.User;

import com.google.gson.Gson;
import org.example.Class.User;
import org.example.Manage.ShowUser;
import org.example.methods.MethodsUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserUpdate extends JFrame implements ActionListener {
    private JTextField updateUsernameText;
    private JTextField usernameText;
    private JPasswordField passwordUserText;
    private JPasswordField passwordMountText;
    private JTextField genderText;
    private JButton updateUser;

    public UserUpdate() {
        setTitle("修改用户");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置布局
        JLabel updateUsername = new JLabel("旧账号：");
        JLabel username = new JLabel("新账号：");
        JLabel gender = new JLabel("新性别");
        JLabel passwordUser = new JLabel("新密码：");
        JLabel passwordMount = new JLabel("管理员密码");
        usernameText = new JTextField(15);
        updateUsernameText = new JTextField(15);
        genderText = new JTextField(15);
        passwordUserText = new JPasswordField(15);
        passwordMountText = new JPasswordField(15);
        updateUser = new JButton("修改用户");
        updateUser.addActionListener(this);
        //
        JPanel usernameJp = new JPanel();
        JPanel updateUsernameJp = new JPanel();
        JPanel genderJp = new JPanel();
        JPanel passwordUserJp = new JPanel();
        JPanel passwordMountJp = new JPanel();
        JPanel updateJp = new JPanel();
        updateUsernameJp.setBounds(34, 00, 300, 40);
        usernameJp.setBounds(34, 40, 300, 40);
        genderJp.setBounds(34, 80, 300, 40);
        passwordUserJp.setBounds(34, 120, 300, 40);
        passwordMountJp.setBounds(34, 160, 300, 40);
        updateJp.setBounds(34, 200, 300, 40);
        //
        updateUsernameJp.add(updateUsername);
        updateUsernameJp.add(updateUsernameText);
        usernameJp.add(username);
        usernameJp.add(usernameText);
        genderJp.add(gender);
        genderJp.add(genderText);
        passwordUserJp.add(passwordUser);
        passwordUserJp.add(passwordUserText);
        passwordMountJp.add(passwordMount);
        passwordMountJp.add(passwordMountText);
        updateJp.add(updateUser);
        //
        add(updateUsernameJp);
        add(usernameJp);
        add(genderJp);
        add(passwordUserJp);
        add(passwordMountJp);
        add(updateJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String oldUsername = updateUsernameText.getText();
        String newUsername = usernameText.getText();
        String gender = genderText.getText();
        String passwordUser = new String(passwordUserText.getPassword());
        String passwordMount = new String(passwordMountText.getPassword());
        boolean temp = (!oldUsername.equals("") && !newUsername.equals("") && !gender.equals("") && !passwordUser.equals("") && !passwordMount.equals(""));
        if (temp) {
            //判断用户是否存在
            MethodsUser methodsUser = new MethodsUser();
            ArrayList<User> dataList = methodsUser.getUserList();
            temp = methodsUser.checkUser(dataList, oldUsername);
            if (temp && passwordMount.equals("123456")) {
                int index = methodsUser.indexUser(dataList, oldUsername);//用户位置
                Gson gson = new Gson();
                dataList.get(index).setPassword(passwordUser);
                dataList.get(index).setUsername(newUsername);
                dataList.get(index).setGender(gender);
                String jsonString = gson.toJson(dataList); // 将List<User>对象转换为JSON字符串

                boolean update = methodsUser.updateUser(dataList, jsonString);
                if (update) {
                    JOptionPane.showMessageDialog(this, "修改成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ShowUser();
                } else {
                    JOptionPane.showMessageDialog(this, "修改失败", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "修改失败，用户不存在或者管理员密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "修改失败，请按要求输入用户信息", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
