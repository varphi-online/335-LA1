import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;

@Testable
public class UserTest {

    @Test
    void createUserTest() throws Exception {

        User user = new User("userTest.csv", "test", "test");
        File file = new File("userTest.csv");
        assertTrue(file.exists(), "User file should exist after creation.");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] userInfo = line.split(", ");
        assertEquals("test", userInfo[0], "Username should match.");
        assertEquals(User.getCryptoHash(Base64.getDecoder().decode(userInfo[2]), "test", "test"), userInfo[1], "Password hash should match.");
        br.close();
        file.delete(); // Clean up the test file after the test
    }

    @Test
    void createUserNullSaltTest() throws Exception {
        User user = new User("userTest.csv", "test", "test");
        File file = new File("userTest.csv");
        assertTrue(file.exists(), "User file should exist after creation.");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] userInfo = line.split(", ");
        assertEquals("test", userInfo[0], "Username should match.");
        assertNotEquals(User.getCryptoHash(null, "test", "test"), userInfo[1], "Password hash should match.");
        br.close();
        file.delete(); // Clean up the test file after the test
    }

    @Test
    void loginTest() throws Exception {
        User user = new User("userTest.csv", "test", "test");
        assertTrue(User.login("userTest.csv", "test", "test"), "Login should succeed with correct credentials.");
        assertFalse(User.login("userTest.csv", "wrongUser", "wrongPass"), "Login should fail with incorrect credentials.");
        new File("userTest.csv").delete(); // Clean up the test file after the test
    }

    @Test
    void getLibraryTest() throws Exception {
        User user = new User("userTest.csv", "test", "test");
        assertNotNull(user.getLibrary(), "User library should not be null.");
        new File("userTest.csv").delete(); // Clean up the test file after the test
    }

    @Test
    void getUsernameTest() throws Exception {
        User user = new User("userTest.csv", "test", "test");
        assertEquals("test", user.getUsername(), "Username should match.");
        new File("userTest.csv").delete(); // Clean up the test file after the test
    }



    // public static void main(String[] args) throws Exception {}
}
