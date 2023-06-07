package org.example.change.Project;

import com.google.gson.Gson;
import org.example.Class.Project;
import org.example.Class.User;
import org.example.Manage.ShowProject;
import org.example.Manage.ShowUser;
import org.example.methods.MethodsProject;
import org.example.methods.MethodsUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectDelete extends JFrame implements ActionListener {
    private JTextField nameText;
    private JPasswordField passwordText;
    private JButton deleteProject;

    public ProjectDelete() {
        setTitle("删除用户");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置布局
        JLabel name = new JLabel("项目名：");
        JLabel password = new JLabel("管理员密码");
        nameText = new JTextField(15);
        passwordText = new JPasswordField(15);
        deleteProject = new JButton("删除项目");
        deleteProject.addActionListener(this);//监听按钮事件
        //
        JPanel nameJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel deleteJp = new JPanel();
        nameJp.setBounds(30, 00, 300, 40);
        passwordJp.setBounds(20, 40, 300, 40);
        deleteJp.setBounds(40, 80, 300, 40);
        //
        nameJp.add(name);
        nameJp.add(nameText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
        deleteJp.add(deleteProject);
        //
        add(nameJp);
        add(passwordJp);
        add(deleteJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //删除项目
        String name = nameText.getText();
        String password = new String(passwordText.getPassword());
        boolean temp = (!name.equals("") && !password.equals(""));
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> dataList = methodsProject.getProjectList();
        if (temp) {
            //1.检查有没有此用户
            int index = methodsProject.indexProject(dataList, name);//用户位置
            if (index != -1 && password.equals("123456")) {
                Gson gson = new Gson();
                dataList.get(index).setStatus(0);
                String jsonString = gson.toJson(dataList); // 将List<User>对象转换为JSON字符串
                boolean delete = methodsProject.updateProject(dataList, jsonString);
                if (delete) {
                    JOptionPane.showMessageDialog(this, "删除成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ShowProject();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "删除失败，该项目不存在或者密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "删除失败，请按要求输入项目名和管理员密码", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
