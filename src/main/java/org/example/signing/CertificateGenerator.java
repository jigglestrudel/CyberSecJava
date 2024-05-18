package org.example.signing;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.util.Date;
import java.util.Set;

public class CertificateGenerator {
    private KeyPair keyPair;
    private Signature signature;

    public CertificateGenerator(KeyPair keyPair,Signature signature){
        this.keyPair=keyPair;
        this.signature=signature;
    }

    public void generateCertificate() throws Exception {
        // Utwórz obiekt do generowania certyfikatu
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

        // Ustaw odpowiednie wartości certyfikatu
        Date startDate = new Date();
        Date expiryDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000L); // 1 rok ważności
        X509Certificate cert = new X509CertificateImpl(keyPair.getPublic(), startDate, expiryDate);

        // Podpisz certyfikat
        signature.update(cert.getTBSCertificate());
        byte[] signedBytes = signature.sign();
        cert.verify(keyPair.getPublic());
        saveCertificateToFile(cert,"certificate");
    }

    public void saveCertificateToFile(X509Certificate certificate, String filePath) throws Exception {
        // Inicjalizuj FileOutputStream z podaną ścieżką do pliku
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Pobierz bajty certyfikatu
            byte[] certBytes = certificate.getEncoded();
            // Zapisz bajty certyfikatu do pliku
            fos.write(certBytes);
            // Zwolnij zasoby strumienia
            fos.close();
            System.out.println("Certificate saved to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementacja własnej klasy X509Certificate, ponieważ nie można użyć konstruktora X509Certificate
    private static class X509CertificateImpl extends X509Certificate {
        private PublicKey publicKey;
        private Date startDate;
        private Date expiryDate;

        public X509CertificateImpl(PublicKey publicKey, Date startDate, Date expiryDate) {
            this.publicKey = publicKey;
            this.startDate = startDate;
            this.expiryDate = expiryDate;
        }

        @Override
        public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
            Date currentDate = new Date();
            if (currentDate.before(startDate)) {
                throw new CertificateNotYetValidException("Certificate not yet valid");
            }
            if (currentDate.after(expiryDate)) {
                throw new CertificateExpiredException("Certificate expired");
            }
        }

        @Override
        public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {

        }

        @Override
        public int getVersion() {
            // Implementacja pominięta
            return 0;
        }

        @Override
        public BigInteger getSerialNumber() {
            return null;
        }

        @Override
        public Principal getIssuerDN() {
            return null;
        }

        @Override
        public Principal getSubjectDN() {
            return null;
        }

        @Override
        public Date getNotBefore() {
            return null;
        }

        @Override
        public Date getNotAfter() {
            return null;
        }

        @Override
        public byte[] getTBSCertificate() throws CertificateEncodingException {
            return new byte[0];
        }

        @Override
        public byte[] getSignature() {
            return new byte[0];
        }

        @Override
        public String getSigAlgName() {
            return null;
        }

        @Override
        public String getSigAlgOID() {
            return null;
        }

        @Override
        public byte[] getSigAlgParams() {
            return new byte[0];
        }

        @Override
        public boolean[] getIssuerUniqueID() {
            return new boolean[0];
        }

        @Override
        public boolean[] getSubjectUniqueID() {
            return new boolean[0];
        }

        @Override
        public boolean[] getKeyUsage() {
            return new boolean[0];
        }

        @Override
        public int getBasicConstraints() {
            return 0;
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);

                // Zapisz numer wersji certyfikatu
                dos.writeInt(11111);
                // Zapisz numer seryjny
                dos.writeLong(11111);
                // Zapisz datę ważności "NotBefore"
                dos.writeLong(startDate.getTime());
                // Zapisz datę ważności "NotAfter"
                dos.writeLong(expiryDate.getTime());
                // Zapisz klucz publiczny
                byte[] publicKeyBytes = publicKey.getEncoded();
                dos.writeInt(publicKeyBytes.length);
                dos.write(publicKeyBytes);

                byte[] certBytes = bos.toByteArray();

                bos.close();
                dos.close();

                return certBytes;
            } catch (IOException e) {
                throw new CertificateEncodingException("Error encoding certificate: " + e.getMessage());
            }
        }

        @Override
        public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

        }

        @Override
        public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {

        }

        @Override
        public String toString() {
            return null;
        }

        @Override
        public PublicKey getPublicKey() {
            return null;
        }

        @Override
        public boolean hasUnsupportedCriticalExtension() {
            return false;
        }

        @Override
        public Set<String> getCriticalExtensionOIDs() {
            return null;
        }

        @Override
        public Set<String> getNonCriticalExtensionOIDs() {
            return null;
        }

        @Override
        public byte[] getExtensionValue(String oid) {
            return new byte[0];
        }

    }
}
