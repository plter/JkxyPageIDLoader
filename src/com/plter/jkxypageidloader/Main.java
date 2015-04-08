package com.plter.jkxypageidloader;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main implements KeyListener, ActionListener, LoadIdThread.IFoundTitleListener {

    private JPanel rootContainer;
    private JButton btnStartLoad;
    private JTextArea taOutput;
    private JTextField tfStartId;
    private JTextField tfEndId;
    private JButton btnStopLoad;
    private JButton btnSaveToTextFile;

    private LoadIdThread currentThread = null;

    public Main() {
        btnStartLoad.addActionListener(this);
        btnStopLoad.addActionListener(this);
        btnSaveToTextFile.addActionListener(this);
        tfStartId.addKeyListener(this);
        tfEndId.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()<KeyEvent.VK_0||e.getKeyChar()>KeyEvent.VK_9){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnStartLoad) {
            if (currentThread==null) {
                currentThread = new LoadIdThread(Integer.parseInt(tfStartId.getText()), Integer.parseInt(tfEndId.getText()),this);
                currentThread.start();
            }
        }else if (e.getSource()==btnStopLoad){
            if (currentThread!=null){
                currentThread.stop();
                currentThread = null;
            }
        }else if ((e.getSource()==btnSaveToTextFile)){
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(rootContainer);

            File file = fc.getSelectedFile();
            if (file!=null){
                if (!file.exists()){
                    try {
                        file.createNewFile();

                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(taOutput.getText().getBytes("utf-8"));
                        fos.flush();
                        fos.close();

                        JOptionPane.showMessageDialog(rootContainer,"文件保存成功");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(rootContainer,"无法创建文件，请确认你是否有权限在此处创建文件");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("极客学院页面ID加载工具");
        frame.setContentPane(new Main().rootContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    @Override
    public void foundTitle(String title, int id) {
        taOutput.append(String.format("%d\t%s\n",id,title));
        taOutput.setCaretPosition(taOutput.getText().length());
    }
}