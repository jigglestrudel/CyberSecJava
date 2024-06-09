package org.example.view;

import org.example.signing.SignatureGenerator;
import org.example.verification.Verifier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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

        // PANEL 1
        JLabel fileLabel = new JLabel("Select a file:");
        JTextField filePath = new JTextField(25);
        filePath.setEditable(false);
        JButton fileButton = new JButton("Browse");
        fileButton.setBackground(new Color(255, 128, 174));
        fileButton.setForeground(Color.WHITE);

        JLabel signatureLabel = new JLabel("Select a signature:");
        JTextField signaturePath = new JTextField(25);
        signaturePath.setEditable(false);
        JButton signatureButton = new JButton("Browse");
        signatureButton.setBackground(new Color(255, 128, 174));
        signatureButton.setForeground(Color.WHITE);

        JLabel publicKeyLabel = new JLabel("Select public key:");
        JTextField publicKeyPath = new JTextField(25);
        publicKeyPath.setEditable(false);
        JButton publicKeyButton = new JButton("Browse");
        publicKeyButton.setBackground(new Color(255, 128, 174));
        publicKeyButton.setForeground(Color.WHITE);

        JLabel algorithmLabel = new JLabel("Choose a signing algorithm:");
        ButtonGroup bg = new ButtonGroup();
        JRadioButton algorithmDSA = new JRadioButton("DSA");
        algorithmDSA.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmRSA = new JRadioButton("RSA");
        algorithmRSA.setBackground(new Color(255, 218, 232));
        bg.add(algorithmDSA);
        bg.add(algorithmRSA);

        JButton verifyButton = new JButton("Verify");
        verifyButton.setBackground(new Color(255, 128, 174));
        verifyButton.setForeground(Color.WHITE);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // Set the current directory to the project directory
            File projectDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(projectDirectory);

            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePath.setText(selectedFile.getAbsolutePath());
            }
        });

        signatureButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // Set the current directory to the project directory
            File projectDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(projectDirectory);

            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                signaturePath.setText(selectedFile.getAbsolutePath());
            }
        });

        publicKeyButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // Set the current directory to the project directory
            File projectDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(projectDirectory);

            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                publicKeyPath.setText(selectedFile.getAbsolutePath());
            }
        });

        verifyButton.addActionListener(e -> {
            String algorithm = "";
            if (algorithmDSA.isSelected()) {
                algorithm = algorithmDSA.getText();
            }
            else if (algorithmRSA.isSelected()) {
                algorithm = algorithmRSA.getText();
            }
            Verifier verifier = new Verifier( filePath.getText(), signaturePath.getText());
            if (verifier.verifySignature()) {
                JOptionPane.showMessageDialog(frame, "Digital signature is valid", "Verification Result", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame, "Digital signature is not valid", "Verification Result", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.insets = new Insets(10, 10, 10, 10);

        // 1 row: file selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(fileLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(filePath, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(fileButton, gbc);

        // 2 row: file selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(signatureLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(signaturePath, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(signatureButton, gbc);

        // 3 row: public key
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(publicKeyLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(publicKeyPath, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(publicKeyButton, gbc);

        // 4 row: algorithm selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(algorithmLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(algorithmDSA, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(algorithmRSA, gbc);

        // 5 row: verify button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(verifyButton, gbc);

        // PANEL 2
        JLabel fileLabel2 = new JLabel("Select a file:");
        JTextField filePath2 = new JTextField(25);
        filePath2.setEditable(false);
        JButton fileButton2 = new JButton("Browse");
        fileButton2.setBackground(new Color(255, 128, 174));
        fileButton2.setForeground(Color.WHITE);

        JLabel algorithmLabel2 = new JLabel("Choose a signing algorithm:");
        ButtonGroup bg2 = new ButtonGroup();
        JRadioButton algorithmDSA2 = new JRadioButton("DSA");
        algorithmDSA2.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmRSA2 = new JRadioButton("RSA");
        algorithmRSA2.setBackground(new Color(255, 218, 232));
        bg2.add(algorithmDSA2);
        bg2.add(algorithmRSA2);

        JLabel signatureLabel2 = new JLabel("Signature file name:");
        JTextField signaturePath2 = new JTextField(25);
        JLabel publicKeyLabel2 = new JLabel("Public key file name:");
        JTextField publicKeyPath2 = new JTextField(25);
        JCheckBox generatePrivateKey = new JCheckBox("Save private key?");
        generatePrivateKey.setBackground(new Color(255, 218, 232));
        JLabel privateKeyLabel = new JLabel("Private key file name:");
        JTextField privateKeyPath = new JTextField(25);
        privateKeyLabel.setVisible(false);
        privateKeyPath.setVisible(false);

        JButton signButton = new JButton("Sign");
        signButton.setBackground(new Color(255, 128, 174));
        signButton.setForeground(Color.WHITE);

        fileButton2.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            // Set the current directory to the project directory
            File projectDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(projectDirectory);

            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePath2.setText(selectedFile.getAbsolutePath());
            }
        });

        generatePrivateKey.addActionListener(e -> {
            if (generatePrivateKey.isSelected()) {
                privateKeyLabel.setVisible(true);
                privateKeyPath.setVisible(true);
            }
            else {
                privateKeyLabel.setVisible(false);
                privateKeyPath.setVisible(false);
            }
        });

        signButton.addActionListener(e -> {
            String algorithm = "";
            if (algorithmDSA2.isSelected()) {
                algorithm = algorithmDSA2.getText();
            }
            else if (algorithmRSA2.isSelected()) {
                algorithm = algorithmRSA2.getText();
            }
            SignatureGenerator signatureGenerator = null;
            try {
                signatureGenerator = new SignatureGenerator(filePath2.getText(), signaturePath2.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if ( signatureGenerator.generateSignature()) {
                JOptionPane.showMessageDialog(frame, "Digital Signature created successfully", "Signing Result", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame, "Digital Signature creation unsuccessful", "Signing Result", JOptionPane.ERROR_MESSAGE);
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
        panel2.add(filePath2, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel2.add(fileButton2, gbc);

        // 2 row: algorithm selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(algorithmLabel2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(algorithmDSA2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(algorithmRSA2, gbc);

        // 3 row: signature name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(signatureLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(signaturePath2, gbc);

        // 4 row: public key name
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(publicKeyLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(publicKeyPath2, gbc);

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
        panel2.add(privateKeyPath, gbc);

        // 7 row: sign button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel2.add(signButton, gbc);

        frame.add(tabPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(650, 500);
        frame.setVisible(true);
    }
}
