package com.bookstore.utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtils {

    // Hash password (dùng khi đăng ký, tạo mới tài khoản)
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // So sánh password người dùng nhập với password trong DB
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
