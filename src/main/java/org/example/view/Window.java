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

        // PANEL 1
        JLabel fileLabel = new JLabel("Select a file:");
        JTextField selectedFilePathField = new JTextField(25);
        selectedFilePathField.setEditable(false);
        JButton browseButton = new JButton("Browse");
        browseButton.setBackground(new Color(255, 128, 174));
        browseButton.setForeground(Color.WHITE);

        JLabel signatureLabel = new JLabel("Select a signature:");
        JTextField signatureField = new JTextField(25);
        signatureField.setEditable(false);
        JButton signatureButton = new JButton("Browse");
        signatureButton.setBackground(new Color(255, 128, 174));
        signatureButton.setForeground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();
        JRadioButton publicKey = new JRadioButton("Public key");
        publicKey.setBackground(new Color(255, 218, 232));
        JRadioButton certificate = new JRadioButton("Certificate");
        certificate.setBackground(new Color(255, 218, 232));
        bg.add(publicKey);
        bg.add(certificate);

        JLabel publicKeyLabel = new JLabel("Public key:");
        JTextField publicKeyField = new JTextField(25);
        publicKeyLabel.setVisible(false);
        publicKeyField.setVisible(false);
        JLabel algLabel = new JLabel("Choose a signing algorithm:");
        ButtonGroup bg2 = new ButtonGroup();
        JRadioButton algDSA = new JRadioButton("DSA");
        algDSA.setBackground(new Color(255, 218, 232));
        JRadioButton algRSA = new JRadioButton("RSA");
        algRSA.setBackground(new Color(255, 218, 232));
        JRadioButton algEC = new JRadioButton("EC");
        algEC.setBackground(new Color(255, 218, 232));
        bg2.add(algDSA);
        bg2.add(algRSA);
        bg2.add(algEC);
        algLabel.setVisible(false);
        algDSA.setVisible(false);
        algRSA.setVisible(false);
        algEC.setVisible(false);

        JLabel certificateLabel = new JLabel("Choose a certificate:");
        JTextField certificateField = new JTextField(25);
        certificateField.setEditable(false);
        JButton certificateButton = new JButton("Browse");
        certificateButton.setBackground(new Color(255, 128, 174));
        certificateButton.setForeground(Color.WHITE);
        certificateLabel.setVisible(false);
        certificateField.setVisible(false);
        certificateButton.setVisible(false);

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

        publicKey.addActionListener(e -> {
            publicKeyLabel.setVisible(true);
            publicKeyField.setVisible(true);
            algLabel.setVisible(true);
            algDSA.setVisible(true);
            algRSA.setVisible(true);
            algEC.setVisible(true);

            certificateLabel.setVisible(false);
            certificateField.setVisible(false);
            certificateButton.setVisible(false);
        });

        certificate.addActionListener(e -> {
            certificateLabel.setVisible(true);
            certificateField.setVisible(true);
            certificateButton.setVisible(true);

            publicKeyLabel.setVisible(false);
            publicKeyField.setVisible(false);
            algLabel.setVisible(false);
            algDSA.setVisible(false);
            algRSA.setVisible(false);
            algEC.setVisible(false);
        });

        verifyButton.addActionListener(e -> {

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
        panel1.add(selectedFilePathField, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(browseButton, gbc);

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
        panel1.add(signatureField, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(signatureButton, gbc);

        // 3 row: public key or certificate
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(publicKey, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(certificate, gbc);

        // 4 row: public key / certificate
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(publicKeyLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(publicKeyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(certificateLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(certificateField, gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(certificateButton, gbc);

        // 5 row: algorithm selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(algLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(algDSA, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(algRSA, gbc);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(algEC, gbc);

        // 6 row: verify button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
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
        ButtonGroup bg3 = new ButtonGroup();
        JRadioButton algorithmDSA = new JRadioButton("DSA");
        algorithmDSA.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmRSA = new JRadioButton("RSA");
        algorithmRSA.setBackground(new Color(255, 218, 232));
        JRadioButton algorithmEC = new JRadioButton("EC");
        algorithmEC.setBackground(new Color(255, 218, 232));
        bg3.add(algorithmDSA);
        bg3.add(algorithmRSA);
        bg3.add(algorithmEC);

        JLabel signatureLabel2 = new JLabel("Signature name:");
        JTextField signatureField2 = new JTextField(25);
        JLabel publicKeyLabel2 = new JLabel("Public key name:");
        JTextField publicKeyField2 = new JTextField(25);
        JCheckBox generatePrivateKey = new JCheckBox("Save private key?");
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

        signButton.addActionListener(e -> {

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
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(algorithmDSA, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(algorithmRSA, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel2.add(algorithmEC, gbc);

        // 3 row: signature name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(signatureLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(signatureField2, gbc);

        // 4 row: public key name
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(publicKeyLabel2, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(publicKeyField2, gbc);

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

        frame.add(tabPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}
