package com.tianee.test;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;


public class SocketTest {
	
	public static String data="hello world";  
	public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT1JkhUUTpGSFDiLA+xFrEnEl7TsZW6U31cuwXX9l+FqXD5EJnm9JmRDp46OABQP6h7RTi25eGqWpYE4sb5GK2D3U2Rl5Pg0ISehspjsNiKQQid+fHun+5xdZVMoYVFZwzL0JzNX6qB0Mz+QzwxRSJaQkddRqJWTb/8Vf8WqyyqwIDAQAB";  
	public static String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJPUmSFRROkZIUOIsD7EWsScSXtOxlbpTfVy7Bdf2X4WpcPkQmeb0mZEOnjo4AFA/qHtFOLbl4apalgTixvkYrYPdTZGXk+DQhJ6GymOw2IpBCJ358e6f7nF1lUyhhUVnDMvQnM1fqoHQzP5DPDFFIlpCR11GolZNv/xV/xarLKrAgMBAAECgYAN/xoEgopQu5VfrIhrWPHzHY0DhMDYp7w+2gOqbuPLwV8ufeUfpCw2jI0wt3PGCp5RPH42wG3HTTEJZ8hK+sOxrktsYjV//Nw5U5lWYS7P9cA9Jd1cCSetRKFi26gjOk3K6dXm/YUKB9LpmOlBl1JpPQSulwzPuhrq9/BktE+Z0QJBAMWD3C5Sf/C4l9WWIKF73SaHP4tVyynKiEMJpMAB4WqQ6q12JWz1bz5i8pvg9RRUmQyrdiJ6VapoB2X+dPi2ztMCQQC/moTkhViSfK6ClDflrh610vPq8LeFX7uLWqJ1nGIa+T60A/d/WEasH0dafwNMk9pvgywj5/+UITwBaZew/xXJAkA1wpQ1j4L/VF8PZZNwILSq/fkPxcRMlbHM/Vz4Xgqq5NLbb+Lz60HiKzB5uRs782LNDgZTkBStEy+gVUglQiy9AkAaWPE1S3Dmk+abZi5Zkxv9/Mg14mwaFxP/EbONYLfM5cFNl84M4OK3AIYbV6Pof9xlwS/ssBZhFEDgINkPKcShAkBUIrpqtoZ+/XWbeZbMvjde3+z0cxnx5lZxRfx7PgjzfQ/nikW7K3Y0nrYt77wYlUU+Iz2S+99XQtBWNqpfEiIR";
    
	
	public static void main(String[] args) throws Exception{
		
		//获取公钥   
//        PublicKey publicKey=getPublicKey(publicKeyString);  
          
        //获取私钥   
        PrivateKey privateKey=getPrivateKey(privateKeyString);
        
        //私钥解密  
        String userName = "hx+xLCYgQgdhhtjoXNCA76USXjt8HSZ2Tbp/QBVSz5FPt5bxCXBKitdo8r7P+UrVduEq2kgsZe4Hls4zNF6LFqopC6ACBaBU0e4gF8G5nQvQnma0BVzU+VbhvFdYXIPa+PXPRngLYNXlm71h2M759if3meVEdyAzIJrXBD/2c4g=";
        byte b[] = new BASE64Decoder().decodeBuffer(userName);
        byte[] decryptedBytes=decrypt(b, privateKey);        
        System.out.println("解密后："+new String(decryptedBytes));
	
	}
	
	
	//将base64编码后的公钥字符串转成PublicKey实例  
    public static PublicKey getPublicKey(String publicKey) throws Exception{  
        byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(new ByteArrayInputStream(publicKey.getBytes()));
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePublic(keySpec);    
    }  
      
    //将base64编码后的私钥字符串转成PrivateKey实例  
    public static PrivateKey getPrivateKey(String privateKey) throws Exception{  
        byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(new ByteArrayInputStream(privateKey.getBytes()));
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePrivate(keySpec);  
    }  
      
    //公钥加密  
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(content);  
    }  
      
    //私钥解密  
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(content);  
    }  
	
}
