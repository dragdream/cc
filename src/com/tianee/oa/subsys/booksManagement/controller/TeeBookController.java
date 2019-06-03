package com.tianee.oa.subsys.booksManagement.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookInfo;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookManage;
import com.tianee.oa.subsys.booksManagement.bean.TeeBookType;
import com.tianee.oa.subsys.booksManagement.model.TeeBookManageModel;
import com.tianee.oa.subsys.booksManagement.model.TeeBookManagerModel;
import com.tianee.oa.subsys.booksManagement.model.TeeBookModel;
import com.tianee.oa.subsys.booksManagement.service.TeeBookService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author CXT
 * 
 */
@Controller
@RequestMapping("/bookManage")
public class TeeBookController {
	@Autowired
	private TeeBookService bookService;
	@Autowired
	private TeePersonService personService;

	/**
	 * @author CXT 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeeBookModel model = new TeeBookModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = bookService.addOrUpdate(request, model);
		return json;
	}

	/**
	 * @author CXT 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/addOrUpdateManager")
	@ResponseBody
	public TeeJson addOrUpdateManager(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeeBookManagerModel model = new TeeBookManagerModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = bookService.addOrUpdateManager(request, model);
		return json;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bookTypeList.action")
	public ModelAndView bookTypeList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookType/bookType.jsp");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		mav.addObject("bookTypeList", bookTypeList);
		return mav;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnManage.action")
	public ModelAndView returnManage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManage/returnManage.jsp");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookInfo> bookList = bookService.bookList();
		mav.addObject("bookList", bookList);
		return mav;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bookSearch.action")
	public ModelAndView bookSearch(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManage/bookSearch2.jsp");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		List<TeeBookInfo> bookList = bookService.bookList();
		mav.addObject("bookTypeList", bookTypeList);
		mav.addObject("bookList", bookList);
		return mav;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/borrowSign.action")
	public ModelAndView borrowSing(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManage/borrowSign.jsp");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookInfo> bookList = bookService.bookList();
		mav.addObject("bookList", bookList);
		return mav;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addBookInfo.action")
	public ModelAndView addBookInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookInfo/addOrUpdate.jsp");
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		mav.addObject("bookTypeList", bookTypeList);
		return mav;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateBookInfo.action")
	public ModelAndView updateBookInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookInfo/addOrUpdate.jsp");
		String sid = request.getParameter("sid");
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		mav.addObject("sidStr", sid);
		mav.addObject("bookTypeList", bookTypeList);
		return mav;
	}

	/**
	 * 管理员设置界面 spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setManager.action")
	public ModelAndView setManager(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManager/bookManager.jsp");
		List<TeeBookManagerModel> bookManagerList = bookService.bookManagerList();
		mav.addObject("bookManagerList", bookManagerList);
		return mav;
	}

	/**
	 * @author CXT 新增图书类别
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/addType")
	@ResponseBody
	public TeeJson addType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String typeName = request.getParameter("typeName");
			int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
			TeeBookType bookType = new TeeBookType();
			bookType.setTypeName(typeName);
			if(sid>0){
				bookType.setSid(sid);
				bookService.updateBookType(bookType);
			}else{
				bookService.saveBookType(bookType);
			}
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 更新图书类别
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/updateType")
	@ResponseBody
	public TeeJson updateType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String typeName = request.getParameter("typeName");
			String sid = request.getParameter("sid");
			TeeBookType bookType = bookService.getBookType(sid);
			bookType.setTypeName(typeName);
			bookService.updateBookType(bookType);
			json.setRtMsg("更新成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("更新失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 删除图书类别
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/deleteType")
	@ResponseBody
	public TeeJson deleteType(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			bookService.deleteBookType(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 获取图书类别根据id
	 * */
	@RequestMapping("/selectById")
	@ResponseBody
	public TeeJson selectById(int sid) throws Exception {
		return bookService.selectById(sid);
	}
	
	/**
	 * @author CXT 删除管理信息
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBookManager")
	@ResponseBody
	public TeeJson deleteBookManager(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			bookService.deleteBookManager(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 根据id获取图书管理信息
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getBookManager")
	@ResponseBody
	public TeeJson getBookManager(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			TeeBookManagerModel model = bookService.getBookManager(sid);
			json.setRtData(model);
			json.setRtMsg("获取成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 查询图书
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/searchBook.action")
	@ResponseBody
	public TeeEasyuiDataGridJson searchBook(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException {
		List<Object> values = new ArrayList<Object>();
		String bookTypeId = request.getParameter("bookTypeId");
		String bookName = request.getParameter("bookName");
		String bookNo = request.getParameter("bookNo");
		String auther = request.getParameter("auther");
		String lend = request.getParameter("lend");
		String ISBN = request.getParameter("ISBN");
		String pubHouse = request.getParameter("pubHouse");
		String area = request.getParameter("area");
		String order = request.getParameter("order");
		String lendDesc = "";

		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		/*
		 * String queryStr =
		 * " or exists (select 1 from book.postDept postDept where postDept.uuid=?) "
		 * ; queryStr +=
		 * " or exists (select 1 from book.postUser postUser where postUser.uuid=?) "
		 * ; queryStr +=
		 * " or exists (select 1 from book.postUserRole postUserRole where postUserRole.uuid=?) "
		 * ;
		 */

		String hql = " from TeeBookInfo book where 1 = 1 ";
		if (!bookTypeId.equals("all")) {
			hql += " and book.bookType.sid = ?";
			values.add(Integer.parseInt(bookTypeId));
		}
		if (!TeeUtility.isNullorEmpty(bookNo)) {
			hql += " and book.bookNo like ?";
			values.add("%" + bookNo + "%");
		}
		if (!TeeUtility.isNullorEmpty(auther)) {
			hql += " and book.auther like ?";
			values.add("%" + auther + "%");
		}
		if (!TeeUtility.isNullorEmpty(ISBN)) {
			hql += " and book.ISBN like ?";
			values.add("%" + ISBN + "%");
		}
		if (!TeeUtility.isNullorEmpty(pubHouse)) {
			hql += " and book.pubHouse like ?";
			values.add("%" + pubHouse + "%");
		}
		if (!TeeUtility.isNullorEmpty(area)) {
			hql += " and book.area like ?";
			values.add("%" + area + "%");
		}
		if (!TeeUtility.isNullorEmpty(bookName)) {
			hql += " and book.bookName like ?";
			values.add("%" + bookName + "%");
		}
		String orderValue = "";

		if (order.equals("bumen")) {
			orderValue = "book.createDept.deptName";
		} else if (order.equals("leibie")) {
			orderValue = "book.bookType.typeName";
		} else if (order.equals("shuming")) {
			orderValue = "book.bookName";
		} else if (order.equals("zuozhe")) {
			orderValue = "book.author";
		} else if (order.equals("chubanshe")) {
			orderValue = "book.pubHouse";
		} else if (order.equals("tushubianhao")) {
			orderValue = "book.bookNo";
		}
		if(!"".equals(orderValue)){
			hql += " order by "+orderValue+" desc";
		}else{
			hql += " order by book.sid desc";
		}

		return bookService.searchBook(dm, values, hql, lend);
	}

	/**
	 * @author CXT 保存借书信息
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/saveBorrowInfo")
	@ResponseBody
	public TeeJson saveBorrowInfo(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeBookManage manage = new TeeBookManage();
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String bookNo = request.getParameter("bookNo");
			String borrowDate = request.getParameter("borrowDate");
			String returnDate = request.getParameter("returnDate");
			String borrowRemark = request.getParameter("borrowRemark");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(borrowDate);
			Date date2 = sdf.parse(returnDate);
			manage.setBookNo(bookNo);
			manage.setBorrowDate(date1);
			manage.setReturnDate(date2);
			manage.setBorrowRemark(borrowRemark);
			manage.setBuser(person);
			manage.setRuser(person);
			bookService.saveBookManage(manage);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 检查图书数量
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/checkNum")
	@ResponseBody
	public TeeJson checkNum(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String bookNo = request.getParameter("bookNo");
			String amt = request.getParameter("amt");
			int count = bookService.checkNum(bookNo, amt);
			json.setRtData(count);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 检查图书数量
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/checkNumManage")
	@ResponseBody
	public TeeJson checkNumManage(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String bookNo = request.getParameter("bookNo");
			int count = bookService.checkNumManage(bookNo);
			json.setRtData(count);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBookDetail.action")
	public ModelAndView getBookDetail(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManage/detail.jsp");
		String sid = request.getParameter("sid");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		TeeBookInfo book = bookService.getBookInfo(sid);
		TeeBookModel model = new TeeBookModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BeanUtils.copyProperties(book, model);
		if (book.getAvatar() != null) {
			model.setAvatarId(book.getAvatar().getSid());
		}
		String postDeptNames = "";
		String postUserNames = "";
		String postUserRoleNames = "";
		for (TeeDepartment d : book.getPostDept()) {
			postDeptNames += d.getDeptName() + ",";
		}
		for (TeePerson p : book.getPostUser()) {
			postUserNames += p.getUserName() + ",";
		}
		for (TeeUserRole r : book.getPostUserRole()) {
			postUserRoleNames += r.getRoleName() + ",";
		}
		model.setPostDeptNames(postDeptNames);
		model.setPostUserNames(postUserNames);
		model.setPostUserRoleNames(postUserRoleNames);
		if (book.getCreateDept() != null) {
			model.setCreateDeptName(book.getCreateDept().getDeptName());
			model.setPubDateStr(sdf.format(book.getPubDate()));
		}
		model.setCreateUserName(book.getCreateUser().getUserName());
		model.setBookTypeName(book.getBookType().getTypeName());
		List<Object> values = new ArrayList<Object>();
		values.add(book.getBookNo());
		String hql1 = "from TeeBookManage manage where manage.bookNo=? and (manage.bookStatus='0' and  manage.status='0')";
		String hql2 = "from TeeBookManage manage where manage.bookNo=? and ((manage.bookStatus='0' and  manage.status='1') or (manage.bookStatus='1'and (manage.status='0' or manage.status='2')))";

		List<TeeBookManage> managelist1 = bookService.getBookManager(hql1, values);
		List<TeeBookManage> managelist2 = bookService.getBookManager(hql2, values);
		List<TeeBookManageModel> list1 = new ArrayList<TeeBookManageModel>();
		List<TeeBookManageModel> list2 = new ArrayList<TeeBookManageModel>();
		for (TeeBookManage m1 : managelist1) {
			TeeBookManageModel model1 = new TeeBookManageModel();
			BeanUtils.copyProperties(m1, model1);
			model1.setBuserName(m1.getBuser().getUserName());
			model1.setBorrowDateStr(sdf.format(m1.getBorrowDate()));
			model1.setReturnDateStr(sdf.format(m1.getReturnDate()));
			if (!TeeUtility.isNullorEmpty(m1.getRealReturnDate())) {
				model1.setRealReturnDateStr(sdf.format(m1.getRealReturnDate()));
			}

			list1.add(model1);
		}
		for (TeeBookManage m2 : managelist2) {
			TeeBookManageModel model2 = new TeeBookManageModel();
			BeanUtils.copyProperties(m2, model2);
			model2.setBuserName(m2.getBuser().getUserName());
			model2.setBorrowDateStr(sdf.format(m2.getBorrowDate()));
			model2.setReturnDateStr(sdf.format(m2.getReturnDate()));
			if (!TeeUtility.isNullorEmpty(m2.getRealReturnDate())) {
				model2.setRealReturnDateStr(sdf.format(m2.getRealReturnDate()));
			}
			list2.add(model2);
		}
		String lendDesc = bookService.lendDesc(book.getBookNo(), book.getAmt());
		mav.addObject("list1", list1);
		mav.addObject("list2", list2);
		mav.addObject("lendDesc", lendDesc);
		mav.addObject("bookModel", model);
		return mav;
	}

	/**
	 * 查询借阅信息
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/queryBookManage.action")
	@ResponseBody
	public TeeEasyuiDataGridJson queryBookManage(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException {
		List<Object> values = new ArrayList<Object>();
		String status = request.getParameter("status");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		values.add(Integer.parseInt(status));
		values.add(person.getUuid());
		String hql = " from TeeBookManage b where b.status=? and b.buser.uuid=? and b.deleteFlag!=1 order by b.returnDate desc";

		return bookService.queryBookManage(dm, values, hql);
	}

	/**
	 * @author CXT 删除、更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/updateManage")
	@ResponseBody
	public TeeJson updateManage(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			String flag = request.getParameter("flag");
			String hql = "";
			switch (Integer.parseInt(flag)) {
			case 1:
				hql = "delete from TeeBookManage t where t.sid='" + sid + "'";
				break;
			case 2:
				hql = "update TeeBookManage t  set t.deleteFlag='1' where  t.sid='" + sid + "'";
				break;
			case 3:
				hql = "update TeeBookManage t set t.bookStatus='1',t.status='0',t.regFlag='0' where t.sid='" + sid + "'";
				break;
			}
			bookService.updateManage(hql);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 查询借阅信息
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/borrowManage.action")
	@ResponseBody
	public TeeEasyuiDataGridJson borrowManage(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException {
		List<Object> values = new ArrayList<Object>();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		values.add(person.getUuid());
		String hql = " from TeeBookManage t where t.bookStatus='0' and t.status='1' and t.regFlag='1' and t.ruser.uuid=? order by t.returnDate desc";
        
		return bookService.queryBookManage(dm, values, hql);
	}

	/**
	 * 查询借阅信息
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/borrowManage2.action")
	@ResponseBody
	public TeeEasyuiDataGridJson borrowManage2(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException {
		List<Object> values = new ArrayList<Object>();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		values.add(person.getUuid());
		String hql = " from TeeBookManage t where t.bookStatus='0' and t.status='1' and t.regFlag='1' and t.ruser.uuid=? ";
		String postUserIds=request.getParameter("postUserIds");
		String borrowDateBegin=request.getParameter("borrowDateBegin");
		String borrowDateEnd=request.getParameter("borrowDateEnd");
		String bookNo=request.getParameter("bookNo");
		String status=request.getParameter("status");
		if(postUserIds!=null && !"".equals(postUserIds)){
			hql+=" and t.buser="+Integer.parseInt(postUserIds);
		}
		if(borrowDateBegin!=null && !"".equals(borrowDateBegin)){
			hql+=" and t.borrowDate>='"+borrowDateBegin+"'";
		}
		if(borrowDateEnd!=null && !"".equals(borrowDateEnd)){
			hql+=" and t.borrowDate<='"+borrowDateEnd+"'";
		}
		if(!"0".equals(bookNo)){
			hql+=" and t.bookNo="+bookNo;
		}
//		if(!"all".equals(status)){
//			hql+=" and t.status="+Integer.parseInt(bookNo);
//		}
		hql+=" order by t.returnDate desc";
		return bookService.queryBookManage(dm, values, hql);
	}
	
	/**
	 * 查询借阅信息
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/borrowTrue.action")
			@ResponseBody
			public TeeEasyuiDataGridJson borrowTrue(TeeDataGridModel dm,HttpServletRequest request) throws HibernateException, SQLException{
				List<Object> values = new ArrayList<Object>();
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				values.add(person);
				
				String hql = " from TeeBookManage t where t.bookStatus='0' and t.status='0' and t.regFlag='0' and "+
						" ( exists (select 1 from TeeBookInfo b where b.bookNo= t.bookNo and exists (select 1 from TeeBookManager m where exists (select 1 from m.postDept dept where dept = b.createDept) and exists (select 1 from m.postUser user where user = ?))) " +
						") " +
				"order by t.returnDate desc";

				return bookService.queryBookManage(dm,values,hql);
			}

	/**
	 * 查询借阅信息
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws Exception
	 */
	@RequestMapping("/returnTrue.action")
	@ResponseBody
	public TeeEasyuiDataGridJson returnTrue(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException {
		List<Object> values = new ArrayList<Object>();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		values.add(person);
		String hql = " from TeeBookManage t where t.bookStatus='1' and t.status='0' and t.regFlag='0' and "
				+ " ( exists (select 1 from TeeBookInfo b where b.bookNo= t.bookNo and exists (select 1 from TeeBookManager m where exists (select 1 from m.postDept dept where dept = b.createDept) and exists (select 1 from m.postUser user where user = ?))) "
				+ ") " + "order by t.returnDate desc";
		return bookService.queryBookManage(dm, values, hql);
	}

	/**
	 * @author CXT 图书借阅信息管理
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/allManage")
	@ResponseBody
	public TeeJson allManage(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			String bookNo = request.getParameter("bookNo");
			String flag = request.getParameter("flag");
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String hql = "";
			String hql1 = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-hh HH:mm:ss");
			String dateTime = sdf.format(new Date());
			switch (Integer.parseInt(flag)) {
			case 0:
				hql = "update TeeBookManage t set t.bookStatus='1',t.status='1',t.realReturnDate='" + dateTime + "' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				hql1 = "update TeeBookInfo b set b.lend='0' where b.bookNo='" + bookNo + "'";
				bookService.updateManage(hql1);
				break;
			case 1:
				hql = "update TeeBookManage t set t.bookStatus='0',t.status='1',t.ruser.uuid='" + person.getUuid() + "' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				int count = bookService.getLendCount(bookNo);
				int amt = bookService.retuenNum(bookNo);
				if (count >= amt) {
					hql1 = "update TeeBookInfo b set b.lend='1' where b.bookNo='" + bookNo + "'";
					bookService.updateManage(hql1);
				}
				break;
			case 2:
				hql = "update TeeBookManage t set t.status='2' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				break;
			case 3:
				hql = "update TeeBookManage t set t.bookStatus='1',t.status='1',t.realReturnDate='" + dateTime + "' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				hql1 = "update TeeBookInfo b set b.lend='0' where b.bookNo='" + bookNo + "'";
				bookService.updateManage(hql1);
				break;
			case 4:
				hql = "update TeeBookManage t set t.status='2' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				break;
			case 5:
				hql = "update TeeBookManage t set t.deleteFlag='1' where t.sid='" + sid + "'";
				bookService.updateManage(hql);
				break;
			}

			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 保存借书信息
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/saveBorrowSign")
	@ResponseBody
	public TeeJson saveBorrowSign(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			TeeBookManage manage = new TeeBookManage();
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			String postUserIds = request.getParameter("postUserIds");
			String bookNo = request.getParameter("bookNo");
			String borrowDate = request.getParameter("borrowDate");
			String returnDate = request.getParameter("returnDate");
			String borrowRemark = request.getParameter("borrowRemark");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(borrowDate);
			Date date2 = sdf.parse(returnDate);
			manage.setBuser(personService.selectByUuid(Integer.parseInt(postUserIds)));
			manage.setBookNo(bookNo);
			manage.setBorrowDate(date1);
			manage.setReturnDate(date2);
			manage.setBorrowRemark(borrowRemark);
			manage.setRuser(person);
			manage.setRegFlag(1);
			manage.setStatus(1);
			bookService.saveBookManage(manage);
			json.setRtMsg("保存成功");
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtMsg("保存失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 查询还书管理
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping("/returnBook.action")
	@ResponseBody
	public TeeJson returnBook(HttpServletRequest request) throws HibernateException, SQLException, ParseException {
		List<Object> values = new ArrayList<Object>();
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			String hql = "update TeeBookManage t set t.bookStatus='1' where t.sid='" + sid + "'";
			bookService.updateManage(hql);
			json.setRtState(true);
			json.setRtMsg("还书成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("还书失败！");
		}
		return json;
	}

	/**
	 * 查询还书管理
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws SQLException
	 * @throws HibernateException
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping("/searchReturn.action")
	@ResponseBody
	public TeeEasyuiDataGridJson searchReturn(TeeDataGridModel dm, HttpServletRequest request) throws HibernateException, SQLException, ParseException {
		List<Object> values = new ArrayList<Object>();
		String postUserIds = request.getParameter("postUserIds");
		String bookNo = request.getParameter("bookNo");
		String borrowDateBegin = request.getParameter("borrowDateBegin");
		String borrowDateEnd = request.getParameter("borrowDateEnd");
		String status = request.getParameter("status");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hql = " from TeeBookManage b where b.deleteFlag='0' and b.status!='2' ";
		if (status.equals("1"))
			hql += " and b.bookStatus='1' and b.status='1'";
		if (status.equals("0"))
			hql += " and ((b.bookStatus='0' and b.status='1') or (b.bookStatus='1' and b.status='0'))";
		if (status.equals("all"))
			hql += " and ((b.bookStatus='0' and b.status='1') or (b.bookStatus='1' and b.status='0') or (b.bookStatus='1' and b.status='1'))";
		if (!TeeUtility.isNullorEmpty(borrowDateBegin)) {
			Date date = sdf.parse(borrowDateBegin);
			hql += " and b.borrowDate >= ?";
			values.add(date);
		}
		if (!TeeUtility.isNullorEmpty(borrowDateEnd)) {
			hql += " and b.borrowDate <= ?";
			Date date = sdf.parse(borrowDateEnd);
			values.add(date);
		}
		if (!TeeUtility.isNullorEmpty(bookNo)) {
			hql += " and b.bookNo = ?";
			values.add(bookNo);
		}
		if (!TeeUtility.isNullorEmpty(postUserIds)) {
			hql += " and b.buser.uuid = ?";
			values.add(Integer.parseInt(postUserIds));
		}

		hql += " order by b.returnDate desc";
		System.out.println(hql);
		return bookService.searchReturn(dm, values, hql);
	}

	/**
	 * spring mvc 返回mav 用el表达式获取值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bookQuery.action")
	public ModelAndView bookQuery(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/system/subsys/booksManagement/bookManage/bookQuery.jsp");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeBookType> bookTypeList = bookService.bookTypeList();
		mav.addObject("bookTypeList", bookTypeList);
		return mav;
	}

	/**
	 * @author CXT 获取图书信息
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getBookInfoById")
	@ResponseBody
	public TeeJson getBookInfoById(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			TeeBookInfo book = bookService.getBookInfo(sid);
			TeeBookModel model = new TeeBookModel();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			BeanUtils.copyProperties(book, model);
			String postDeptNames = "";
			String postDeptIds = "";
			String postUserNames = "";
			String postUserIds = "";
			String postUserRoleNames = "";
			String postUserRoleIds = "";
			for (TeeDepartment d : book.getPostDept()) {
				postDeptNames += d.getDeptName() + ",";
				postDeptIds += d.getUuid() + ",";
			}
			if (postDeptIds.length() != 0) {
				postDeptIds = postDeptIds.substring(0, postDeptIds.length() - 1);
			}
			if (postDeptNames.length() != 0) {
				postDeptNames = postDeptNames.substring(0, postDeptNames.length() - 1);
			}
			for (TeePerson p : book.getPostUser()) {
				postUserNames += p.getUserName() + ",";
				postUserIds += p.getUuid() + ",";
			}
			if (postUserIds.length() != 0) {
				postUserIds = postUserIds.substring(0, postUserIds.length() - 1);
			}
			if (postUserNames.length() != 0) {
				postUserNames = postUserNames.substring(0, postUserNames.length() - 1);
			}
			for (TeeUserRole r : book.getPostUserRole()) {
				postUserRoleNames += r.getRoleName() + ",";
				postUserRoleIds += r.getUuid() + ",";
			}
			if (postUserRoleIds.length() != 0) {
				postUserRoleIds = postUserRoleIds.substring(0, postUserRoleIds.length() - 1);
			}
			if (postUserRoleNames.length() != 0) {
				postUserRoleNames = postUserRoleNames.substring(0, postUserRoleNames.length() - 1);
			}
			model.setPostDeptIds(postDeptIds);
			model.setPostUserIds(postUserIds);
			model.setPostUserRoleIds(postUserRoleIds);
			model.setPostDeptNames(postDeptNames);
			model.setPostUserNames(postUserNames);
			model.setPostUserRoleNames(postUserRoleNames);
			model.setCreateUserName(book.getCreateUser().getUserName());
			model.setCreateUserId(book.getCreateUser().getUuid());
			if (book.getCreateDept() != null) {
				model.setCreateDeptName(book.getCreateDept().getDeptName());
				model.setCreateDeptId(book.getCreateDept().getUuid());
			}
			model.setBookTypeName(book.getBookType().getTypeName());
			model.setBookTypeId(book.getBookType().getSid() + "");
			if (!TeeUtility.isNullorEmpty(book.getPubDate())) {
				model.setPubDateStr(sdf.format(book.getPubDate()));
			}

			model.setSid(book.getSid());
			model.setISBN(book.getISBN());

			if (book.getAvatar() != null) {
				model.setAvatarId(book.getAvatar().getSid());
			}
			json.setRtData(model);
			json.setRtState(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 删除图书
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/deleteBook")
	@ResponseBody
	public TeeJson deleteBook(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			bookService.deleteBook(sid);
			json.setRtMsg("删除成功");
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("删除失败");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * @author CXT 检查图书编号
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/checkBookNo")
	@ResponseBody
	public TeeJson checkBookNo(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		try {
			String bookNo = request.getParameter("bookNo");
			String sid = request.getParameter("sid");
			int flag = bookService.checkBookNo(bookNo, sid);
			json.setRtMsg("获取成功");
			json.setRtData(flag);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtMsg("获取失败");
			json.setRtState(false);
		}
		return json;
	}
	
	/**
	 * 获取借阅待批图书
	 * @function: 
	 * @author: wyw
	 * @data: 2015年7月13日
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getBorrowBookApproval")
	@ResponseBody
	public TeeEasyuiDataGridJson getBorrowBookApproval(TeeDataGridModel dm, HttpServletRequest request) throws ParseException, java.text.ParseException {
		TeeBookManageModel model = new TeeBookManageModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return bookService.getBorrowBookApproval(dm, request, model);
	}
	
	/**
	 * 获取图书列表
	 * */
	@RequestMapping("/booList")
	@ResponseBody
	public TeeJson booList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeBookModel> modelList = bookService.bookList2();
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 获取图书类别列表
	 * */
	@RequestMapping("/bookType")
	@ResponseBody
	public TeeJson bookType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeBookType> modelList = bookService.bookTypeList();
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	
}
	/**
	 * 获取图书类别列表
	 * */
	@RequestMapping("/bookType2")
	@ResponseBody
	public TeeEasyuiDataGridJson bookType2(TeeDataGridModel dm) {
		return bookService.bookType2(dm);
  }
	
	/**
	 * 获取管理员权限列表
	 * */
	@ResponseBody
	@RequestMapping("/bookManagerList2")
	public TeeEasyuiDataGridJson bookManagerList2(TeeDataGridModel dm){
		return bookService.bookManagerList2(dm);
	}
}
