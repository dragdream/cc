package com.tianee.oa.subsys.officialSeal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.officialSeal.bean.TeeOfficialSeal;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeOfficialSealService extends TeeBaseService{

	
	
	/**
	 * 新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的参数
		String keyWord=TeeStringUtil.getString(request.getParameter("keyWord"));
		String bussRuleNum=TeeStringUtil.getString(request.getParameter("bussRuleNum"));
		String templateNum=TeeStringUtil.getString(request.getParameter("templateNum"));
		String creditCode=TeeStringUtil.getString(request.getParameter("creditCode"));
		String userName=TeeStringUtil.getString(request.getParameter("userName"));
		
		//获取前台页面传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeOfficialSeal seal=null;
		if(sid>0){//编辑
			seal=(TeeOfficialSeal) simpleDaoSupport.get(TeeOfficialSeal.class,sid);
		    if(seal!=null){
		    	seal.setBussRuleNum(bussRuleNum);
                seal.setCreditCode(creditCode);
                seal.setKeyWord(keyWord);
                seal.setTemplateNum(templateNum);
                seal.setUserName(userName);
                
                simpleDaoSupport.update(seal);
                json.setRtState(true);
		    }else{
		    	json.setRtState(false);
		    	json.setRtMsg("该公章已被删除！");
		    }
		}else{//新建
			seal=new TeeOfficialSeal();
			seal.setBussRuleNum(bussRuleNum);
            seal.setCreditCode(creditCode);
            seal.setKeyWord(keyWord);
            seal.setTemplateNum(templateNum);
            seal.setUserName(userName);
            
            simpleDaoSupport.save(seal);
            json.setRtState(true);
		}
		return json;
	}

	
	
	
	/**
	 * 根据主键删除
	 * @param request
	 * @return
	 */
	public TeeJson delById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		//根据主键获取对象
		TeeOfficialSeal s=(TeeOfficialSeal) simpleDaoSupport.get(TeeOfficialSeal.class,sid);
		if(s!=null){
			simpleDaoSupport.deleteByObj(s);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该公章信息已不存在！");
		}
		return json;
	}




	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeOfficialSeal s=(TeeOfficialSeal) simpleDaoSupport.get(TeeOfficialSeal.class,sid);
		if(s!=null){
			json.setRtState(true);
			json.setRtData(s);
		}else{
			json.setRtState(false);
			json.setRtMsg("获取公章信息失败！");
		}
		return json;
	}




	
	/**
	 * 根据关键字获取公章的信息
	 * @param request
	 * @return
	 */
	public TeeOfficialSeal getSealByKeyWord(String keyWord) {
		String hql=" from TeeOfficialSeal where keyWord=? ";
		List<TeeOfficialSeal> list=simpleDaoSupport.executeQuery(hql, new  Object[]{keyWord});
		TeeOfficialSeal s=null;
		if(list!=null&&list.size()>0){
			 s=list.get(0);
		}
		return s;
	}




	/**
	 * 获取所有公章列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getAllList(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from TeeOfficialSeal where 1=1 ";
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数

		hql+=" order by sid asc ";
		List<TeeOfficialSeal> TeeOfficialSeal = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		j.setRows(TeeOfficialSeal);// 设置返回的行
		return j;
	}

}
