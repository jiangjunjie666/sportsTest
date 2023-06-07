package org.example.Manage;

import org.example.Class.User;
import org.example.change.User.UserAdd;
import org.example.change.User.UserDelete;
import org.example.change.User.UserLook;
import org.example.change.User.UserUpdate;
import org.example.methods.MethodsUser;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowUser extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            this.dispose();
            new UserAdd();
        } else if (e.getSource() == delButton) {
            this.dispose();
            new UserDelete();
        } else if (e.getSource() == lookButton) {
            this.dispose();
            new UserLook();
        } else {
            this.dispose();
            new UserUpdate();
        }
    }

    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JButton addButton, delButton, updButton, lookButton;//增删改三个按钮

    public ShowUser() {
        setTitle("用户信息展示");
        setAlwaysOnTop(true);
        setBounds(100, 100, 700, 300);
        setDefaultCloseOperation(2);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中

//        JPanel panel = new JPanel(new BorderLayout());

        Object[] columnNames = {"ID", "gender", "username", "password", "project1", "project2"};
        Object[][] rowData = {};
        //读取用户数据
        rowData = getUser();
        if (rowData[0][0] == null) {
            JOptionPane.showMessageDialog(this, "读取用户信息失败", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
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
            addButton = new JButton("增加用户");
            delButton = new JButton("删除用户");
            updButton = new JButton("修改用户");
            lookButton = new JButton("查看异常用户");
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
    }
    public Object[][] getUser() {
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> dataList = methodsUser.getUserList();
        int len = methodsUser.statusTrue(dataList);
        Object[][] newUser = new Object[len][6];
        int i = 0;
        for (User data : dataList) {
            if (data.getStatus() != 0) {
                newUser[i][0] = data.getId();
                newUser[i][1] = data.getGender();
                newUser[i][2] = data.getUsername();
                newUser[i][3] = data.getPassword();
                newUser[i][4] = data.getPro1();
                newUser[i][5] = data.getPro2();
                i++;
            }
        }
        return newUser;
    }
}
