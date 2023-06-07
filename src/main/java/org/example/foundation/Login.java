package org.example.foundation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Class.User;
import org.example.singUp.User.ShowPersonage;
import org.example.singUp.User.UserMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Login extends JFrame implements ActionListener {

    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginBtn;
    private JButton registerBtn;
    private JButton management;

    public Login() {
        setTitle("登录");
        setAlwaysOnTop(true);
        setLayout(null);//关闭默认布局类型 自己手动设置布局
        setSize(400, 400);
        setDefaultCloseOperation(3);//设置关闭模式
        setLocationRelativeTo(null);//设置GUI显示居中
        //创建界面组件
        JLabel username = new JLabel("账号：");
        JLabel password = new JLabel("密码：");
        loginBtn = new JButton("登录");
        loginBtn.addActionListener(this);//监听登录事件
        registerBtn = new JButton("注册");
        registerBtn.addActionListener(this);//监听注册事件
        management = new JButton("管理员");
        management.addActionListener(this);//管理员登录
        usernameText = new JTextField(15);
        passwordText = new JPasswordField(15);
        // 设置字体和背景颜色
        usernameText.setForeground(Color.BLACK);
        passwordText.setForeground(Color.BLACK);
        usernameText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //创建装组件的容器
        JPanel usernameJp = new JPanel();
        JPanel passwordJp = new JPanel();
        JPanel loginJp = new JPanel();
        //设置容器的位置
        usernameJp.setBounds(30, 40, 300, 50);
        passwordJp.setBounds(30, 80, 300, 50);
        loginJp.setBounds(50, 130, 300, 60);
        usernameJp.add(username);
        usernameJp.add(usernameText);
        passwordJp.add(password);
        passwordJp.add(passwordText);
        loginJp.add(loginBtn);
        loginJp.add(registerBtn);
        loginJp.add(management);
        //将组件装入GUI
        add(usernameJp);
        add(passwordJp);
        add(loginJp);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = usernameText.getText();
            String password = new String(passwordText.getPassword());
            // 根据读取的用户账号信息进行校验
            boolean temp = readUser(username, password);
            System.out.println(temp);
            if (temp) {
                JOptionPane.showMessageDialog(this, "登录成功", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();//登录成功关闭此窗口 跳转页面
                //更新临时存放用户名的文件
                try{
                    File file = new File("src\\main\\java\\org\\example\\data\\Personage.txt"); // 假设文件名为file.txt
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    String newData = username; // 新的数据
                    writer.write(newData); // 将新数据写入文件中
                    writer.close();
                    fileWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                new UserMenu();
            } else {
                JOptionPane.showMessageDialog(this, "登陆失败", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if(e.getSource() == management) {//管理员登录
            this.dispose();
            new Management();
        }
        //注册操作
        else {
            this.dispose();
            new Register();
        }
    }

    //读取用户数据
    public boolean readUser(String username, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\data\\user.json"))) {
            String json = "";
            String line;
            while ((line = br.readLine()) != null) {
                json += line + "\n";
            }
            Gson gson = new Gson();
            ArrayList<User> dataList = gson.fromJson(json, new TypeToken<ArrayList<User>>() {
            }.getType());
            for (User data : dataList) {
                if (data.getUsername().equals(username) && data.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

