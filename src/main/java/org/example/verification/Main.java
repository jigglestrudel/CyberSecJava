package org.example.verification;

public class Main {
    public static void main(String[] args) {
        //testing for DSA algorithm
        try {
            Verifier verifier = new Verifier("testFiles/DSA_test/test.txt", "testFiles/DSA_test/signature", "testFiles/DSA_test/publicKey", "testFiles/DSA_test/algorithm");
            if (verifier.verifySignature()) {
                System.out.println("Digital signature is valid");
            }
            else {
                System.out.println("Digital signature is not valid");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //testing for RSA algorithm
        try {
            Verifier verifier = new Verifier("testFiles/RSA_test/test.txt", "testFiles/RSA_test/signature", "testFiles/RSA_test/publicKey", "testFiles/RSA_test/algorithm");
            if (verifier.verifySignature()) {
                System.out.println("Digital signature is valid");
            }
            else {
                System.out.println("Digital signature is not valid");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //testing for getting data from certificate
        /*try {
            Verifier verifier = new Verifier();
            if (verifier.verifySignature()) {
                System.out.println("Digital signature is valid");
            }
            else {
                System.out.println("Digital signature is not valid");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}