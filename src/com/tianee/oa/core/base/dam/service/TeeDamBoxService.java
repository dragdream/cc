package com.tianee.oa.core.base.dam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.dam.bean.TeeDamBox;
import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.base.dam.dao.TeeDamBoxDao;
import com.tianee.oa.core.base.dam.dao.TeeStoreHouseDao;
import com.tianee.oa.core.base.dam.model.TeeDamBoxModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeDamBoxService extends TeeBaseService{
	@Autowired
	TeeDamBoxDao boxDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeeStoreHouseDao roomDao;
	
	/**
	 * 新建/编辑卷盒
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String boxNo=TeeStringUtil.getString(request.getParameter("boxNo"));//盒号
		String year=TeeStringUtil.getString(request.getParameter("year"));//年份
		String mj=TeeStringUtil.getString(request.getParameter("mj"));//密级
		String retentionPeriod=TeeStringUtil.getString(request.getParameter("retentionPeriod"));//保管期限
		String remark=TeeStringUtil.getString(request.getParameter("remark"));//备注
		TeeDamBox box=null;
		if(sid>0){//编辑
			box=(TeeDamBox) simpleDaoSupport.get(TeeDamBox.class,sid);
			box.setBoxNo(boxNo);
			box.setMj(mj);
			box.setRemark(remark);
			box.setRetentionPeriod(retentionPeriod);
			box.setYear(year);
			simpleDaoSupport.update(box);
			json.setRtState(true);
			
		}else{//新增
			box=new TeeDamBox();
			box.setBoxNo(boxNo);
			box.setCreateTime(Calendar.getInstance());
			box.setCreateUser(loginUser);
			box.setFileNum(0);
			box.setFlag(0);
			box.setMj(mj);
			box.setRemark(remark);
			box.setRetentionPeriod(retentionPeriod);
			box.setYear(year);
			simpleDaoSupport.save(box);
			json.setRtState(true);
		}
		return json;
	}

	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeDamBox box=(TeeDamBox) simpleDaoSupport.get(TeeDamBox.class,sid);
			if(box!=null){
				TeeDamBoxModel model=parseToModel(box);
				json.setRtState(true);
				json.setRtData(model);
			}else{
				json.setRtState(false);
				json.setRtMsg("数据获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}	
		return json;
	}




	/**
	 * 将实体类转换成model
	 * @param box
	 * @return
	 */
	private TeeDamBoxModel parseToModel(TeeDamBox box) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeDamBoxModel model=new TeeDamBoxModel();
		BeanUtils.copyProperties(box, model);
		//创建人
		if(box.getCreateUser()!=null){
			model.setCreateUserId(box.getCreateUser().getUuid());
			model.setCreateUserName(box.getCreateUser().getUserName());
		}
		//创建时间
		if(box.getCreateTime()!=null){
			model.setCreateTimeStr(sf.format(box.getCreateTime().getTime()));
		}
		//归档时间
		if(box.getArchiveTime()!=null){
			model.setArchiveTimeStr(sf.format(box.getArchiveTime().getTime()));
		}
		//所属卷库
		if(box.getStoreHouse()!=null){
			model.setStoreHouseId(box.getStoreHouse().getSid());
			model.setStoreHouseName(box.getStoreHouse().getRoomName());
		}
		//保管年限
		if(box.getRetentionPeriod()!=null&&!("").equals(box.getRetentionPeriod())){
			model.setRetentionPeriodStr(TeeSysCodeManager.getChildSysCodeNameCodeNo("DAM_RT", box.getRetentionPeriod()));
		}
		return model;
	}




	/**
	 * 根据主键删除卷盒子
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeDamBox box=(TeeDamBox) simpleDaoSupport.get(TeeDamBox.class,sid);
			if(box!=null){
				//判断该卷盒下是否有档案   
				List<TeeFiles> fileList=simpleDaoSupport.executeQuery(" from TeeFiles where box.sid=? ", new Object[]{box.getSid()});
				if(fileList!=null&&fileList.size()>0){//有文件存在
					json.setRtState(false);
					json.setRtMsg("该卷盒里存在档案文件，暂且不能删除！");
				}else{
					simpleDaoSupport.deleteByObj(box);
					json.setRtState(true);
					json.setRtMsg("删除成功！");
				}
				
			}else{
				json.setRtState(false);
				json.setRtMsg("数据不存在！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}



    /**
     * 获取当前登录人创建的  并且 未归档的卷盒
     * @param dm
     * @param request
     * @return
     */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<Object> param=new ArrayList<Object>();
		String hql = " from TeeDamBox where flag=0  and createUser.uuid=? ";
		param.add(loginUser.getUuid());
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by createTime asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeDamBox> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeDamBoxModel> modelList=new ArrayList<TeeDamBoxModel>();
		TeeDamBoxModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeDamBox b:list) {
				model=parseToModel(b);
				modelList.add(model);
			}	
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}




	/**
	 * 判断盒号是否存在
	 * @param request
	 * @return
	 */
	public TeeJson checkBoxNo(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的盒号
		String boxNo=TeeStringUtil.getString(request.getParameter("boxNo"));
		//获取前台页面传来的sid   以此来判断是新建还是编辑
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		List<TeeDamBox> list=null;
		if(sid>0){//编辑
			list=simpleDaoSupport.find(" from TeeDamBox where boxNo=? and  sid!=? ", new Object[]{boxNo,sid});
		}else{//新建
			list=simpleDaoSupport.find(" from TeeDamBox where boxNo=? ", new Object[]{boxNo});
		}
		if(list!=null&&list.size()>0){//盒号已经存在
			json.setRtState(false);
		}else{
			json.setRtState(true);//盒号不存在
		}
		return json;
	}




	/**
	 * 获取当前登陆人创建   并且未归档的卷盒
	 * @param request
	 * @return
	 */
	public TeeJson getAllBoxByLoginUser(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		String hql = " from TeeDamBox where flag=0  and createUser.uuid=?  order by createTime asc";
		
		List<TeeDamBox> list =simpleDaoSupport.executeQuery(hql, new Object[]{loginUser.getUuid()});

		List<TeeDamBoxModel> modelList=new ArrayList<TeeDamBoxModel>();
		TeeDamBoxModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeDamBox b:list) {
				model=parseToModel(b);
				modelList.add(model);
			}	
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}




	/**
	 * 归档
	 * @param request
	 * @return
	 */
	public TeeJson archive(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int storeHouseId=TeeStringUtil.getInteger(request.getParameter("storeHouseId"), 0);
		String boxIds=TeeStringUtil.getString(request.getParameter("boxIds"));
		String []boxIdArray=boxIds.split(",");
		if(storeHouseId!=0){
			TeeStoreHouse storeHouse=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,storeHouseId);
			if(storeHouse!=null){
			   TeeDamBox box=null;
			   for (String str : boxIdArray) { 
				   //修改卷盒信息
				   int boxId=TeeStringUtil.getInteger(str, 0);
				   box=(TeeDamBox) simpleDaoSupport.get(TeeDamBox.class,boxId);
				   box.setArchiveTime(Calendar.getInstance());
				   box.setFlag(1);//已归档
				   box.setStoreHouse(storeHouse);
				   simpleDaoSupport.update(box);
                   //修改档案文件信息 
				   simpleDaoSupport.executeUpdate(" update TeeFiles file  set file.archiveTime=?,file.opFlag=4,file.storeHouse=? where file.box.sid=?",
						   new Object[]{Calendar.getInstance(),storeHouse,box.getSid()});  
			   }
			   json.setRtState(true);
			   json.setRtMsg("归档成功！");
			}else{
				json.setRtState(false);
				json.setRtMsg("卷库数据获取失败!");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("卷库数据获取失败!");
		}
		return json;
	}
	
	
}
