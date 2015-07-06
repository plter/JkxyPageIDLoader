package com.plter.jkxypageidloader;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow implements KeyListener, ActionListener, LoadIdThread.IFoundTitleListener, MouseListener {

    public JPanel rootContainer;
    private JButton btnStartLoad;
    private JTextField tfStartId;
    private JTextField tfEndId;
    private JButton btnStopLoad;
    private JButton btnSaveToTextFile;
    private JLabel labelLinesCount;
    private JButton btnClear;
    private JTable tableOutput;

    private LoadIdThread currentThread = null;
    private int linesCount = 0;
    private TMPageList pageListModel = new TMPageList();

    public MainWindow() {
        btnStartLoad.addActionListener(this);
        btnStopLoad.addActionListener(this);
        btnSaveToTextFile.addActionListener(this);
        btnClear.addActionListener(this);
        tfStartId.addKeyListener(this);
        tfEndId.addKeyListener(this);

        tableOutput.setModel(pageListModel);
        tableOutput.getColumnModel().getColumn(0).setMaxWidth(100);
        tableOutput.addMouseListener(this);
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
            if (currentThread==null||!currentThread.isRunning()) {
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

                        saveDataToFile(file);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(rootContainer,"无法创建文件，请确认你是否有权限在此处创建文件");
                    }
                }else {
                    saveDataToFile(file);
                }
            }
        }else if (e.getSource()==btnClear){
            pageListModel.clear();
            setLinesCount(0);
        }
    }

    private void saveDataToFile(File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(pageListModel.toString().getBytes("utf-8"));
            fos.flush();
            fos.close();

            JOptionPane.showMessageDialog(rootContainer, "文件保存成功");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(rootContainer, "文件不存在");
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(rootContainer,"你的系统不支持UTF-8编码方式");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootContainer, "无法创建文件，请确认你是否有权限在此处创建文件");
        }
    }


    @Override
    public void foundPage(String title, int id,String url) {
        pageListModel.add(new CellData(title,id,url));
        setLinesCount(++linesCount);
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
        labelLinesCount.setText(String.format("共%d行",linesCount));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount()==2){
            pageListModel.getCellData(tableOutput.getSelectedRow()).openPage();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
