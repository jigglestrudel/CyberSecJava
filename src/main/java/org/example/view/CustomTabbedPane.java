package org.example.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTabbedPane extends JTabbedPane {

    public CustomTabbedPane() {
        setUI(new CustomTabbedPaneUI());
    }

    private static class CustomTabbedPaneUI extends BasicTabbedPaneUI {

        private final Color selectedColor = new Color(180, 19, 96);  // Kolor zaznaczonej zakładki
        private final Color unselectedColor = new Color(72, 10, 48);  // Domyślny kolor niezaznaczonych zakładek
        private final Color backgroundBehindTabs = new Color(255, 218, 232); // Background color behind the tabs
        //private final Color separatorColor = new Color(180, 19, 96);  // Kolor paska oddzielającego

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            if (isSelected) {
                g.setColor(selectedColor);
            } else {
                g.setColor(unselectedColor);
            }
            g.fillRect(x, y, w, h);
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            if (isSelected) {
                g.setColor(selectedColor);
            } else {
                g.setColor(unselectedColor);
            }
            g.drawRect(x, y, w, h);
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            // Brak wskaźnika fokusu
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            // Paint the background behind the tabs
            g.setColor(backgroundBehindTabs);
            g.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));

            // Call the superclass method to paint the tabs on top of the background
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }

//        @Override
//        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
//            int width = tabPane.getWidth();
//            int height = tabPane.getHeight();
//            Insets insets = tabPane.getInsets();
//            Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
//
//            int tabAreaHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
//            int x = insets.left;
//            int y = insets.top + tabAreaHeight;
//            int w = width - insets.right - insets.left;
//            int h = height - insets.top - tabAreaHeight - insets.bottom;
//
//            // Rysowanie paska oddzielającego
//            g.setColor(separatorColor);
//            g.fillRect(x, y, w, 3);
//
//            // Rysowanie domyślnego obramowania zawartości
//            super.paintContentBorder(g, tabPlacement, selectedIndex);
//        }
    }
}
