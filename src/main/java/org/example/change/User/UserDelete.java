package org.example.change.User;

import com.google.gson.Gson;
import org.example.Class.User;
import org.example.Manage.ShowUser;
import org.example.methods.MethodsUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserDelete extends JFrame implements ActionListener {
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton deleteUser;

    public UserDelete() {
        setTitle("删除用户");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中

        //设置布局
        JLabel username = new JLabel("账号：");
        JLabel password = new JLabel("管理员密码");
        usernameText = new JTextField(15);
        passwordText = new JPasswordField(15);
        deleteUser = new JButton("删除用户");
        deleteUser.addActionListener(this);//监听按钮事件
        //
        JPanel usernameJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel deleteJp = new JPanel();
        usernameJp.setBounds(34, 00, 300, 40);
        passwordJp.setBounds(20, 40, 300, 40);
        deleteJp.setBounds(40, 80, 300, 40);
        //
        usernameJp.add(username);
        usernameJp.add(usernameText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
        deleteJp.add(deleteUser);
        //
        add(usernameJp);
        add(passwordJp);
        add(deleteJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //删除用户
        String username = usernameText.getText();
        String password = new String(passwordText.getPassword());
        boolean temp = (!username.equals("") && !password.equals(""));
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> dataList = methodsUser.getUserList();
        if (temp) {
            //1.检查有没有此用户
            temp = methodsUser.checkUser(dataList, username);
            if (temp && password.equals("123456")) {
                int index = methodsUser.indexUser(dataList, username);//用户位置
                Gson gson = new Gson();
                dataList.get(index).setStatus(0);
                String jsonString = gson.toJson(dataList); // 将List<User>对象转换为JSON字符串
                boolean delete = methodsUser.updateUser(dataList, jsonString);
                if (delete) {
                    JOptionPane.showMessageDialog(this, "删除成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ShowUser();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "删除失败，管理员密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "删除失败，请按要求输入用户账号和密码", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
