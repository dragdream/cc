package com.tianee.oa.core.priv.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.oreilly.servlet.Base64Encoder;
import com.sun.xml.internal.bind.v2.runtime.output.Encoded;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.priv.bean.TeeMenuGroup;
import com.tianee.oa.core.priv.bean.TeeSysMenu;
import com.tianee.oa.core.priv.bean.TeeSysMenuPriv;
import com.tianee.oa.core.priv.dao.TeeMenuDao;
import com.tianee.oa.core.priv.dao.TeeMenuGroupDao;
import com.tianee.oa.core.priv.model.TeeMenuGroupModul;
import com.tianee.oa.core.priv.model.TeeSysMenuModel;
import com.tianee.oa.core.priv.model.TeeSysMenuPrivModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.cache.Cache;
import com.tianee.webframe.util.cache.Element;
import com.tianee.webframe.util.cache.TeeCacheManager;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

/**
 * 
 * @author syl
 *
 */
@Service
public class TeeMenuGroupService extends TeeBaseService  {
	@Autowired
	private TeeMenuGroupDao menuDao;
	
	@Autowired
	private TeeMenuDao sysMenuDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSysMenuPrivService menuPrivService;
	
	
	
	/**
	 * 新增
	 * @param menugroup
	 */
	@TeeLoggingAnt(template="新建权限组，【名称：{$1.menuGroupName},编号：{$1.menuGroupNo}】",type="005A")
	public void addService(TeeMenuGroup menugroup){
		menuDao.addMenuGroup(menugroup);
	}

	/**
	 * 通用列表
	 * @param dm
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,TeePerson loginPerson) {
		TeePerson person = personDao.get(loginPerson.getUuid());
		TeeDepartment dept1 = person.getDept();
		Object parm[] = {dept1.getUuid()};
		
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeMenuGroup t where 1=1 and t.dept.uuid = ?  ";
		String totalHql = " select count(*) " + hql;
		j.setTotal(menuDao.count(totalHql,parm));// 设置总记录数
		if (!TeeUtility.isNullorEmpty(dm.getSort()) ) {// 设置排序
			String sortDesc = "";
			if(dm.getSort().startsWith(",")){
				sortDesc = dm.getSort().substring(1, dm.getSort().length());
			}
			hql += " order by " + sortDesc + " " + dm.getOrder();
		}else{
			hql += "order by menuGroupNo";
		}

		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		List<TeeMenuGroup> groupList = menuDao.pageFind(hql,firstIndex , dm.getRows(), parm);// 查询
		List<TeeMenuGroupModul> groupModelList = new ArrayList<TeeMenuGroupModul>();
		if (groupList != null && groupList.size() > 0) {
			for (TeeMenuGroup group : groupList) {
				TeeMenuGroupModul um = new TeeMenuGroupModul();

				BeanUtils.copyProperties(group, um);
				//
				if(group.getDept()!=null) {
					TeeDepartment dept = group.getDept();
					um.setDeptUuid(dept.getUuid());
					um.setDeptName(dept.getDeptName());
					TeeDepartment unit = dept.getUnit();
					if(unit != null) {
						um.setUnitUuid(unit.getUuid());
						um.setUnitName(unit.getDeptName());
					}
				}
				//获取拥有该权限组的用户数
				um.setUserNum(getUserNumByMenuGroupId(group.getUuid()));
				groupModelList.add(um);
				//赋权部门
				String deptsIdStr = "";
				String deptsNameStr = "";
				List<TeeDepartment> deptList = group.getDepts();
				if(deptList != null){
					for (TeeDepartment dept:deptList) {
						deptsIdStr = deptsIdStr + dept.getUuid() +",";
						deptsNameStr = deptsNameStr + dept.getDeptName() +",";
					}
					
				}
				um.setDeptsIdStr(deptsIdStr);
				um.setDeptsNameStr(deptsNameStr);
				
			}
		}
		j.setRows(groupModelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 根据权限组主键    获取拥有该权限组的人员数
	 * @param uuid
	 * @param i
	 * @return
	 */
	private int getUserNumByMenuGroupId(int uuid) {
		return TeeStringUtil.getInteger(simpleDaoSupport.count("select count(p.uuid) from TeePerson p where exists(select 1 from p.menuGroups mg where mg.uuid=? ) and p.deleteStatus<>1 ", new Object[]{uuid}), 0);
	}

	/**
	 * 更新
	 * @param TeeMenuGroup
	 */
	@TeeLoggingAnt(template="编辑权限组，【名称：{$1.menuGroupName},编号：{$1.menuGroupNo}】",type="005B")
	public void updateMenuService(TeeMenuGroup menugroup) {
		menuDao.update(menugroup);	
	}
	
	
	
	
	/**
	 * 查询 byId
	 * @param TeeMenuGroup
	 */
	public TeeMenuGroup selectByUuid(int uuid) {
		
		//TeeSysMenu sysMenu = (TeeSysMenu) menuDao.selectMenuByUuid(uuid);	
		TeeMenuGroup sysMenu = (TeeMenuGroup) menuDao.selectById(uuid);
		return sysMenu;
	}
	
	
	/**
	 * 查询 by uuids 字符串
	 * @param uuids :uuid字符串
	 */
	public List<TeeMenuGroup> selectByUuids(String uuids) {
		return  menuDao.getMenuGroupListByUuids(uuids);
	}
	
	public void clearMenuAndPreson(String uuids ){
		//清空下菜单
		List<TeeMenuGroup> list = menuDao.getMenuGroupListByUuids(uuids);
		for (int i = 0; i < list.size(); i++) {
			TeeMenuGroup group = list.get(i);
			group.setSysMenus(null);
			menuDao.updateMenuGroup(group);
		}
		//清空人员
		menuDao.delGroupPerson(uuids);
	}
	
	/**
	 * 删除  by UUIds
	 * 
	 * @param uuids : 根据菜单组UUID字符串
	 */
	public int delSysMenuGroupByUUids(String uuids) {
		int count = 0 ;
		List<TeeMenuGroup> group = menuDao.getMenuGroupListByUuids(uuids);
		for (int i = 0; i < group.size(); i++) {
			TeeSysLog sysLog = TeeSysLog.newInstance();
			sysLog.setType("005C");
			sysLog.setRemark("删除菜单组,【名称：" + group.get(i).getMenuGroupName() + ",编号：" +group.get(i).getMenuGroupNo() + " 】");
			TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
			menuDao.deleteByObj(group.get(i));
			count++;
		}
		return count;
		//return menuDao.delMenuGroupByUuids(uuids);
	}
	
	
	/**
	 * 查询 条件查询 
	 * @param TeeMenuGroup
	 */
	public List<TeeMenuGroup> selectGroupMenu(String hql, Object[] values) {
		List<TeeMenuGroup> list = menuDao.selectGroupMenu(hql, values);
		return list;
	}
	
	

	/**
	 * 查询 条件查询 
	 * @param TeeMenuGroup
	 */
	public List<TeeMenuGroupModul> getMenuGroupSelectAll() {
		List<TeeMenuGroupModul> modulList = new ArrayList<TeeMenuGroupModul>();
		List list = menuDao.selectAll();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			TeeMenuGroupModul model = new TeeMenuGroupModul();
			model.setUuid(Integer.parseInt(obj[0].toString()));
			model.setMenuGroupName((String)obj[1]);
			modulList.add(model);
		}
		return modulList;
	}
	
	
	
	
	/**
	 * 根据擦操作类型重新获取菜单数组
	 * @param list 源菜单list
	 * @param opt ： 操作类型 0-添加 ， 1- 删除
	 * @param menuList : 被添加或删除的菜单list
	 * @return 
	 */
	public List<TeeSysMenu> reGetMenuList( List<TeeSysMenu> list,String opt, List<TeeSysMenu> menuList){
		for (int i = 0; i < menuList.size(); i++) {
			TeeSysMenu sysMenu = menuList.get(i);
			//String sysMenuUUID  = sysMenu.getUuid();
			if(opt.equals("0")){//添加
				if(list.size()>0){//如果存在
					if(!list.contains(sysMenu)){
						list.add(sysMenu);
					}
				}else{//如果不存在
					list.add(sysMenu);
				}
				
			}else{//删除
				if(list.size()>0){//如果存在
					if(list.contains(sysMenu)){
						list.remove(sysMenu);
					}
				}
			}
			
		}
		return list;
		
	}
	/***
	 * 获取带权限的菜单 与登录人绑定
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
/*	@Cacheable(value="menuResPriv",condition="#person.uuid == ''",key="'MENU_RES_PRIV_'+#person.uuid")
	@Transactional(readOnly = true) *///spring 三级缓存，原生
	public List<TeeSysMenuModel> getPrivSysMenu(TeePerson person) throws UnsupportedEncodingException{
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		String sysMenuIds = "";//菜单uuid字符串，已逗号分隔，方便查有权限路径（页面和controller）
		List<TeeSysMenu>  listSet = new ArrayList<TeeSysMenu>(); 
		List<TeeSysMenuModel>  listModel = new ArrayList<TeeSysMenuModel>(); 
		
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		
		
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			return listModel;
		}
	   list = sysMenuDao.getSysMenuListByMenuGroupUuids(groupIds);
		
	   BASE64Encoder en=new BASE64Encoder(); 
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menu = list.get(i);
			if(!listSet.contains(menu)){
				listSet.add(menu);
				TeeSysMenuModel um = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, um);
				//重新设置menuCode
				if(!TeeUtility.isNullorEmpty(menu.getMenuCode())){
					um.setMenuCode("/teeMenuGroup/visitPirv.action?url="+en.encode(menu.getMenuCode().getBytes()).replaceAll("\r\n", "")+"&menuName="+URLEncoder.encode(menu.getMenuName(), "utf-8"));
				}
				
				listModel.add(um);
				sysMenuIds = sysMenuIds + menu.getUuid() + ",";
			}
		
		}
		//获取有关联的菜单路径
		if(!TeeUtility.isNullorEmpty(sysMenuIds)){
			//获取缓存对象
			Cache cache = TeeCacheManager.getCache("menuPrivPersonal");
			if(cache != null){
				//先删除个人缓存菜单权限元素
				cache.remove(person.getUuid());
				Map<String , Object> map = new HashMap<String , Object>();
				TeeSysMenuPrivModel model = new TeeSysMenuPrivModel();
				model.setPrivFlag("1");
				List<TeeSysMenuPriv> menuPrivlist  = menuPrivService.selectPrivByMenuIds(model, sysMenuIds);
				for (int i = 0; i < menuPrivlist.size(); i++) {
					TeeSysMenuPriv priv = menuPrivlist.get(i);
					if(!TeeUtility.isNullorEmpty(priv.getPrivUrl())){
						map.put(priv.getPrivUrl(), priv.getSid());
					}
				}
				//存放个人缓存菜单权限元素
				Element element = new Element(person.getUuid() , map);
				//存放到缓存里
				cache.put(element);
				/*Element element2 =  cache.get(person.getUuid());
				Map o = (Map)element2.getValue();
				System.out.println(o);*/
			}
			
		}
		return listModel;
	}
	
	
	/***
	 * 获取带权限的一级菜单 与登录人绑定
	 * @param request
	 * @return
	 */
/*	@Cacheable(value="menuResPriv",condition="#person.uuid == ''",key="'MENU_RES_PRIV_'+#person.uuid")
	@Transactional(readOnly = true) *///spring 三级缓存，原生
	public List<TeeSysMenuModel> getPrivFirstSysMenu(TeePerson person  ){
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		List<TeeSysMenu>  listSet = new ArrayList<TeeSysMenu>(); 
		List<TeeSysMenuModel>  listModel = new ArrayList<TeeSysMenuModel>(); 
		
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			return listModel;
		}
	   list = sysMenuDao.getSysMenuListByMenuGroupUuids(groupIds);
		
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menu = list.get(i);
			if(!listSet.contains(menu) && menu.getMenuId().length() == 3){
				
				listSet.add(menu);
				TeeSysMenuModel um = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, um);
				listModel.add(um);
			}
		
		}
		return listModel;
	}

	/**
	 * 批量设置菜单组 与人员的权限----根据所选人员设置
	 * @param request
	 * @return
	 */
	@TeeLoggingAnt(template="批量设置人员菜单权限组: {logModel.desc} ;{#.rtMsg}",type="0020")
	public TeeJson setMuiltPersonMenuGroupPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeRequestInfoContext.getRequestInfo().setUserId(loginPerson.getUuid() + "");
		TeeRequestInfoContext.getRequestInfo().setUserName(loginPerson.getUserName());
		
		String uuids = request.getParameter("uuids");//菜单组UUID字符串已逗号分隔
		String personIds = request.getParameter("personIds");//所选人员Id，以逗号分隔
		String opt = TeeStringUtil.getString(request.getParameter("opt") , "0");
		String usreNames = "";//人员姓名
		String menuGroupNames = "";//菜单组名称
		String optDesc = "添加";
		if(opt.equals("1")){
			optDesc = "删除";
		}
		try {

			if(!TeeUtility.isNullorEmpty(uuids) && !TeeUtility.isNullorEmpty(personIds)){
				List<TeePerson> pl = personDao.getPersonByUuids(personIds);
				List<TeeMenuGroup> groupList = menuDao.getMenuGroupListByUuids(uuids);
				Set<TeeMenuGroup> set = new HashSet<TeeMenuGroup>(groupList);
				if(pl.size() > 0 && groupList.size() > 0){
					for (int i = 0; i < pl.size(); i++) {
						TeePerson person = pl.get(i);
						usreNames = usreNames + person.getUserName() + ",";
						List<TeeMenuGroup> mgList = person.getMenuGroups();
						Set<TeeMenuGroup> set2 = new HashSet<TeeMenuGroup>(mgList);
						if(opt.equals("1")){//删除
							set2.removeAll(set);
						}else{//添加
							set2.addAll(set);
						}
						person.setMenuGroups(new ArrayList(set2));
					}
				}
				
				for (int i = 0; i < groupList.size(); i++) {
					TeeMenuGroup group = groupList.get(i);
					menuGroupNames = menuGroupNames + group.getMenuGroupName() + ",";
				}
				
			}
			json.setRtState(true);
			json.setRtMsg("设置成功！");
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			//throw ex;
		}
		String desc = "[操作:" + optDesc + "],[人员:" + usreNames + "],[菜单组:" + menuGroupNames + "]";
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("desc", desc);//添加其他参数
		return json;
	}
	

	public void setMenuDao(TeeMenuGroupDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setSysMenuDao(TeeMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

	
	
	/**
	 * 获取所有的权限组  不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAllMenuGroupList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql = " from TeeMenuGroup t where 1=1  order by t.menuGroupNo";
		List<TeeMenuGroup> roles =simpleDaoSupport.executeQuery(hql, null);
		List<TeeMenuGroupModul> rolemodel = new ArrayList<TeeMenuGroupModul>();
		if (roles != null && roles.size() > 0) {
			for (TeeMenuGroup role : roles) {
				TeeMenuGroupModul um = new TeeMenuGroupModul();
				BeanUtils.copyProperties(role, um);
				rolemodel.add(um);
			}
		}
		json.setRtState(true);
		json.setRtData(rolemodel);
		return json;
	}

	
	/**
	 * 访问带有url的菜单  需要写入实时访问日志
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public TeeJson visitPirv(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		
		BASE64Decoder den=new BASE64Decoder(); 
		TeeJson json=new TeeJson();
		String menuName=URLDecoder.decode(TeeStringUtil.getString(request.getParameter("menuName")),"utf-8");
		String url=TeeStringUtil.getString(request.getParameter("url"));
		String menuId=TeeStringUtil.getString(request.getParameter("menuId"));
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		
		TeeSysLog log=TeeSysLog.newInstance();
		log.setErrorFlag(0);
		log.setPersonId(loginUser.getUuid());
		log.setRemark(loginUser.getUserName()+"访问了'"+menuName+"'页面");
		log.setTime(Calendar.getInstance());
		log.setType("VISIT");
		log.setUserId(loginUser.getUserId());
		log.setUserName(loginUser.getUserName());
		
		simpleDaoSupport.save(log);
		//进行页面跳转
		String url1=new String(den.decodeBuffer(url),"utf-8");
		if(url1.indexOf("?") >= 0) {
			response.sendRedirect(url1 + "&menuId=" + menuId);
		}else {
			response.sendRedirect(url1 + "?menuId=" + menuId);
		}
		
		return json;
	}

	
	/**
	 * 拥有权限组用户数
	 * @param request
	 * @param response
	 * @return
	 */
	public TeeJson getStatisticsDetail(HttpServletRequest request,
			HttpServletResponse response) {
		TeeJson json=new TeeJson();
		int menuGroupId=TeeStringUtil.getInteger(request.getParameter("menuGroupId"), 0);
	   
		List<Map> data=new ArrayList<Map>();
	    Map map=null;
	    List<Map> personList=null;
	    
	    //获取有权限的部门
	    List<TeeDepartment> deptList=simpleDaoSupport.executeQuery(" select distinct(p.dept) from TeePerson p where p.deleteStatus<>1 and exists(select 1 from p.menuGroups mg where mg.uuid=? )", new Object[]{menuGroupId});
		if(deptList!=null&&deptList.size()>0){
			for (TeeDepartment teeDepartment : deptList) {
				String userNames="";
				map=new HashMap();
				map.put("deptName", teeDepartment.getDeptFullName());
				//获取人数
				personList=simpleDaoSupport.getMaps("select p.userName as userName from TeePerson p where p.deleteStatus<>1 and exists(select 1 from p.menuGroups mg where mg.uuid=? ) and p.dept.uuid=? ", new Object[]{menuGroupId,teeDepartment.getUuid()});
			    if(personList!=null&&personList.size()>0){
			    	map.put("userNum",personList.size());
			    	for (Map p : personList) {
						userNames+=p.get("userName")+",";
					}
			    	if(userNames.endsWith(",")){
			    		userNames=userNames.substring(0,userNames.length()-1);
			    	}
			    	map.put("userNames", userNames);
			    }
				
			   data.add(map);
			}
			
		}
		
		if(data!=null&&data.size()>0){
			Map hj=new HashMap();
			hj.put("deptName", "<span style=\"font-weight:bold;\">合计</span>");
			hj.put("userNames","");
			hj.put("userNum","<span style=\"font-weight:bold;\">"+ getUserNumByMenuGroupId(menuGroupId)+"</span>");
		    data.add(hj);
		}
		json.setRtState(true);
		json.setRtData(data);
		return json;
	}

	
	
	/**
	 * 根据应用系统id 获取有权限的菜单
	 * @param person
	 * @param sysId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
public List<TeeSysMenuModel> getPrivSysMenu2(TeePerson person, int sysId) throws UnsupportedEncodingException {
		List<TeeSysMenu>  list = new ArrayList<TeeSysMenu>(); 
		String sysMenuIds = "";//菜单uuid字符串，已逗号分隔，方便查有权限路径（页面和controller）
		List<TeeSysMenu>  listSet = new ArrayList<TeeSysMenu>(); 
		List<TeeSysMenuModel>  listModel = new ArrayList<TeeSysMenuModel>(); 
		
		person = personDao.selectPersonById(person.getUuid());//重新从数据库取依稀
		
		
		List<TeeMenuGroup> groupList = person.getMenuGroups();
		String groupIds = "";
		for (int i = 0; i < groupList.size(); i++) {
			TeeMenuGroup group = groupList.get(i);
			groupIds = groupIds  + group.getUuid() + ",";
		}
		if(TeeUtility.isNullorEmpty(groupIds)){
			return listModel;
		}
	   list = sysMenuDao.getSysMenuListByMenuGroupUuids2(groupIds,sysId);
		
	   BASE64Encoder en=new BASE64Encoder(); 
		for (int i = 0; i < list.size(); i++) {
			TeeSysMenu menu = list.get(i);
			if(!listSet.contains(menu)){
				listSet.add(menu);
				TeeSysMenuModel um = new TeeSysMenuModel();
				BeanUtils.copyProperties(menu, um);
				//重新设置menuCode
				if(!TeeUtility.isNullorEmpty(menu.getMenuCode())){
					um.setMenuCode("/teeMenuGroup/visitPirv.action?url="+en.encode(menu.getMenuCode().getBytes())+"&menuName="+URLEncoder.encode(menu.getMenuName(), "utf-8") + "&menuId=" + menu.getUuid());
				}
				
				listModel.add(um);
				sysMenuIds = sysMenuIds + menu.getUuid() + ",";
			}
		
		}
		//获取有关联的菜单路径
		if(!TeeUtility.isNullorEmpty(sysMenuIds)){
			//获取缓存对象
			Cache cache = TeeCacheManager.getCache("menuPrivPersonal");
			if(cache != null){
				//先删除个人缓存菜单权限元素
				cache.remove(person.getUuid());
				Map<String , Object> map = new HashMap<String , Object>();
				TeeSysMenuPrivModel model = new TeeSysMenuPrivModel();
				model.setPrivFlag("1");
				List<TeeSysMenuPriv> menuPrivlist  = menuPrivService.selectPrivByMenuIds(model, sysMenuIds);
				for (int i = 0; i < menuPrivlist.size(); i++) {
					TeeSysMenuPriv priv = menuPrivlist.get(i);
					if(!TeeUtility.isNullorEmpty(priv.getPrivUrl())){
						map.put(priv.getPrivUrl(), priv.getSid());
					}
				}
				//存放个人缓存菜单权限元素
				Element element = new Element(person.getUuid() , map);
				//存放到缓存里
				cache.put(element);
				/*Element element2 =  cache.get(person.getUuid());
				Map o = (Map)element2.getValue();
				System.out.println(o);*/
			}
			
		}
		return listModel;
	}
	
	
	public List<TeeMenuGroup>  getGroupListByDept(TeeDepartment dept) {
		return this.menuDao.getGroupListByDept(dept);	
	}
	
	public List<TeeMenuGroup>  getGroupListByDeptUuid(int detpUuid) {
		return this.menuDao.getGroupListByDeptUuid(detpUuid);	
	}

	
}
