package com.tianee.test.syl;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.mvel2.MVEL;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector.Matcher;
public class TeeTest {






	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String temp = "s1=0.2*0.4*0.5";
		Map varseee = new HashMap();
		MVEL.eval(temp, varseee);
		System.out.println(varseee);
		
		/*String expression = "if(foobar < temp1){a=12;foobar = a;"
				
				+ "}else if(foobar >= temp1 && foobar < temp2){"
				+ "foobar=8000;" 
				+ "}else{"
				+ "foobar=2;}";
		Map vars = new HashMap();
		vars.put("foobar", "1000");
		vars.put("temp1", "1000");
		vars.put("temp2", "10000");
		MVEL.eval(expression, vars);
		System.out.println(vars);
		String ss = "s4 = s2/10 + (s3)";
		String sss = "s4 = s2/2 * s3 + userId"; 
		
		Map vars2 = new HashMap();
		vars2.put("s2", 1000);
		vars2.put("s3", 2000);
		vars2.put("userId", 2000);
		Map vars23= new HashMap();
		MVEL.eval(sss, vars2);
		System.out.println(vars2);
		//获取里面的字符串
		 String text = "“今天天{[3]}气不错，阳光挺[(10]}好的····”";  
		 Pattern pattern1 = Pattern.compile("\\[(.*?)\\]");
		 java.util.regex.Matcher m = pattern1.matcher(text); 
		 while(m.find()) { 
			 System.out.println(m.group(1)); 
		}
		 
		 String expression2 = "s3=s2+1000 +s1";
		 
		 Map vars3 = new HashMap();
			vars3.put("s1", 100);
			vars3.put("s2", 500);	vars3.put("s3", 0);
			MVEL.eval(expression2, vars3);
			System.out.println(vars3);*/
		
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oaop?characterEncoding=UTF-8","root","oaop2014");
//		Statement stmt = conn.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT a.CONSTRAINT_NAME,b.TABLE_NAME from information_schema.REFERENTIAL_CONSTRAINTS a,information_schema.TABLES b WHERE a.TABLE_NAME=b.TABLE_NAME AND b.TABLE_SCHEMA = 'oaop'");
//		while(rs.next()){
//			System.out.println("alter table "+rs.getString("TABLE_NAME")+" drop foreign key "+rs.getString("CONSTRAINT_NAME")+";");
//		}
		
//		Expression ex = ExpressionFactory.createExpression("");
//		JexlContext jc = JexlHelper.createContext();
//		System.out.println(ex.evaluate(jc));
		
//		TeeRegexpAnalyser an = new TeeRegexpAnalyser("{1} and ( {2} or {2})");
//		String text = an.replace(new String[]{"\\{[0-9]+\\}"}, new TeeExpFetcher() {
//			
//			@Override
//			public String parse(String pattern) {
//				// TODO Auto-generated method stub
//				int itemId = TeeStringUtil.getInteger(pattern.replaceAll("[\\{\\}]", ""), 0);
//				
//				return itemId+"";
//			}
//		});
//		System.out.println(text);
//		YUICompressor.main(new String[]{"c:\\1.js","-o","c:\\1.js","--charset","utf-8"});
		
//		InetAddress ia = InetAddress.getByName("localhost");
//	    DatagramSocket socket = new DatagramSocket(9998);
//	    socket.connect(ia, 8089);
//	    byte[] buffer = null;
//	    buffer = ("{\"t\":3,\"from\":\"admin\",\"to\":\"admin\",\"c\":\"尼玛\",dvc:1,\"time\":\"2014-08-05 17:52:26\"}")
//	      .getBytes();
//	    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
//	    socket.send(dp);
////	    for(int i=0;i<1000;i++){
////	    	Thread.sleep(1000);
////	 	    socket.send(dp);
////	    }
//	    socket.close();
		
	}

}
