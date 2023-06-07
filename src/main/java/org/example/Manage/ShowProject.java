package org.example.Manage;

import org.example.Class.Project;
import org.example.change.Project.ProjectAdd;
import org.example.change.Project.ProjectDelete;
import org.example.change.Project.ProjectLook;
import org.example.change.Project.ProjectUpdate;
import org.example.methods.MethodsProject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowProject extends JFrame implements ActionListener {
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JButton addButton, delButton, updButton, lookButton;//增删改三个按钮

    public ShowProject() {
        setTitle("运动会信息展示");
        setAlwaysOnTop(true);
        setBounds(100, 100, 700, 500);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        Object[] columnNames = {"ID", "name", "type", "property", "Number", "already"};
        Object[][] rowData = {};
        rowData = getProject();

            //布局设置
            model = new DefaultTableModel(rowData, columnNames);//设置模型
            table = new JTable(model);//引用模型，或table.setModel(model);
            //设置文本居中对齐
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.setDefaultRenderer(Object.class, cellRenderer);
            JScrollPane sc = new JScrollPane(table);//设置滚动条
            getContentPane().add(sc, BorderLayout.CENTER);
            final JPanel panel = new JPanel();//内部默认流布局
            getContentPane().add(panel, BorderLayout.SOUTH);
            addButton = new JButton("增加项目");
            delButton = new JButton("删除项目");
            updButton = new JButton("修改项目");
            lookButton = new JButton("查看异常项目");
            addButton.addActionListener(this);
            delButton.addActionListener(this);
            updButton.addActionListener(this);
            lookButton.addActionListener(this);
            panel.add(addButton);
            panel.add(delButton);
            panel.add(updButton);
            panel.add(lookButton);
            setVisible(true);
        }


    public Object[][] getProject() {
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> dataList = methodsProject.getProjectList();
        int len = methodsProject.statusTrue(dataList);
        Object[][] newProject = new Object[len][6];
        int i = 0;
        for (Project data : dataList) {
            if (data.getStatus() != 0) {
                newProject[i][0] = data.getId();
                newProject[i][1] = data.getName();
                newProject[i][2] = data.getType();
                newProject[i][3] = data.getProperty();
                newProject[i][4] = data.getNumber();
                newProject[i][5] = data.getAlready();
                i++;
            }
        }
        return newProject;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            this.dispose();
            new ProjectAdd();
        } else if (e.getSource() == delButton) {
            this.dispose();
            new ProjectDelete();
        } else if (e.getSource() == updButton) {
            this.dispose();
            new ProjectUpdate();
        } else {
            this.dispose();
            new ProjectLook();
        }
    }
}
