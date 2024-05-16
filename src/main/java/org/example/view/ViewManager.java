package org.example.view;

import javax.swing.*;

public class ViewManager {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OurWindow();
            }
        });
    }
}
