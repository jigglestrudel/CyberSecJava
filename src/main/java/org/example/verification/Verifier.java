package org.example.verification;

import org.json.JSONObject;

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
    public String username;
    String signingAlgorithm;
    String filePath;
    String publicKeysPath;
    byte[] signatureToVerify;

    public Verifier(String filePath, String signaturePath) {
        try {
            this.filePath = filePath;
            this.signatureToVerify = readSignatureBytes(signaturePath);
            publicKeysPath = "publicKeys.json";

            // Get the algorithm from file metadata
            this.signingAlgorithm = readAlgorithmFromMetadata(Paths.get(signaturePath));
            if (this.signingAlgorithm == null) {
                System.out.println("Could not read algorithm from metadata");
                return;
            }

            // Get the username from file metadata
            this.username = readUsernameFromMetadata(Paths.get(signaturePath));
            if (username == null) {
                System.out.println("Could not read username from metadata");
                return;
            }

            // Load public keys from JSON file
            JSONObject publicKeys = readPublicKeys(publicKeysPath);
            assert publicKeys != null;
            if (!publicKeys.has(username)) {
                System.out.println("User not found in public keys file");
                return;
            }

            // Get the public key for the specified username
            String encodedKeyString = publicKeys.optString(username, null);
            if (encodedKeyString == null) {
                System.out.println("User not found in public keys file");
                return;
            }

            // Decode the public key
            byte[] encodedKey = java.util.Base64.getDecoder().decode(encodedKeyString);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(signingAlgorithm);
            this.publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("An exception occurred in creating verifier: " + e.getMessage());
        }
    }
    public String getHashFunction(String algorithm) {
        return switch (algorithm) {
            case "DSA" -> "SHA1withDSA";
            case "RSA" -> "SHA256withRSA";
            default -> "error";
        };
    }

    private String readAlgorithmFromMetadata(Path filePath) {
        try {
            UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
            if (view == null) {
                System.out.println("UserDefinedFileAttributeView is not supported on this file system.");
                return null;
            }
            System.out.println(view.list());
            ByteBuffer buffer = ByteBuffer.allocate(view.size("user.algorithm"));
            view.read("user.algorithm", buffer);
            buffer.flip();
            return StandardCharsets.UTF_8.decode(buffer).toString();
        } catch (IOException e) {
            System.out.println("An exception occurred in reading algorithm from metadata: " + e.getMessage());
            return null;
        }
    }

    public String readUsernameFromMetadata(Path filePath) {
        try {
            UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
            if (view == null) {
                System.out.println("UserDefinedFileAttributeView is not supported on this file system.");
                return null;
            }
            ByteBuffer buffer = ByteBuffer.allocate(view.size("user.username"));
            view.read("user.username", buffer);
            buffer.flip();
            return StandardCharsets.UTF_8.decode(buffer).toString();
        } catch (IOException e) {
            System.out.println("An exception occurred in reading username from metadata: " + e.getMessage());
            return null;
        }
    }

    static private byte[] readSignatureBytes(String signaturePath) {
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
        try {
            if (signingAlgorithm == null || publicKey == null) {
                return false;
            }
            Signature signature = Signature.getInstance(getHashFunction(signingAlgorithm));
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

    private JSONObject readPublicKeys(String publicKeysPath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(publicKeysPath)), StandardCharsets.UTF_8);
            return new JSONObject(content);
        } catch (IOException e) {
            System.out.println("An exception occurred in reading public keys: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Verifier verifier = new Verifier("example.txt", "signature.sig" );
        boolean result = verifier.verifySignature();
        System.out.println("Signature verification " + (result ? "succeeded" : "failed"));
    }
}
