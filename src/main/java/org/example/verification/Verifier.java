package org.example.verification;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Verifier {
    PublicKey publicKey;
    String signingAlgorithm;
    String filePath;
    byte[] signatureToVerify;

    public Verifier(String filePath, String signaturePath, String keyPath) {
        //verifier initialization using path to files passed by user
        try {
            this.filePath = filePath;
            this.signatureToVerify = readSignatureBytes(signaturePath);

            // Get the algorithm from file metadata
            this.signingAlgorithm = readAlgorithmFromMetadata(Paths.get(filePath));
            if (this.signingAlgorithm == null) {
                System.out.println("Could not read algorithm from metadata");
                return;
            }

            //key encoding
            byte[] encodedKey = readEncodedPublicKey(keyPath);
            if (encodedKey == null) {
                return;
            }
            X509EncodedKeySpec publicKeySpecification = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(getAlgorithmBase(this.signingAlgorithm));
            this.publicKey = keyFactory.generatePublic(publicKeySpecification);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("An exception occurred in creating verifier: " + e.getMessage());
        }
    }

    private String getAlgorithmBase(String algorithm) {
        if (algorithm.contains("DSA")) {
            return "DSA";
        } else if (algorithm.contains("RSA")) {
            return "RSA";
        } else {
            System.out.println("Algorithm not found");
            return null;
        }
    }

    private String readAlgorithmFromMetadata(Path filePath) {
        try {
            UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
            if (view == null) {
                System.out.println("UserDefinedFileAttributeView is not supported on this file system.");
                return null;
            }
            ByteBuffer buffer = ByteBuffer.allocate(view.size("user.algorithm"));
            view.read("user.algorithm", buffer);
            buffer.flip();
            return StandardCharsets.UTF_8.decode(buffer).toString();
        } catch (IOException e) {
            System.out.println("An exception occurred in reading algorithm from metadata: " + e.getMessage());
            return null;
        }
    }

    static private byte[] readEncodedPublicKey(String keyPath) {
        //reads encrypted public key from file
        try {
            FileInputStream keyInputStream = new FileInputStream(keyPath);
            byte[] encodedKey = new byte[keyInputStream.available()];
            keyInputStream.read(encodedKey);
            keyInputStream.close();
            return encodedKey;
        } catch (IOException e) {
            System.out.println("An exception occurred in reading encoded public key: " + e.getMessage());
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
        } catch (IOException e) {
            System.out.println("An exception occurred in reading signature bytes: " + e.getMessage());
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
            while ((len = bufferedInput.read(buffer)) != -1) {
                signature.update(buffer, 0, len);
            }
            bufferedInput.close();
            return signatureToVerify != null && signature.verify(signatureToVerify);
        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException e) {
            System.out.println("An exception occurred in verifying signature: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        // Example usage
        Verifier verifier = new Verifier("example.txt", "signature.sig", "public.key");
        boolean result = verifier.verifySignature();
        System.out.println("Signature verification " + (result ? "succeeded" : "failed"));
    }
}
