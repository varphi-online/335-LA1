package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {
    private final String username;
    private final String passwordHash;
    private LibraryModel library;

    public User(String authDB, String username, String password) throws Exception {
        this.username = username;
        this.passwordHash = getCryptoHash(password);
        this.library = new LibraryModel();
        File file = new File(authDB);
        FileWriter fr = new FileWriter(file, true);
        fr.write("%s, %s\n".formatted(username,passwordHash));
        fr.close();
    }


    public static Boolean login(String authDB, String username, String password) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(authDB));
        String line;
        while ((line = br.readLine()) != null) {
            String[] userInfo = line.split(", ");
        if (userInfo[0].equals(username) && userInfo[1].equals(getCryptoHash(password))) {
            br.close();
            return true;
        }
    } return false;
    }

    public static String getCryptoHash(String password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash);
    }
}
