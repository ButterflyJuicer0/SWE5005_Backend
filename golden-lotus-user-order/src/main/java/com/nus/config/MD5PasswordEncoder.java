//package com.nus.config;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.util.DigestUtils;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class MD5PasswordEncoder implements PasswordEncoder {
//
//    @Override
//    public String encode(CharSequence rawPassword) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
//            byte[] digest = md.digest();
//            StringBuilder sb = new StringBuilder();
//            for (byte b : digest) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error encoding password", e);
//        }
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        // 使用 DigestUtils.md5DigestAsHex 进行密码比对
//        String expected = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
//        return expected.equals(encodedPassword);
//    }
//
//    // Helper method to convert hex string to byte array
//    private static byte[] hexStringToByteArray(String s) {
//        int len = s.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
//                    + Character.digit(s.charAt(i + 1), 16));
//        }
//        return data;
//    }
//}
