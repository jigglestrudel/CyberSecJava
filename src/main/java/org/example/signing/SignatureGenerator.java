package org.example.signing;
import java.io.*;
import java.security.*;

//https://docs.oracle.com/javase%2Ftutorial%2F/security/apisign/gensig.html
public class SignatureGenerator {
    private final String path;
    private final String algorithm;
    private final String publicKeyFileName;
    private final String signatureFileName;
    private final String privateKeyFileName;
    public SignatureGenerator(String path, String algorithm, String signatureFileName,String privateKeyFileName, String publicKeyFileName){
        this.path=path;
        this.algorithm = algorithm;
        this.signatureFileName = signatureFileName;
        this.privateKeyFileName = privateKeyFileName;
        this.publicKeyFileName = publicKeyFileName;
    }
    public String getHashFunction(String algorithm){
        //algorytmy podpisu cyfrowego łączą funkcję skrótu z algorytmem podpisu.
        // Różne algorytmy podpisu wymagają różnych kombinacji funkcji skrótu i algorytmu podpisu
        return switch (algorithm) {
            case "DSA" -> "SHA1withDSA";
            case "RSA" -> "SHA256withRSA";
            default -> "error";
        };
    }


    public boolean generateSignature(){
        try{
            //A key pair is generated by using the KeyPairGenerator class.
            //The private key is needed for signing the data.
            //The public key will be used by the VerSig program for verifying the signature.
            KeyPairGenerator keyGenerated = KeyPairGenerator.getInstance(algorithm);

            //SecureRandom class provides a cryptographically strong random number generator
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGenerated.initialize(1024, random);

            //Generate the key pair and store the keys in PrivateKey and PublicKey objects.
            KeyPair keyPair = keyGenerated.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            //A digital signature is created using an instance of the Signature class
            String hashFunction = getHashFunction(algorithm);

            //Get a Signature object for generating signatures using algorithm
            Signature cipher = Signature.getInstance(hashFunction);
            cipher.initSign(privateKey);

            //read in the data a buffer at a time and supply it to the Signature object by update method
            FileInputStream file = new FileInputStream(path);
            BufferedInputStream inputStrem = new BufferedInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStrem.read(buffer)) >= 0) {
                cipher.update(buffer, 0, length);
            }
            inputStrem.close();

            //generate the digital signature of that data
            byte [] signature = cipher.sign();

            //save the signature, public key and private key in files
            saveDataToFiles(signature, publicKey, privateKey);
            return true;

        } catch (Exception e) {
            System.err.println("Exception occured " + e);
            return false;
        }
    }

    private void saveDataToFiles(byte[] signature, PublicKey publicKey, PrivateKey privateKey) throws IOException {
        FileOutputStream fileSignature = new FileOutputStream(signatureFileName);
        fileSignature.write(signature);
        fileSignature.close();

        // save the public key in a file
        byte[] key = publicKey.getEncoded();
        FileOutputStream fileKey = new FileOutputStream(publicKeyFileName);
        fileKey.write(key);
        fileKey.close();

        if(privateKeyFileName != null && !privateKeyFileName.isEmpty()) {
            byte[] priv = privateKey.getEncoded();
            FileOutputStream privateFileKey = new FileOutputStream(privateKeyFileName);
            privateFileKey.write(priv);
            privateFileKey.close();
        }
    }

}
