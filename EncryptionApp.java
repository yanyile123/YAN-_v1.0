// YAN加密工具主程序
import java.util.Scanner;

public class EncryptionApp {
    public static void main(String[] args) {
        boolean running = true;
        
        // 显示欢迎信息
        System.out.println("==================================");
        System.out.println("      YAN加密解密工具 v1.0       ");
        System.out.println("==================================");

        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                showMenu();
                System.out.print("请选择操作(1-3): ");
                
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    
                    switch (choice) {
                        case 1 -> {
                            try {
                                processEncryption(scanner);
                            } catch (Exception e) {
                                System.out.println("加密操作异常：" + e.getMessage());
                            }
                        }
                        case 2 -> {
                            try {
                                processDecryption(scanner);
                            } catch (Exception e) {
                                System.out.println("解密操作异常：" + e.getMessage());
                            }
                        }
                        case 3 -> {
                            running = false;
                            System.out.println("感谢使用YAN加密工具！再见！");
                        }
                        default -> System.out.println("无效选择，请输入1-3之间的数字");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("输入错误：请输入有效的数字（1-3）");
                } catch (Exception e) {
                    System.out.println("操作异常：" + e.getMessage());
                }
                
                System.out.println();
            }
        }
    }
    
    // 显示菜单
    private static void showMenu() {
        System.out.println("\n======= 功能菜单 =======");
        System.out.println("1. 加密文本");
        System.out.println("2. 解密文本");
        System.out.println("3. 退出程序");
        System.out.println("=========================");
    }
    
    // 处理加密操作
    private static void processEncryption(Scanner scanner) throws Exception {
        System.out.print("请输入要加密的文本: ");
        String plainText = scanner.nextLine();
        
        System.out.print("请输入密钥（建议长度：16/24/32字符）: ");
        String key = scanner.nextLine();
        
        // 检查密钥长度
        if (key.getBytes().length < 16) {
            System.out.println("警告：密钥过短，建议使用更长的密钥以确保安全");
        }
        
        String encrypted = AESUtil.encrypt(plainText, key);
        System.out.println("加密结果（Base64格式）: " + encrypted);
    }
    
    // 处理解密操作
    private static void processDecryption(Scanner scanner) throws Exception {
        System.out.print("请输入要解密的文本（Base64格式）: ");
        String cipherText = scanner.nextLine();
        
        System.out.print("请输入解密密钥: ");
        String key = scanner.nextLine();
        
        String decrypted = AESUtil.decrypt(cipherText, key);
        System.out.println("解密结果: " + decrypted);
    }
}