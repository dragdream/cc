package com.tianee.oa.core.system.encryption;



public class TeeDealInputStream{
	
	public byte[] dealInputStream(byte[] srcByte,byte[] deByte,int encryCount){
		byte[] destByte=null;
		int srcLength=0;
		int deLength=0;
		try {
			//解密后流大小
			srcLength = srcByte.length;
			//解密返回的流
			deLength = deByte.length;
			destByte = new byte[srcLength-encryCount+deLength];
			System.arraycopy(deByte, 0, destByte, 0,deLength);
			System.arraycopy(srcByte, encryCount, destByte, deLength,srcLength-encryCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destByte;
	}

}