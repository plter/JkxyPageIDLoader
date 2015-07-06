package com.plter.jkxypageidloader;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Created by plter on 7/6/15.
 */
public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("极客学院页面ID加载工具");
        frame.setContentPane(new MainWindow().rootContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
