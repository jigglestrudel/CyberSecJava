package org.example.verification;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
//źródło: https://docs.oracle.com/javase%2Ftutorial%2F/security/apisign/versig.html

public class Verifier {
    //weryfikator musi sprawdzać podpis pliku na podstawie podanego klucza i algorytmu lub certyfikatu
    //musimy wczytać plik z podpisem, plik, oraz pobrać informacje z certyfikatu lub odczytać klucz z pliku oraz użyty algorytm
    PublicKey publicKey;
    String signingAlgorithm;
    String filePath;
    byte[] signatureToVerify;

    public Verifier(String filePath, String signaturePath, String keyPath, String signingAlgorithmFile) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        //inicjalizacja weryfikatora podpisów dla podanych przez użytkownika plików
        this.signingAlgorithm = Files.readString(Paths.get(signingAlgorithmFile));
        this.filePath = filePath;
        byte[] encodedKey = readEncodedPublicKey(keyPath);
        X509EncodedKeySpec publicKeySpecification = new X509EncodedKeySpec(encodedKey);
        String algorithm = publicKeySpecification.getAlgorithm();
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        this.publicKey = keyFactory.generatePublic(publicKeySpecification);
        this.signatureToVerify = readSignatureBytes(signaturePath);
    }
    static private byte[] readEncodedPublicKey(String key_path) throws IOException {
        //reads encrypted public key from file
        FileInputStream keyInputStream = new FileInputStream(key_path);
        byte[] encodedKey = new byte[keyInputStream.available()];
        keyInputStream.read(encodedKey);
        keyInputStream.close();
        return encodedKey;
    }
    static private byte[] readSignatureBytes(String signaturePath) throws IOException {
        //reads digital signature of file as bytes
        FileInputStream signatureFileInputStream = new FileInputStream(signaturePath);
        byte[] signatureToVerify = new byte[signatureFileInputStream.available()];
        signatureFileInputStream.read(signatureToVerify);
        signatureFileInputStream.close();
        return signatureToVerify;
    }
    public boolean verifySignature() throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        //verifies digital signature of a file by generating new digital signature for file and comparing of keys
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
        return signature.verify(signatureToVerify);
    }
}
