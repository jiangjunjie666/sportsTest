package org.example.change.User;

import com.google.gson.Gson;
import org.example.Class.User;
import org.example.methods.MethodsUser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserLook extends JFrame implements ActionListener {
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JButton rescind;//按钮
    private JButton rescindAll;//全部
    private JTextField aTextField, bTextField;//按钮旁边的输入框

    public UserLook() {
        setTitle("异常状态用户");
        setBounds(100, 100, 600, 400);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(2);
        setLocationRelativeTo(null);//设置GUI显示居中
        String[] columnNames = {"id", "username", "password", "gender", "pro1", "pro2"};//定义表格列名
        //获取所有异常用户
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> userList = methodsUser.getUserList();
        int len = userList.toArray().length - methodsUser.statusTrue(userList);
        String[][] tableValues = new String[len][6];//表格内容
        //筛选用户
        int i = 0;
        for (User data : userList) {
            if (data.getStatus() == 0) {
                tableValues[i][0] = data.getId();
                tableValues[i][1] = data.getUsername();
                tableValues[i][2] = data.getPassword();
                tableValues[i][3] = data.getGender();
                tableValues[i][4] = data.getPro1();
                tableValues[i][5] = data.getPro2();
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
        panel.add(new JLabel("账户:"));
        aTextField = new JTextField(10);//文本框的内容和宽度
        panel.add(aTextField);
        panel.add(new JLabel("管理员密码:"));
        bTextField = new JTextField(10);
        panel.add(bTextField);
        rescind = new JButton("恢复用户");
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
        MethodsUser methodsUser = new MethodsUser();
        ArrayList<User> userList = methodsUser.getUserList();
        if (e.getSource() == rescind) {//解除单个用户
            boolean temp = (!username.equals("") && !password.equals(""));
            if (temp) {
                temp = methodsUser.checkUser(userList, username);
                if (temp && password.equals("123456")) {
                    //解除单个用户
                    Gson gson = new Gson();
                    for (User data : userList) {
                        if (data.getUsername().equals(username)) {
                            data.setStatus(1);
                        }
                    }
                    String jsonString = gson.toJson(userList); // 将List<User>对象转换为JSON字符串
                    boolean update = methodsUser.updateUser(userList, jsonString);
                    if (update) {
                        JOptionPane.showMessageDialog(this, "修改成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        new UserLook();
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "该用户不存在", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请正确填写用户名和密码", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //一键解除
        else {
            if (!password.equals("") && password.equals("123456")) {
                //解除所有用户
                Gson gson = new Gson();
                for (User data : userList) {
                    data.setStatus(1);
                }
                String jsonString = gson.toJson(userList); // 将List<User>对象转换为JSON字符串
                boolean update = methodsUser.updateUser(userList, jsonString);
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
