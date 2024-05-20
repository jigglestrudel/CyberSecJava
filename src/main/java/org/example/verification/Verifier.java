package org.example.verification;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
//źródło: https://docs.oracle.com/javase%2Ftutorial%2F/security/apisign/versig.html

public class Verifier {
    PublicKey publicKey;
    String signingAlgorithm;
    String filePath;
    byte[] signatureToVerify;

    public Verifier(String filePath, String signaturePath, String keyPath, String signingAlgorithmFile) {
        //verifier initialization using path to files passed by user
        try {
            Path pathToAlgorithmFile = Paths.get(signingAlgorithmFile);
            this.signingAlgorithm = getAlgorithmName(Files.readString(pathToAlgorithmFile));
            this.filePath = filePath;
            this.signatureToVerify = readSignatureBytes(signaturePath);
            //key encoding
            String keyCypheringAlgorithm = Files.readString(pathToAlgorithmFile);
            byte[] encodedKey = readEncodedPublicKey(keyPath);
            if (encodedKey == null) {
                return;
            }
            X509EncodedKeySpec publicKeySpecification = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(keyCypheringAlgorithm);
            this.publicKey = keyFactory.generatePublic(publicKeySpecification);
        }
        catch (IOException | NoSuchAlgorithmException |InvalidKeySpecException e) {
            System.out.println("An exception occured in creating verifier");
        }

    }
    static private String getAlgorithmName(String algorithm) {
        //gets name of algorithm used to sign file
        if (algorithm.equals("DSA")) {
            return "SHA1withDSA";
        }
        else if (algorithm.equals("RSA")) {
            return "SHA256withRSA";
        }
        else {
            System.out.println("Algotithm not found");
            return null;
        }
    }
    static private byte[] readEncodedPublicKey(String key_path) {
        //reads encrypted public key from file
        try {
            FileInputStream keyInputStream = new FileInputStream(key_path);
            byte[] encodedKey = new byte[keyInputStream.available()];
            keyInputStream.read(encodedKey);
            keyInputStream.close();
            return encodedKey;
        }
        catch (IOException e) {
            System.out.println("An exception occured in reading encoded public key");
            return null;
        }
    }
    static private byte[] readSignatureBytes(String signaturePath) {
        //reads digital signature of file as bytes
        try {
            FileInputStream signatureFileInputStream = new FileInputStream(signaturePath);
            byte[] signatureToVerify = new byte[signatureFileInputStream.available()];
            signatureFileInputStream.read(signatureToVerify);
            signatureFileInputStream.close();
            return signatureToVerify;
        }
        catch (IOException e) {
            System.out.println("An exception occured in reading signature bytes");
            return null;
        }
    }
    public boolean verifySignature() {
        //verifies digital signature of a file by generating new digital signature for file and comparing of keys
        try {
            if (signingAlgorithm == null) {
                return false;
            }
            Signature signature = Signature.getInstance(signingAlgorithm);
            signature.initVerify(publicKey);
            FileInputStream dataFileInputStream = new FileInputStream(filePath);
            BufferedInputStream bufferedInput = new BufferedInputStream(dataFileInputStream);
            byte[] buffer = new byte[1024];
            int len;
            while (bufferedInput.available() != 0) {
                len = bufferedInput.read(buffer);
                signature.update(buffer, 0, len);
            }
            bufferedInput.close();
            if (signatureToVerify != null) {
                return signature.verify(signatureToVerify);
            }
            else {
                return false;
            }
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException e) {
            System.out.println("An exception occured in veryfing signature");
            return false;
        }
    }
}
