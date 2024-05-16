package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OurWindow {
    JFrame frame;

    public OurWindow(){
        frame = new JFrame("My Swing App");
        //JButton button = new JButton("Click me!");

        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("This is Panel 1"));

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("This is Panel 2"));

        cardPanel.add(panel1, "panel1");
        cardPanel.add(panel2, "panel2");

        JButton selectFileButton = new JButton("Select File");
        JTextField selectedFilePathField = new JTextField(20);
        selectedFilePathField.setEditable(false);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFilePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        panel1.add(new JLabel("Select a file:"));
        panel1.add(selectFileButton);
        panel1.add(selectedFilePathField);

        //frame.getContentPane().add(button);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(600, 40));
        // Create radio button menu items
        JRadioButtonMenuItem button1 = new JRadioButtonMenuItem("Weryfikacja");
        JRadioButtonMenuItem button2 = new JRadioButtonMenuItem("Podpisywanie");
        button1.setBackground(new Color(255, 30, 150));
        button2.setBackground(new Color(255, 30, 150));
        button1.setForeground(new Color(255, 255, 255));
        button2.setForeground(new Color(255, 255, 255));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button1 clicked");
                cardLayout.show(cardPanel, "panel1");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button2 clicked");
                cardLayout.show(cardPanel, "panel2");
            }
        });

        // Add radio button menu items to the menu bar
        menuBar.add(button1);
        menuBar.add(button2);



        frame.setJMenuBar(menuBar);
        frame.add(cardPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 500);
        frame.setVisible(true);

    }
}
