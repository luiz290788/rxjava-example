package com.github.luiz290788.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import rx.Observable;

public class FireAlarmUI extends JPanel {
    private static final long serialVersionUID = -427994638314106255L;

    private static final Color ROOF_COLOR = new Color(128, 0, 0);
    private static final Color WALL_COLOR = new Color(160, 82, 45);
    private static final Color FONT_COLOR = Color.WHITE;
    private static final Polygon ROOF = new Polygon(new int[] {0, 20, 40}, new int[] {15, 0, 15}, 3);
    private static final Polygon WALL = new Polygon(new int[] {5, 35, 35, 5}, new int[] {15, 15, 35, 35}, 4);
    private static final Polygon FIRE = new Polygon(new int[] {5, 7, 12, 22, 26, 30, 35}, new int[] {12, -5, 3, -3, 7, -4, 14}, 7);

    private final Map<String, House> houses = new HashMap<String, House>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintHouses(g);
    }

    private void paintHouses(Graphics g) {
        Observable.<House> from(houses.values()).subscribe(house -> {
            paintHouse(g, house.label, house.x, house.y, house.onFire);
        });
    }

    private void paintHouse(Graphics g, String label, int x, int y, boolean onFire) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(x, y);
        g2d.setColor(ROOF_COLOR);
        g2d.fillPolygon(ROOF);
        g2d.setColor(WALL_COLOR);
        g2d.fillPolygon(WALL);
        g2d.setColor(FONT_COLOR);
        g2d.drawString(label, 17, 30);
        if (onFire) {
            g2d.setColor(Color.RED);
            g2d.fillPolygon(FIRE);
        }
        g2d.translate(-x, -y);
    }

    public void addHouse(String label, int x, int y) {
        houses.put(label, new House(label, x, y, false));
        repaint();
    }

    public void houseOnFire(String label, boolean onFire) {
        houses.get(label).onFire = onFire;
        repaint();
    }

    public void removeHouse(String label) {
        houses.remove(label);
        repaint();
    }

    public void removeAllHouses() {
        houses.clear();
    }

    private static final class House {
        private String label;
        private int x, y;
        private boolean onFire;

        public House(String label, int x, int y, boolean onFire) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.onFire = onFire;
        }
    }

}
