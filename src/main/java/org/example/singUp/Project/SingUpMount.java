package org.example.singUp.Project;

import com.google.gson.Gson;
import org.example.Class.Project;
import org.example.Class.User;
import org.example.methods.MethodsProject;
import org.example.methods.MethodsSingUp;
import org.example.methods.MethodsUser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SingUpMount extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //处理报名事件
        String username = usernameText.getText();//账号
        String project = projectText.getText();//账号
        String password = passwordText.getText();//密码
        //数据
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> projectList = methodsProject.getProjectList();//项目数据
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> userList = methodsUser.getUserList();//用户数据
        //1.先判断用户有没有输入信息
        boolean temp = (!username.equals("") && !project.equals("") && !password.equals(""));
        if(temp){
            //2.判断密码有没有对错并判断有没有此用户和项目
            temp = methodsUser.checkUser(userList,username);
            temp = methodsProject.indexProject(projectList,project) == -1 ? false : true;
            if(temp && password.equals("123456")){
                //3.基础安全性检验判断完毕，对用户进行添加项目，并对项目剩余余额进行改变
                MethodsSingUp methodsSingUp = new MethodsSingUp();
                boolean found = methodsSingUp.InspectSingUp(userList,username,project);
                if(found){
                    JOptionPane.showMessageDialog(this, "该用户已经报名了此项目", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                 }
                //查找此项目有没有剩余名额
                found = methodsSingUp.InspectProject(projectList,project);
                if(!found){
                    JOptionPane.showMessageDialog(this, "该项目已经没有了名额", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //进一步判断报名者的性别符不符合要求
                found = methodsSingUp.InspectGender(userList,project,username);
                System.out.println(found);
                if(!found){
                    JOptionPane.showMessageDialog(this, "该用户的性别不符合", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int num = methodsSingUp.InspectNum(userList,username);
                if(num == 0){
                    JOptionPane.showMessageDialog(this, "该用户已经报名了二个项目，不可继续报名", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    Gson gson = new Gson();
                    //所有安全性检验完毕，现在修改用户的报名信息
                    if(num == 2){
                        //在他的第一项处加一个项目
                        int index = methodsUser.indexUser(userList,username);
                        userList.get(index).setPro1(project);
                        String jsonString = gson.toJson(userList);
                        methodsSingUp.updateUser(jsonString);
                        //修改项目的剩余名额
                        methodsSingUp.updateProject(projectList,project);
                    }else{
                        //在他的第二处加一个项目
                        int index = methodsUser.indexUser(userList,username);
                        userList.get(index).setPro2(project);
                        String jsonString = gson.toJson(userList);
                        methodsSingUp.updateUser(jsonString);
                        methodsSingUp.updateProject(projectList,project);
                    }
                    JOptionPane.showMessageDialog(this, "报名成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new SingUpMount();
                }
            }else{
                JOptionPane.showMessageDialog(this, "用户或项目不存在，或密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "请正确填写用户名,项目名和密码", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JTextField usernameText;
    private JTextField projectText;
    private JTextField passwordText;
    private JButton addProject;

    public SingUpMount() {
        setTitle("帮助用户报名");
        setAlwaysOnTop(true);
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置表格
        Object[] columnNames = {"ID", "项目名", "可参加人数", "剩余名额"};
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> projectList = methodsProject.getProjectList();//项目数据
        //摘选表格中的数据
        int len = projectList.toArray().length;
        Object[][] rowData = new Object[len][4];
        //筛选需要的数据
        int i =0;
        for (Project data : projectList){
            rowData[i][0] = data.getId();
            rowData[i][1] = data.getName();
            rowData[i][2] = data.getNumber();
            rowData[i][3] = data.getAlready();
            i++;
        }
        model = new DefaultTableModel(rowData, columnNames);//设置模型
        table = new JTable(model);//引用模型，或table.setModel(model);
        //设置文本居中对齐
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);
        JScrollPane sc = new JScrollPane(table);//设置滚动条
        getContentPane().add(sc, BorderLayout.CENTER);
        //
        final JPanel panel = new JPanel();//内部默认流布局
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("用户名"));
        usernameText = new JTextField(8);
        panel.add(usernameText);
        panel.add(new JLabel("项目名"));
        projectText = new JTextField(10);
        panel.add(projectText);
        panel.add(new JLabel("管理员密码"));
        passwordText = new JTextField(10);
        panel.add(passwordText);
        addProject = new JButton("报名参加");
        panel.add(addProject);
        addProject.addActionListener(this);
        setVisible(true);
    }
}
