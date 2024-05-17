package org.example.signing;

import org.example.signing.SignatureGenerator;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SignatureGenerator generatorPodpisow = new SignatureGenerator("C:\\Users\\DELL\\Desktop\\wera.txt", "RSA");
        generatorPodpisow.generateSignature();
        System.out.println("DONE");

    }
}