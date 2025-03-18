package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {
    private String username;
    private String passwordHash;
    private byte[] salt;
    private LibraryModel library;
    public User() {
    }

    public User(String authDB, String username, String password)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.username = username;

        // Salt is made once per user lifetime and saved in the database as a base64 string
        SecureRandom random = new SecureRandom();
        this.salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        this.passwordHash = getCryptoHash(this.salt, username, password);

        // Each user has an associated library object
        this.library = new LibraryModel();

        // Write the new user to the database
        File file = new File(authDB);
        FileWriter fr = new FileWriter(file, true);
        fr.write("%s, %s, %s\n".formatted(username, passwordHash, encodedSalt));
        fr.close();
    }

    public static Boolean login(String authDB, String username, String password) throws Exception {
        // Checks with auth db to see if username and password are correct
        BufferedReader br = new BufferedReader(new FileReader(authDB));
        String line;
        while ((line = br.readLine()) != null) {
            String[] userInfo = line.split(", ");
            byte[] saltBytes = Base64.getDecoder().decode(userInfo[2]);
            if (userInfo[0].equals(username) && userInfo[1].equals(getCryptoHash(saltBytes, username, password))) {
                br.close();
                return true;
            }
        }
        br.close();
        return false;
    }

    public static String getCryptoHash(byte[] salt, String username, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (salt == null) {
            SecureRandom random = new SecureRandom();
            salt = new byte[16];
            random.nextBytes(salt);
        }
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new String(hash);
    }

    public String getUsername() {
        return username;
    }

    public LibraryModel getLibrary() {
        return library;
    }
}
