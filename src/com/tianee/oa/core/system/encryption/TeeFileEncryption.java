package com.tianee.oa.core.system.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 通用加密
 * @author nieyi
 *
 */
public class TeeFileEncryption{
	/**
	 * 加密算法
	 * XXTEA128    XXTEA256    BLOWFISH128      BLOWFISH256    RC2128    RC2256
	 */
	private String encryAlgo;
	
	private String key128="beijingzatpkejiyouxiangongsi0001";
	
	private String key256="beijingzatpkejiyouxiangongsi0001beijingzatpkejiyouxiangongsi0001";
	
	public TeeFileEncryption(String encryAlgo){
		this.encryAlgo = encryAlgo;
	}
	/**
	 * 具体加密方法
	 * @param fis
	 * @return
	 */
	public InputStream encrypt(byte[] data){
		InputStream is=null;
		try{
			byte[] result = null;
			if(encryAlgo.equals("XXTEA128")){
				if (data.length == 0) {
					return null;
				}
				try {
					result = TeeXXTEA.encrypt(data, key128.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				is = new ByteArrayInputStream(result);
				return is;
				
			}else if(encryAlgo.equals("XXTEA256")){
				if (data.length == 0) {
					return null;
				}
				try {
					result = TeeXXTEA.encrypt(data, key256.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				is = new ByteArrayInputStream(result);
				return is;
			}else if(encryAlgo.equals("BLOWFISH128")){
				if (data.length == 0) {
					return null;
				}
				TeeBlowfish bf = new TeeBlowfish();
				try {
					bf.init(true, key128.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = bf.encrypt(data);
				is = new ByteArrayInputStream(result);
				return is;
			}else if(encryAlgo.equals("BLOWFISH256")){
				if (data.length == 0) {
					return null;
				}
				TeeBlowfish bf = new TeeBlowfish();
				try {
					bf.init(true, key256.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = bf.encrypt(data);
				is = new ByteArrayInputStream(result);
				return is;
			}else if(encryAlgo.equals("RC2128")){
				
			}else if(encryAlgo.equals("RC2256")){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getEncryAlgo() {
		return encryAlgo;
	}

	public void setEncryAlgo(String encryAlgo) {
		this.encryAlgo = encryAlgo;
	}
	
}