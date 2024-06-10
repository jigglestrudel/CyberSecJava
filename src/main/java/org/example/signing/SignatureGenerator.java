package org.example.signing;

import org.json.JSONObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignatureGenerator {
    private final String path;
    private final String algorithm;
    private final String signatureFileName;
    private final String settingsFileName = "settings.json";
    public SignatureGenerator(String path, String signatureFileName) throws IOException {
        this.path = path;
        this.algorithm = readAlgorithmFromSettings();
        this.signatureFileName = signatureFileName;
    }

    public String getHashFunction(String algorithm) {
        return switch (algorithm) {
            case "DSA" -> "SHA1withDSA";
            case "RSA" -> "SHA256withRSA";
            default -> "error";
        };
    }

    public boolean generateSignature() {
        try {
            PrivateKey privateKey;
            PublicKey publicKey;

            // Read keys from JSON file if they exist
            JSONObject settings = readOrInitializeJson(Paths.get("settings.json"));
            if (settings.has("username") && settings.getString("username").equals(getUsername())) {
                privateKey = loadPrivateKey(settings.getString("privateKey"));
            } else {
                KeyPairGenerator keyGenerated = KeyPairGenerator.getInstance(algorithm);
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                keyGenerated.initialize(1024, random);

                KeyPair keyPair = keyGenerated.generateKeyPair();
                privateKey = keyPair.getPrivate();
                publicKey = keyPair.getPublic();

                // Save the keys to JSON
                JSONObject keys = readOrInitializeJson(Paths.get("publicKeys.json"));
                saveKeysToSettings(privateKey, publicKey, settings);
                savePublicKeyToJson(publicKey, keys);
            }

            String hashFunction = getHashFunction(algorithm);
            Signature cipher = Signature.getInstance(hashFunction);
            cipher.initSign(privateKey);

            FileInputStream file = new FileInputStream(path);
            BufferedInputStream inputStream = new BufferedInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) >= 0) {
                cipher.update(buffer, 0, length);
            }
            inputStream.close();

            byte[] signature = cipher.sign();
            saveSignatureToFile(signature);

            addAlgorithmToMetadata(Paths.get(signatureFileName), algorithm);
            addUsernameToMetadata(Paths.get(signatureFileName),getUsername());
            return true;

        } catch (Exception e) {
            System.err.println("Exception occurred " + e);
            return false;
        }
    }

    private JSONObject readOrInitializeJson(Path filePath) {
        try {
            if (!Files.exists(filePath) || Files.readString(filePath).isBlank()) {
                return new JSONObject();
            }
            return new JSONObject(Files.readString(filePath));
        } catch (IOException | org.json.JSONException e) {
            return new JSONObject();
        }
    }
    private void saveKeysToSettings(PrivateKey privateKey, PublicKey publicKey, JSONObject settings) throws IOException {
        settings.put("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        settings.put("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        settings.put("username", getUsername());
        Files.writeString(Paths.get("settings.json"), settings.toString(4));
    }
    private void savePublicKeyToJson(PublicKey publicKey, JSONObject keysJSON) throws IOException {
        keysJSON.put(getUsername(), Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        Files.writeString(Paths.get("publicKeys.json"), keysJSON.toString(4));
    }

    private void saveSignatureToFile(byte[] signature) throws IOException {
        try (FileOutputStream fileSignature = new FileOutputStream(signatureFileName)) {
            fileSignature.write(signature);
        }
    }

    private void addAlgorithmToMetadata(Path filePath, String algorithm) throws IOException {
        UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
        view.write("user.algorithm", ByteBuffer.wrap(algorithm.getBytes(StandardCharsets.UTF_8)));
    }
    private void addUsernameToMetadata(Path filePath, String username) throws IOException {
        UserDefinedFileAttributeView view = Files.getFileAttributeView(filePath, UserDefinedFileAttributeView.class);
        view.write("user.username", ByteBuffer.wrap(username.getBytes(StandardCharsets.UTF_8)));
    }

    private String readAlgorithmFromSettings() throws IOException {
        Path settingsPath = Paths.get(this.settingsFileName);
        String content = Files.readString(settingsPath);
        JSONObject json = new JSONObject(content);
        return json.getString("algorithm").trim();
    }

    private String getUsername() {
        return System.getProperty("user.name");
    }

    private PrivateKey loadPrivateKey(String key) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(spec);
    }
}
