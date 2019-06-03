package com.tianee.oa.core.workflow.workmanage.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowRuleModel;
import com.tianee.oa.core.workflow.workmanage.model.TeeSeniorQueryModel;

import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeFlowSeniorQueryDao extends TeeBaseDao<TeeFlowQueryTpl>{
	
	public List<TeeSeniorQueryModel> getQueryTplByFlowId(TeePerson person,int flowId){
		 String sql = "select A.SID, A.TPL_NAME, B.FLOW_NAME from FLOW_QUERY_TPL A left outer join FLOW_TYPE B on A.FLOW_ID=B.SID where A.FLOW_ID="+flowId;
		 int uuid = person.getUuid();
		 boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person,"");
		 
		 if(!isSuperAdmin){
			 sql+= " and USER_ID="+uuid;
		 }
	    Session session = getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    List<Object[]> objList = query.list();
	    List<TeeSeniorQueryModel> list = new ArrayList<TeeSeniorQueryModel>();
	    for(Object[] o : objList){
	    	TeeSeniorQueryModel model = new TeeSeniorQueryModel();
	    	model.setSid(TeeStringUtil.getInteger(o[0], 0));
	    	model.setTplName(TeeStringUtil.getString(o[1]));
	    	list.add(model);
	    }
		return list;
	}
}
