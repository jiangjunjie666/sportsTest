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

public class ProjectUpdate extends JFrame implements ActionListener {
    private JTextField updateNameText;
    private JTextField nameText;
    private JTextField typeText;
    private JTextField propertyText;
    private JTextField NumberText;
    private JPasswordField passwordMountText;
    private JButton updateProject;
    public ProjectUpdate(){
        setTitle("修改项目");
        setAlwaysOnTop(true);
        setLayout(null);
        setSize(350, 400);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //设置布局
        JLabel updateName = new JLabel("旧项目名：");
        JLabel name = new JLabel("新项目名：");
        JLabel type = new JLabel("新项目类别");
        JLabel property = new JLabel("新项目类型：");
        JLabel number = new JLabel("新项目人数：");
        JLabel passwordMount = new JLabel("管理员密码");
        updateNameText = new JTextField(15);
        nameText = new JTextField(15);
        typeText = new JTextField(15);
        propertyText = new JTextField(15);
        NumberText = new JTextField(15);
        passwordMountText = new JPasswordField(15);
        updateProject = new JButton("修改项目");
        updateProject.addActionListener(this);//监听事件
        //
        JPanel nameJp = new JPanel();
        JPanel updateNameJp = new JPanel();
        JPanel typeJp = new JPanel();
        JPanel propertyJp = new JPanel();
        JPanel numberJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel updateJp = new JPanel();
        nameJp.setBounds(34, 00, 300, 40);
        updateNameJp.setBounds(34, 40, 300, 40);
        typeJp.setBounds(34, 80, 300, 40);
        propertyJp.setBounds(34, 120, 300, 40);
        numberJp.setBounds(34, 160, 300, 40);
        passwordJp.setBounds(34, 200, 300, 40);
        updateJp.setBounds(34, 240, 300, 40);
        //
        nameJp.add(updateName);
        nameJp.add(updateNameText);
        updateNameJp.add(name);
        updateNameJp.add(nameText);
        typeJp.add(type);
        typeJp.add(typeText);
        propertyJp.add(property);
        propertyJp.add(propertyText);
        numberJp.add(number);
        numberJp.add(NumberText);
        passwordJp.add(passwordMount);
        passwordJp.add(passwordMountText);
        updateJp.add(updateProject);
        //
        add(nameJp);
        add(updateNameJp);
        add(typeJp);
        add(propertyJp);
        add(numberJp);
        add(passwordJp);
        add(updateJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String oldName = updateNameText.getText();
        String newName = nameText.getText();
        String type = typeText.getText();
        String property = propertyText.getText();
        String number = NumberText.getText();
        String passwordMount = new String(passwordMountText.getPassword());
        boolean temp = (!oldName.equals("") && !number.equals("")  && !newName.equals("") && !type.equals("") && !property.equals("") && !passwordMount.equals(""));
        if (temp) {
            //判断项目是否存在
            MethodsProject methodsProject = new MethodsProject();
            ArrayList<Project> dataList = methodsProject.getProjectList();
            int index = methodsProject.indexProject(dataList, oldName);
            if (index!=-1 && passwordMount.equals("123456")) {
                Gson gson = new Gson();
                dataList.get(index).setName(newName);
                dataList.get(index).setType(type);
                dataList.get(index).setProperty(property);
                dataList.get(index).setNumber(number);
                String jsonString = gson.toJson(dataList); // 将List<User>对象转换为JSON字符串

                boolean update = methodsProject.updateProject(dataList, jsonString);
                if (update) {
                    JOptionPane.showMessageDialog(this, "修改成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ShowProject();
                } else {
                    JOptionPane.showMessageDialog(this, "修改失败", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "修改失败，项目不存在或者管理员密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "修改失败，请按要求输入项目信息", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
