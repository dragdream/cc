package com.tianee.oa.core.workflow.plugins;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.oa.subsys.budget.bean.TeeUserBudget;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class BudgetUserAuditPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		TeeJsonProxy teeJsonProxy=new TeeJsonProxy();
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
				
					//获取部门显示值  
					String userId = this.flowRunDatas.get("EXTRA_"+formItemIdentities.get("人员名称").split("_")[1]);
					String year=this.flowRunDatas.get(formItemIdentities.get("目标年度"));
					String m1=this.flowRunDatas.get(formItemIdentities.get("1月金额"));
					String m2=this.flowRunDatas.get(formItemIdentities.get("2月金额"));
					String m3=this.flowRunDatas.get(formItemIdentities.get("3月金额"));
					String m4=this.flowRunDatas.get(formItemIdentities.get("4月金额"));
					String m5=this.flowRunDatas.get(formItemIdentities.get("5月金额"));
					String m6=this.flowRunDatas.get(formItemIdentities.get("6月金额"));
					String m7=this.flowRunDatas.get(formItemIdentities.get("7月金额"));
					String m8=this.flowRunDatas.get(formItemIdentities.get("8月金额"));
					String m9=this.flowRunDatas.get(formItemIdentities.get("9月金额"));
					String m10=this.flowRunDatas.get(formItemIdentities.get("10月金额"));
					String m11=this.flowRunDatas.get(formItemIdentities.get("11月金额"));
					String m12=this.flowRunDatas.get(formItemIdentities.get("12月金额"));
					String[] str={m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12};
                    
					if(TeeUtility.isNullorEmpty(userId)){
						TeeDbUtility.rollback(dbConn);
                    	teeJsonProxy.setRtMsg("未选择人员名称！");
                    	teeJsonProxy.setRtState(false);
					}else if(TeeUtility.isNullorEmpty(year)){
						TeeDbUtility.rollback(dbConn);
                    	teeJsonProxy.setRtMsg("未选择目标年度！");
                    	teeJsonProxy.setRtState(false);
					}else{
						String sql = "select BU.UUID from BUDGET_USER BU where BU.USER_ID=? and BU.YEAR=?";
						List<TeeUserBudget> list = dbUtils.queryToArrayList(sql, new Object[]{userId,year});
						if(list.size()<11){
							for (int i = 0; i < str.length; i++) {
								String i1=String.valueOf(i+1);
								if(i<9){
									i1="0"+i1;
								}
								dbUtils.executeInsert("insert into budget_user("
										+"uuid,"
										+"amount,"
										+"month,"
										+"year,"
										+"cr_user_id,"
										+"user_id)"
										+ "values(?,?,?,?,?,?)", dialect, 
										new Object[]{
												//this.getFlowRunProxy().getBeginUserUuid(),//获取流程发起人主键ID
												UUID.randomUUID().toString().replaceAll("\\-", ""),
												TeeStringUtil.getDouble(str[i], 0),
												i1,
												year,
												this.getFlowRunProxy().getBeginUserUuid(),
												TeeStringUtil.getInteger(userId, 0)
										});
							}
							
							dbConn.commit();
							
							//指定为处理成功状态
							teeJsonProxy.setRtState(true);
						}else{
							TeeDbUtility.rollback(dbConn);
							teeJsonProxy.setRtMsg("该用户年度预算已存在！");
							teeJsonProxy.setRtState(false);
						}
						
					}
					
					
				}catch(Exception ex){//异常处理
					
					//数据库事务回滚
					TeeDbUtility.rollback(dbConn);
					ex.printStackTrace();//输出异常
					teeJsonProxy.setRtMsg(ex.getMessage());//将异常信息存入对象消息中
					teeJsonProxy.setRtState(false);//指定为处理失败状态
					
				}finally{
					TeeDbUtility.closeConn(dbConn);
				}
				
		
		
		
		
		
		
		
		
		return teeJsonProxy;

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
	public void afterSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}

}
