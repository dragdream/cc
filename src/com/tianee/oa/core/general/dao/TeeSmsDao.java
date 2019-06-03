package com.tianee.oa.core.general.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeSmsDao extends TeeBaseDao<TeeSms>{
	
	 /**
		 * page TeeDataGridModel.page 当前页
		 * rows TeeDataGridModel.rows 每页显示记录数
		 * @param hql
		 * @param page
		 * @param rows
		 * @param param
		 * @return
		 */
		public List<Object> loadList(String hql, int page, int rows, List<Object> param) {
			Query q = getSession().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i, param.get(i));
				}
			}
			return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		
		 /**
		 * page TeeDataGridModel.page 当前页
		 * rows TeeDataGridModel.rows 每页显示记录数
		 * @param hql
		 * @param page
		 * @param rows
		 * @param param
		 * @return
		 */
		public List<Object> getList(String hql, List<Object> param) {
			Query q = getSession().createQuery(hql);
			if (param != null && param.size() > 0) {
				for (int i = 0; i < param.size(); i++) {
					q.setParameter(i, param.get(i));
				}
			}
			//List<Object> list = new ArrayList<Object>();
			return q.list();
		}
		
		/**
		   * 通过body_seq_id和to_id索引所有seq_id,可能有重复,getSmsSeqId方法只能取一条会引起bug
		   * @param toId
		   * @param seqId
		   * @return
		   * @throws Exception
		   */
		
		public List<String> getSmsSeqIds( String toId, String seqUuId) throws Exception{
		    List<String> list = new ArrayList<String>();
		    List<Object> values = new ArrayList<Object>();

		    String hql = "select sms from TeeSms sms left join sms.bodyId sb where sb.uuid = '"+seqUuId+"' and sms.toId = '"+toId+"'";            
		    List<Object> objList = getList(hql, values);
		    for(Object obj : objList){
		    	
		    	TeeSms sms = (TeeSms)obj;
    			list.add(sms.getUuid());
		    }

		    return list;
		  }
		
		/**
		   * @param toId
		   * @param seqId
		   * @return
		   * @throws Exception
		   */
		
		public long remindCheck(String personUuid) throws Exception{
			int result = 0;
			
		    String sql = "select count(sms.uuid) from TeeSms sms where sms.toId="+personUuid+" and sms.remindFlag=0 and sms.deleteFlag=0 and sms.smsBody.sendFlag=1";
		    return count(sql,null);
		  }
		
		public void popup(String personUuid) throws Exception{
		    String hql = "update TeeSms sms set sms.remindFlag=2 where sms.toId="+personUuid+" and sms.remindFlag=0 and sms.deleteFlag=0 and sms.remindTime is not null";
		    executeUpdate(hql, null);
		}
		
		/**
		   * @param toId
		   * @param seqId
		   * @return
		   * @throws Exception
		   */
		
		public String getRemindInBox( String personUuid) throws Exception{
			String data = "";

		    String sql =  "select " +
		            "SMS_BODY.SID as s2," +
		            "SMS_BODY.FROM_ID," +
		            "SMS_BODY.TYPE_ID," +
		            "SMS_BODY.CONTENT," +
		            "SMS_BODY.SEND_TIME," +
		            "SMS_BODY.REMIND_URL," +
		            "SMS.REMIND_TIME," +
		            "SMS.SID as s1," +
		            " USER_NAME," +
		            "SMS.TO_ID" +
		            " FROM " +
		            "SMS left join " +
		            "SMS_BODY on SMS.BODY_ID = SMS_BODY.SID left join PERSON on PERSON.UUID = SMS_BODY.FROM_ID" +
		            " WHERE " +
		            " AND SMS.TO_ID='" + personUuid +
		            "' AND DELETE_FLAG IN(0,2) " +
		            " AND SEND_TIME<=?"+
		            " AND (SMS.REMIND_TIME IS NULL OR SMS.REMIND_TIME<=?)" +
		            " AND REMIND_FLAG IN(1,2)";
		        sql += " ORDER BY SMS.REMIND_TIME DESC,SEND_TIME DESC";
		        //System.out.println(sql);
		     Session session = getSession();
		     String smsIds = "";
		     SQLQuery query = session.createSQLQuery(sql);
		     query.setTimestamp(0, new Timestamp(new Date().getTime()));
		     query.setTimestamp(1, new Timestamp(new Date().getTime()));
		     List<Object[]> objList = query.list();
		     for(Object[] o : objList){
		    	 int bodyId = TeeStringUtil.getInteger(o[0], 0);
			     int smsId = TeeStringUtil.getInteger(o[7], 0);
				 String fromId = TeeStringUtil.getString(o[1]);
				 int smsType = TeeStringUtil.getInteger(o[2], 0);
				 String content = TeeStringUtil.getString(o[3]);
				 Date sendTime = TeeStringUtil.getDate(o[4]);
				 String remindUrl = TeeStringUtil.getString(o[5]);
				 Date remindTime = TeeStringUtil.getDate(o[6]);
				 String userName = TeeStringUtil.getString(o[8]);
				 String toId = TeeStringUtil.getString(o[9]);
				 if (TeeUtility.isNullorEmpty(userName)) {
			          userName = "已删除用户";
			     }
				 if(fromId.equals("0")){
					 userName = "<Strong><font color='red'>系统信息</font></Strong>";
				 }
		         if(remindTime != null){
		           sendTime = remindTime;
		         }
		         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		         String remindTimeStr  = sdf.format(sendTime);
		         String smsTypeName = "";
		         data+="{";
		         data+="bodyId:" + bodyId;
		         data+=",sms_id:" + smsId ;
		         data+=",from_id:\"" + fromId + "\"";
		         data+=",to_id:\""+ toId + "\"";
		         data+=",unread:1";
		         data+=",receive:1";
		         data+=",type_id:\"" + smsType + "\"";
		         data+=",type_name:\"" + smsTypeName + "\"";
		         data+=",content:\"" + TeeUtility.encodeSpecial(content) + "\"";
		         data+=",send_time:\"" + remindTimeStr + "\"";
		         data+=",url:\"" + TeeUtility.encodeSpecial(remindUrl) + "\"";
		         data+=",from_name:\"" + TeeUtility.encodeSpecial(userName) + "\"";
		         data+="},";
		         
		         if(!"".equals(smsIds)){
		             smsIds += ",";
		         }
		         smsIds += smsId;
		         if(!"".equals(smsIds)){
		             updateFlag("REMIND_FLAG", "2", smsIds, "SMS");
		         }
		     }
		     if(data.endsWith(",")){
	        	 data=data.substring(0, data.length()-1);
	         }
		    
		    //System.out.println(data);
		     
		    return "["+data+"]";
		  }
		
//		/**
//		   * @param toId
//		   * @param seqId
//		   * @return
//		   * @throws Exception
//		   */
//		
//		public List<TeeSmsModel> getSessionSmsList(String personUuid) throws Exception{
//			String data = "";
//
//			 String sql =  "select " +
//			            "SMS_BODY.SID as s2," +
//			            "SMS_BODY.FROM_ID," +
//			            "SMS_BODY.TYPE_ID," +
//			            "SMS_BODY.CONTENT," +
//			            "SMS_BODY.SEND_TIME," +
//			            "SMS_BODY.REMIND_URL," +
//			            "SMS.REMIND_TIME," +
//			            "SMS.SID as s1," +
//			            " USER_NAME," +
//			            "SMS.TO_ID" +
//			            " FROM " +
//			            "SMS left join " +
//			            "SMS_BODY on SMS.BODY_ID = SMS_BODY.SID left join PERSON on PERSON.UUID = SMS_BODY.FROM_ID" +
//			            " WHERE " +
//			            " AND SMS.TO_ID='" + personUuid +
//			            "' AND DELETE_FLAG IN(0,2) " +
//			            " AND SEND_TIME<=?"+
//			            " AND (SMS.REMIND_TIME IS NULL OR SMS.REMIND_TIME<=?)" +
//			            " AND REMIND_FLAG IN(1,2)";
//			        sql += " ORDER BY SMS.REMIND_TIME DESC,SEND_TIME DESC";
//		     Session session = getSession();
//		     String smsIds = "";
//		     SQLQuery query = session.createSQLQuery(sql);
//		     query.setTimestamp(0, new Timestamp(new Date().getTime()));
//		     query.setTimestamp(1, new Timestamp(new Date().getTime()));
//		     List<TeeSmsModel> list = new ArrayList<TeeSmsModel>();
//		     List<Object[]> objList = query.list();
//		     for(Object[] o : objList){
//		    	 TeeSmsModel model = new TeeSmsModel();
//		    	 int bodyId = TeeStringUtil.getInteger(o[0], 0);
//			     int smsId = TeeStringUtil.getInteger(o[7], 0);
//				 String fromId = TeeStringUtil.getString(o[1]);
//				 int smsType = TeeStringUtil.getInteger(o[2], 0);
//				 String content = TeeStringUtil.getString(o[3]);
//				 Date sendTime1 = TeeStringUtil.getDate(o[4]);
//				 String remindUrl = TeeStringUtil.getString(o[5]);
//				 Date remindTime = TeeStringUtil.getDate(o[6]);
//				 String userName = TeeStringUtil.getString(o[8]);
//				 String toId = TeeStringUtil.getString(o[9]);
//				 if (TeeUtility.isNullorEmpty(userName)) {
//			          userName = "已删除用户";
//			     }
//				 if(fromId.equals("0")){
//					 userName = "<Strong><font color=red>系统信息</font></Strong>";
//				 }
//		         if(remindTime != null){
//		           sendTime1 = remindTime;
//		         }
//		         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//		         String sendTime = sdf.format(sendTime1);
//		         String smsTypeName = "";
//
//		         model.setBodyId(bodyId);
//		         model.setSms_id(smsId);
//		         model.setTo_id(toId);
//		         model.setFrom_id(fromId);
//		         model.setUnread(1);
//		         model.setReceive(1);
//		         model.setContent(TeeUtility.encodeSpecial(content));
//		         model.setType_id(smsType);
//		         model.setType_name(smsTypeName);
//		         model.setSend_time(sendTime);
//		         model.setFrom_name(TeeUtility.encodeSpecial(userName));
//		         model.setUrl(TeeUtility.encodeSpecial(remindUrl));
//		         list.add(model);
//		         if(!"".equals(smsIds)){
//		             smsIds += ",";
//		         }
//		         smsIds += smsId;
//		         if(!"".equals(smsIds)){
//		             updateFlag("REMIND_FLAG", "2", smsIds, "SMS");
//		         }
//		     }
//		    return list;
//		  }
		
//		/**
//		   * @param toId
//		   * @param seqId
//		   * @return
//		   * @throws Exception
//		   */
//		
//		public List<TeeSmsModel> getSessionSmsListAll(String uuIdStr) throws Exception{
//			String data = "";
//
//			 String sql =  "select " +
//			            "SMS_BODY.SID as s2," +
//			            "SMS_BODY.FROM_ID," +
//			            "SMS_BODY.TYPE_ID," +
//			            "SMS_BODY.CONTENT," +
//			            "SMS_BODY.SEND_TIME," +
//			            "SMS_BODY.REMIND_URL," +
//			            "SMS.REMIND_TIME," +
//			            "SMS.SID as s1," +
//			            " USER_NAME," +
//			            "SMS.TO_ID" +
//			            " FROM " +
//			            "SMS left join " +
//			            "SMS_BODY on SMS.BODY_ID = SMS_BODY.SID left join PERSON on PERSON.UUID = SMS_BODY.FROM_ID" +
//			            " WHERE 1=1" ;
//		            if(!TeeUtility.isNullorEmpty(uuIdStr)){
//		            	sql+=" AND (SMS.TO_ID in " + uuIdStr +")";
//		            }
//		            sql+=" AND DELETE_FLAG IN(0,2) " +
//		            " AND SEND_TIME<=?"+
//		            " AND (SMS.REMIND_TIME IS NULL OR SMS.REMIND_TIME<=?)" +
//		            " AND REMIND_FLAG IN(1,2)";
//		        sql += " ORDER BY SMS.REMIND_TIME DESC,SEND_TIME DESC";
//		        //System.out.println(sql);
//		     Session session = getSession();
//		     String smsIds = "";
//		     SQLQuery query = session.createSQLQuery(sql);
//		     query.setTimestamp(0, new Timestamp(new Date().getTime()));
//		     query.setTimestamp(1, new Timestamp(new Date().getTime()));
//		     List<TeeSmsModel> list = new ArrayList<TeeSmsModel>();
//		     List<Object[]> objList = query.list();
//		     for(Object[] o : objList){
//		    	 TeeSmsModel model = new TeeSmsModel();
//		    	 int bodyId = TeeStringUtil.getInteger(o[0], 0);
//			     int smsId = TeeStringUtil.getInteger(o[7], 0);
//				 String fromId = TeeStringUtil.getString(o[1]);
//				 int smsType = TeeStringUtil.getInteger(o[2], 0);
//				 String content = TeeStringUtil.getString(o[3]);
//				 Date sendTime1 = TeeStringUtil.getDate(o[4]);
//				 String remindUrl = TeeStringUtil.getString(o[5]);
//				 Date remindTime = TeeStringUtil.getDate(o[6]);
//				 String userName = TeeStringUtil.getString(o[8]);
//				 String toId = TeeStringUtil.getString(o[9]);
//				 if (TeeUtility.isNullorEmpty(userName)) {
//			          userName = "已删除用户";
//			     }
//				 if(fromId.equals("0")){
//					 userName = "<Strong><font color=red>系统信息</font></Strong>";
//				 }
//		         if(remindTime != null){
//		           sendTime1 = remindTime;
//		         }
//		         SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//		         String sendTime = sdf.format(sendTime1);
//		         String smsTypeName = "";
//
//		         model.setBodyId(bodyId);
//		         model.setSms_id(smsId);
//		         model.setTo_id(toId);
//		         model.setFrom_id(fromId);
//		         model.setUnread(1);
//		         model.setReceive(1);
//		         model.setContent(TeeUtility.encodeSpecial(content));
//		         model.setType_id(smsType);
//		         model.setType_name(smsTypeName);
//		         model.setSend_time(sendTime);
//		         model.setFrom_name(TeeUtility.encodeSpecial(userName));
//		         model.setUrl(TeeUtility.encodeSpecial(remindUrl));
//		         list.add(model);
//		     }
//		    return list;
//		  }
		
		/**
		   * 标记标记位

		   * @param conn
		   * @param field
		   * @param value
		   * @param sid
		   * @param tableName
		   * @throws SQLException
		   */
		  public void updateFlag(String field,String value,String seqId,String tableName) throws Exception{
		    String sql = "UPDATE " + tableName + " SET " + field + " = '" + value + "'" + " WHERE SID IN (" + seqId + ")";
		    Session session = getSession();
		    String smsIds = "";
		    SQLQuery query = session.createSQLQuery(sql);
		    query.executeUpdate();
		    
		  }
		  
		  /**
		    * 批量删除SMS
		    * @param conn
		    * @param sids
		    * @param tableName
		    * @throws Exception
		    */
		   public void deleteAll(String seqIds,String tableName) throws Exception{
		     String sql = "DELETE FROM " + tableName + " WHERE SID IN(" + seqIds +")";
		     Session session = getSession();
			 SQLQuery query = session.createSQLQuery(sql);
			 query.executeUpdate();
		   }
		  
		  /**
		   * 返回详情列表
		   * @param conn
		   * @param smsIds
		   * @return
		   * @throws Exception
		   */
		   public List<Map<String, String>> getViewDetails(String smsIds,int userId) throws Exception{
		     List<Map<String, String>> l = new ArrayList<Map<String,String>>();
		     if(smsIds == null || "".equals(smsIds.trim()) || ",".equals(smsIds.trim())){
		       return l;
		     }
		     smsIds = smsIds.trim();
		     if(smsIds.endsWith(",")){
		       smsIds = smsIds.substring(0, smsIds.length() -1);
		     }
		     String sql = "select sms.sid,sms_body.TYPE_ID ,sms_body.content,sms_body.REMIND_URL from tee_sms sms,tee_sms_body sms_body where sms_body.sid = sms.body_id and sms.sid in(" + smsIds + ") and sms.to_id = " + userId;
		     //System.out.println(sql);
		     Session session = getSession();
		     SQLQuery query = session.createSQLQuery(sql);
		     List<Object[]> objList = query.list();
		     int count = 1;
		     int personCount = 0;
		     for(Object[] o : objList){
		    	 int smsId = TeeStringUtil.getInteger(o[0], 0);
		    	 String smsType = TeeStringUtil.getString(o[1]);
		         String content = TeeStringUtil.getString(o[2]);
		         String remindUrl = TeeStringUtil.getString(o[3]);
		         if("0".equals(smsType)){
			           if(personCount != 0){
			             continue;
			           }
			           remindUrl = "/system/core/sms/receiveSmsList.jsp";
			           personCount ++;
		         }
		         if(content.length() > 30){
		           content = content.substring(0, 30);
		         }
		         String img = "sms_type" + smsType + ".gif";
		         Map<String, String> m = new HashMap<String, String>();
		         m.put("text", "工作" + count);
		         m.put("url", remindUrl);
		         m.put("title", content);
		         m.put("img", img);
		         l.add(m);
		         count ++;
		     }
		     return l;
		   }
		   
		   /**
		    * 删除短信
		    * @param conn
		    * @param bodyId
		    * @param isform
		    * @param userId
		    * @throws Exception 
		    */
		   public boolean doDelSms(String bodyId,String isform,int userId) throws Exception{
			    boolean isDe = true;
			    String seqIdArr = "";
			    String sql = "select uuid,DELETE_FLAG,REMIND_FLAG,TO_ID from tee_sms where BODY_ID = '"+bodyId+"'";
			    Session session = getSession();
			    SQLQuery query = session.createSQLQuery(sql);
			    List<Object[]> objList = query.list();
			    try{
			      for (Object[] o : objList) {
			        String uuid = TeeStringUtil.getString(o[0]);
			        String deleteFlag =TeeStringUtil.getString(o[1]);
			        String readFlag = TeeStringUtil.getString(o[2]);
			        int toId = TeeStringUtil.getInteger(o[3], 0);
			        //如果
			        boolean b = false;
			        if(userId == toId && "1".equals(isform)){
			          b = isCanDeleteSms(isform, uuid + "", deleteFlag, readFlag);
			        } else if("2".equals(isform)){
			          b = isCanDeleteSms(isform, uuid + "", deleteFlag, readFlag);
			        }
			        if(b){
			          if(!"".equals(seqIdArr)){
			            seqIdArr += ",";
			          }
			          seqIdArr += "'"+uuid+"'";
			        }
			        isDe = isDe && b;
			      }
			      if(!"".equals(seqIdArr.trim())){
			        deleteAll(seqIdArr, "SMS");
			      }
			    } catch (Exception e){
			      throw e;
			    }
			    if(isDe){
			      deleteAll(String.valueOf(bodyId), "SMS_BODY");
			    }
			    return isDe;
			  }
		   
		   /**
		    * 查看短信是否处于可删除状态

		    * 1收件人，2发件人

		    * @param conn
		    * @param sid {sid,sid,sid...}
		    * @param deletFlag
		    * @param readFlag
		    * @throws SQLException
		    */
		   public boolean isCanDeleteSms(String isFrom,String seqId,String deletFlag,String readFlag) throws Exception{
		     int deletbit = Integer.valueOf(deletFlag.trim());
		     int readbit = Integer.valueOf(readFlag.trim());
		     boolean bool = false;
		     //1.收件人删除01/11
		     if("1".equals(isFrom)){
		       if(deletbit == 0){
		         deletbit = 1;
		         updateFlag("REMIND_FLAG",0 + "",seqId,"SMS");
		       }else if(deletbit == 2){
		         bool = true;
		       }
		     } else if("2".equals(isFrom)){
		       //2.发件人删除10/11
		       
		         if(deletbit == 0) {
		           if(readbit == 2 || readbit == 1 ){
		             bool = true;
		           }else if(readbit == 0){
		             deletbit = 2;
		           }
		         }else if(deletbit ==  1) {
		           bool = true;
		         }
		     }
		     updateFlag("DELETE_FLAG",deletbit + "",seqId,"SMS");
		     return bool;
		   }
		   

			  
			public int getMaxId( String sql) throws Exception{
				int result = 0;
			     Session session = getSession();
			     SQLQuery query = session.createSQLQuery(sql);
			     query.uniqueResult();
			     result = (Integer.parseInt( query.uniqueResult().toString()));
			    return result;
			}
			 /**
			   * quarzJob
			   * @param conn
			   * @param smsIds
			   * @return
			   * @throws Exception
			   */
			   public List<Object[]> getQuarzList(String sql,List params) throws Exception{
			   
			     Session session = getSession();
			     SQLQuery query = session.createSQLQuery(sql);
			     if(params!=null){
			    	 for(int i=0;i<params.size();i++){
			    		 query.setParameter(i, params.get(i));
			    	 }
			     }
			     List<Object[]> objList = query.list();
			     return objList;
			   }
			/**
			   * quarzJob
			   * @throws SQLException
			   */
			  public void exectSql(String sql) throws Exception{
			    Session session = getSession();
			    SQLQuery query = session.createSQLQuery(sql);
			    query.executeUpdate();
			  }

	/**
	 * 获取未读短信
	 * @author syl
	 * @date 2014-6-15
	 * @param person
	 * @return
	 */
	public long getReceiveNoReadCount(TeePerson person){
		long total = 0;
		if(person != null){
			String hql = "select count(*) from TeeSms sms where sms.toId="+person.getUuid()+" and sms.remindFlag <>1 and sms.deleteFlag=0 and sms.smsBody.delFlag=0";
			 total = count(hql, null);
		}
	
		return total;
	}
}
