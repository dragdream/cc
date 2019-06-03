package com.tianee.oa.core.base.fileNetdisk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileDeptPriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdiskReader;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileOptRecord;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileRolePriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskReaderDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskReaderModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeFileNetdiskReaderService extends TeeBaseService {
	@Autowired
	private TeeFileNetdiskReaderDao dao;

	@Autowired
	private TeeFileNetdiskDao fileNetdiskDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeFileUserPrivDao fileUserPrivDao;
	@Autowired
	private TeeFileDeptPrivDao fileDeptPrivDao;
	@Autowired
	private TeeFileRolePrivDao fileRolePrivDao;

	@Autowired
	private TeeFileNetdiskService fileNetDiskService;
	/**
	 * @function: 新建数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson addObj(Map requestMap, TeePerson loginPerson, TeeFileNetdiskReaderModel model) throws ParseException {

		boolean isSignRead = dao.isSignReadDao(model.getFileNetdiskId(), loginPerson.getUuid());
		if (!isSignRead) {
			TeeFileNetdiskReader netdiskReader = new TeeFileNetdiskReader();
			/*TeeFileNetdisk fileNetdisk = new TeeFileNetdisk();
			fileNetdisk.setSid(model.getFileNetdiskId());*/
			TeeFileNetdisk fileNetdisk = (TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,model.getFileNetdiskId());
			netdiskReader.setFileNetdisk(fileNetdisk);

			netdiskReader.setCreateTime(new Date());
			netdiskReader.setReader(loginPerson);
			dao.save(netdiskReader);
			
			
			//往操作表中插入操作记录 数据
			TeeFileOptRecord record=new TeeFileOptRecord();
			record.setCreateTime(Calendar.getInstance());
			record.setFileId(fileNetdisk.getSid());
			record.setFileName(fileNetdisk.getFileName());
			record.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
			record.setOptContent(TeeRequestInfoContext.getRequestInfo().getUserName()+"签阅了["+fileNetDiskService.getFullPathName(fileNetdisk.getSid())+"]文件");
			record.setOptType(7);//文件签阅
			record.setUserId(TeeRequestInfoContext.getRequestInfo().getUserSid());
			record.setUserName(TeeRequestInfoContext.getRequestInfo().getUserName());
			
			int readCount = fileNetdisk.getReadCount();
			readCount=readCount+1;
			fileNetdisk.setReadCount(readCount);
			fileNetdiskDao.update(fileNetdisk);
			simpleDaoSupport.save(record);
		}
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 编辑数据
	 * @author: wyw
	 * @data: 2014年9月5日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 * @throws ParseException
	 */
	public TeeJson updateObj(Map requestMap, TeePerson loginPerson, TeeFileNetdiskReaderModel model) throws ParseException {

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * @function: 删除
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson deleteObjById(String sids) {

		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @function: 根据文件Id删除签阅情况
	 * @author: wyw
	 * @data: 2014年8月30日
	 * @param sids
	 * @return TeeJson
	 */
	public TeeJson delInfoByFileId(Map requestMap, TeePerson loginPerson) {
		String fileNetdiskId = TeeStringUtil.getString(requestMap.get("fileNetdiskId"), "0") ;
		dao.deleteInfoByFileIdDao(fileNetdiskId);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}

	/**
	 * 获取详情
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月22日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeJson
	 */
	public TeeJson getInfoById(Map requestMap, TeePerson loginPerson, TeeFileNetdiskReaderModel model) throws ParseException {
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(model.getSid())) {
			TeeFileNetdiskReader obj = dao.get(model.getSid());
			if (obj != null) {
				model = parseReturnModel(obj);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * 转换成返回对象
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月22日
	 * @param obj
	 * @return TeeFileNetdiskReaderModel
	 */
	public TeeFileNetdiskReaderModel parseReturnModel(TeeFileNetdiskReader obj) {
		TeeFileNetdiskReaderModel model = new TeeFileNetdiskReaderModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (!TeeUtility.isNullorEmpty(obj.getCreateTime())) {
			model.setCreateTimeStr(TeeUtility.getDateTimeStr(obj.getCreateTime()));
			;
		} else {
			model.setCreateTimeStr("");
		}
		if (obj.getReader() != null) {
			model.setReaderId(obj.getReader().getUuid());
			model.setReaderName(obj.getReader().getUserName());
		} else {
			model.setReaderName("");
		}

		return model;

	}

	/**
	 * 获取详情
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月22日
	 * @param requestMap
	 * @param loginPerson
	 * @param model
	 * @return
	 * @throws ParseException
	 *             TeeJson
	 */
	public TeeJson isSignRead(Map requestMap, TeePerson loginPerson, TeeFileNetdiskReaderModel model) throws ParseException {
		TeeJson json = new TeeJson();
		boolean isSignRead = dao.isSignReadDao(model.getFileNetdiskId(), loginPerson.getUuid());
		Map map = new HashMap();
		map.put("isSignRead", isSignRead);
		json.setRtData(model);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * 查看签阅情况
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月24日
	 * @param requestMap
	 * @param person
	 * @return
	 * @throws ParseException
	 *             TeeJson
	 */
	public TeeEasyuiDataGridJson showSignReadDetail(Map requestMap, TeePerson person) throws ParseException {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		int sid = TeeStringUtil.getInteger(requestMap.get("sid"), 0);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 获取根目录
		TeeFileNetdisk dbFileNetdisk = fileNetdiskDao.get(sid);
		int rootFolderSid = 0;
		if (dbFileNetdisk != null) {
			TeeFileNetdisk paraentFileNetdisk = dbFileNetdisk.getFileParent();
			if (paraentFileNetdisk != null) {// 子目录
//				String fileFullPath = TeeUtility.null2Empty(dbFileNetdisk.getFileFullPath());
//				rootFolderSid = TeeStringUtil.getInteger(fileFullPath.split("/")[0], 0);
				rootFolderSid = paraentFileNetdisk.getSid();
				
			} else {
				rootFolderSid = dbFileNetdisk.getSid();
			}
		}
		// 获取权限
		List<TeeFileUserPriv> userPrivList = fileUserPrivDao.getUserPrivListDao(person,rootFolderSid);
		List<TeeFileDeptPriv> deptPrivList = fileDeptPrivDao.getDeptPrivListDao(person,rootFolderSid);
		List<TeeFileRolePriv> rolePrivList = fileRolePrivDao.getRolePrivListDao(person,rootFolderSid);

		StringBuffer userBuffer = new StringBuffer();
		StringBuffer deptBuffer = new StringBuffer();
		StringBuffer userRoleBuffer = new StringBuffer();

		if (userPrivList != null && userPrivList.size() > 0) {
			for (TeeFileUserPriv obj : userPrivList) {
				if(obj == null ){
					continue;
				}
				if(obj.getUser() == null ){
					continue;
				}
				if (userBuffer.length() > 0) {
					userBuffer.append(",");
				}
				userBuffer.append(obj.getUser().getUuid());
			}
		}
		if (deptPrivList != null && deptPrivList.size() > 0) {
			for (TeeFileDeptPriv obj : deptPrivList) {
				if(obj == null ){
					continue;
				}
				if(obj.getDept() == null ){
					continue;
				}
				if (deptBuffer.length() > 0) {
					deptBuffer.append(",");
				}
				deptBuffer.append(obj.getDept().getUuid());
			}
		}
		if (rolePrivList != null && rolePrivList.size() > 0) {
			for (TeeFileRolePriv obj : rolePrivList) {
				if(obj == null ){
					continue;
				}
				if(obj.getUserRole() == null ){
					continue;
				}
				if (userRoleBuffer.length() > 0) {
					userRoleBuffer.append(",");
				}
				userRoleBuffer.append(obj.getUserRole().getUuid());
			}
		}

		// 获取权限范围----部门、人员、角色人员
		List<TeePerson> personList = personDao.getPersonsByUuidsOrDeptIdsOrRoleIds(userBuffer.toString(), deptBuffer.toString(), userRoleBuffer.toString());

		// 获取已签阅用户
		List<TeeFileNetdiskReader> signReaderList = dao.getSignReaderList(sid);
		Map<Integer, TeeFileNetdiskReader> signReaderMap = new HashMap<Integer, TeeFileNetdiskReader>();

		if (signReaderList != null && signReaderList.size() > 0) {
			for (TeeFileNetdiskReader obj : signReaderList) {
				signReaderMap.put(obj.getReader().getUuid(), obj);
			}
		}
		if (personList != null && personList.size() > 0) {
			for (TeePerson obj : personList) {
				if (!"1".equals(obj.getDeleteStatus())) {
					String userName = "";
					String deptName = "";
					String roleName = "";
					String signState = "";
					String signTime = "";

					TeeFileNetdiskReader reader = signReaderMap.get(obj.getUuid());
					if (reader != null) {
						userName = reader.getReader().getUserName();
						deptName = reader.getReader().getDept().getDeptName();
						roleName = reader.getReader().getUserRole().getRoleName();
						signState = "1";
						//signTime = TeeUtility.getDateTimeStr(reader.getCreateTime());
						signTime = sdf.format(reader.getCreateTime());
					} else {
						userName = obj.getUserName();
						deptName = obj.getDept().getDeptName();
						roleName = obj.getUserRole().getRoleName();
						signState = "0";
						signTime = "";
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("userName", userName);
					map.put("deptName", deptName);
					map.put("roleName", roleName);
					map.put("signState", signState);
					map.put("signTime", signTime);
					mapList.add(map);
				}
			}
		}
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		json.setRows(mapList);
		json.setTotal(TeeStringUtil.getLong(mapList.size(), 0));
		//json.setRtState(true);
		//json.setRtMsg("数据获取成功!");
		return json;
	}

}
