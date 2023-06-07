package org.example.singUp.User;

import org.example.foundation.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame implements ActionListener {
    private JButton User;//查
    private JButton SignUp;//报名
    private JButton unLogin;//退出登录
    public UserMenu(){
        setTitle("用户菜单界面");
        setAlwaysOnTop(true);
        setLayout(null);//关闭默认布局类型 自己手动设置布局
        setSize(400, 400);
        setDefaultCloseOperation(3);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //新增按钮
        User = new JButton("展示用户个人信息");
        SignUp = new JButton("报名项目");
        unLogin = new JButton("退出登录");
        User.addActionListener(this);//展示用户信息
        SignUp.addActionListener(this);//报名
        unLogin.addActionListener(this);//退出登录
        //新增存放按钮的容器
        JPanel show = new JPanel();

        JPanel sig = new JPanel();
        JPanel UnLogin = new JPanel();
        //设置容器位置
        show.setBounds(40, 40, 300, 40);

        sig.setBounds(40, 80, 300, 40);
        UnLogin.setBounds(40, 120, 300, 40);
        //将按钮添加进容器
        show.add(User);
        sig.add(SignUp);
        UnLogin.add(unLogin);
        //将组添加至父容器
        add(show);
        add(sig);
        add(UnLogin);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == User){
            new ShowPersonage();
        }else if(e.getSource() == SignUp){
            new SingUpUser();
        }else{
            this.dispose();
            new Login();
        }
    }
}
