package org.example.Manage;

import com.google.gson.Gson;
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
import java.util.ArrayList;

public class AuditUser extends JFrame implements ActionListener {
    //审核报名表
    private DefaultTableModel model;//表格模型
    private JTable table;//表格
    private JButton rescind;//按钮
    private JButton rescindAll;//全部
    private JButton delete;//删除某个申请
    private JTextField aTextField, bTextField, cTextField;//按钮旁边的输入框

    public AuditUser() {
        setTitle("异常状态项目");
        setBounds(100, 100, 700, 400);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(2);
        setLocationRelativeTo(null);//设置GUI显示居中
        Object[] columnNames = {"id", "projectName", "username", "type", "property", "Number", "already"};//定义表格列名
        MethodsSingUpUser methodsSingUpUser = new MethodsSingUpUser();
        ArrayList<Audit> audit = methodsSingUpUser.getAuditList();
        int len = audit.toArray().length;
        Object[][] tableValues = new Object[len][7];//表格内容
        //筛选项目
        int i = 0;
        for (Audit data : audit) {
            tableValues[i][0] = data.getId();
            tableValues[i][1] = data.getProjectName();
            tableValues[i][2] = data.getUsername();
            tableValues[i][3] = data.getType();
            tableValues[i][4] = data.getProperty();
            tableValues[i][5] = data.getNumber();
            tableValues[i][6] = data.getAlready();
            i++;
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
        panel.add(new JLabel("账户名:"));
        aTextField = new JTextField(10);//文本框的内容和宽度
        panel.add(aTextField);
        panel.add(new JLabel("管理员密码:"));
        bTextField = new JTextField(10);
        panel.add(bTextField);
        rescind = new JButton("审批项目");
//        rescindAll = new JButton("一键审批");
        delete = new JButton("删除该申请");
        rescind.addActionListener(this);//按钮监听事件
//        rescindAll.addActionListener(this);//按钮监听事件
        delete.addActionListener(this);
        panel.add(rescind);
        panel.add(delete);
//        panel.add(rescindAll);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = aTextField.getText();//账号
        String password = bTextField.getText();//密码
        MethodsSingUpUser methodsSingUpUser = new MethodsSingUpUser();
        MethodsSingUp methodsSingUp = new MethodsSingUp();
        MethodsUser methodsUser = new MethodsUser();
        MethodsProject methodsProject = new MethodsProject();
        ArrayList<Project> project = methodsProject.getProjectList();
        ArrayList<User> user = methodsUser.getUserList();
        ArrayList<Audit> audit = methodsSingUpUser.getAuditList();
        if (e.getSource() == rescind) {
            //通过单个申请
            boolean temp = (!name.equals("") && !password.equals(""));
            if (temp && password.equals("123456")) {
                //将数据更新，user表更新，project表更新，audit表更新
                //1.user表更新
                int num = methodsSingUp.InspectNum(user, name);
                Gson gson = new Gson();
                for (User data : user) {
                    if (data.getUsername().equals(name) && num == 2) {
                        data.setPro1(methodsSingUpUser.ret(name));
                        String jsonString = gson.toJson(user);
                        methodsUser.updateUser(user,jsonString);
                    }
                    if(data.getUsername().equals(name) && num == 1){
                        data.setPro2(methodsSingUpUser.ret(name));
                        String jsonString = gson.toJson(user);
                        methodsUser.updateUser(user,jsonString);
                    }
                }
                //修改project表，修改项目剩余名额
                //获取项目名
                String proName = methodsSingUpUser.ret(name);
                methodsSingUp.updateProject(project,proName);
                //删除申请表中的数据
                methodsSingUpUser.remove(audit,name);
                JOptionPane.showMessageDialog(this, "审核成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new AuditUser();
            } else {
                JOptionPane.showMessageDialog(this, "请正确填写用户名和密码", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //删除单个申请
            boolean temp = (!name.equals("") && !password.equals(""));
            if(temp && password.equals("123456")){
                //删除申请表中的数据
                methodsSingUpUser.remove(audit,name);
                JOptionPane.showMessageDialog(this, "删除成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new AuditUser();
            }else{
                JOptionPane.showMessageDialog(this, "请正确填写用户名和密码", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
