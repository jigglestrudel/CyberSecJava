package org.example.verification;

public class Main {
    public static void main(String[] args) {
        try {
            Verifier verifier = new Verifier("testFiles/test.txt", "testFiles/signature", "testFiles/publicKey", "testFiles/algorithm");
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
    }
}