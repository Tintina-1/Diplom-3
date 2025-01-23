package utils;

import java.util.UUID;

public class TestDataUtils {

    public static String generateUniqueEmail() {
        return "test" + System.currentTimeMillis() + "@example.com";
    }

    public static String generateUniquePassword() {
        return "Pass" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateUniqueName() {
        return "User" + System.currentTimeMillis();
    }
}
