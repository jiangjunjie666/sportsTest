package org.example.singUp.User;

import org.example.Class.Audit;
import org.example.Class.Project;
import org.example.Class.User;
import org.example.methods.MethodsProject;
import org.example.methods.MethodsSingUp;
import org.example.methods.MethodsSingUpUser;
import org.example.methods.MethodsUser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SingUpUser extends JFrame implements ActionListener {
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JTextField projectText;
    private JButton addProject;

    public SingUpUser() {
        setTitle("用户报名项目");
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
        int i = 0;
        for (Project data : projectList) {
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
        panel.add(new JLabel("项目名"));
        projectText = new JTextField(10);
        panel.add(projectText);
        addProject = new JButton("报名参加");
        panel.add(addProject);
        addProject.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //处理报名事件
        String project = projectText.getText();//项目名//数据
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> userList = methodsUser.getUserList();
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> projectList = methodsProject.getProjectList();//项目数据
        boolean temp = !project.equals("");
        if (temp) {
            //判断此项目在不在 以及 各项安全性检验（是否已经报名，是否还有名额）
            MethodsSingUpUser methodsSingUpUser = new MethodsSingUpUser();
            String str = methodsSingUpUser.UserName();
            boolean found = methodsSingUpUser.ProjectLookTrue(projectList, project);
            //项目存在匹配
            if (!found) {
                JOptionPane.showMessageDialog(this, "此项目不存在", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //性别匹配
            if (!methodsSingUpUser.UserGender(projectList, userList, str, project)) {
                JOptionPane.showMessageDialog(this, "你的性别不符合此项目", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String type[] = methodsSingUpUser.getProjectType(projectList, project);
            //项目剩余名额匹配
            if (Integer.parseInt(type[3]) == 0) {
                JOptionPane.showMessageDialog(this, "此项目名额不足", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //检查是否出现重复报名的情况
            if (methodsSingUpUser.repeat(project)) {
                JOptionPane.showMessageDialog(this, "请勿重复报名", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //检查用户有没有报名了项目
            MethodsSingUp methodsSingUp = new MethodsSingUp();
            if (methodsSingUp.InspectSingUp(userList, str, project)) {
                JOptionPane.showMessageDialog(this, "你已经报名了此项目,请勿重复报名", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //基础检验完成，写入对应的json文件
            //自动创建id
            ArrayList<Audit> audit = methodsSingUpUser.getAuditList();
            int len = audit.toArray().length;
            String id = "";
            if (len == 0) id = "a001";
            else id = "a00" + (Integer.parseInt(audit.get(len - 1).getId().substring(3)) + 1);
            audit.add(new Audit(id, project, str, type[0], type[1], type[2], type[3]));
            methodsSingUpUser.writeAudit(audit);
            JOptionPane.showMessageDialog(this, "报名成功，等待管理员审核", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "请正确填写项目名", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
