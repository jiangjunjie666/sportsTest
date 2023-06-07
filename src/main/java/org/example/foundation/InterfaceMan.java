package org.example.foundation;

import org.example.Manage.AuditUser;
import org.example.Manage.ShowProject;
import org.example.Manage.ShowUser;
import org.example.singUp.Project.SingUpMount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//管理员表单界面
public class InterfaceMan extends JFrame implements ActionListener {
    private JButton User;//查
    private JButton Project;
    private JButton SignUp;//报名
    private JButton audit;//审核报名
    private JButton unLogin;//退出登录

    public InterfaceMan() {
        setTitle("管理员菜单界面");
        setAlwaysOnTop(true);
        setLayout(null);//关闭默认布局类型 自己手动设置布局
        setSize(400, 400);
        setDefaultCloseOperation(3);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中

        //新增按钮
        User = new JButton("展示用户信息");
        Project = new JButton("展示运动会项目");
        SignUp = new JButton("帮助学生报名");
        audit = new JButton("审核报名信息");
        unLogin = new JButton("退出管理员登录");

        User.addActionListener(this);//展示用户信息
        Project.addActionListener(this);//展示运动会项目信息
        SignUp.addActionListener(this);//报名
        audit.addActionListener(this);//审核
        unLogin.addActionListener(this);//退出登录
        //新增存放按钮的容器
        JPanel show = new JPanel();
        JPanel pro = new JPanel();
        JPanel sig = new JPanel();
        JPanel aud = new JPanel();
        JPanel UnLogin = new JPanel();
        //设置容器位置
        show.setBounds(40, 40, 300, 40);
        pro.setBounds(40, 80, 300, 40);
        sig.setBounds(40, 120, 300, 40);
        aud.setBounds(40, 160, 300, 40);
        UnLogin.setBounds(40, 200, 300, 40);
        //将按钮添加进容器
        show.add(User);
        pro.add(Project);
        sig.add(SignUp);
        aud.add(audit);
        UnLogin.add(unLogin);
        //将组添加至父容器
        add(show);
        add(pro);
        add(sig);
        add(aud);
        add(UnLogin);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == User) {
            new ShowUser();
        } else if (e.getSource() == Project) {
            new ShowProject();
        } else if (e.getSource() == SignUp) {
            new SingUpMount();
        } else if (e.getSource() == audit) {
            new AuditUser();
        } else {
            this.dispose();
            new Login();
        }
    }
}
