package com.github.luiz290788.ui;

import java.awt.Component;

import javax.swing.JFrame;

public class Window extends JFrame {

    private static final long serialVersionUID = 5895737550493371496L;

    public Window(String title, Component c) {
        add(c);

        setTitle(title);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
