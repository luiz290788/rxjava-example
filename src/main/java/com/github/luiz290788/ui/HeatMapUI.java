package com.github.luiz290788.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class HeatMapUI extends JPanel {

    private static final long serialVersionUID = 4244205615621520272L;

    private Color[][] temperatures = null;

    public HeatMapUI(int width, int height) {
        temperatures = new Color[width][height];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < temperatures.length; i++) {
            for (int j = 0; j < temperatures[i].length; j++) {
                if (temperatures[i][j] != null) {
                    paintSensor(g, i * 35 + 10, j * 35 + 10, temperatures[i][j]);
                }
            }
        }
    }

    public void setColor(int x, int y, Color temp) {
        temperatures[x][y] = temp;
        repaint();

    }

    private void paintSensor(Graphics graphics, int i, int j, Color color) {
        Graphics2D g2d = (Graphics2D) graphics;

        Ellipse2D e = new Ellipse2D.Double(i, j, 25, 25);
        g2d.setColor(color);
        g2d.fill(e);
    }
}
