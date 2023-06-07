package org.example.change.Project;

import com.google.gson.Gson;
import org.example.Class.Project;
import org.example.Class.User;
import org.example.change.User.UserLook;
import org.example.methods.MethodsProject;
import org.example.methods.MethodsUser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectLook extends JFrame implements ActionListener {
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JButton rescind;//按钮
    private JButton rescindAll;//全部
    private JTextField aTextField, bTextField;//按钮旁边的输入框
    public ProjectLook(){
        setTitle("异常状态项目");
        setBounds(100, 100, 600, 400);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(2);
        setLocationRelativeTo(null);//设置GUI显示居中
        Object[] columnNames = {"id", "name", "type", "property", "Number", "already"};//定义表格列名
        //获取所有异常用户
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> project = methodsProject.getProjectList();
        int len = project.toArray().length - methodsProject.statusTrue(project);
        Object[][] tableValues = new Object[len][6];//表格内容
        //筛选项目
        int i = 0;
        for (Project data : project) {
            if (data.getStatus() == 0) {
                tableValues[i][0] = data.getId();
                tableValues[i][1] = data.getName();
                tableValues[i][2] = data.getType();
                tableValues[i][3] = data.getProperty();
                tableValues[i][4] = data.getNumber() ;
                tableValues[i][5] = data.getAlready();
                i++;
            }
        }
        //设置模型
        model = new DefaultTableModel(tableValues, columnNames);
        table = new JTable(model);//引用模型，或table.setModel(model);
        //设置滚动条
        JScrollPane sc = new JScrollPane(table);
        getContentPane().add(sc, BorderLayout.CENTER);
        //设置文本居中对齐
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);
        //设置文本框和按钮
        final JPanel panel = new JPanel();//内部默认流布局
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("项目名:"));
        aTextField = new JTextField(10);//文本框的内容和宽度
        panel.add(aTextField);
        panel.add(new JLabel("管理员密码:"));
        bTextField = new JTextField(10);
        panel.add(bTextField);
        rescind = new JButton("恢复项目");
        rescindAll = new JButton("一键恢复");
        rescind.addActionListener(this);//按钮监听事件
        rescindAll.addActionListener(this);//按钮监听事件
        panel.add(rescind);
        panel.add(rescindAll);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = aTextField.getText();//账号
        String password = bTextField.getText();//密码
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> project = methodsProject.getProjectList();
        if (e.getSource() == rescind) {//解除单个用户
            boolean temp = (!username.equals("") && !password.equals(""));
            if (temp) {
                int index = methodsProject.indexProject(project, username);
                if (index != -1 && password.equals("123456")) {
                    //解除单个用户
                    Gson gson = new Gson();
                    for (Project data : project) {
                        if (data.getName().equals(username)) {
                            data.setStatus(1);
                        }
                    }
                    String jsonString = gson.toJson(project); // 将List<User>对象转换为JSON字符串
                    boolean update = methodsProject.updateProject(project, jsonString);
                    if (update) {
                        JOptionPane.showMessageDialog(this, "修改成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        new ProjectLook();
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "该用户不存在", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请正确填写用户名和密码", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            if (!password.equals("") && password.equals("123456")) {
                //解除所有用户
                Gson gson = new Gson();
                for (Project data : project) {
                    data.setStatus(1);
                }
                String jsonString = gson.toJson(project); // 将List<User>对象转换为JSON字符串
                boolean update = methodsProject.updateProject(project, jsonString);
                if (update) {
                    JOptionPane.showMessageDialog(this, "一键修改成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new UserLook();
                } else {
                    JOptionPane.showMessageDialog(this, "修改失败", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this, "管理员密码错误", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
