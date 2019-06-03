package com.tianee.thirdparty.newcapec.quartzjob;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysCustomerProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Repository
public class TeeUserTimer{
	
	@Autowired
	private TeePersonService userService;	
	
	@SuppressWarnings("unchecked")
	public void userTimer() throws Exception{
		Connection conn=null;
		 Connection conn1=null;
	    	 try{
	    		TeeDataSource dataSource = new TeeDataSource();  //链接数据库直接写在定时任务里面   (自己数据库连接)
	    		dataSource.setPassWord(TeeSysCustomerProps.getString("MYSQL_PWD"));
	    		dataSource.setUserName(TeeSysCustomerProps.getString("MYSQL_NAME"));
	    		dataSource.setDriverClass(TeeSysCustomerProps.getString("DRIVER_CLASS"));
	    		dataSource.setUrl(TeeSysCustomerProps.getString("MYSQL_URL"));
	    		//获取连接
	    		 conn = TeeDbUtility.getConnection(dataSource);
	    		
	    		//获取数据
	    		DbUtils dbUtils = new DbUtils(conn);
	    		
	    		TeeDataSource dataSource1 = new TeeDataSource();  //链接数据库直接写在定时任务里面   (外部数据库连接)
	    		dataSource1.setPassWord(TeeSysCustomerProps.getString("MYSQL_PWD1"));
	    		dataSource1.setUserName(TeeSysCustomerProps.getString("MYSQL_NAME1"));
	    		dataSource1.setDriverClass(TeeSysCustomerProps.getString("DRIVER_CLASS1"));
	    		dataSource1.setUrl(TeeSysCustomerProps.getString("MYSQL_URL1"));
	    		//获取连接
	    		 conn1 = TeeDbUtility.getConnection(dataSource1);
	    		 DbUtils dbUtils1 = new DbUtils(conn1);
	    		 
	    		
	          	//List<Map> t_m=dbUtils1.queryToMapList("select * from t_mid_jg_info where GH not in(select UNIQUE_ID from person ) ");
	    		
	    		 List<Map> pp = dbUtils.queryToMapList("select * from person");
	    		 String cs="";//取参数
	    		 String cs1="";//取参数1
	    		 String cs2="";
	    		 String cs3="";
	    		 if(pp.size()==0){
	    			 cs2="0";
	    		 }else{
	    		 for (Map mapu : pp) {
	    			 if(mapu.get("unique_id")!=""&&mapu.get("unique_id")!=null){
	    			cs+=TeeStringUtil.getString(mapu.get("unique_id"))+",";
	    			 }else{
	    				 cs+="0"+",";
	    			 }
				}
	    		 cs2=cs.substring(0, cs.length()-1);
	    		 }
	    		 
	    		
	    		 
	    		 //(外部连接查询)
		    		List<Map> renList = dbUtils1.queryToMapList("select * from t_mid_jg_info");
		    		
		    		if(renList.size()==0){
		    			cs3="0";
		    		}else{
		    			 for (Map mapu1 : renList) {
		    				 if(mapu1.get("GH")!=""&&mapu1.get("GH")!=null){
				    		   cs1+=TeeStringUtil.getString(mapu1.get("GH"))+",";
		    				 }else{
		    					 cs1+="0"+",";
		    				 }
						}
				    	  cs3=cs1.substring(0,cs1.length()-1);
		    		}
		    		
			  
	    		 //(外部连接查询)
				@SuppressWarnings("rawtypes")
				List<Map> t_m=dbUtils1.queryToMapList("select * from t_mid_jg_info where t_mid_jg_info.GH not in("+cs2+")  ");
	    		//(自己连接查询)
	    		List<Map> personList= dbUtils.queryToMapList("select * from person  where person.UNIQUE_ID not in("+cs3+")");
	    	
	    		//需要insert的数据
	    		List<String> l1 = new ArrayList();
	    		
	    		//需要delete的数据
	    		List<String> l2 = new ArrayList();
	    	    //需要update的数据
	    		List<String> l3= new ArrayList();
	    		
	    		//从对方表里面找出咱表里没有的数据，并加到l1里面
	    		for(Map tm:t_m){
	    			//取出对方表里面的工号
	    			String tmId = TeeStringUtil.getString(tm.get("GH"));
	    			boolean hasExists = false;
	    			for(Map ps:personList){
	    				//取出咱表里面的uniqueId
	    				String psId = TeeStringUtil.getString(ps.get("unique_id"));
	    				if(tmId.equals(psId)){
	    					hasExists = true;
	    					continue;
	    				}
	    			}
	    			
	    			if(!hasExists){
	    				l1.add(tmId);
	    			}
	    		}
	    		

	    		TeePersonModel personModul=new TeePersonModel();
	    		
	    		//遍历我们表没有的数据进行添加
	    		for (String aa : l1) {
	    			List<Map> mz = dbUtils1.queryToMapList("select * from t_mid_jg_info where t_mid_jg_info.GH ="+aa);
	    			List<Map> list= dbUtils.queryToMapList("select * from person where person.uuid="+aa);//根据当前 aa  数据查出person表里的数据
	    			for (Map t : mz) {
	    				personModul.setUuid(Integer.parseInt(String.valueOf(t.get("GH"))));
	        			personModul.setDeptId(String.valueOf(t.get("DWH")));
	        		    personModul.setUniqueId(String.valueOf(t.get("GH")));
	        			personModul.setUserName(String.valueOf(t.get("XM")));
	        			personModul.setBirthdayStr(String.valueOf(t.get("CSRQ")));
	        		
	        			if(String.valueOf(t.get("XB")).equals("男")){
	        				personModul.setSex("0");
	        				
	        			}else {
	        				personModul.setSex("1");
	        			}
	        			
	        			personModul.setMenuImage(String.valueOf(t.get("SFZJH"))); 
	        	        
	        			personModul.setUserRoleStr(TeeSysCustomerProps.getString("USER_ROLE_YG"));
	        		//如果list有值就是之前添加到   然后找到数据进行删除
	        			for (Map p : list) {
	        				if(p!=null){
	        				personModul.setUuid(Integer.parseInt(String.valueOf(p.get("uuid"))));
	            			userService.deleteuserss(personModul);
	        				}
						}
	        			userService.adduserss(personModul);
	        			dbUtils.executeUpdate("update person set uuid=UNIQUE_ID where UNIQUE_ID!=''");
	    	    		conn.commit();
					}
	    		   
				}
	    		
	    		//取我们表他们没有的数据 addl2里
	    		for(Map pe:personList){
	    			//他们表里没有的数据unique_id
	    			String unId = TeeStringUtil.getString(pe.get("unique_id"));
	    		/*	boolean hasExists = false;
	    			if(unId.equals("4")){
	    				hasExists=true;
	    			}
	    			if(!hasExists){*/
		    			l2.add(unId);
		    			//}
	    		
	    		}
	    		//进入删除
	    		for (String sc : l2) {
	    			if(sc!=null&&sc!=""){
	    			personModul.setUuid(Integer.parseInt(sc));
	    			userService.deleteuserss(personModul);
	    			}
				}
	    		
	    	
	    	   for (Map ren:renList) {
	    		   String renId = TeeStringUtil.getString(ren.get("GH"));
	    		   l3.add(renId);
	    		  
			}
	    	   for(String xg:l3){
	    		   List<Map> mz = dbUtils1.queryToMapList("select * from t_mid_jg_info where t_mid_jg_info.GH ="+xg);
	    			for (Map t : mz) {
	    				personModul.setUuid(Integer.parseInt(String.valueOf(t.get("GH"))));
	        			personModul.setDeptId(String.valueOf(t.get("DWH")));
	        		    personModul.setUniqueId(String.valueOf(t.get("GH")));
	        			personModul.setUserName(String.valueOf(t.get("XM")));
	        			personModul.setBirthdayStr(String.valueOf(t.get("CSRQ")));
	        		
	        			if(String.valueOf(t.get("XB")).equals("男")){
	        				personModul.setSex("0");
	        				
	        			}else {
	        				personModul.setSex("1");
	        			}
	        			
	        			personModul.setMenuImage(String.valueOf(t.get("SFZJH"))); 
	        			userService.updateuserss(personModul);
					}
	    		  
	    	   }
	    		
	    	
	    	   
	    	 }catch(Exception ex){
	    		 ex.printStackTrace();
	    	 }finally{
	    		conn.close();
	    		conn1.close();
	    	 }
	    	 
	} 
}
