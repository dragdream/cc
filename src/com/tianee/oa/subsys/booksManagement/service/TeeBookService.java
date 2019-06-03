package com.tianee.oa.subsys.booksManagement.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookInfo;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManage;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManager;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookDao;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookManageDao;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookManagerDao;
import com.tianee.oa.subsys.booksManagement.dao.TeeBookTypeDao;
import com.tianee.oa.subsys.booksManagement.model.TeeBookManageModel;
import com.tianee.oa.subsys.booksManagement.model.TeeBookManagerModel;
import com.tianee.oa.subsys.booksManagement.model.TeeBookModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeBookService  extends TeeBaseService {
	@Autowired
	private TeeBookDao bookDao;
	@Autowired
	private TeeBookTypeDao bookTypeDao;
	@Autowired
	private TeeBookManagerDao bookManagerDao;
	@Autowired
	private TeeBookManageDao bookManageDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeeUserRoleDao userRoleDao;
	@Autowired
	private TeeBaseUpload upload;
	@Autowired
	private TeePersonManagerI personManagerI;
	@Autowired
	private TeeSmsSender smsSender;
	@Autowired
	private TeeAttachmentDao attachmentDao;

	/**
	 * @author CXT
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeBookModel model) throws IOException, ParseException {
		/*附件处理*/		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.BOOK);

		//记录日志
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		TeeBookInfo book = null;
		if(model.getSid()>0){
			book = bookDao.get(model.getSid());
		}else{
			book = new TeeBookInfo();
		}
		if(attachments.size()>0){
			TeeAttachment t = attachments.get(0);
			book.setAvatar(t);
		}else{
			String avatar = multipartRequest.getParameter("avatar");
			if(TeeUtility.isNullorEmpty(avatar)){
				book.setAvatar(null);
			}
		}
		BeanUtils.copyProperties(model,book);
		if(!TeeUtility.isNullorEmpty(model.getBookTypeId())){
			TeeBookType bookType = bookTypeDao.get(Integer.parseInt(model.getBookTypeId()));
			book.setBookType(bookType);
		}else{
			book.setBookType(null);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//借阅权限  ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			book.setPostDept(listDept);
		}else{
			book.setPostDept(null);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//借阅权限- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			book.setPostUser(listDept);
		}else{
			book.setPostUser(null);
		}
		
		if(!TeeUtility.isNullorEmpty(model.getPostUserRoleIds())){//借阅权限 -- 角色
			List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(model.getPostUserRoleIds());
			book.setPostUserRole(listRole);
		}
		if(!TeeUtility.isNullorEmpty(model.getPubDateStr())){
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getPubDateStr());
			book.setPubDate(date);
		}
		//Calendar cal = Calendar.getInstance();
		book.setCreateDept(deptDao.get((model.getCreateDeptId())));
		book.setCreateUser(person);
		if(model.getSid()>0){
			bookDao.updateBook(book);
		}else{
			bookDao.add(book);
		}
		//sysLog.setType("029A");
		json.setRtState(true);
		json.setRtMsg("保存成功！");
		//创建日志
		//sysLog.setRemark(getSysLogInfo(model));
		//TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtData(model);
		
		return json;
	}
	
	/**
	 * @author CXT
	 * 新增 或者 更新图书管理员
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdateManager(HttpServletRequest request, TeeBookManagerModel model) throws IOException, ParseException {
		//记录日志
		TeeSysLog sysLog = TeeSysLog.newInstance();
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TeeBookManager book = new TeeBookManager();
		if(model.getSid()>0){//判断是否是更新
			book = bookManagerDao.get(model.getSid());
		}
		BeanUtils.copyProperties(model,book);
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//借阅权限  ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			book.setPostDept(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//借阅权限- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			book.setPostUser(listDept);
		}
		if(model.getSid()>0){//判断是否是更新
			bookManagerDao.update(book);
			json.setRtMsg("更新成功！");
		}else{
			bookManagerDao.add(book);
			json.setRtMsg("保存成功！");
		}
		//sysLog.setType("029A");
		json.setRtState(true);
		//创建日志
		//sysLog.setRemark(getSysLogInfo(model));
		//TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		json.setRtData(model);
		
		return json;
	}
	
	/**
	 * 获取图书分类列表
	 * @author 兴涛
	 * @return
	 */
	public List<TeeBookType> bookTypeList(){
		List<TeeBookType> list = new ArrayList<TeeBookType>();
		list = bookTypeDao.bookTypeList();
		return list;
	}
	
	/**
	 * 获取图书管理员列表
	 * @author 兴涛
	 * @return
	 */
	public List<TeeBookManagerModel> bookManagerList(){
		List<TeeBookManager> list = new ArrayList<TeeBookManager>();
		list = bookManagerDao.bookManagerList();
		List<TeeBookManagerModel> modelList = new ArrayList<TeeBookManagerModel>();
		for(TeeBookManager manager : list){
			TeeBookManagerModel model = new TeeBookManagerModel();
			String postDeptIds ="";
			String postDeptNames="";
			String postUserIds="";
			String postUserNames="";
			for(TeeDepartment dept : manager.getPostDept()){
				postDeptIds += dept.getUuid()+",";
				postDeptNames += dept.getDeptName()+",";
			}
			for(TeePerson person : manager.getPostUser()){
				postUserIds += person.getUuid()+",";
				postUserNames += person.getUserName() +",";
			}
			BeanUtils.copyProperties(manager,model);
			model.setPostDeptIds(postDeptIds);
			model.setPostDeptNames(postDeptNames);
			model.setPostUserIds(postUserIds);
			model.setPostUserNames(postUserNames);
			modelList.add(model);
		}
		return modelList;
	}
	
	
	/**
	 * 获取管理员权限列表
	 * */
	public TeeEasyuiDataGridJson bookManagerList2(TeeDataGridModel dm){
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<TeeBookManager> list = bookManagerDao.pageFind("from TeeBookManager", dm.getFirstResult(), dm.getRows(), null);
		Long count = bookManagerDao.count("select count(*) from TeeBookManager", null);
		List<TeeBookManagerModel> modelList = new ArrayList<TeeBookManagerModel>();
		if(list!=null && list.size()>0){
			for(TeeBookManager manager : list){
				TeeBookManagerModel model = new TeeBookManagerModel();
				String postDeptIds ="";
				String postDeptNames="";
				String postUserIds="";
				String postUserNames="";
				for(TeeDepartment dept : manager.getPostDept()){
					postDeptIds += dept.getUuid()+",";
					postDeptNames += dept.getDeptName()+",";
				}
				for(TeePerson person : manager.getPostUser()){
					postUserIds += person.getUuid()+",";
					postUserNames += person.getUserName() +",";
				}
				BeanUtils.copyProperties(manager,model);
				model.setPostDeptIds(postDeptIds);
				model.setPostDeptNames(postDeptNames);
				model.setPostUserIds(postUserIds);
				model.setPostUserNames(postUserNames);
				modelList.add(model);
			}
		}
		easyJson.setRows(modelList);
		easyJson.setTotal(count);
		return easyJson;
	}
	
	/**
	 * 添加图书分类
	 * @author 兴涛
	 */
	public void saveBookType(TeeBookType bookType){
		bookTypeDao.add(bookType);
	}
	
	/**
	 * 更新图书分类
	 * @author 兴涛
	 */
	public void updateBookType(TeeBookType bookType){
		bookTypeDao.update(bookType);
	}
	
	/**
	 * 删除图书分类
	 * @author 兴涛
	 */
	public void deleteBookType(String sid){
		//获取该分类下的所有图书
		List<TeeBookInfo> bookInfos = simpleDaoSupport.find("from TeeBookInfo where bookType.sid="+sid, null);
		//删除分类下的所有图书申请
		for(TeeBookInfo bookinfo:bookInfos){
			simpleDaoSupport.executeUpdate("delete from TeeBookManage where bookNo=?", new Object[]{bookinfo.getBookNo()});
			simpleDaoSupport.deleteByObj(bookinfo);
		}
		
		bookTypeDao.delete(Integer.parseInt(sid));
	}
	
	/**
	 * 删除图书
	 * @author 兴涛
	 */
	public void deleteBook(String sid){
		TeeBookInfo bookInfo = getBookInfo(sid);
		simpleDaoSupport.executeUpdate("delete from TeeBookManage where bookNo=?", new Object[]{bookInfo.getBookNo()});
		bookDao.delete(Integer.parseInt(sid));
	}
	
	/**
	 * 删除图书管理信息
	 * @author 兴涛
	 */
	public void deleteBookManager(String sid){
		bookManagerDao.delete(Integer.parseInt(sid));
	}
	
	/**
	 * 获取图书分类
	 * @author 兴涛
	 */
	public TeeBookType getBookType(String sid){
		
		TeeBookType bookType = bookTypeDao.get(Integer.parseInt(sid));
		return bookType;
	}
	
	/**
	 * 获取图书管理员信息
	 * @author 兴涛
	 */
	public TeeBookManagerModel getBookManager(String sid){
		
		TeeBookManager manager = bookManagerDao.get(Integer.parseInt(sid));
		TeeBookManagerModel model = new TeeBookManagerModel();
		String postDeptIds ="";
		String postDeptNames="";
		String postUserIds="";
		String postUserNames="";
		for(TeeDepartment dept : manager.getPostDept()){
			postDeptIds += dept.getUuid()+",";
			postDeptNames += dept.getDeptName()+",";
		}
		for(TeePerson person : manager.getPostUser()){
			postUserIds += person.getUuid()+",";
			postUserNames += person.getUserName() +",";
		}
		BeanUtils.copyProperties(manager,model);
		model.setPostDeptIds(postDeptIds);
		model.setPostDeptNames(postDeptNames);
		model.setPostUserIds(postUserIds);
		model.setPostUserNames(postUserNames);
		return model;
	}
	
	public TeeEasyuiDataGridJson searchBook(TeeDataGridModel dm,
			List<Object> values,String hql,String lend) throws HibernateException, SQLException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<TeeBookInfo> bookList = simpleDaoSupport.pageFindByList(hql, dm.getRows()* (dm.getPage() - 1), dm.getRows(), values);
		long total = simpleDaoSupport.countByList("select count(*) " + hql, values);
		List<TeeBookModel> modelList = new ArrayList<TeeBookModel>();
		

		for (int i = 0; i < bookList.size(); i++) {
			List<Object> values1 = new ArrayList<Object>();
			values1.add(bookList.get(i).getBookNo());
			String sql = "select COUNT(1) from BOOK_MANAGE where BOOK_NO = ? and (BOOK_STATUS = '0' and STATUS = '0' or BOOK_STATUS = '0' and STATUS = '1' or BOOK_STATUS = '1' and STATUS = '0' or BOOK_STATUS = '1' and STATUS = '2') ";
			int count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
			int amt = bookList.get(i).getAmt();
			String lendDesc = "共"+amt+"册，已借出"+count+"册，剩余"+(amt-count)+"册";
			TeeBookModel model = new TeeBookModel();
			BeanUtils.copyProperties(bookList.get(i), model);
			model.setBorrowCount(count);
			model.setCreateUserId(bookList.get(i).getCreateUser().getUuid());
			model.setCreateUserName(bookList.get(i).getCreateUser().getUserName());
			if(bookList.get(i).getCreateDept()!=null){
				model.setCreateDeptId(bookList.get(i).getCreateDept().getUuid());
				model.setCreateDeptName(bookList.get(i).getCreateDept().getDeptName());
			}
			
			model.setLendDesc(lendDesc);
			if(bookList.get(i).getBookType()!=null){
				model.setBookTypeId(bookList.get(i).getBookType().getSid()+"");
				model.setBookTypeName(bookList.get(i).getBookType().getTypeName());
			}
			modelList.add(model);
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 添加借书信息
	 * @author 兴涛
	 */
	public void saveBookManage(TeeBookManage bookManage){
		bookManageDao.add(bookManage);
	}
	
	/**
	 * 返回图书剩余数量
	 * @author 兴涛
	 */
	public int checkNum(String bookNo,String amt){
		List<Object> values1 = new ArrayList<Object>();
		values1.add(bookNo);
		String sql = "select COUNT(1) from BOOK_MANAGE where BOOK_NO = ? and (BOOK_STATUS = '0' and STATUS = '0' or BOOK_STATUS = '0' and STATUS = '1' or BOOK_STATUS = '1' and STATUS = '0' or BOOK_STATUS = '1' and STATUS = '2') ";
		int count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
		int num = Integer.parseInt(amt);
		return num - count;
	}
	
	/**
	 * 返回图书剩余数量
	 * @author 兴涛
	 */
	public int checkNumManage(String bookNo){
		List<Object> values1 = new ArrayList<Object>();
		values1.add(bookNo);
		int amt = 0;
		if(bookDao.bookListByBookNo(bookNo).size()>0){
			amt = bookDao.bookListByBookNo(bookNo).get(0).getAmt();
		}
		String sql = "select COUNT(1) from BOOK_MANAGE where BOOK_NO = ? and (BOOK_STATUS = '0' and STATUS = '0' or BOOK_STATUS = '0' and STATUS = '1' or BOOK_STATUS = '1' and STATUS = '0' or BOOK_STATUS = '1' and STATUS = '2') ";
		int count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
		int num = amt;
		return num - count;
	}
	/**
	 * 返回图书剩余数量
	 * @author 兴涛
	 */
	public int retuenNum(String bookNo){
		int count = 0;
		List<TeeBookInfo> list = new ArrayList<TeeBookInfo>();
		list = bookDao.bookListByBookNo(bookNo);
		if(list.size()>0){
			count = list.get(0).getAmt();
		}
		return count;
	}
	
	/**
	 * 获取图书信息
	 * @author 兴涛
	 */
	public TeeBookInfo getBookInfo(String sid){
		return bookDao.get(Integer.parseInt(sid));
	}

	/**
	 * 获取图书信息
	 * @author 兴涛
	 */
	public List<TeeBookManage> getBookManager(String hql,List<Object> values){
		
		List<TeeBookManage> list = simpleDaoSupport.executeQueryByList(hql, values);
		return list;
	}
	
	/**
	 * 返回图书描述
	 * @author 兴涛
	 */
	public String lendDesc(String bookNo,int amt){
		List<Object> values1 = new ArrayList<Object>();
		values1.add(bookNo);
		String sql = "select COUNT(1) from BOOK_MANAGE where BOOK_NO = ? and (BOOK_STATUS = '0' and STATUS = '0' or BOOK_STATUS = '0' and STATUS = '1' or BOOK_STATUS = '1' and STATUS = '0' or BOOK_STATUS = '1' and STATUS = '2') ";
		int count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
		String lendDesc = "共"+amt+"册，已借出"+count+"册，剩余"+(amt-count)+"册";
		return lendDesc;
	}
	
	public TeeEasyuiDataGridJson queryBookManage(TeeDataGridModel dm,
			List<Object> values,String hql) throws HibernateException, SQLException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<TeeBookManage> manageList = simpleDaoSupport.pageFindByList(hql, dm.getRows()* (dm.getPage() - 1), dm.getRows(), values);
		long total = simpleDaoSupport.countByList("select count(*) " + hql, values);
		List<TeeBookManageModel> modelList = new ArrayList<TeeBookManageModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < manageList.size(); i++) {
			String desc = "";
			TeeBookManageModel model = new TeeBookManageModel();
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==0)
			    desc = "借书待批";
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==1)
			    desc = "借书已准";
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==2)
			    desc = "借书未准";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==0)
			    desc = "还书待批";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==1)	  
			 	desc = "还书已准";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==2)
			    desc = "还书未准";
			model.setDesc(desc);
			List<TeeBookInfo> list = bookDao.bookListByBookNo(manageList.get(i).getBookNo());
			if(list.size()>0){
				model.setBookName(list.get(0).getBookName());
			}
			model.setSid(manageList.get(i).getSid());
			model.setBorrowDateStr(sdf.format(manageList.get(i).getBorrowDate()));
			model.setReturnDateStr(sdf.format(manageList.get(i).getReturnDate()));
			model.setBuserName(manageList.get(i).getBuser().getUserName());
			if(manageList.get(i).getRuser()!=null){
				model.setRuserName(manageList.get(i).getRuser().getUserName());
			}
			model.setBookNo(manageList.get(i).getBookNo());
			model.setBorrowRemark(manageList.get(i).getBorrowRemark());
			model.setStatus(manageList.get(i).getStatus());
			model.setBookStatus(manageList.get(i).getBookStatus());
			modelList.add(model);
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	public void updateManage(String hql){
		Object[] values = {};
		simpleDaoSupport.deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 返回借出图书数量
	 * @author 兴涛
	 */
	public int getLendCount(String bookNo){
		List<Object> values1 = new ArrayList<Object>();
		values1.add(bookNo);
		String sql = "SELECT count(*) from BOOK_MANAGE where BOOK_NO=? and ((BOOK_STATUS='1' and STATUS='0') or (BOOK_STATUS='0' and STATUS='1'))";
		int count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
		return count;
	}
	
	
	public TeeEasyuiDataGridJson searchReturn(TeeDataGridModel dm,
			List<Object> values,String hql) throws HibernateException, SQLException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List<TeeBookManage> manageList = simpleDaoSupport.pageFindByList(hql, dm.getRows()* (dm.getPage() - 1), dm.getRows(), values);
		long total = simpleDaoSupport.countByList("select count(*) " + hql, values);
		List<TeeBookManageModel> modelList = new ArrayList<TeeBookManageModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < manageList.size(); i++) {
			String desc = "";
			TeeBookManageModel model = new TeeBookManageModel();
			if(manageList.get(i).getBookStatus()==1&&manageList.get(i).getStatus()==1)
				desc="已还";
			if((manageList.get(i).getBookStatus()==0&&manageList.get(i).getStatus()==1)||(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==0))
				desc="未还";
			model.setDesc(desc);
			List<TeeBookInfo> list = bookDao.bookListByBookNo(manageList.get(i).getBookNo());
			if(list.size()>0){
				model.setBookName(list.get(0).getBookName());
			}
			model.setSid(manageList.get(i).getSid());
			model.setBorrowDateStr(sdf.format(manageList.get(i).getBorrowDate()));
			model.setReturnDateStr(sdf.format(manageList.get(i).getReturnDate()));
			model.setBuserName(manageList.get(i).getBuser().getUserName());
			model.setBuserId(manageList.get(i).getBuser().getUuid());
			if(manageList.get(i).getRuser()!=null){
				model.setRuserName(manageList.get(i).getRuser().getUserName());
			}
			model.setDesc(desc);
			model.setBorrowRemark(manageList.get(i).getBorrowRemark());
			model.setBookNo(manageList.get(i).getBookNo());
			model.setStatus(manageList.get(i).getStatus());
			model.setBookStatus(manageList.get(i).getBookStatus());
			modelList.add(model);
			
		}
		j.setTotal(total);// 设置总记录数
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 返回图书剩余数量
	 * @author 兴涛
	 */
	public int checkBookNo(String bookNo,String sid){
		List<Object> values1 = new ArrayList<Object>();
		values1.add(bookNo);
		int count = 0;
		String sql = "select COUNT(1) from BOOK_INFO where BOOK_NO = ?";
		if(!TeeUtility.isNullorEmpty(sid)){
			sql += " and SID != "+sid;
		}
		count = simpleDaoSupport.countSQLByList(sql, values1).intValue();
		if(count>0){
			count = 1;
		}
		return count;
	}
	
	public List<TeeBookInfo> bookList(){

		List<TeeBookInfo> list = bookDao.bookList();
		return list;
	}

	public List<TeeBookModel> bookList2(){
		List<TeeBookModel> modellist=new ArrayList<TeeBookModel>();
		List<TeeBookInfo> list = bookDao.find("from TeeBookInfo", null);
		for(TeeBookInfo info:list){
			TeeBookModel model=new TeeBookModel();
			model.setSid(info.getSid());
			model.setBookName(info.getBookName());
			model.setBookNo(info.getBookNo());
			modellist.add(model);
		}
		return modellist;
	}
	/**
	 * 获取借阅待批图书
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月13日
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException TeeEasyuiDataGridJson
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getBorrowBookApproval(TeeDataGridModel dm, HttpServletRequest request, TeeBookManageModel model1) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);

		Map requestDatas = TeeServletUtility.getParamMap(request);
		String cityName = (String) requestDatas.get("cityName");
		String cityCode = (String) requestDatas.get("cityCode");

		String startDateStr = (String) requestDatas.get("startDateStr");
		String endDateStr = (String) requestDatas.get("endDateStr");
		//借阅待批为0（status='0'），借书标记 为0（bookStatus='0'），且TeeBookInfo.bookNo=TeeBookManage.bookNo,且图书的部门=管理员所管理的部门（TeeBookInfo.createDept=TeeBookManager.postDept），且当前登录人为设置的图书管理员（TeeBookManager.postUser=loginPerson）
		String hql2 = " from TeeBookManage t where t.bookStatus='0' and t.status='0' and t.regFlag='0' and "+
				" ( exists (select 1 from TeeBookInfo b where b.bookNo= t.bookNo and exists (select 1 from TeeBookManager m where exists (select 1 from m.postDept dept where dept = b.createDept) and exists (select 1 from m.postUser user where user = ?))) " +
				") " + "order by t.returnDate desc";

		String queryStr = " 1=1";
		String hql = " from TeeBookManage t where t.bookStatus='0' and t.status='0' and t.regFlag='0'   ";
		List param = new ArrayList();
		// 设置总记录数
		j.setTotal(bookManageDao.countByList("select count(*) " + hql, param));// 设置总记录数

		hql += " order by t.returnDate desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeBookManage> manageList = bookManageDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeBookManageModel> modelList = new ArrayList<TeeBookManageModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < manageList.size(); i++) {
			String desc = "";
			TeeBookManageModel model = new TeeBookManageModel();
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==0)
			    desc = "借书待批";
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==1)
			    desc = "借书已准";
			 if(manageList.get(i).getBookStatus()==0 && manageList.get(i).getStatus()==2)
			    desc = "借书未准";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==0)
			    desc = "还书待批";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==1)	  
			 	desc = "还书已准";
			 if(manageList.get(i).getBookStatus()==1 && manageList.get(i).getStatus()==2)
			    desc = "还书未准";
			model.setDesc(desc);
			List<TeeBookInfo> list = bookDao.bookListByBookNo(manageList.get(i).getBookNo());
			if(list.size()>0){
				model.setBookName(list.get(0).getBookName());
			}
			model.setSid(manageList.get(i).getSid());
			model.setBorrowDateStr(sdf.format(manageList.get(i).getBorrowDate()));
			model.setReturnDateStr(sdf.format(manageList.get(i).getReturnDate()));
			model.setBuserName(manageList.get(i).getBuser().getUserName());
			if(manageList.get(i).getRuser()!=null){
				model.setRuserName(manageList.get(i).getRuser().getUserName());
			}
			model.setBookNo(manageList.get(i).getBookNo());
			model.setBorrowRemark(manageList.get(i).getBorrowRemark());
			model.setStatus(manageList.get(i).getStatus());
			model.setBookStatus(manageList.get(i).getBookStatus());
			modelList.add(model);
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	public List<TeeBookType> bookType() {
		
		return null;
	}

	public TeeEasyuiDataGridJson bookType2(TeeDataGridModel dm) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<TeeBookType> find = bookTypeDao.pageFind("from TeeBookType", dm.getFirstResult(), dm.getRows(), null);
		Long count = bookTypeDao.countByList("select count(*) from TeeBookType", null);
		List<TeeBookType> type=new ArrayList<TeeBookType>();
		if(find!=null && find.size()>0){
			for(TeeBookType t:find){
				TeeBookType bookType=new TeeBookType();
				bookType.setSid(t.getSid());
				bookType.setTypeName(t.getTypeName());
				type.add(bookType);
			}
			
		}
		easyJson.setRows(type);
		easyJson.setTotal(count);
		return easyJson;
	}

	/**
	 * 获取图书类别根据id
	 * */
	public TeeJson selectById(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
		  TeeBookType type = bookTypeDao.get(sid);
		  json.setRtData(type);
		  json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
	
	
}
