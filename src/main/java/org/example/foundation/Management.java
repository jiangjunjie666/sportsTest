package org.example.foundation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Management extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameText.getText();
        String password = new String(passwordText.getPassword());
        if (username.equals("jjj") && password.equals("123456")) {//管理员只有一个 不能创建
            JOptionPane.showMessageDialog(this, "管理员登录成功", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            //跳转至管理员界面
            new InterfaceMan();
        } else {
            JOptionPane.showMessageDialog(this, "密码或者账号错误", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginBtn;

    public Management() {
        setTitle("管理员登录");
        setAlwaysOnTop(true);
        setLayout(null);//关闭默认布局类型 自己手动设置布局
        setSize(400, 400);
        setDefaultCloseOperation(3);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中

        JLabel username = new JLabel("账号：");
        JLabel password = new JLabel("密码：");
        loginBtn = new JButton("管理员登录");
        loginBtn.addActionListener(this);//管理员登录事件
        usernameText = new JTextField(15);
        passwordText = new JPasswordField(15);
        // 设置字体和背景颜色
        usernameText.setForeground(Color.BLACK);
        passwordText.setForeground(Color.BLACK);
        usernameText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JPanel usernameJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel loginJp = new JPanel();
        //设置容器的位置
        usernameJp.setBounds(30, 40, 300, 50);
        passwordJp.setBounds(30, 80, 300, 50);
        loginJp.setBounds(50, 130, 300, 60);
        usernameJp.add(username);
        usernameJp.add(usernameText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
        loginJp.add(loginBtn);
        //将组件装入GUI
        add(usernameJp);
        add(passwordJp);
        add(loginJp);
        setVisible(true);
    }
}
