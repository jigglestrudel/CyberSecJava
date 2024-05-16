package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SignatureGenerator generatorPodpisow = new SignatureGenerator("C:\\Users\\kules\\Desktop\\studia\\aga.txt", "EC");
        generatorPodpisow.generateSignature();
        System.out.println("DONE");

    }
}