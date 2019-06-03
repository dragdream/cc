package com.tianee.oa.core.system.attachCenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.base.fileNetdisk.service.TeeApacheZipUtil;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.system.attachCenter.dao.TeeAttachCenterDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeAttachCenterService extends TeeBaseService {
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeAttachmentDao attachmentDao;
	@Autowired
	private TeeAttachCenterDao attachCenterDao;

	/**
	 * @function: 获取各模块所占空间比例
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson getAttachSeries(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();

		// 获取要显示的附件模块
		Map<String, String> showAttachModelMap = TeeAttachCenterConst.getAttachAttachMaps();
		Map modelMap = this.getAttachModelCount();

		List<Map> returnList = new ArrayList<Map>();
		for (String key : showAttachModelMap.keySet()) {
			Map returnMap = new HashMap();
			returnMap.put("modelName", showAttachModelMap.get(key));
			returnMap.put("modelCount", modelMap.get(key) == null ? 0 : modelMap.get(key));
			returnList.add(returnMap);
		}
		json.setRtData(returnList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * @function: 获取附件模块列表
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param requestMap
	 * @param loginPerson
	 * @return TeeJson
	 */
	public TeeJson getAttachSeriesList(Map requestMap, TeePerson loginPerson) {
		TeeJson json = new TeeJson();
		// 获取要显示的附件模块
		Map<String, String> showAttachModelMap = TeeAttachCenterConst.getAttachAttachMaps();
		// 取系统附件模块名称、数量
		Map attachModelCountMap = this.getAttachModelCount();
		// 获取每个模块有效文件数
		Map validModelCountMap = this.getValidModelCount();
		// 获取每个模块无效文件数
		Map inValidModelCountMap = this.getInValidModelCount();

		// 总文件大小（M）
		Map attachModelSizeMap = getAttachModelSize();
		// 有效文件大小(M)
		Map validModelSizeMap = getValidModelSize();
		// 无效文件大小(M)
		Map inValidModelSizeMap = getInValidModelSize();

		List<Map> returnList = new ArrayList<Map>();
		for (String key : showAttachModelMap.keySet()) {
			Map returnMap = new HashMap();
			//附件表中的model
			returnMap.put("model", key);
			// 模块名称
			returnMap.put("modelName", showAttachModelMap.get(key));
			// 模块总文件数量
			returnMap.put("attachModelCount", attachModelCountMap.get(key) == null ? 0 : attachModelCountMap.get(key));
			// 有效文件数量
			returnMap.put("validModelCount", validModelCountMap.get(key) == null ? 0 : validModelCountMap.get(key));
			// 无效文件数量
			returnMap.put("inValidModelCount", inValidModelCountMap.get(key) == null ? 0 : inValidModelCountMap.get(key));

			// 总文件大小（M）
			returnMap.put("attachModelSize", attachModelSizeMap.get(key) == null ? 0 : TeeApacheZipUtil.getDisckSize(TeeUtility.getLongFromStr(String.valueOf(attachModelSizeMap.get(key)))));
			// 有效文件大小(M)
			returnMap.put("validModelSize", validModelSizeMap.get(key) == null ? 0 : TeeApacheZipUtil.getDisckSize(TeeUtility.getLongFromStr(String.valueOf(validModelSizeMap.get(key)))));
			// 无效文件大小(M)
			returnMap.put("inValidModelSize", inValidModelSizeMap.get(key) == null ? 0 : TeeApacheZipUtil.getDisckSize(TeeUtility.getLongFromStr(String.valueOf(inValidModelSizeMap.get(key)))));

			returnList.add(returnMap);
		}
		json.setRtData(returnList);
		json.setRtState(true);
		json.setRtMsg("查询成功!");
		return json;
	}

	/**
	 * @function: 获取系统附件模块名称、数量
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getAttachModelCount() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		Map<String, String> Attachmap = TeeAttachCenterConst.getAttachAttachMaps();

		// 或者分组查询条件
		StringBuffer buffer = new StringBuffer();
		for (String key : Attachmap.keySet()) {
			if (buffer.length() > 1) {
				buffer.append(",");
			}
			buffer.append("'" + key + "'");
		}
		List<Map> listMap = attachCenterDao.getAttachModelCountList(buffer.toString());
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("modelCount"));
			}
		}
		return modelMap;
	}

	/**
	 * @function: 获取每个模块有效文件数
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getValidModelCount() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		List<Map> listMap = attachCenterDao.getValidModelCountList();
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("modelCount"));
			}
		}
		return modelMap;
	}

	/**
	 * @function: 获取每个模块无效文件数
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getInValidModelCount() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		List<Map> listMap = attachCenterDao.getInValidModelCountList();
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("modelCount"));
			}
		}
		return modelMap;
	}

	/**
	 * @function: 获取每个模块总文件大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getAttachModelSize() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		List<Map> listMap = attachCenterDao.getAttachModelSizeList();
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("fileSize"));
			}
		}
		return modelMap;
	}

	/**
	 * @function: 获取每个模块有效文件总大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getValidModelSize() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		List<Map> listMap = attachCenterDao.getValidModelSizeList();
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("fileSize"));
			}
		}
		return modelMap;
	}

	/**
	 * @function: 获取每个模块无效文件总大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map
	 */
	public Map getInValidModelSize() {
		Map modelMap = new HashMap();
		// 获取要显示的附件模块
		List<Map> listMap = attachCenterDao.getInValidModelSizeList();
		if (listMap != null && listMap.size() > 0) {
			for (Map map : listMap) {
				modelMap.put(map.get("modelName"), map.get("fileSize"));
			}
		}
		return modelMap;
	}

	/**
	 * 获取附件空间
	 * @return
	 */
	public TeeJson getAttachSpaces() {
		TeeJson json = new TeeJson();
		List<Map> list = new ArrayList<Map>();
		String hql = "select ts.sid as sid,ts.spacePath as spacePath from TeeAttachmentSpace ts order by sid asc";
		list = simpleDaoSupport.getMaps(hql, null);
		int paraValue = TeeStringUtil.getInteger(TeeSysProps.getString("DEFAULT_ATTACHSPACE"), 0);
		for(int i =0;i<list.size();i++){
			if(((Integer)list.get(i).get("sid"))==paraValue){
				list.get(i).put("isDefault", true);
			}else{
				list.get(i).put("isDefault", false);
			}
		}
		json.setRtState(true);
		json.setRtData(list);
		return json;
	}
	
	/**
	 * 获取附件空间
	 * @param sid 
	 * @return
	 */
	public TeeJson getAttachSpace(int sid) {
		TeeJson json = new TeeJson();
		TeeAttachmentSpace space =(TeeAttachmentSpace)simpleDaoSupport.get(TeeAttachmentSpace.class,sid);
		json.setRtState(true);
		json.setRtData(space);
		return json;
	}
	/**
	 * 获取附件空间
	 * @param space 
	 * @return
	 */
	public TeeJson addAttachSpace(TeeAttachmentSpace space) {
		TeeJson json = new TeeJson();
		simpleDaoSupport.save(space);
		json.setRtState(true);
		json.setRtMsg("添加成功！");
		return json;
	}
	/**
	 * 获取附件空间
	 * @param sid 
	 * @return
	 */
	public TeeJson updateAttachSpace(TeeAttachmentSpace space) {
		TeeJson json = new TeeJson();
		simpleDaoSupport.update(space);
		json.setRtMsg("更新成功！");
		json.setRtState(true);
		return json;
	}

	public TeeJson setDefault(int sid) {
		TeeJson json = new TeeJson();
		String sql="update sys_para set para_value = "+sid+" where para_name = 'DEFAULT_ATTACHSPACE'";
		simpleDaoSupport.executeNativeUpdate(sql, null);
		TeeSysProps.getProps().setProperty("DEFAULT_ATTACHSPACE", String.valueOf(sid));
		json.setRtMsg("设置成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 清除无用的附件大小
	 */
	public void clearUnusefulAttachment(){
		
		
		//清除Email邮件的附件
		List<TeeAttachment> emailAttaches = simpleDaoSupport.find("from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL", null);
		//删除附件
		File file = null;
		for(TeeAttachment attach:emailAttaches){
			file = new File(attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName());
			if(file.exists()){
				file.delete();
			}
		}
		//删除附件记录
		simpleDaoSupport.executeUpdate("delete from TeeAttachment attach where attach.model='email' and attach.modelId IS NULL", null);
	}
	
	
	/**
	 * 数据迁移，添加附件
	 * 
	 */
	public void save(TeeAttachment attachment){
		attachmentDao.save(attachment);
	}
	
	/**
	 * 数据迁移更新附件
	 * 
	 */
	public void update(TeeAttachment attachment){
		attachmentDao.update(attachment);
	}
	
	

}
