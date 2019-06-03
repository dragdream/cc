package com.beidasoft.xzzf.task.caseAssigned.dao;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.beidasoft.xzzf.task.caseOrder.bean.CaseOrder;
import com.beidasoft.xzzf.task.caseOrder.model.CaseOrderModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class CaseAssignedDao extends TeeBaseDao<CaseOrder> {
	
	/**
	 * 返回总记录结果
	 * @return
	 */
	public long getTotal(CaseOrderModel queryModel){
		
		String hql = "select count(id) from CaseOrder where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getCaseOrderName())){//名称
			hql+=" and caseOrderName like '%"+queryModel.getCaseOrderName()+"%'";
		}
		if(queryModel.getCaseOrderSource()!=-1){//来源
			hql+=" and caseOrderSource = "+queryModel.getCaseOrderSource();
		}
		if(queryModel.getCurrentLink()!=-1){//当前环节
			hql+=" and currentLink = "+queryModel.getCurrentLink();
		}
		if(queryModel.getHandleStage()!=-1){//办理阶段
			hql+=" and candleStage = "+queryModel.getHandleStage();
		}
			return super.count(hql, null);//最后一个参数是条件查询的条件
	}
	/**
	 * 根据分页查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<CaseOrder> listByPage(int firstResult,int rows,CaseOrderModel queryModel){
		String hql = "from TeeLaw where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getCaseOrderName())){//名称
			hql+=" and caseOrderName like '%"+queryModel.getCaseOrderName()+"%'";
		}
		if(queryModel.getCaseOrderSource()!=-1){//来源
			hql+=" and caseOrderSource = "+queryModel.getCaseOrderSource();
		}
		if(queryModel.getCurrentLink()!=-1){//当前环节
			hql+=" and currentLink = "+queryModel.getCurrentLink();
		}
		if(queryModel.getHandleStage()!=-1){//办理阶段
			hql+=" and candleStage = "+queryModel.getHandleStage();
		}
		List<CaseOrder> uu = super.pageFind(hql, firstResult, rows, null);
		return super.pageFind(hql, firstResult, rows, null);//最后一个参数是条件查询的条件
		
	}
	public Object getCaseSegment(int caseId) {

		Session session = this.getSession();
		String sql = "select za.taskName,za.taskRec,zs.segmentName from CaseSegment zs,CaseAppointedInfo za where za.baseId = zs.baseId AND zs.caseId ="+caseId;
		 Object ss = session.createQuery(sql).uniqueResult();
		 return ss;
	}
	
	
}
