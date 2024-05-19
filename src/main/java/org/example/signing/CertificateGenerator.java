package org.example.signing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CertificateGenerator {
    public static void generateCertificate() throws Exception {
        String keystore = "mykeystore.jks";
        String storepass = "changeit";
        String keypass = "changeit";
        String alias = "pg_alias";//to trzbea zmieniac, do kazdego certyfikatu inne
        String certfile = "pg_ossip.cer";
        String dname = "CN=pg_ossip, OU=gossips, O=pg, L=Gdańsk, S=pomorskie, C=Polska";

        // Komenda do generowania pary kluczy
        String keytoolGenCmd = String.format(
                "keytool -genkeypair -alias %s -keyalg RSA -keysize 2048 -keystore %s -storepass %s -keypass %s -dname \"%s\"",
                alias, keystore, storepass, keypass, dname);

        // Komenda do eksportowania certyfikatu
        String keytoolExportCmd = String.format(
                "keytool -exportcert -alias %s -file %s -keystore %s -storepass %s",
                alias, certfile, keystore, storepass);

        // Uruchomienie komend
        executeCommand(keytoolGenCmd);
        executeCommand(keytoolExportCmd);
    }

    private static void executeCommand(String command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
