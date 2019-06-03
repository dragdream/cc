package com.tianee.oa.core.base.address.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.address.bean.TeeAddress;
import com.tianee.oa.core.base.address.dao.TeeAddressDao;
import com.tianee.oa.core.base.address.model.TeeAddressModel;
import com.tianee.oa.core.base.address.model.TeePersonLastName;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.model.TeePersonModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.file.TeeVcfUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeAddressService extends TeeBaseService  {

	@Autowired
	private TeeAddressDao addressDao;
	
	@Autowired
	private TeeAddressGroupService teeAddressGroupService;
	
	
	@Autowired
	private TeePersonDao personDao;
	
	/**
	 * 通用分页
	 * 个人的通讯簿 应该能看到公共的 （默认组）
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getAddressByGroupId(TeeDataGridModel dm,int groupId,Map paraMap,TeePerson person) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeAddress t where 1=1 ";
		String seqIds = (String)paraMap.get("seqIds");
		String sex = (String)paraMap.get("sex");
		String nickName =  (String)paraMap.get("nickName");
		String telNoDept =  (String)paraMap.get("telNoDept");
		String telNoHome =  (String)paraMap.get("telNoHome");
		String mobilNo =  (String)paraMap.get("mobilNo");
		String psnName =  (String)paraMap.get("psnName");
		String deptName =  (String)paraMap.get("deptName");
		String addDept =  (String)paraMap.get("addDept");
		String addHome =  (String)paraMap.get("addHome");
		String notes =  (String)paraMap.get("notes");
		String isPub = (String)paraMap.get("isPub");
		String birthday = (String)paraMap.get("birthday");
		//需要区分 默认分组 0  是 公共通讯簿 还是 个人通讯薄
		if(groupId > -1){
			if(!TeeUtility.isNullorEmpty(isPub) && "1".equals(isPub)){
				hql = hql + " and t.userId is null and t.groupId = "+groupId;
			}else{
				//区分是不是默认分组
				if(groupId==0){
					hql = hql + " and t.groupId = "+groupId+"and ( t.userId is null  or t.userId = "+person.getUuid()+")" ;
				}else{
					
					hql = hql + " and t.groupId = "+groupId;
				}
				
			}
		}else{
			if(!TeeUtility.isNullorEmpty(seqIds)){
				seqIds = TeeUtility.formatIdsQuote(seqIds);
				hql = hql + " and t.sid in ("+seqIds+")";
			}else{
				//获取其 有权限的组 然后 in
				String groupIds = null;
				if(!TeeUtility.isNullorEmpty(isPub) && "1".equals(isPub)){
					groupIds = teeAddressGroupService.getAddressGroups2String(person);
				}else{
					groupIds = teeAddressGroupService.getPublicAndPrivateAddressGroups2String(person);
				}
				if(TeeUtility.isNullorEmpty(groupIds)){
					groupIds = "-1";
				}
				if(groupIds.endsWith(",")){
					groupIds = groupIds.substring(0, groupIds.length() - 1);
				}
				if(!TeeUtility.isNullorEmpty(isPub) && "1".equals(isPub)){
					groupIds = groupIds +","+"0";
					hql = hql + " and t.userId is null and t.groupId in ("+groupIds+")";
				}else{
					hql = hql + " and  ( t.groupId in (" + groupIds + ") or (t.groupId = 0  and ( t.userId is null  or t.userId = "+person.getUuid()+" ))) ";
				}
			}
		}
		
		List queryList = new ArrayList();
		if(!TeeUtility.isNullorEmpty(sex)){
			hql = hql + " and t.sex = '" +sex + "'" ;
		}
		if(!TeeUtility.isNullorEmpty(nickName)){
			queryList.add("%" + nickName + "%");
			hql = hql + " and t.nickName like ?";
		}
		/////////////////////////////////////
		if(!TeeUtility.isNullorEmpty(telNoDept)){
			queryList.add("%" + telNoDept + "%");
			hql = hql + " and t.telNoDept like ? ";
		}
		if(!TeeUtility.isNullorEmpty(telNoHome)){
			queryList.add("%" + telNoHome + "%");
			hql = hql + " and t.telNoHome like ?";
		}
		if(!TeeUtility.isNullorEmpty(mobilNo)){
			queryList.add("%" + mobilNo + "%");
			hql = hql + " and t.mobilNo like ? ";
		}
		if(!TeeUtility.isNullorEmpty(psnName)){
			queryList.add("%" + psnName + "%");
			hql = hql + " and t.psnName like ?";
		}
		if(!TeeUtility.isNullorEmpty(deptName)){
			
			queryList.add("%" + deptName + "%");
			hql = hql + " and t.deptName like ?";
		}
		if(!TeeUtility.isNullorEmpty(addDept)){
			queryList.add("%" + addDept + "%");
			hql = hql + " and t.addDept like ?";
		}
		if(!TeeUtility.isNullorEmpty(addHome)){
			queryList.add("%" + addHome + "%");
			hql = hql + " and t.addHome like ?";
		}
		if(!TeeUtility.isNullorEmpty(notes)){
			queryList.add("%" + notes + "%");
			hql = hql + " and t.notes like ?";
		}
		
		if(!TeeUtility.isNullorEmpty(birthday)){
			Date birthdayDate = TeeUtility.parseDate("yyyy-MM-dd", birthday);
			queryList.add(birthdayDate);
			hql = hql + " and t.birthday = ?";
		}
		/*System.out.println(hql);*/
		String totalHql = " select count(*) " + hql;
		j.setTotal(addressDao.countByList(totalHql,queryList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			hql += " order by " + dm.getSort() + " " + dm.getOrder();
		}
		List<TeeAddress> addresses = addressDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),queryList);// 查询
		List<TeeAddressModel> addressesmodel = new ArrayList<TeeAddressModel>();
		if (addresses != null && addresses.size() > 0) {
			for (TeeAddress address : addresses) {
				TeeAddressModel m = new TeeAddressModel();
				BeanUtils.copyProperties(address, m);
				addressesmodel.add(m);
			}
		}
		j.setRows(addressesmodel);// 设置返回的行
		return j;
	}
	/**
	 * 首页 模糊 查询 通讯簿 
	 * 查询当前人 有权限的 公共通讯薄 以及 个人通讯簿 按照姓名 匹配
	 * @author zhp
	 * @createTime 2014-2-19
	 * @editTime 下午09:19:36
	 * @desc
	 */
	public List queryAddress2PageIndex(TeePerson person,String psnName){
		String hql = " from TeeAddress t where 1=1 ";
		//获取其 有权限的组 然后 in
		String groupIds = null;
		groupIds = teeAddressGroupService.getPublicAndPrivateAddressGroups2String(person);
		if(TeeUtility.isNullorEmpty(groupIds)){
			groupIds = "-1";
		}
		if(!groupIds.endsWith(",")){
			groupIds = groupIds +","+"0";
		}else{
			groupIds = groupIds +"0";
		}
		hql = hql + " and   t.groupId in ("+groupIds+")";
		if(!TeeUtility.isNullorEmpty(psnName)){
			hql = hql + " and t.psnName like '%"+psnName+"%'";
		}
		List<TeeAddress> addresses = addressDao.executeQuery(hql, null);// 查询
		List<TeeAddressModel> addressesmodel = new ArrayList<TeeAddressModel>();
		if (addresses != null && addresses.size() > 0) {
			for (TeeAddress address : addresses) {
				TeeAddressModel m = new TeeAddressModel();
				BeanUtils.copyProperties(address, m);
				addressesmodel.add(m);
			}
		}
		return addressesmodel;
	}
	/**
	 * 删除 通讯薄
	 * @author zhp
	 * @createTime 2013-12-3
	 * @editTime 上午12:16:20
	 * @desc
	 */
	public void delAddress(int sid){
		String hql = "delete from TeeAddress t where t.sid in ("+sid+")";
		addressDao.deleteOrUpdateByQuery(hql, null);
	}
	
	
	
	/**
	 * 批量   删除 通讯薄
	 * @author zhp
	 * @createTime 2013-12-3
	 * @editTime 上午12:16:20
	 * @desc
	 */
	public TeeJson delAddressByIds(String ids){
		TeeJson json = new TeeJson();
		long count = addressDao.delelteByIds(ids);
		json.setRtState(true);
		json.setRtData(count);
		return json;
	}
	
	
	/**
	 *获取 个人通讯簿 以及 公共通讯簿 的 姓氏 （字母） 应该还包括 当前登录人 有权限的公共组 以及 默认组
	 * @author zhp
	 * @createTime 2013-12-3
	 * @editTime 上午12:16:20
	 * @desc
	 */
	public List getAddressLastName(TeePerson person){
		String groupIds = null;
		groupIds = teeAddressGroupService.getAddressGroups2String(person);
		List resultList = null;
		String hql = "from TeeAddress a where a.userId ="+person.getUuid();
		if(!TeeUtility.isNullorEmpty(groupIds)){
			hql = hql + " or a.groupId in ("+groupIds+")  or (a.groupId = 0 and a.userId is null)";
		}
		 List<TeeAddress> list = getAllAddressByHql(hql);
		 if(list.size() > 0 ){
			 resultList =  makeLastNamMap(list);
		 }
		 return resultList;
	}
	
	/**
	 * 获取所有 公共通讯薄组的 通讯薄
	 * @author zhp
	 * @createTime 2014-1-4
	 * @editTime 上午09:04:53
	 * @desc
	 */
	public List getPublicAddressLastName(TeePerson person){
		String groupIds = null;
		groupIds = teeAddressGroupService.getAddressGroups2String(person);
		if(TeeUtility.isNullorEmpty(groupIds)){
			return null;
		}
		List resultList = null;
		String hql = "from TeeAddress a where a.groupId in ("+groupIds+") or (a.groupId = 0 and a.userId is null)";
		 List<TeeAddress> list = getAllAddressByHql(hql);
		 if(list.size() > 0 ){
			 resultList =  makeLastNamMap(list);
		 }
		 return resultList;
	}
	
	public static void main(String[] args) {
		List<TeeAddress> list = new ArrayList<TeeAddress>();
		
		TeeAddress add = new TeeAddress();
		add.setSid(1);
		add.setPsnName("张三");
		list.add(add);
		
		TeeAddress add2 = new TeeAddress();
		add2.setSid(1);
		add2.setPsnName("李四");
		list.add(add2);
		//makeFullNamList(list);
		
	}
	
	

	/**
	 *获取 个人通讯簿 以及 公共通讯簿 的 姓氏 （字母） 应该还包括 当前登录人 有权限的公共组 以及 默认组
	 * @author syl
	 * @createTime 2013-12-3
	 * @editTime 上午12:16:20
	 * @desc
	 */
	public TreeMap getAddressFullNamList(TeePerson person , int groupId , String userName){
		String groupIds = null;
		groupIds = teeAddressGroupService.getAddressGroups2String(person);
		TreeMap resultList = null;
		String hql = "from TeeAddress a where 1= 1";
		if(groupId >= 0){
			String isPub = "";
			if(groupId == 0){
				hql = hql + " and a.groupId = "+groupId + " and (a.userId is null or a.userId ="+person.getUuid() +") ";
			}else{
				hql = hql + " and a.groupId = "+groupId ;
			}
		}else{
			if(!TeeUtility.isNullorEmpty(groupIds)){
				hql = hql + " and (a.userId ="+person.getUuid() +" or a.groupId in ("+groupIds+")  or (a.groupId = 0 and a.userId is null) )";
			}
		}
		 List values = new ArrayList();
		 if(!userName.equals("")){
			 values.add("%" + userName + "%");
			 hql = hql + " and a.psnName like ?" ;
		 }	
	
		hql = hql + " order by psnName";
		 List<TeeAddress> list = addressDao.executeQueryByList(hql, values);
		 if(list.size() > 0 ){
			 resultList =  makeFullNamList(list);
		 }
		 return resultList;
	}
	
	public List getAddressFullNamListByMobile(TeePerson person){
		String groupIds = null;
		groupIds = teeAddressGroupService.getAddressGroups2String(person);
		TreeMap resultList = null;
		String hql = "from TeeAddress a where 1= 1";
		if(!TeeUtility.isNullorEmpty(groupIds)){
			hql = hql + " and (a.userId ="+person.getUuid() +" or a.groupId in ("+groupIds+")  or (a.groupId = 0 and a.userId is null) )";
		}
	
		hql = hql + " order by psnName";
		List outerList = new ArrayList();
		 List<TeeAddress> list = addressDao.executeQueryByList(hql, new ArrayList());
		 if(list.size() > 0 ){
			 resultList =  makeFullNamList(list);
			 Set<String> keys = resultList.keySet();
			 Map d = new HashMap();
			 for(String key:keys){
				 d = new HashMap();
				 List<TeeAddressModel> tmpList = (List<TeeAddressModel>) resultList.get(key);
				 List<Map> dataList = new ArrayList();
				 Map data = null;
				 for(TeeAddressModel model:tmpList){
					 data = new HashMap();
					 data.put("sid", model.getSid());
					 data.put("name", model.getPsnName());
					 dataList.add(data);
				 }
				 d.put("datas", dataList);
				 d.put("title", key);
				 outerList.add(d);
			 }
		 }
		 return outerList;
	}
	
	/**
	 *获取 个人通讯簿 以及 公共通讯簿 的 姓氏 （字母） 应该还包括   查询用户
	 * @author syl
	 * @createTime 2013-12-3
	 * @editTime 上午12:16:20
	 * @desc
	 */
	public TreeMap getPersonAddress(TeePerson person  , String userName){
		 String hql  = "select uuid from TeePerson  where  deleteStatus <> '1' ";
		 List values = new ArrayList();
		 if(!userName.equals("")){
			 values.add("%" + userName + "%");
			 hql = hql + " and userName like ?" ;
		 }	
		 
		 hql = hql +  " order by userName";
		 List<TeePerson> list = new ArrayList<TeePerson>();
			List uuidList = addressDao.executeQueryByList(hql, values);
			TeePerson p = null;
			for(Object uuid:uuidList){
				p = personDao.load(TeeStringUtil.getInteger(uuid, 0));
				if(p!=null){
					list.add(p);
				}
			}
		TreeMap resultList = null;
		if(list.size() > 0 ){
			 resultList =  makeFullNameList(list);
		}
		return resultList;
	}
	
	//	System.out.println(model);
	/**
	 * 转通讯录信息  syl
	 * 格式：{L=[{sid=1, name=李四}], Z=[{sid=1, name=张三}]}
	 * @param list
	 *
	 * @return
	 */
	public  TreeMap makeFullNamList (List<TeeAddress> list){
		   TeePersonLastName  ls = new TeePersonLastName();
		   Map m = null;
			m = ls.getMap();
			TreeMap model = new TreeMap();
		   for(int j=0; j<list.size();j++){
			   String psnName = "";
		       String firstName = "";
		       String capital = "";
		       String capitalStr = "";
		       String firstBigLetter = "";
			  TeeAddress addre = list.get(j);
			  int seqId = addre.getSid();
			  psnName = addre.getPsnName();//全名
			  firstName = psnName.substring(0, 1);//第一个字(姓)
			   Iterator it = m.entrySet().iterator();
		        char[]chars = firstName.toCharArray();                               
		        for(int i = 0; i < chars.length; i++){                                 
		          //System.out.println(" "+chars[i]+" "+(int)chars[i]);
		          if((int)chars[i] >= 128){                                   
		            while (it.hasNext()) {
		              Map.Entry pairs = (Map.Entry) it.next();
		              if (String.valueOf(pairs.getKey()).indexOf(firstName) != -1) {
		                capital = String.valueOf(pairs.getValue());
		                capitalStr = capital.toUpperCase();
		                //找到姓氏对应的英文字母（转大写），作为索引值
		                break;
		              }
		            } 
		          }else if((int)(firstName.toUpperCase()).charAt(0) >= 65 && (int)(firstName.toUpperCase()).charAt(0) <= 90){                                                         //如果是姓名第一个字节是英文字母
		            firstBigLetter = firstName.toUpperCase();
		            if((int)(firstBigLetter).charAt(0) >= 65 && (int)(firstBigLetter).charAt(0) <= 90){  //A-65   Z-90
		              capitalStr = firstBigLetter;
		            }
		          }else{ //其它（不是中文和英文）
		            Set<String> keySet = m.keySet();
		            boolean name = false;
		            for (String mapStr : keySet) {
		              name = mapStr.contains(psnName);
		            } 
		            if(!name){
		              capitalStr = "#";
		            }
		          }
		        }
		           Object temp = model.get(capitalStr)  ;
		        	if(temp != null){
		        		List<TeeAddressModel> tempList = (List<TeeAddressModel>)temp ;
		        		TeeAddressModel mTemp = new TeeAddressModel();
						BeanUtils.copyProperties(addre,mTemp);
		        		tempList.add(mTemp);
		        		model.put(capitalStr, tempList);
		        	}else{
		        		List<TeeAddressModel> tempList = new ArrayList<TeeAddressModel>();
		        		TeeAddressModel mTemp = new TeeAddressModel();
						BeanUtils.copyProperties(addre,mTemp);
		        		tempList.add(mTemp);
		        		model.put(capitalStr, tempList);
				   };
		      }	
		   return model;
		}
	
	/**
	 * 转通讯录信息  syl
	 * 格式：{L=[{sid=1, name=李四}], Z=[{sid=1, name=张三}]}
	 * @param list
	 *
	 * @return
	 */
	public  TreeMap makeFullNameList (List<TeePerson> list){
		TeePersonLastName ls = new TeePersonLastName();
		Map m = null;
		m = ls.getMap();
		TreeMap model = new TreeMap();
		for (int j = 0; j < list.size(); j++) {
			String psnName = "";
			String firstName = "";
			String capital = "";
			String capitalStr = "";
			String firstBigLetter = "";
			TeePerson addre = list.get(j);
			int seqId = addre.getUuid();
			psnName = addre.getUserName();// 全名
			String isMobilNoHidden = TeeStringUtil.getString(addre.getMobilNoHidden());//是否不公开
			firstName = psnName.substring(0, 1);// 第一个字(姓)
			Iterator it = m.entrySet().iterator();
			char[] chars = firstName.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				// System.out.println(" "+chars[i]+" "+(int)chars[i]);
				if ((int) chars[i] >= 128) {
					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry) it.next();
						if (String.valueOf(pairs.getKey()).indexOf(firstName) != -1) {
							capital = String.valueOf(pairs.getValue());
							capitalStr = capital.toUpperCase();
							// 找到姓氏对应的英文字母（转大写），作为索引值
							break;
						}
					}
				} else if ((int) (firstName.toUpperCase()).charAt(0) >= 65
						&& (int) (firstName.toUpperCase()).charAt(0) <= 90) { // 如果是姓名第一个字节是英文字母
					firstBigLetter = firstName.toUpperCase();
					if ((int) (firstBigLetter).charAt(0) >= 65
							&& (int) (firstBigLetter).charAt(0) <= 90) { // A-65
																			// Z-90
						capitalStr = firstBigLetter;
					}
				} else { // 其它（不是中文和英文）
					Set<String> keySet = m.keySet();
					boolean name = false;
					for (String mapStr : keySet) {
						name = mapStr.contains(psnName);
					}
					if (!name) {
						capitalStr = "#";
					}
				}
			}
			Object temp = model.get(capitalStr);
			List<TeeAddressModel> tempList;
			TeeAddressModel mTemp = new TeeAddressModel();
			if (temp != null) {
				tempList = (List<TeeAddressModel>) temp;
			} else {
				tempList = new ArrayList<TeeAddressModel>();
			}
			mTemp.setSid(addre.getUuid());
			mTemp.setPsnName(addre.getUserName());
			mTemp.setSex(addre.getSex());
			mTemp.setEmail(addre.getEmail());
			TeeDepartment dept = addre.getDept();
			TeeUserRole role = addre.getUserRole();
			String deptName = "";
			String userRoleName = "";
			if (dept != null) {
				deptName = dept.getDeptName();
			}
			if (role != null) {
				userRoleName = role.getRoleName();
			}
			mTemp.setDeptName(deptName);
			mTemp.setAddHome(addre.getAddHome());
			mTemp.setTelNoDept(addre.getTelNoDept());
			String mobilNo = "";
			if(!isMobilNoHidden.equals("1")){//手机公开
				mobilNo = TeeStringUtil.getString(addre.getMobilNo());
			}
			mTemp.setMobilNo(mobilNo);
			mTemp.setMinistration(userRoleName);
			tempList.add(mTemp);
			model.put(capitalStr, tempList);
		}
		return model;
	}
	/**
	 * 获取 通讯簿
	 * @author zhp
	 * @createTime 2014-1-3
	 * @editTime 下午01:26:03
	 * @desc
	 */
	public TeeAddress getAddressById(int aid){
		String hql = "from TeeAddress a where a.sid = "+aid;
		 return addressDao.loadSingleObject(hql, null);
	}
	
	/**
	 * 获取 通讯簿 记录
	 * @author zhp
	 * @createTime 2013-12-23
	 * @editTime 上午10:45:27
	 * @desc
	 */
	public List<TeeAddress> getAllAddressByHql(String hql){
		
		return (List<TeeAddress>)addressDao.executeQuery(hql, null);
	}
	/**
	 * 添加通讯簿
	 * @author zhp
	 * @createTime 2013-12-18
	 * @editTime 下午10:00:44
	 * @desc
	 */
	public void addAddress(TeeAddress add){
		addressDao.save(add);
	}
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-1-3
	 * @editTime 下午09:10:44
	 * @desc
	 */
	public void updateAddress(TeeAddress add){
		addressDao.update(add);
	}
	
	public List makeLastNamMap (List<TeeAddress> list){
	   TeePersonLastName  ls = new TeePersonLastName();
	   Map m = null;
		m = ls.getMap();
       String psnName = "";
       String firstName = "";
       String capital = "";
       String capitalStr = "";
       String firstBigLetter = "";
       String leSeqId = "";
       String mbStrs = "";
       String leNameSeqId = "";
       String otherSeqId = "";
       List datList = null;
	  // firstName = psnName.substring(0, 1);
		
	   for(int j=0; j<list.size();j++){
		  TeeAddress addre = list.get(j);
		  int seqId = addre.getSid();
		  psnName = addre.getPsnName();
		  firstName = psnName.substring(0, 1);
		   Iterator it = m.entrySet().iterator();
	        char[]chars = firstName.toCharArray();                               
	        for(int i = 0; i < chars.length; i++){                                 
	          //System.out.println(" "+chars[i]+" "+(int)chars[i]);
	          if((int)chars[i] >= 128){                                   
	            while (it.hasNext()) {
	              Map.Entry pairs = (Map.Entry) it.next();
	              if (String.valueOf(pairs.getKey()).indexOf(firstName) != -1) {
	                capital = String.valueOf(pairs.getValue());
	                capitalStr = capital.toUpperCase();  
	                //找到姓氏对应的英文字母（转大写），作为索引值
	                break;
	              }
	            } 
	            leNameSeqId += capitalStr + firstName + seqId + ",";
	          }else if((int)(firstName.toUpperCase()).charAt(0) >= 65 && (int)(firstName.toUpperCase()).charAt(0) <= 90){                                                         //如果是姓名第一个字节是英文字母
	            firstBigLetter = firstName.toUpperCase();
	            if((int)(firstBigLetter).charAt(0) >= 65 && (int)(firstBigLetter).charAt(0) <= 90){  //A-65   Z-90
	              leSeqId += firstBigLetter + "+" + seqId + ",";
	            }
	          }else{ //其它（不是中文和英文）
	            Set<String> keySet = m.keySet();
	            boolean name = false;
	            for (String mapStr : keySet) {
	              name = mapStr.contains(psnName);
	            } 
	            if(!name){
	              otherSeqId += psnName + "+" + seqId + ",";
	            }
	          }
	        }
	      }
	   		mbStrs = leNameSeqId + leSeqId + otherSeqId;
	      if(!TeeUtility.isNullorEmpty(mbStrs)){
	        try {
	        	datList = resolve(leNameSeqId, leSeqId, otherSeqId);
			} catch (Exception e) {
				e.printStackTrace();
			}
	      }
		return datList;
		//[{nameStrs=D(1) － 邓, , seqId=8,}, {nameStrs=L(2) － 李, 露, , seqId=16,31,}, {nameStrs=N(1) － 聂, , seqId=29,}, {nameStrs=W(3) － 王, 吴, , seqId=2,6,20,}, {nameStrs=X(2) － 夏, 邢, , seqId=4,19,}, {nameStrs=Z(1) － 赵, , seqId=30,}]
	}
	
	  /**
	   * 解析 姓氏
	   * @param leNameSeqId    中文
	   * @param leSeqId        英文
	   * @param otherSeqId     其它
	   * @return
	   * @throws Exception
	   */
	  
	  public List resolve(String leNameSeqId, String leSeqId, String otherSeqId) throws Exception {
		List list = new ArrayList<Map>();
	    String[] indexs = null;
	    String[] middleIndex = null;
	    String[] othersIndex = null;
	    if(!TeeUtility.isNullorEmpty(leNameSeqId)){
	      indexs = leNameSeqId.split(",");
	    }
	    if(!TeeUtility.isNullorEmpty(leSeqId)){
	      middleIndex =  leSeqId.split(",");
	    }
	    if(!TeeUtility.isNullorEmpty(otherSeqId)){
	      othersIndex = otherSeqId.split(",");
	    }
	    String letter[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","W","X","Y","Z"};
	    for(int i = 0; i < letter.length; i++){
	      String strs = "";
	      String seqIdStr = "";
	      int count = 0;
	      if(!TeeUtility.isNullorEmpty(leSeqId)){
	        for(int y = 0; y < middleIndex.length; y++){
	          if(middleIndex[y].startsWith(letter[i])){
	            count ++;
	            if(count > 0){
	              if(strs.indexOf(middleIndex[y].substring(0,1)) == -1){
	                strs += middleIndex[y].substring(0,1) + "," + " ";
	              }
	              seqIdStr += middleIndex[y].substring(2,middleIndex[y].length()) + ",";
	            }
	          }
	        }
	      }
	      if(!TeeUtility.isNullorEmpty(leNameSeqId)){
	        for(int x = 0; x < indexs.length; x++){
	          if(indexs[x].startsWith(letter[i])){
	            count ++;
	            if(count > 0){
	              if(strs.indexOf(indexs[x].substring(1,2)) == -1){
	                strs += indexs[x].substring(1,2) + "," + " ";
	              }
	              seqIdStr += indexs[x].substring(2,indexs[x].length()) + "," ;
	            }
	          }
	        }
	      }
	      if(count > 0){
	        String strw = letter[i] + "(" + count + ")" + " " + "－" + " " + strs;
	        String seqIds = seqIdStr;
	        Map dataMap = new HashMap<String, String>();
	        dataMap.put("nameStrs", strw);
	        dataMap.put("seqId", seqIds);
	        list.add(dataMap);
	      }
	    }
	    String otherStr = "";
	    int sum = 0;
	    String seqIdOther = "";
	    if(!TeeUtility.isNullorEmpty(otherSeqId)){
	      for(int n = 0; n < othersIndex.length; n++){
	        sum++;
	        if(otherStr.indexOf(othersIndex[n].substring(0, 1)) == -1){
	          otherStr += othersIndex[n].substring(0, 1) + " " + "," + " ";
	        }
	        seqIdOther += othersIndex[n].substring(othersIndex[n].indexOf("+")+1, othersIndex[n].length()) + ",";
	      }
	      String othersStr = "其它" + "(" +sum + ")" + " " + "－" + otherStr;
	      Map dataMap1 = new HashMap<String, String>();
	      dataMap1.put("nameStrs", othersStr);
	      dataMap1.put("seqId", seqIdOther);
	      list.add(dataMap1);
	    }
	    return list;
	  }
	 
	  /**
	   * 获取通讯薄邮件
	   * @author zhp
	   * @createTime 2014-2-12
	   * @editTime 下午10:50:07
	   * @desc
	   */
	  public List<TeeAddress> getAddressByGroupId(boolean isPub,int groupId,TeePerson loginUser){
			String hql = " from TeeAddress t where 1=1 ";
			
			
			if(isPub){//公共
				hql = hql + " and t.userId is null and t.groupId = " +groupId;
				
			}else{//个人
				if(groupId==0){//默认分组
					hql = hql + " and t.groupId = " +groupId+" and (t.userId is null or t.userId="+loginUser.getUuid()+")";
				}else{
					hql = hql + " and t.groupId = " +groupId;
				}
				
			}
			
			
			List<TeeAddress> addresses = addressDao.executeQuery(hql, null);
			return addresses;
	  }
	  /**
		 *获取通讯薄，并转换为导出数据格式
		 * @author zhp
		 * @date 2013-11-14
		 * @return
		 */
		public ArrayList<TeeDataRecord> exportAddressInfo(boolean isPub,int groupId,TeePerson loginUser){
			ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
			List<TeeAddress> addresses = getAddressByGroupId(isPub,groupId,loginUser);
		    for (int i = 0; i < addresses.size(); i++) {
		    	TeeAddress address = addresses.get(i);
		    	String sex = "";
		    	if("0".equals(address.getSex()) ){
		    		sex = "男";
		    	}else if("1".equals(address.getSex())){
		    		sex = "女";
		    	}
		        TeeDataRecord dbrec = new TeeDataRecord();
		        dbrec.addField("姓名", address.getPsnName());
		        dbrec.addField("性别", sex);
		        dbrec.addField("昵称", address.getNickName());
		        dbrec.addField("生日", address.getBirthday());
		        dbrec.addField("职务", address.getMinistration());
//		        dbrec.addField("配偶", address.getMate());
//		        dbrec.addField("子女", address.getChild());
		        dbrec.addField("部门名称", address.getDeptName());
		        dbrec.addField("单位地址", address.getAddDept());
		        dbrec.addField("单位邮编", address.getPostNoDept());
		        dbrec.addField("工作电话", address.getTelNoDept());
		        dbrec.addField("工作传真", address.getFaxNoDept());
		        dbrec.addField("个人住址", address.getAddHome());
		        dbrec.addField("个人邮编", address.getPostNoHome());
		        dbrec.addField("个人电话", address.getTelNoHome());
		        dbrec.addField("手机", address.getMobilNo());
//		        dbrec.addField("小灵通", address.getBpNo());
		        dbrec.addField("电子邮件", address.getEmail());
		        dbrec.addField("QQ号码", address.getOicqNo());
//		        dbrec.addField("MSN号码", address.getIcqNo());
		        dbrec.addField("备注", address.getNotes());
		        list.add(dbrec);
		      }
			return list;
		}
	
	/**
	 * 导入通讯簿
	 * @param isPub
	 * @param groupId
	 * @param records
	 * @param ext  后缀扩展类型   csv/vcf
	 */
	public void importAddress(boolean isPub,int groupId,InputStream input,String ext,TeePerson loginUser){
		if("vcf".equals(ext.toLowerCase())){
			
			//解析vcf标准文件格式
			BufferedReader reader = null;
			String line = null;
			String name = null;
			String phone = null;
			try {
				TeeAddress address = null;
				reader = new BufferedReader(new InputStreamReader(input));
				while(true){
					line = reader.readLine();
					if("\n".equals(line) || line==null){
						break;
					}
					do{
						if(line.startsWith("N;")){
							name = line;
						}else if(line.startsWith("TEL;CELL;PREF:")){
							phone = line;
						}
					}while(!"END:VCARD".equals(line = reader.readLine()));
					
					//处理name和phone
					name = TeeVcfUtil.qpDecodingUTF8(name.split(";")[3]);
					phone = TeeVcfUtil.qpDecodingUTF8(phone.split(";")[2]).replace("PREF:", "");
					
					address = new TeeAddress();
					address.setPsnName(name);
					if("".equals(address.getPsnName())){
						continue;
					}
					address.setMobilNo(phone);
					address.setGroupId(groupId);
					if(!isPub){
						address.setUserId(String.valueOf(loginUser.getUuid()));
					}
					addressDao.save(address);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else if("csv".equals(ext.toLowerCase())){
			
			try {
				ArrayList<TeeDataRecord> list = TeeCSVUtil.CVSReader(input);
				TeeAddress address = null;
				String sex = null;
				for(TeeDataRecord dr:list){
					address = new TeeAddress();
					address.setPsnName(TeeStringUtil.getString(dr.getValueByName("姓名")));
					if("".equals(address.getPsnName())){
						continue;
					}
					sex = TeeStringUtil.getString(dr.getValueByName("性别"));
					if("男".equals(sex) ){
			    		sex = "0";
			    	}else if("女".equals(sex)){
			    		sex = "1";
			    	}
					address.setSex(sex);
					address.setNickName(TeeStringUtil.getString(dr.getValueByName("昵称")));
					address.setBirthday(TeeDateUtil.format(TeeStringUtil.getString(dr.getValueByName("生日"))));
					address.setMinistration(TeeStringUtil.getString(dr.getValueByName("职务")));
					address.setDeptName(TeeStringUtil.getString(dr.getValueByName("部门名称")));
					address.setAddDept(TeeStringUtil.getString(dr.getValueByName("单位地址")));
					address.setPostNoDept(TeeStringUtil.getString(dr.getValueByName("单位邮编")));
					address.setTelNoDept(TeeStringUtil.getString(dr.getValueByName("工作电话")));
					address.setFaxNoDept(TeeStringUtil.getString(dr.getValueByName("工作传真")));
					address.setAddHome(TeeStringUtil.getString(dr.getValueByName("个人住址")));
					address.setPostNoHome(TeeStringUtil.getString(dr.getValueByName("个人邮编")));
					address.setTelNoHome(TeeStringUtil.getString(dr.getValueByName("个人电话")));
					address.setMobilNo(TeeStringUtil.getString(dr.getValueByName("手机")));
					address.setEmail(TeeStringUtil.getString(dr.getValueByName("电子邮件")));
					address.setOicqNo(TeeStringUtil.getString(dr.getValueByName("QQ号码")));
					address.setNotes(TeeStringUtil.getString(dr.getValueByName("备注")));
					address.setGroupId(groupId);
					if(!isPub){
						address.setUserId(String.valueOf(loginUser.getUuid()));
					}
					addressDao.save(address);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	  
	public TeeAddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(TeeAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public TeeAddressGroupService getTeeAddressGroupService() {
		return teeAddressGroupService;
	}

	public void setTeeAddressGroupService(
			TeeAddressGroupService teeAddressGroupService) {
		this.teeAddressGroupService = teeAddressGroupService;
	}
	
	
	
	/**
	 * leiqisheng
	 * @param dm
	 * @param aa 
	 * @param tpModel 
	 * @param groupId
	 * @param userName
	 * @param loginPerson
	 * @return
	 */
	public TeeEasyuiDataGridJson getColleagueList(TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson(); 
		String userName=TeeStringUtil.getString(request.getParameter("userName")); 
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"), 0);
		int roleId=TeeStringUtil.getInteger(request.getParameter("roleId"), 0);
		String tel=TeeStringUtil.getString(request.getParameter("tel")); 
		String email=TeeStringUtil.getString(request.getParameter("email")); 
		String hql ="from TeePerson tp where 1=1 ";
	    List param=new ArrayList();
		if(!TeeUtility.isNullorEmpty(userName)){
			hql+=" and tp.userName like ? ";
			param.add("%"+userName+"%");
		}
		
		if(!TeeUtility.isNullorEmpty(tel)){
			hql+=" and (tp.telNoDept like ? or tp.mobilNo   like ?) ";
			param.add("%"+tel+"%");
			param.add("%"+tel+"%");
		}
		
		if(!TeeUtility.isNullorEmpty(email)){
			hql+=" and tp.email like ? ";
			param.add("%"+email+"%");
		}
		
		if(roleId!=0){
			hql+=" and tp.userRole.uuid=?";
			param.add(roleId);
		}
		if(deptId!=0){
			hql+=" and tp.dept.uuid=? ";
			param.add(deptId);
		}
		
		@SuppressWarnings("unchecked")
		List<TeePerson> persons = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param.toArray());// 查询
		long total = simpleDaoSupport.count("select count(*) "+hql,  param.toArray());
			List<TeePersonModel> list = new ArrayList<TeePersonModel>();
		if (persons != null && persons.size() > 0) {
			for (TeePerson person : persons) {
				               
				TeePersonModel model = new TeePersonModel();
				BeanUtils.copyProperties(person, model);
				if( !TeeUtility.isNullorEmpty(person.getDept()) ){
					model.setDeptIdName(person.getDept().getDeptName());
				}
				if(!TeeUtility.isNullorEmpty(person.getUserRole())){
			
					model.setUserRoleStrName(person.getUserRole().getRoleName());
					
				}
				model.setUserName(person.getUserName());
				model.setMobilNo(person.getMobilNo());//手机号
				model.setSex(person.getSex());
				
				list.add(model);
			}
		}
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		return dataGridJson;

	}
	public TeePersonModel getPersonById(int sid) {
		TeePerson tu = (TeePerson) simpleDaoSupport.get(TeePerson.class, sid);
		TeePersonModel model = new TeePersonModel();
		BeanUtils.copyProperties(tu, model);
		model.setUserName(tu.getUserName());
		model.setSex(tu.getSex());
		model.setUserRoleStrName(tu.getUserRole().getRoleName());
		model.setDeptIdName(tu.getDept().getDeptName());
		
		return model;
	}

	
		
	
	}
