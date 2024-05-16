package org.example;
import java.io.*;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;

//https://docs.oracle.com/javase%2Ftutorial%2F/security/apisign/gensig.html
public class SignatureGenerator {
    String path;
    String algorithm;

    public SignatureGenerator(String path, String algorithm){
        this.path=path;
        this.algorithm = algorithm;
    }

    public String getHashFunction(String algorithm){
        //algorytmy podpisu cyfrowego łączą funkcję skrótu z algorytmem podpisu.
        // Różne algorytmy podpisu wymagają różnych kombinacji funkcji skrótu i algorytmu podpisu
        if(algorithm.equals("DSA")){
            return "SHA1withDSA";
        }
        else if (algorithm.equals("RSA")){
            return  "SHA256withRSA";
        }
        else if (algorithm.equals("EC")){
            return "SHA256withRSA";
        }
        else return "error";
    }


    public void generateSignature(){
        try{
            //Generate a key pair (public key and private key). The private key is needed for signing the data.
            //The public key will be used by the VerSig program for verifying the signature.

            //A key pair is generated by using the KeyPairGenerator class.
            // generate a public/private key pair for the DSA. Keys will be with a 1024-bit length.
            KeyPairGenerator keyGenerated = KeyPairGenerator.getInstance(algorithm);

            //SecureRandom class provides a cryptographically strong random number generator
            //generowania kryptograficznie silnych liczb losowych
            if(algorithm.equals("EC")){
                AlgorithmParameterSpec ecKey =new ECGenParameterSpec("secp256r1");
                keyGenerated.initialize(ecKey);
            }
            else {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                keyGenerated.initialize(1024, random);
            }

            //Generate the key pair and store the keys in PrivateKey and PublicKey objects.
            KeyPair pair = keyGenerated.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            //A digital signature is created (or verified) using an instance of the Signature class
            String hashFunction = getHashFunction(algorithm);
            //Get a Signature object for generating or verifying signatures using the DSA algorithm
            Signature cipher = Signature.getInstance(hashFunction);
            cipher.initSign(privateKey);

            //read in the data a buffer at a time and supply it to the Signature object by update method
            FileInputStream file = new FileInputStream(path);
            BufferedInputStream inputStrem = new BufferedInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStrem.read(buffer)) >= 0) {
                cipher.update(buffer, 0, length);
            };
            inputStrem.close();

            //generate the digital signature of that data
            byte[] signature = cipher.sign();

            //save the signature in a file
            saveDataToFiles(signature, publicKey,algorithm);

        } catch (Exception e) {
            System.err.println("Exception occured " + e.toString());
        }
    }

    private static void saveDataToFiles(byte[] signature, PublicKey publicKey, String algorithm) throws IOException {
        FileOutputStream fileSignature = new FileOutputStream("signature");
        fileSignature.write(signature);
        fileSignature.close();

        // save the public key in a file
        byte[] key = publicKey.getEncoded();
        FileOutputStream fileKey = new FileOutputStream("publicKey");
        fileKey.write(key);
        fileKey.close();

        FileOutputStream fileAlgorithm = new FileOutputStream("algorithm");
        fileAlgorithm.write(algorithm.getBytes());
        fileAlgorithm.close();
    }

}
