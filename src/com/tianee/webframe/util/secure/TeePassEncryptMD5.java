package com.tianee.webframe.util.secure;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import com.tianee.webframe.util.str.TeeUtility;


/**
 * 
 * @author syl
 *
 */
public class TeePassEncryptMD5 {
	
	static private final String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	static private final String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	static private final int startLength = 8;//开始字符串长度
	
	static private final int endLength = 4;//开始字符串长度
	
	private static Random myRand = null;
	private static SecureRandom mySecureRand = null;
	 private static String s_id = null;
	static {
	    mySecureRand = new SecureRandom();
	    long secureInitializer = mySecureRand.nextLong();
	    myRand = new Random(secureInitializer);
	    try {
	        s_id = InetAddress.getLocalHost().toString();
	      } catch (UnknownHostException e) {
	        e.printStackTrace();
	      }
	}

	/**
	 * 返回MD5加密  
	 * @param password
	 * @return
	 */
	static public String crypt(String password){
	   return DigestUtils.md5Hex(password);
    }
   
	
	/**
	 * 返回MD5加密  
	 * @param password
	 * @return
	 */
    static public boolean checkCrypt(String realPass ,String encryptPass){
	    if(TeeUtility.isNullorEmpty(encryptPass)){
		    return false;
	    }
	    return DigestUtils.md5Hex(realPass).equals(encryptPass);
	    
	    
    }
    
    
    /**
     * 动态获取MD5密码
     * @author syl
     * @date 2014-3-31
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
	static public final String cryptDynamic (String password)
			throws NoSuchAlgorithmException {
		StringBuffer salt = new StringBuffer();
		java.util.Random randgen = new java.util.Random();//随机取8个字符串
		while (salt.length() < startLength) {
			int index = (int) (randgen.nextFloat() * itoa64.length());
			salt.append(itoa64.substring(index, index + 1));
		}
		
		StringBuffer end = new StringBuffer();
		while (end.length() < endLength) {
			int index = (int) (randgen.nextFloat() * itoa64.length());
			end.append(itoa64.substring(index, index + 1));
		}
		return cryptDynamic(password, salt.toString() ,end.toString());
	}
	
	/**
	 * 获取动态MD5字符串
	 * @author syl
	 * @date 2014-3-31
	 * @param password
	 * @param salt
	 * @return
	 */
	static public final String cryptDynamic (String password , String salt , String end){
		String magic = "$1$";
		String md5Str =  DigestUtils.md5Hex(password);
		return magic + salt + "$" + md5Str + end;
	}
	
	/**
	 * 动态对比
	 * @author syl
	 * @date 2014-3-31
	 * @param realPass
	 * @param encryptPass
	 * @return
	 */
   static public boolean checkCryptDynamic(String realPass ,String encryptPass){
	    if(TeeUtility.isNullorEmpty(encryptPass)){
		    return false;
	    }
	    String realPassMd5 = DigestUtils.md5Hex(realPass);
	    if(realPassMd5.equals(encryptPass.substring(encryptPass.lastIndexOf("$")+ 1 , encryptPass.length() - endLength))){
	    	return true;
	    }
	    return false;
    }
    
    /*
     * 自动获取唯一Guid
     * Method to generate the random GUID
     * 
     */
    static public String getRandomGUID(){
      MessageDigest md5 = null;
      StringBuffer sbValueBeforeMD5 = new StringBuffer();
      try {
        md5 = MessageDigest.getInstance("MD5");
        long time = System.currentTimeMillis();
        long rand = 0;
        rand = myRand.nextLong();
        sbValueBeforeMD5.append(s_id);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));
        String seedingString = sbValueBeforeMD5.toString();
        md5.update(seedingString.getBytes());
        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < array.length; ++j) {
          int b = array[j] & 0xFF;
          if (b < 0X10) {
            sb.append('0');
          }
          sb.append(Integer.toHexString(b));
        }

       String  rawGUID = sb.toString();
       return rawGUID;
      } catch (NoSuchAlgorithmException ex) {
        
      } catch (Exception e) {
        //System.out.println("Error:" + e);
      }
      return  "";
    }
   
    
    
	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*String en = crypt("33d");
		System.out.println(checkCrypt("33d", en));*/
		
		System.out.println(crypt("love"));
		
		System.out.println(checkCrypt("love" , "b5c0b187fe309af0f4d35982fd961d7e"));
	}
}
