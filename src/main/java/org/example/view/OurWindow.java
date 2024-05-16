package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OurWindow {
    JFrame frame;

    public OurWindow(){
        frame = new JFrame("My Swing App");
        //JButton button = new JButton("Click me!");

        //frame.getContentPane().add(button);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(255, 30, 150));
        menuBar.setPreferredSize(new Dimension(600, 40));
        // Create radio button menu items
        JRadioButtonMenuItem button1 = new JRadioButtonMenuItem("Button1");
        JRadioButtonMenuItem button2 = new JRadioButtonMenuItem("Button2");
        button1.setBackground(new Color(191, 8, 109));
        button2.setBackground(new Color(191, 8, 109));
        button1.setSize(100, 40);
        button2.setSize(100, 40);

        // Create a button group for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(button1);
        buttonGroup.add(button2);

        // Add action listeners to handle button clicks
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button1 clicked");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button2 clicked");
            }
        });

        // Add radio button menu items to the menu bar
        menuBar.add(button1);
        menuBar.add(button2);
        menuBar.add(Box.createHorizontalGlue());

        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 500);
        frame.setVisible(true);

    }
}
