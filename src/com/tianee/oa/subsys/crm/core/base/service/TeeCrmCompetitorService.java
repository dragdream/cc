package com.tianee.oa.subsys.crm.core.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.subsys.crm.core.base.bean.TeeCrmCompetitor;
import com.tianee.oa.subsys.crm.core.base.dao.TeeCrmCompetitorDao;
import com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager;
import com.tianee.oa.subsys.crm.setting.dao.TeeChinaCityDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeCrmCompetitorService extends TeeBaseService{
	@Autowired
	TeeCrmCompetitorDao competitorDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	private TeeChinaCityDao chinaCityDao;

	/**
	 * 新建或者更新
	 * @param request 
	 * @param object 模型
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmCompetitor object){
		TeeJson json = new TeeJson();
		if(object.getSid() > 0){
			TeeCrmCompetitor old = competitorDao.get(object.getSid());
			if(old == null){
				json.setRtMsg("此竞争对手已被删除！");
				return json;
			}
			BeanUtils.copyProperties(object,old );
			competitorDao.update(old);
			json.setRtMsg("更新成功！");
		}else{
			competitorDao.save(object);
			json.setRtMsg("新建成功！");
		}
		
		//附件
		String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr") ,"0");
		List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
		List<TeeAttachment> dbAttachSidList = attachmentDao.getAttachmentsByIds(dbAttachSid);
		List<TeeAttachment> attachments = attachmentDao.getAttachmentsByIds(attachmentSidStr);
		attachAll.addAll(dbAttachSidList);
		attachAll.addAll(attachments);
		//处理邮件附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(object.getSid()));
			simpleDaoSupport.update(attach);
		}
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 通用列表
	 * 
	 * @param dm
	 * @param request
	 * @param person
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson manager(TeeDataGridModel dm,
			HttpServletRequest request, TeePerson person ,TeeCrmCompetitor model) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = "from TeeCrmCompetitor where 1 =1 ";
		List paraList = new ArrayList();
		if (!TeeUtility.isNullorEmpty(model.getCompanyAddress())) {//公司地址
			paraList.add("%" + model.getCompanyAddress() + "%");
			hql = hql + " and companyAddress like ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getCompany())) {//公司名称 
			paraList.add( "%" +  model.getCompany() + "%");
			hql = hql + " and company like ?";
		}
		if (!TeeUtility.isNullorEmpty(model.getTelephone())) {//联系电话
			paraList.add("%" + model.getTelephone() + "%");
			hql = hql + " and telephone like ?";
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(competitorDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			hql += " order by " + dm.getSort() + " " + dm.getOrder();
		} else {
			hql += " order by companyCreateDate desc";
		}
		List<TeeCrmCompetitor> list = competitorDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(),dm.getRows(), paraList);// 查询
		List<Map> modelList = new ArrayList<Map>();
		if (list != null && list.size() > 0) {
			for (TeeCrmCompetitor competitor : list) {
				Map map  = parseMap(competitor, true);
				modelList.add(map);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	
	/**
	 *删除 BYIds
	 * @param request
	 * @return
	 */
	public TeeJson delByIds(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("ids"));
		int count = competitorDao.deleteById(sid);
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}
	
	/**
	 * 获取 byId
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid") ,0 );
		TeeCrmCompetitor competitor = competitorDao.get(sid);
		
		Map map  = parseMap(competitor, false);
		if(competitor == null){
			json.setRtMsg("数据未找到！");
			return json;
		}
	
		json.setRtState(true);
		json.setRtData(map);
		json.setRtMsg("获取成功！");
		return json;
	}
	
	/**
	 * 对象转Map
	 * @param competitor
	 * @param isSimple
	 * @return
	 */
	public Map parseMap(TeeCrmCompetitor competitor  , boolean isSimple){
		Map map = new HashMap();
		if(competitor != null){
			map = TeeJsonUtil.bean2Map(competitor);
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			if(!isSimple){
				List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.CRM_COMPETITOR, String.valueOf(competitor.getSid()));
				for(TeeAttachment attach:attaches){
					TeeAttachmentModel m = new TeeAttachmentModel();
					BeanUtils.copyProperties(attach, m);
					m.setUserId(attach.getUser().getUuid()+"");
					m.setUserName(attach.getUser().getUserName());
					m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
					attachmodels.add(m);
				}
			}
			map.put("attachmodels", attachmodels);
			String companyScaleDesc = TeeCrmCodeManager.getChildSysCodeNameCodeNo("COMPANY_SCALE",competitor.getCompanyScale());//公司规模
			map.put("companyScaleDesc", companyScaleDesc);
			String provinceCode = TeeStringUtil.getString(competitor.getProvince());
			String cityCode = TeeStringUtil.getString(competitor.getCity());
			
			String provinceName = "";
			String cityName = "";
			if(!provinceCode.equals("")){
				provinceName = chinaCityDao.getNameByCityCode(provinceCode);
			}
			if(!cityCode.equals("")){
				cityName = chinaCityDao.getNameByCityCode(cityCode);
			}
			map.put("provinceName", provinceName);
			
			map.put("cityName", cityName);
		}
		return map;
	}
	
	
}
