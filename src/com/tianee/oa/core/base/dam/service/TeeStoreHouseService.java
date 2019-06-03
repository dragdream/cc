package com.tianee.oa.core.base.dam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.dam.bean.TeeDamBox;
import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.base.dam.dao.TeeStoreHouseDao;
import com.tianee.oa.core.base.dam.model.TeeStoreHouseModel;
import com.tianee.oa.core.base.imgbase.bean.TeeImgBase;
import com.tianee.oa.core.base.imgbase.utility.Base64Private;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeStoreHouseService extends TeeBaseService{
	@Autowired
	TeeStoreHouseDao roomDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeeDamBoxService damBoxService;
	
	public TeeStoreHouse addRoomModel(TeeStoreHouseModel roomModel){
		TeeStoreHouse room = parseToEntity(roomModel);
		roomDao.save(room);
		return room;
	}
	
	
	/**
	 * model转换成实体类
	 * @param model
	 * @return
	 */
	public  TeeStoreHouse parseToEntity(TeeStoreHouseModel roomModel){
		TeeStoreHouse room=new TeeStoreHouse();
		BeanUtils.copyProperties(roomModel, room);
		Calendar cl = Calendar.getInstance();
		TeePerson person=personDao.get(roomModel.getCreateUserId());
		
		room.setCreateTime(cl);
		room.setCreateUser(person);
		
		//借阅管理员
		if(roomModel.getBorrowManagerId()!=0){
			TeePerson borrowManager=(TeePerson) simpleDaoSupport.get(TeePerson.class,roomModel.getBorrowManagerId());
		    room.setBorrowManager(borrowManager);
		}
		
		//父卷库
		int parentId=roomModel.getParentId();
		if(parentId!=0){
			TeeStoreHouse parent=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,parentId);
			room.setParent(parent);
		}
		
		//所属部门
		TeeDepartment tmpDept = null;
		int deptIds[] = TeeStringUtil.parseIntegerArray(roomModel.getDeptIds());
		for(int deptId:deptIds){
			if(deptId==0){
				continue;
			}
			tmpDept = new TeeDepartment();
			tmpDept.setUuid(deptId);
			room.getDeptPriv().add(tmpDept);
		}
		
		//所属人员
		TeePerson p = null;
		int userIds[] = TeeStringUtil.parseIntegerArray(roomModel.getUserIds());
		for(int userId:userIds){
			if(userId==0){
				continue;
			}
			p = new TeePerson();
			p.setUuid(userId);
			room.getUserPriv().add(p);
		}
		
		
		//所属角色
		TeeUserRole role = null;
		int roleIds[] = TeeStringUtil.parseIntegerArray(roomModel.getRoleIds());
		for(int roleId:roleIds){
			if(roleId==0){
				continue;
			}
			role = new TeeUserRole();
			role.setUuid(roleId);
			room.getRolePriv().add(role);
		}
		
		
		return room;
	}
	
	
	
	/**
	 * 实体类转换成model
	 * @param room
	 * @return
	 */
	public  TeeStoreHouseModel parseToModel(TeeStoreHouse room){
		TeeStoreHouseModel model = new TeeStoreHouseModel();
		BeanUtils.copyProperties(room, model);
		
		//处理创建时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String addTimeDesc="";
		if(!TeeUtility.isNullorEmpty(room.getCreateTime())){
			addTimeDesc=sf.format(room.getCreateTime().getTime());
		}
		model.setCreateTimeStr(addTimeDesc);
		
		//处理借阅管理员
		if(room.getBorrowManager()!=null){
			model.setBorrowManagerId(room.getBorrowManager().getUuid());
			model.setBorrowManagerName(room.getBorrowManager().getUserName());
		}
		
		//处理创建人
		if(room.getCreateUser()!=null){
			model.setCreateUserId(room.getCreateUser().getUuid());
			model.setCreateUserName(room.getCreateUser().getUserName());
		}
		
		//处理父卷库
		if(room.getParent()!=null){
			model.setParentId(room.getParent().getSid());
			model.setParentName(room.getParent().getRoomName());
		}
		
		//处理部门权限
		StringBuffer deptIds = new StringBuffer();
		StringBuffer deptNames = new StringBuffer();
		
		Set<TeeDepartment> deptList = room.getDeptPriv();
		for(TeeDepartment dept:deptList){
			deptIds.append(dept.getUuid()+",");
			deptNames.append(dept.getDeptName()+",");
		}
		
		if(deptIds.length()!=0){
			deptIds.deleteCharAt(deptIds.length()-1);
			deptNames.deleteCharAt(deptNames.length()-1);
		}
		model.setDeptIds(deptIds.toString());
		model.setDeptNames(deptNames.toString());
		
		//处理人员权限
		StringBuffer userIds = new StringBuffer();
		StringBuffer userNames = new StringBuffer();
		
		Set<TeePerson> userList = room.getUserPriv();
		for(TeePerson p:userList){
			userIds.append(p.getUuid()+",");
			userNames.append(p.getUserName()+",");
		}
		
		if(userIds.length()!=0){
			userIds.deleteCharAt(userIds.length()-1);
			userNames.deleteCharAt(userNames.length()-1);
		}
		model.setUserIds(userIds.toString());
		model.setUserNames(userNames.toString());
		
		//处理角色权限
		StringBuffer roleIds = new StringBuffer();
		StringBuffer roleNames = new StringBuffer();
		
		Set<TeeUserRole> roleList = room.getRolePriv();
		for(TeeUserRole role:roleList){
			roleIds.append(role.getUuid()+",");
			roleNames.append(role.getRoleName()+",");
		}
		
		if(roleIds.length()!=0){
			roleIds.deleteCharAt(roleIds.length()-1);
			roleNames.deleteCharAt(roleNames.length()-1);
		}
		model.setRoleIds(roleIds.toString());
		model.setRoleNames(roleNames.toString());
		
		return model;
	}
	
	
	public TeeJson deleteRoom(int sid){
		TeeJson json=new TeeJson();
		TeeStoreHouse  room=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,sid);
		if(room!=null){
			//先判断该卷库下是否存在子卷库
			long childCount=simpleDaoSupport.count("select count(*) from TeeStoreHouse h where h.parent.sid=? ", new Object[]{room.getSid()});
			if(childCount>0){//存在子库
				
				json.setRtState(false);
				json.setRtMsg("该卷库下存在子卷库，不可删除！");
			}else{//不存在子库
				
				//判断该卷库下是否存在卷盒
				List<TeeDamBox> boxList=simpleDaoSupport.executeQuery(" from TeeDamBox b where b.storeHouse.sid=? ", new Object[]{sid});
				if(boxList!=null&&boxList.size()>0){//卷库下存在卷盒
					json.setRtState(false);
					json.setRtMsg("该卷库下存在已经归档的卷盒，不可删除！");
				}else{
					simpleDaoSupport.deleteByObj(room);
					json.setRtState(true);
					json.setRtMsg("删除成功！");
				}
				
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("该卷库不存在！");		
		}
		return json;
	}
	
	
	
	public void updateRoomModel(TeeStoreHouseModel roomModel){
		TeeStoreHouse room = (TeeStoreHouse) roomDao.get(roomModel.getSid());
		BeanUtils.copyProperties(roomModel, room);
		
		//处理借阅管理员
		if(roomModel.getBorrowManagerId()!=0){
			TeePerson borrowManager=(TeePerson) simpleDaoSupport.get(TeePerson.class,roomModel.getBorrowManagerId());
		    room.setBorrowManager(borrowManager);
		}
		
		//处理所属卷库
		if(roomModel.getParentId()!=0){
			TeeStoreHouse parent=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,roomModel.getParentId());
		    room.setParent(parent);
		}
		
		//处理所属部门
		room.getDeptPriv().clear();
		TeeDepartment tmpDept = null;
		int deptIds[] = TeeStringUtil.parseIntegerArray(roomModel.getDeptIds());
		for(int deptId:deptIds){
			if(deptId==0){
				continue;
			}
			tmpDept = new TeeDepartment();
			tmpDept.setUuid(deptId);
			room.getDeptPriv().add(tmpDept);
		}
		
		//处理所属角色
		room.getRolePriv().clear();
		TeeUserRole role = null;
		int roleIds[] = TeeStringUtil.parseIntegerArray(roomModel.getRoleIds());
		for(int roleId:roleIds){
			if(roleId==0){
				continue;
			}
			role = new TeeUserRole();
			role.setUuid(roleId);
			room.getRolePriv().add(role);
		}
		
		//处理所属人员
		room.getUserPriv().clear();
		TeePerson p = null;
		int userIds[] = TeeStringUtil.parseIntegerArray(roomModel.getUserIds());
		for(int userId:userIds){
			if(userId==0){
				continue;
			}
			p = new TeePerson();
			p.setUuid(userId);
			room.getUserPriv().add(p);
		}
		roomDao.update(room);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		datagird = roomDao.getRoomList(dm, requestDatas);
		return datagird;
	}
	
	public TeeStoreHouse getById(int sid){
		TeeStoreHouse room = (TeeStoreHouse) roomDao.get(sid);
		return room;
	}
	
	public TeeStoreHouseModel getModelById(int sid){
		TeeStoreHouse room = getById(sid);
		TeeStoreHouseModel model=parseToModel(room);
		return model;
	}
	
	public List<TeeStoreHouse> getAllRoom(){
		return roomDao.getAllRoom();
	}
//********************************************************************//
	/**
	 * 获取卷库目录树
	 * @param sid
	 * @param loginUser
	 * @return
	 */
	public List<TeeZTreeModel> treeNode(int sid, TeePerson loginUser) {
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		long childCount = 0;
		if(sid==0){//一级卷库
			//取出有权限的 真正的一级卷库
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent is null and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) ) order by orderNum asc", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			//取出有权限  但是父节点没有权限的   不连续的卷库节点  直接当成是一级节点
		/*	List<TeeStoreHouse> houseList1= simpleDaoSupport.find("select h from TeeStoreHouse h left join h.parent p where p is not null and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )   and (p.createUser.uuid!=? and (not exists (select 1 from p.userPriv userPriv1 where userPriv1.uuid = ?)) and (not exists (select 1 from p.deptPriv deptPriv1 where deptPriv1.uuid = ?)) and (not exists (select 1 from p.rolePriv rolePriv1 where rolePriv1.uuid = ?)) ) order by h.orderNum asc", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});*/
			/*System.out.println(houseList.size());
			System.out.println(houseList1.size());*/
			/*houseList.addAll(houseList1);*/
			/*System.out.println(houseList.size());*/
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+"");
				//检测该卷库下是否有子卷库
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else{//根据父卷库id  获取子卷库
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent.sid=? and  (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  order by orderNum asc", new Object[]{sid,loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse  h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}
		return list;
	}


	
	/**
	 * 判断所选择的所属卷库是否是当前编辑的卷库的子库
	 * @param request
	 * @return
	 */
	public TeeJson checkIsChildren(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int  sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int checkId=TeeStringUtil.getInteger(request.getParameter("checkId"), 0);
		if(checkId!=0){
			TeeStoreHouse room=(TeeStoreHouse) simpleDaoSupport.get(TeeStoreHouse.class,checkId);
		    if(room!=null){
		    	if(room.getParent()!=null){
		    		if(room.getParent().getSid()==sid){
		    			json.setRtState(true);
		    		}
		    	}else{
		    		json.setRtState(false);
		    	}
		    }
		}
		
		return json;
	}


	/**
	 * 获取当前登陆人又权限的  卷库  以及  卷库下已经归档的卷盒  树结构
	 * @param request
	 * @return
	 */
	public TeeJson getHouseAndBoxTree(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取传来的卷库主键
		String id=TeeStringUtil.getString(request.getParameter("id"));
		int sid=0;
		if(id!=null&&!("").equals(id)){
			String[] idArray=id.split(";");
			sid=TeeStringUtil.getInteger(idArray[0], 0);
		}
		
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		long childCount = 0;
		long  boxCount = 0;
		if(sid==0){//一级卷库
			//取出有权限的 真正的一级卷库
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent is null and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) ) order by createTime asc", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			//取出有权限  但是父节点没有权限的   不连续的卷库节点  直接当成是一级节点
			List<TeeStoreHouse> houseList1= simpleDaoSupport.find("select h from TeeStoreHouse h left join h.parent p where p is not null and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )   and (p.createUser.uuid!=? and (not exists (select 1 from p.userPriv userPriv1 where userPriv1.uuid = ?)) and (not exists (select 1 from p.deptPriv deptPriv1 where deptPriv1.uuid = ?)) and (not exists (select 1 from p.rolePriv rolePriv1 where rolePriv1.uuid = ?)) ) order by h.createTime asc", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			houseList.addAll(houseList1);
			
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+";room");
				//检测该卷库下是否有子卷库
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					//检测该卷库下  是否有卷盒
					boxCount=simpleDaoSupport.count("select count(*) from TeeDamBox where storeHouse.sid=? and flag=1  ", new Object[]{h.getSid()});
					if(boxCount!=0){
						model.setParent(true);
					}else{
						model.setParent(false);
					}	
				}
				list.add(model);
			}
		}else{//根据父卷库id  获取子卷库   以及父卷库下的卷盒
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent.sid=? and  (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  order by createTime asc", new Object[]{sid,loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+";room");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse  h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					//检测该卷库下  是否有卷盒
					boxCount=simpleDaoSupport.count("select count(*) from TeeDamBox where storeHouse.sid=? and flag=1  ", new Object[]{h.getSid()});
					if(boxCount!=0){
						model.setParent(true);
					}else{
						model.setParent(false);
					}	
				}
				list.add(model);
			}
			
			
			//根据父id  获取该卷库下的卷盒子
			List<TeeDamBox> boxList=simpleDaoSupport.executeQuery("from TeeDamBox where flag=1 and storeHouse.sid=? ", new Object[]{sid});
		    if(boxList!=null&&boxList.size()>0){
		    	for (TeeDamBox teeDamBox : boxList) {
		    		TeeZTreeModel model = new TeeZTreeModel();
					model.setOpen(true);
					model.setName(teeDamBox.getBoxNo());
					model.setTitle(teeDamBox.getBoxNo());
					model.setIconSkin(TeeZTreeModel.FILE_FOLDER_SHARE);
					model.setId(teeDamBox.getSid()+";box");
					model.setParent(false);
					list.add(model);
				}
		    }
		}
        json.setRtState(true);
        json.setRtData(list);
		return json;
	}


	public List<TeeZTreeModel> deptArchiveTreeNode(int sid, TeePerson loginUser) {
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		long childCount = 0;
		if(sid==0){//一级卷库
			//取出有权限的 真正的一级卷库  并且不是公文档案文件夹
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent is null and h.isDocDir!=1 and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) ) order by orderNum asc", new Object[]{loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+"");
				//检测该卷库下是否有子卷库
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}else{//根据父卷库id  获取子卷库
			List<TeeStoreHouse> houseList = simpleDaoSupport.find("from TeeStoreHouse h where h.parent.sid=? and  (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  order by orderNum asc", new Object[]{sid,loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
			for(TeeStoreHouse h:houseList){
				TeeZTreeModel model = new TeeZTreeModel();
				model.setOpen(true);
				model.setName(h.getRoomName());
				model.setTitle(h.getRoomName());
				model.setIconSkin(TeeZTreeModel.FILE_FOLDER);
				model.setId(h.getSid()+"");
				//检测该站点下有无栏目
				childCount = simpleDaoSupport.count("select count(*) from TeeStoreHouse  h where h.parent.sid =? and (h.createUser.uuid=? or (exists (select 1 from h.userPriv userPriv where userPriv.uuid = ?)) or (exists (select 1 from h.deptPriv deptPriv where deptPriv.uuid = ?)) or (exists (select 1 from h.rolePriv rolePriv where rolePriv.uuid = ?)) )  ", new Object[]{h.getSid(),loginUser.getUuid(),loginUser.getUuid(),loginUser.getDept().getUuid(),loginUser.getUserRole().getUuid()});
				if(childCount!=0){
					model.setParent(true);
				}else{
					model.setParent(false);
				}
				list.add(model);
			}
		}
		return list;
	}
	
	
	
}
