package mini_project.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static final String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static final boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
