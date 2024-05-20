package org.example.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTabbedPane extends JTabbedPane {

    public CustomTabbedPane() {
        setUI(new CustomTabbedPaneUI());
    }

    private static class CustomTabbedPaneUI extends BasicTabbedPaneUI {

        private final Color selectedColor = new Color(180, 19, 96);
        private final Color unselectedColor = new Color(72, 10, 48);
        private final Color backgroundBehindTabs = new Color(255, 218, 232);

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
            // no focus indicator
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            // paint the background behind the tabs
            g.setColor(backgroundBehindTabs);
            g.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));

            // call the superclass method to paint the tabs on top of the background
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }
    }
}
