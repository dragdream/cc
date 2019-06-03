<%@page import="java.net.URLEncoder"%>
<%@page import="javax.crypto.Cipher"%>
<%@page import="java.security.PrivateKey"%>
<%@page import="java.security.KeyFactory"%>
<%@page import="java.security.spec.PKCS8EncodedKeySpec"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="sun.misc.BASE64Decoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userName = request.getParameter("userName");
	String name = request.getParameter("name");
	String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT1JkhUUTpGSFDiLA+xFrEnEl7TsZW6U31cuwXX9l+FqXD5EJnm9JmRDp46OABQP6h7RTi25eGqWpYE4sb5GK2D3U2Rl5Pg0ISehspjsNiKQQid+fHun+5xdZVMoYVFZwzL0JzNX6qB0Mz+QzwxRSJaQkddRqJWTb/8Vf8WqyyqwIDAQAB";  
	String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJPUmSFRROkZIUOIsD7EWsScSXtOxlbpTfVy7Bdf2X4WpcPkQmeb0mZEOnjo4AFA/qHtFOLbl4apalgTixvkYrYPdTZGXk+DQhJ6GymOw2IpBCJ358e6f7nF1lUyhhUVnDMvQnM1fqoHQzP5DPDFFIlpCR11GolZNv/xV/xarLKrAgMBAAECgYAN/xoEgopQu5VfrIhrWPHzHY0DhMDYp7w+2gOqbuPLwV8ufeUfpCw2jI0wt3PGCp5RPH42wG3HTTEJZ8hK+sOxrktsYjV//Nw5U5lWYS7P9cA9Jd1cCSetRKFi26gjOk3K6dXm/YUKB9LpmOlBl1JpPQSulwzPuhrq9/BktE+Z0QJBAMWD3C5Sf/C4l9WWIKF73SaHP4tVyynKiEMJpMAB4WqQ6q12JWz1bz5i8pvg9RRUmQyrdiJ6VapoB2X+dPi2ztMCQQC/moTkhViSfK6ClDflrh610vPq8LeFX7uLWqJ1nGIa+T60A/d/WEasH0dafwNMk9pvgywj5/+UITwBaZew/xXJAkA1wpQ1j4L/VF8PZZNwILSq/fkPxcRMlbHM/Vz4Xgqq5NLbb+Lz60HiKzB5uRs782LNDgZTkBStEy+gVUglQiy9AkAaWPE1S3Dmk+abZi5Zkxv9/Mg14mwaFxP/EbONYLfM5cFNl84M4OK3AIYbV6Pof9xlwS/ssBZhFEDgINkPKcShAkBUIrpqtoZ+/XWbeZbMvjde3+z0cxnx5lZxRfx7PgjzfQ/nikW7K3Y0nrYt77wYlUU+Iz2S+99XQtBWNqpfEiIR";

	byte[ ] keyBytes=new BASE64Decoder().decodeBuffer(new ByteArrayInputStream(privateKeyString.getBytes()));
    PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
    
    userName = userName.replace(" ", "+");
    byte b[] = new BASE64Decoder().decodeBuffer(userName);
    Cipher cipher=Cipher.getInstance("RSA");  
    cipher.init(Cipher.DECRYPT_MODE, privateKey);  
    
    byte content[] = cipher.doFinal(b);
   
    String userName1 = new String(content);
    
    name = name.replace(" ", "+");
    b = new BASE64Decoder().decodeBuffer(name);
    content = cipher.doFinal(b);
    
    String name1 = new String(content);
//     System.out.println(new String(name1.getBytes("GBK"),"UTF-8"));
	response.sendRedirect("/singleLogin4Prcs.jsp?userName="+URLEncoder.encode(userName1,"UTF-8")+"&name="+URLEncoder.encode(name1,"UTF-8"));
    
	
%>