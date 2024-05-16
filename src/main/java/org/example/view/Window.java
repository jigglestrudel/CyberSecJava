package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

// color palette
// https://lospec.com/palette-list/5-sheep

public class Window {
    JFrame frame;

    public Window(){
        frame = new JFrame("Digital Signature Verifier");

        CustomTabbedPane tabPanel = new CustomTabbedPane();
        tabPanel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setBackground(new Color(255, 218, 232));
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel2 = new JPanel(new GridBagLayout());
        panel2.setBackground(new Color(255, 218, 232));
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));

        tabPanel.addTab("Verification", panel1);
        tabPanel.addTab("Signing", panel2);

//        JPanel cardPanel = new JPanel();
//        CardLayout cardLayout = new CardLayout();
//        cardPanel.setLayout(cardLayout);
//
//        JPanel panel1 = new JPanel();
//        //panel1.add(new JLabel("This is Panel 1"));
//        panel1.setBackground(new Color(255, 218, 232));
//        panel1.setForeground(new Color(72, 10, 48));
//        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        JPanel panel2 = new JPanel();
//        panel2.add(new JLabel("This is Panel 2"));
//        panel2.setBackground(new Color(255, 218, 232));
//        panel2.setForeground(new Color(72, 10, 48));
//        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        cardPanel.add(panel1, "panel1");
//        cardPanel.add(panel2, "panel2");

        // PANEL 1
        JLabel fileLabel = new JLabel("Select a file:");
        JTextField selectedFilePathField = new JTextField(25);
        selectedFilePathField.setEditable(false);
        JButton browseButton = new JButton("Browse");
        browseButton.setBackground(new Color(255, 128, 174));
        browseButton.setForeground(Color.WHITE);
        JButton verifyButton = new JButton("Verify");
        verifyButton.setBackground(new Color(255, 128, 174));
        verifyButton.setForeground(Color.WHITE);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);

        // 1 row: file selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(fileLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(selectedFilePathField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(browseButton, gbc);

        // 2 row: verify button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(verifyButton, gbc);

        // PANEL 2
        JLabel fileLabel2 = new JLabel("Select a file:");
        JTextField selectedFilePathField2 = new JTextField(25);
        selectedFilePathField2.setEditable(false);
        JButton browseButton2 = new JButton("Browse");
        browseButton2.setBackground(new Color(255, 128, 174));
        browseButton2.setForeground(Color.WHITE);

        JLabel algorithmLabel = new JLabel("Choose a signing algorithm:");
        ButtonGroup bg = new ButtonGroup();
        JRadioButton algorithmDSA = new JRadioButton("DSA");
        algorithmDSA.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmRSA = new JRadioButton("RSA");
        algorithmRSA.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmEC = new JRadioButton("EC");
        algorithmEC.setBackground(new Color(255, 218, 232));
        bg.add(algorithmDSA);
        bg.add(algorithmRSA);
        bg.add(algorithmEC);

        JLabel signatureLabel = new JLabel("Signature name:");
        JTextField signatureField = new JTextField(25);
        JLabel publicKeyLabel = new JLabel("Public key name:");
        JTextField publicKeyField = new JTextField(25);
        JCheckBox generatePrivateKey = new JCheckBox("Generate private key");
        generatePrivateKey.setBackground(new Color(255, 218, 232));
        JLabel privateKeyLabel = new JLabel("Private key name:");
        JTextField privateKeyField = new JTextField(25);
        privateKeyLabel.setVisible(false);
        privateKeyField.setVisible(false);

        JButton signButton = new JButton("Sign");
        signButton.setBackground(new Color(255, 128, 174));
        signButton.setForeground(Color.WHITE);

        browseButton2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        generatePrivateKey.addActionListener(e -> {
            if (generatePrivateKey.isSelected()) {
                privateKeyLabel.setVisible(true);
                privateKeyField.setVisible(true);
            }
            else {
                privateKeyLabel.setVisible(false);
                privateKeyField.setVisible(false);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);

        // 1 row: file selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(fileLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(selectedFilePathField2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel2.add(browseButton2, gbc);

        // 2 row: algorithm selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(algorithmLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(algorithmDSA, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(algorithmRSA, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(algorithmEC, gbc);

        // 3 row: signature name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(signatureLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(signatureField, gbc);

        // 4 row: public key name
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(publicKeyLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(publicKeyField, gbc);

        // 5 row: generate private key checkbox
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(generatePrivateKey, gbc);

        // 6 row: private key name
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(privateKeyLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(privateKeyField, gbc);

        // 7 row: sign button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel2.add(signButton, gbc);

        //frame.getContentPane().add(button);
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.setOpaque(true);
//        menuBar.setPreferredSize(new Dimension(600, 40));
//
//        // Create radio button menu items
//        JRadioButtonMenuItem button1 = new JRadioButtonMenuItem("Verification", true);
//        JRadioButtonMenuItem button2 = new JRadioButtonMenuItem("Signing");
//        button1.setBackground(new Color(255, 50, 124));
//        button2.setBackground(new Color(255, 50, 124));
//        button1.setForeground(new Color(255, 255, 255));
//        button2.setForeground(new Color(255, 255, 255));
//
//        ButtonGroup buttonGroup = new ButtonGroup();
//        buttonGroup.add(button1);
//        buttonGroup.add(button2);

//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button1 clicked");
//                cardLayout.show(cardPanel, "panel1");
//            }
//        });
//
//        button2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button2 clicked");
//                cardLayout.show(cardPanel, "panel2");
//            }
//        });

        // Add radio button menu items to the menu bar
//        menuBar.add(button1);
//        menuBar.add(button2);
//
//        frame.setJMenuBar(menuBar);
        //frame.add(cardPanel);
        frame.add(tabPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 500);
        frame.setVisible(true);

    }
}
