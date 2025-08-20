// YAN加密工具类
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {
    // CBC模式加密
    public static String encrypt(String plainText, String keyStr) throws Exception {
        if (keyStr.getBytes().length != 16 && keyStr.getBytes().length != 24 && keyStr.getBytes().length != 32) {
            throw new IllegalArgumentException("密钥长度必须为16/24/32字节");
        }
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = keyStr.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        
        // 生成随机IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        
        // 返回Base64编码的字符串（包含IV+加密内容）
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encrypted);
    }
    
    // 解密方法
    public static String decrypt(String cipherText, String keyStr) throws Exception {
        if (keyStr.getBytes().length != 16 && keyStr.getBytes().length != 24 && keyStr.getBytes().length != 32) {
            throw new IllegalArgumentException("密钥长度必须为16/24/32字节");
        }
        
        String[] parts = cipherText.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encrypted = Base64.getDecoder().decode(parts[1]);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }
    
    // 生成随机密钥
    public static String generateKey(int keySize) {
        byte[] key = new byte[keySize];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}