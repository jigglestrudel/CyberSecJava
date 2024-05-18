package org.example.signing;

import org.example.signing.SignatureGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        String path ="C:\\Users\\DELL\\Desktop\\wera.txt";
        String algorithm = "RSA";
        String signatureFileName = "signature";
        String publicKeyFileName = "publicKey";
        String privateKeyFileName = "privateKey"; //null jak nie zapisujemy

        System.out.println("Hello world!");
        SignatureGenerator generatorPodpisow = new SignatureGenerator(path, algorithm,signatureFileName,privateKeyFileName, publicKeyFileName );
        generatorPodpisow.generateSignature();
        System.out.println("DONE");

        CertificateGenerator certificateGenerator=new CertificateGenerator(generatorPodpisow.getKeyPair(),generatorPodpisow.getSignature());
        certificateGenerator.generateCertificate();
        System.out.println("DONE");


    }
}