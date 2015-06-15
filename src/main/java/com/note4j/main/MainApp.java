package com.note4j.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.awt.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Created by changwei on 2015/6/13.
 * V1.0.1 @note4j.com
 * Copyright (c) 2014-2015 All rights reserved.
 */


public class MainApp implements ActionListener {

    private static Logger logger = Logger.getLogger(MainApp.class.getName());

    JFrame frame;
    JTabbedPane tabPane;//选项卡布局
    Container con;//布局1
    Container con1;//布局2
    Container con2;//布局2
    JLabel label1;
    JLabel label2;
    JLabel label2_1;
    JTextField text1;
    JTextField text2;
    JTextField text2_1;
    JTextArea text3_1;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button2_1;
    JFileChooser jfc;//文件选择器
    String wordFile; //模板文件路径
    String excelFile; //学生数据路径
    String destFile; //学生数据路径
    String majorInfoFile; //专业信息配置文件

    public MainApp() {
        frame = new JFrame("中国矿业大学（北京）研究生院");
        tabPane = new JTabbedPane();//选项卡布局
        con = new Container();//布局1
        con1 = new Container();//布局1
        con2 = new Container();//布局1
        label1 = new JLabel("Word模板");
        label2 = new JLabel("Excel文件");
        label2_1 = new JLabel("选择配置文件");
//        imageLabel = new JLabel("本软件能够将指定的word模板文件从excel文件中的数据进行替换");
        text1 = new JTextField();
        text2 = new JTextField();
        text2_1 = new JTextField();
        text3_1 = new JTextArea("      本程序能够将Excel表格中的源数据自动录入（填充）到先预定义的Word模板中，进而节省大量数据录入时所浪费的时间。软件的具体使用方法请参考README.txt文件。使用过程中如有问题，请将问题发送邮件至chang9wei7@163.com，感谢您的使用！");
        button1 = new JButton("选择");
        button2 = new JButton("选择");
        button3 = new JButton("开始");
        button2_1 = new JButton("选择");
        jfc = new JFileChooser();//文件选择器
    }

    public void show() {
        jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
        //下面两行是取得屏幕的高度和宽度
        double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));//设定窗口出现位置
        frame.setSize(400, 240);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局r
        //下面设定标签等的出现位置和高宽
        label1.setBounds(30, 30, 100, 20);
        label2.setBounds(30, 60, 100, 20);
        label2_1.setBounds(40, 10, 100, 20);
        text1.setBounds(100, 30, 150, 20);
        text1.setEditable(false);
        text2.setBounds(100, 60, 150, 20);
        text2.setEditable(false);
        text2_1.setBounds(120, 10, 110, 20);
        text2_1.setEditable(false);
        text3_1.setBounds(0, 0, 380, 220);
        text3_1.setEditable(false);
        text3_1.setLineWrap(true);
        button1.setBounds(280, 30, 60, 20);
        button2.setBounds(280, 60, 60, 20);
        button3.setBounds(140, 120, 80, 20);
        button2_1.setBounds(260, 10, 60, 20);
        button1.addActionListener(this);//添加事件处理
        button2.addActionListener(this);//添加事件处理
        button3.addActionListener(this);//添加事件处理
        button2_1.addActionListener(this);//添加事件处理
        con.add(label1);
        con.add(label2);
        con.add(text1);
        con.add(text2);
        con.add(button1);
        con.add(button2);
        con.add(button3);
        con.add(jfc);
        con1.add(label2_1);
        con1.add(text2_1);
        con1.add(button2_1);
        con2.add(text3_1);
        tabPane.add("文档替换", con);//添加布局1
        tabPane.add("设置", con1);//添加布局2
        tabPane.add("关于", con2);//添加布局3
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
        frame.setResizable(false);
        frame.setVisible(true);//窗口可见
    }

    public void actionPerformed(ActionEvent e) {//事件处理
        if (e.getSource().equals(button1)) {//判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(0);//设定只能选择到文件夹
            int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;//撤销则返回
            } else {
                File f = jfc.getSelectedFile();//f为选择到的目录
                String regex = "\\w+.doc[x]?";
                String fileName = f.getName();
                if (!fileName.matches(regex)) {
                    System.out.println("Error File Format!");
                    JOptionPane.showMessageDialog(null, "请选择Word文件！", "警告", JOptionPane.ERROR_MESSAGE);
                } else {
                    wordFile = f.getAbsolutePath();
                    destFile = wordFile.substring(0, wordFile.indexOf(fileName)) + "Generate_" + fileName;
                    text1.setText(fileName);
                }

            }
        }
        if (e.getSource().equals(button2)) {
            jfc.setFileSelectionMode(0);//设定只能选择到文件
            int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;//撤销则返回
            } else {
                File f = jfc.getSelectedFile();//f为选择到的文件
                String fileName = f.getName();
                String regex = "\\w+.xls[x]?";
                if (!fileName.matches(regex)) {
                    System.out.println("Error File Format!");
                    JOptionPane.showMessageDialog(null, "请选择Excel文件！", "警告", JOptionPane.ERROR_MESSAGE);
                } else {
                    excelFile = f.getAbsolutePath();
                    text2.setText(fileName);
                }
            }
        }
        if (e.getSource().equals(button3)) {
            if (wordFile == null && excelFile == null) {
                JOptionPane.showMessageDialog(null, "请先选择文件！", "警告", JOptionPane.ERROR_MESSAGE);
            } else {
//                String destWordFile = null;
                ReplaceWord replaceWord = new ReplaceWord(majorInfoFile, wordFile, destFile, excelFile);
                try {
                    replaceWord.replace();
                    JOptionPane.showMessageDialog(null, "文件生成成功！", "提示", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "文件生成失败，请检查配置文件或文件格式！", "提示", JOptionPane.ERROR_MESSAGE);
//                  logger.debug(e1.getMessage());
                    logger.debug(null,e1);
                }
            }
        }
        if (e.getSource().equals(button2_1)) {
            jfc.setFileSelectionMode(0);//设定只能选择到文件
            int state = jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if (state == 1) {
                return;//撤销则返回
            } else {
                File f = jfc.getSelectedFile();//f为选择到的文件
                String fileName = f.getName();
                String regex = "\\w+.dat$";
                if (!fileName.matches(regex)) {
                    System.out.println("Error File Format!");
                    JOptionPane.showMessageDialog(null, "请选择.dat配置文件！", "警告", JOptionPane.ERROR_MESSAGE);
                } else {
                    majorInfoFile = f.getAbsolutePath();
                    text2_1.setText(fileName);
                }
            }
        }

    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/log4j.properties");
        MainApp mainApp = new MainApp();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.debug(null,e);
//            e.printStackTrace();
        }
        mainApp.show();
    }
}