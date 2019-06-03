package com.tianee.oa.core.workflow.plugins;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 公告通知审批插件
 * @author kakalion
 *
 */
public class NotifyAuditPlugin extends TeeWfPlugin{

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTurnnext() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TeeJsonProxy beforeSave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		
		TeeJsonProxy jsonProxy = new TeeJsonProxy();
		
		//获取数据库方言
		String dialect = TeeSysProps.getString("dialect");
		//创建数据库连接对象
		Connection dbConn = null;
		try{
			//获取连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);//设置为不自动提交（建议）
			//获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);
			
			//获取公告标题
			String subject = this.flowRunDatas.get(formItemIdentities.get("主题"));
			
			//获取公告类型
			String typeId = this.flowRunDatas.get(formItemIdentities.get("公告类型选择"));
			
			//获取部门显示值  
			String deptNames = this.flowRunDatas.get(formItemIdentities.get("选择部门"));
			
			//获取部门ID字符串
			//通过formItemIdentities.get("选择部门")方法  返回DATA_XX  然后按照下划线拆分  得到XX  最后拼接EXTRA_XX 为扩展字段值
			String deptIds = this.flowRunDatas.get("EXTRA_"+formItemIdentities.get("选择部门").split("_")[1]);
			
			//获取角色显示值 
			String roleNames = this.flowRunDatas.get(formItemIdentities.get("选择角色"));
			
			//获取角色ID字符串
			String roleIds = this.flowRunDatas.get("EXTRA_"+formItemIdentities.get("选择角色").split("_")[1]);
			
			//获取人员显示值
			String userNames = this.flowRunDatas.get(formItemIdentities.get("选择人员"));
			
			//获取人员ID字符串
			String userIds = this.flowRunDatas.get("EXTRA_"+formItemIdentities.get("选择人员").split("_")[1]);
			
			//获取是否全员发布  0和1
			String isAllPriv = this.flowRunDatas.get(formItemIdentities.get("全员发布"));
			
			//获取有效期开始
			String startTime = this.flowRunDatas.get(formItemIdentities.get("有效期开始"));
			
			//有效期结束
			String endTime = this.flowRunDatas.get(formItemIdentities.get("有效期结束"));
			
			//是否显示为重要  0和1
			String isTop = this.flowRunDatas.get(formItemIdentities.get("重要公告"));
			
			//公告内容
			String content = this.flowRunDatas.get(formItemIdentities.get("公告内容"));
			
			//向数据库表NOTIFY中插入公告数据，执行插入方法，返回主键ID
			Object notifyId = dbUtils.executeInsert("insert into notify("
					+ "begin_date,"
					+ "end_date,"
					+ "content,"
					+ "publish,"
					+ "send_time,"
					+ "subject,"
					+ "is_top,"
					+ "type_id,"
					+ "fromperson_uuid,"
					+ "from_Dept,"
					+ "top_days,"
					+ "all_priv) "
					+ "values(?,?,?,?,?,?,?,?,?,0,0,?)", dialect, 
					new Object[]{
							TeeDateUtil.parseDate(startTime),//有效期开始    日期类型
							TeeDateUtil.parseDate(endTime),//有效期结束   日期类型
							content,//公告内容  字符串型
							1,//publish值  默认为1   即已发布状态
							new Date(),//发布日期   默认获取当前日期
							subject,//公告主题
							TeeStringUtil.getInteger(isTop, 0),//是否显示为重要
							typeId,//公告类型
							this.getFlowRunProxy().getBeginUserUuid(),//获取流程发起人主键ID
							TeeStringUtil.getInteger(isAllPriv, 0)//是否为全部发布  整型  如果有异常，则默认为0
					});
			
			
			//如果没有选择全员发布，则默认走部门、人员、角色权限集合
			if("0".equals(isAllPriv)){
				
				//如果deptIds不为空，则插入子表notify_dept_priv
				if(!TeeUtility.isNullorEmpty(deptIds)){
					int deptIdsArray[] = TeeStringUtil.parseIntegerArray(deptIds);//将deptIds转换成数组
					for(int i=0;i<deptIdsArray.length;i++){
						dbUtils.executeInsert("insert into notify_dept_priv("
								+ "notify_id,"
								+ "dept_uuid) "
								+ "values(?,?)", dialect, 
								new Object[]{
										notifyId,//外键，关联上公告通知主键
										deptIdsArray[i]//外键，关联上接收人主键
								});
					}
				}
				
				//如果roleIds不为空，则插入子表notify_user_role_priv
				if(!TeeUtility.isNullorEmpty(roleIds)){
					int roleIdsArray[] = TeeStringUtil.parseIntegerArray(roleIds);//将roleIds转换成数组
					for(int i=0;i<roleIdsArray.length;i++){
						dbUtils.executeInsert("insert into notify_user_role_priv("
								+ "notify_id,"
								+ "role_uuid) "
								+ "values(?,?)", dialect, 
								new Object[]{
										notifyId,//外键，关联上公告通知主键
										roleIdsArray[i]//外键，关联上接收人主键
								});
					}
				}
				
				//如果userIds不为空，则插入子表notify_person_priv
				if(!TeeUtility.isNullorEmpty(userIds)){
					int userIdsArray[] = TeeStringUtil.parseIntegerArray(userIds);//将userIds转换成数组
					for(int i=0;i<userIdsArray.length;i++){
						dbUtils.executeInsert("insert into notify_person_priv("
								+ "notify_id,"
								+ "user_uuid) "
								+ "values(?,?)", dialect, 
								new Object[]{
										notifyId,//外键，关联上公告通知主键
										userIdsArray[i]//外键，关联上接收人主键
								});
					}
				}
			}
			
			
			
			//给各人员发送事务消息
			//首先需要通过sql获取到满足角色、部门、人员的这些账号主键
			String sql = "select p.uuid as UUID from person p where 1=1 and ";
			if(TeeUtility.isNullorEmpty(userIds)){
				userIds = "0";
			}
			if(TeeUtility.isNullorEmpty(deptIds)){
				deptIds = "0";
			}
			if(TeeUtility.isNullorEmpty(roleIds)){
				roleIds = "0";
			}
			
			sql += "(p.uuid in ("+userIds+") or p.dept_id in ("+deptIds+") or p.user_role in ("+roleIds+"))";
			
			List<Map> uuidList = dbUtils.queryToMapList(sql);
			String personIds = "";
			//拼接用户id字符串
			for(int i=0;i<uuidList.size();i++){
				personIds+=String.valueOf(uuidList.get(i).get("UUID"));
				if(i!=uuidList.size()-1){
					personIds+=",";
				}
			}
			
			//创建消息模型
			Map smsData = new HashMap();
			smsData.put("content", "请查看公告："+subject);//消息内容
			smsData.put("userListIds",personIds);//要发送给具体人员的id字符串
			smsData.put("moduleNo", "021");//021为通知公告，具体模块代号可以参考　module_sort表
			//URL链接地址
			smsData.put("remindUrl", "/system/core/base/notify/person/readNotify.jsp?id="+notifyId+"&isLooked=0&sid="+notifyId);
			//将消息模型加入消息列表中
			this.smsDataList.add(smsData);
			
			
			//注意！一定要提交数据库事务才真正保存到数据库中
			dbConn.commit();
			
			//指定为处理成功状态
			jsonProxy.setRtState(true);
			
		}catch(Exception ex){//异常处理
			
			//数据库事务回滚
			TeeDbUtility.rollback(dbConn);
			ex.printStackTrace();//输出异常
			jsonProxy.setRtMsg(ex.getMessage());//将异常信息存入对象消息中
			jsonProxy.setRtState(false);//指定为处理失败状态
			
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
		
		return jsonProxy;
	}

	@Override
	public void preTurnNextFilter(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}
	
}
