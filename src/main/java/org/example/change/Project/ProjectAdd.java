package org.example.change.Project;

import org.example.Class.Project;
import org.example.Class.User;
import org.example.Manage.ShowProject;
import org.example.methods.MethodsProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectAdd extends JFrame implements ActionListener {
    private JTextField nameText;
    private JTextField typeText;
    private JTextField propertyText;
    private JTextField NumberText;
    private JButton addProject;

    public ProjectAdd() {
        setTitle("新增用户");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置布局
        JLabel name = new JLabel("项目名：");
        JLabel type = new JLabel("类型：");
        JLabel property = new JLabel("性质：");
        JLabel number = new JLabel("可报人数：");

        nameText = new JTextField(15);
        typeText = new JTextField("男子或女子", 15);
        propertyText = new JTextField("个人或团体", 15);
        NumberText = new JTextField(15);
        addProject = new JButton("添加项目");
        addProject.addActionListener(this);//按钮事件监听
        //容器
        JPanel nameJp = new JPanel();
        JPanel typeJp = new JPanel();
        JPanel propertyJp = new JPanel();
        JPanel numberJp = new JPanel();
        JPanel btnJp = new JPanel();
        //设置位置
        nameJp.setBounds(25, 00, 300, 40);
        typeJp.setBounds(30, 40, 300, 40);
        propertyJp.setBounds(30, 80, 300, 40);
        numberJp.setBounds(20, 120, 300, 40);
        btnJp.setBounds(40, 160, 300, 40);

        nameJp.add(name);
        nameJp.add(nameText);
        typeJp.add(type);
        typeJp.add(typeText);
        propertyJp.add(property);
        propertyJp.add(propertyText);
        numberJp.add(number);
        numberJp.add(NumberText);

        btnJp.add(addProject);
        add(nameJp);
        add(typeJp);
        add(propertyJp);
        add(numberJp);
        add(btnJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameText.getText();
        String type = typeText.getText();
        String property = propertyText.getText();
        String number = NumberText.getText();
        boolean temp = (!name.equals("") && !type.equals("") && !property.equals("") && !number.equals(""));
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> project = methodsProject.getProjectList();//获取用户列表
        if (temp) {
            temp = methodsProject.readProject(name, project);//检测用户名是否重复
            if (temp) {
                int len = project.toArray().length;
                String id = "";
                if (len == 0 ) id="g001";
                else id = "g00" + (len + 1);//自动创建id
                project.add(new Project(id, name, type, property, number, 1));//添加新用户
                methodsProject.writeProject(name, type, property, number, 1, project);
                JOptionPane.showMessageDialog(this, "项目添加成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new ShowProject();
            } else {
                JOptionPane.showMessageDialog(this, "项目名重复，添加失败", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "注册失败,请正确填写各项信息", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
