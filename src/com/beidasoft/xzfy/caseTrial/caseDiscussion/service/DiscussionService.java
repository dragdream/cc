package com.beidasoft.xzfy.caseTrial.caseDiscussion.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzfy.caseRegister.bean.FyCaseHandling;
import com.beidasoft.xzfy.caseRegister.dao.CaseReviewMattersDao;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.bean.Discussion;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.dao.DiscussionDao;
import com.beidasoft.xzfy.caseTrial.caseDiscussion.model.DiscussionModel;
import com.beidasoft.xzfy.caseTrial.common.WebUtils;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

/**   
 * Description:调查管理service
 * @title DiscussionService.java
 * @package com.beidasoft.xzfy.caseTrial.caseDiscussion.service 
 * @author zhangchengkun
 * @version 0.1 2019年5月7日
 */
@Service("discussionService")
public class DiscussionService extends TeeBaseService{
	@Autowired
	private DiscussionDao dao;
	
	@Autowired
	private CaseReviewMattersDao caseRegisterDao;
	
	/**
	 * Description:调查管理新增
	 * @author ZCK
	 * @version 0.1 2019年4月23日
	 * @param request
	 * @param json
	 * @return CaseInvestigation
	 */
	public void save(HttpServletRequest request,TeeJson json) throws Exception{
		//获取当前人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Discussion discussion=new Discussion();
		String id = request.getParameter("id");
		discussion.setId(id);
		//调用共通方法给对象赋值
		WebUtils.requestToBean(request, discussion);
		WebUtils.setAddInfo(discussion, loginUser);
		//保存或者更新
		dao.save(discussion);
	}
	/**
	 * Description:听证管理更新
	 * @author zhangchengkun
	 * @version 0.1 2019年6月2日
	 * @param request
	 * @param json
	 * @throws Exception  void
	 */
	public void update(HttpServletRequest request,TeeJson json) throws Exception{
		//获取当前人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Discussion discussion=new Discussion();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			discussion.setId(id);
			//调用共通方法给对象赋值
			WebUtils.requestToBean(request, discussion);
			WebUtils.setModifyInfo(discussion, loginUser);
			//保存或者更新
			dao.update(discussion);
		}
		
	}
		/**
		 * Description:调查管理查询
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param caseId
		 * @return
		 * CaseInvestigation
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TeeEasyuiDataGridJson getListByCaseId(TeeDataGridModel dm,HttpServletRequest request,DiscussionModel model) {
			TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
			List param = new ArrayList();
			//查询出该案件下的所有调查管理
			String hql = " from Discussion where caseId = ? and isDelete = 0 order by createdTime asc";
			param.add(request.getParameter("caseId"));
			// 设置总记录数
			json.setTotal(dao.countByList("select count(*) " + hql, param));
			//分页查询
			List<Discussion> list = dao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);
			//model集合
			List<DiscussionModel> modelList = new ArrayList<DiscussionModel>();
			//类型转换、modelList内添加对象
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					DiscussionModel modelTemp = new DiscussionModel();
					BeanUtils.copyProperties(list.get(i), modelTemp);
					modelList.add(modelTemp);
				}
			}
			json.setRows(modelList);// 设置返回的行
			return json;
		}
		
		/**
		 * Description:删除调查管理
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param request
		 * void
		 */
		public void delete(HttpServletRequest request) {
			Discussion discussion=new Discussion();
			String id = request.getParameter("id");
			discussion.setId(id);
			discussion.setIsDelete(1);
			dao.update(discussion);
		}
		
		/**
		 * Description:根据单个id获取数据
		 * @author ZCK
		 * @version 0.1 2019年4月23日
		 * @param request
		 * void
		 */
		public Discussion getById(HttpServletRequest request) {
			String id = request.getParameter("id");
			Discussion discussion = (Discussion) dao.get(id);
			return discussion;
		}
		
		/**
		 * Description:会议管理获取案件审理承办人
		 * @author zhangchengkun
		 * @version 0.1 2019年5月22日
		 * @param caseId
		 * @return  String
		 */
		public String getCbr(String caseId) {
			FyCaseHandling caseInfo = caseRegisterDao.get(caseId);
			String firstCbr = caseInfo.getDealMan1Name();
			String secondCbr = caseInfo.getDealMan2Name();
			return firstCbr+","+secondCbr;
		}
}
