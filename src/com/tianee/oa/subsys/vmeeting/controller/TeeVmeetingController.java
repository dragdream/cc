package com.tianee.oa.subsys.vmeeting.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.commonword.model.CommonWordModel;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.subsys.vmeeting.model.MeetingModel;
import com.tianee.oa.subsys.vmeeting.service.TeeVmeetingService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("vmeeting")
public class TeeVmeetingController {

	@Autowired
	private TeeSysParaService sysParaServ;

	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeVmeetingService vmeetingService;

	@ResponseBody
	@RequestMapping("getVmeetingConfigs")
	public TeeJson getVmeetingConfigs() {
		TeeJson json = new TeeJson();
		Map data = new HashMap();
		data.put("VMT_IP", TeeSysProps.getString("VMT_IP"));//入口iP
		data.put("VMT_PORT", TeeSysProps.getString("VMT_PORT"));//端口
		data.put("VMT_ADMIN_ID", TeeSysProps.getString("VMT_ADMIN_ID"));//管理员用户
		data.put("VMT_ADMIN_PWD", TeeSysProps.getString("VMT_ADMIN_PWD"));//管理员密码
		data.put("VMT_USER_ID", TeeSysProps.getString("VMT_USER_ID"));//普通用户
		data.put("VMT_USER_PWD", TeeSysProps.getString("VMT_USER_PWD"));//普通密码
		data.put("VMT_MEET_ACCOUNT", TeeSysProps.getString("VMT_MEET_ACCOUNT"));//进入会议账号
		data.put("VMT_MEET_KEY", TeeSysProps.getString("VMT_MEET_KEY"));//进入会议密码
		data.put("VMT_CREATE_PRIV_IDS",
				TeeSysProps.getString("VMT_CREATE_PRIV_IDS"));
		data.put("VMT_CREATE_PRIV_NAMES",
				TeeSysProps.getString("VMT_CREATE_PRIV_NAMES"));
		data.put("VMT_MGR_PRIV_IDS", TeeSysProps.getString("VMT_MGR_PRIV_IDS"));
		data.put("VMT_MGR_PRIV_NAMES",
				TeeSysProps.getString("VMT_MGR_PRIV_NAMES"));

		json.setRtData(data);
		return json;
	}

	@ResponseBody
	@RequestMapping("saveVmeetingConfigs")
	public TeeJson saveVmeetingConfigs(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		sysParaServ.updateSysPara("VMT_IP",
				TeeStringUtil.getString(request.getParameter("VMT_IP")));//入口iP
		sysParaServ
				.updateSysPara("VMT_PORT", TeeStringUtil
						.getString(request.getParameter("VMT_PORT")));//端口
		sysParaServ.updateSysPara("VMT_ADMIN_ID",
				TeeStringUtil.getString(request.getParameter("VMT_ADMIN_ID")));//管理员用户
		sysParaServ.updateSysPara("VMT_ADMIN_PWD",
				TeeStringUtil.getString(request.getParameter("VMT_ADMIN_PWD")));//管理员密码
		sysParaServ.updateSysPara("VMT_USER_ID",
				TeeStringUtil.getString(request.getParameter("VMT_USER_ID")));//管理员用户
		sysParaServ.updateSysPara("VMT_USER_PWD",
				TeeStringUtil.getString(request.getParameter("VMT_USER_PWD")));//管理员密码
		sysParaServ.updateSysPara("VMT_MEET_ACCOUNT",
				TeeStringUtil.getString(request.getParameter("VMT_MEET_ACCOUNT")));//进入会议账号
		sysParaServ.updateSysPara("VMT_MEET_KEY",
				TeeStringUtil.getString(request.getParameter("VMT_MEET_KEY")));//进入会议密码
		sysParaServ.updateSysPara("VMT_CREATE_PRIV_IDS", TeeStringUtil
				.getString(request.getParameter("VMT_CREATE_PRIV_IDS")));
		sysParaServ.updateSysPara("VMT_CREATE_PRIV_NAMES", TeeStringUtil
				.getString(request.getParameter("VMT_CREATE_PRIV_NAMES")));
		sysParaServ.updateSysPara("VMT_MGR_PRIV_IDS", TeeStringUtil
				.getString(request.getParameter("VMT_MGR_PRIV_IDS")));
		sysParaServ.updateSysPara("VMT_MGR_PRIV_NAMES", TeeStringUtil
				.getString(request.getParameter("VMT_MGR_PRIV_NAMES")));
		return json;
	}
	
	/*
   /**
	 * 获取我可管理的视频会议列表
	 * 
	 * @return
	 */
	 /* 
	@ResponseBody
	@RequestMapping("getMyManages")
	public TeeEasyuiDataGridJson getMyManages(TeeDataGridModel dataGridModel,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		dataGridJson.setRows(new ArrayList());
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Connection chatConn = null;
		// Connection dbConn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			chatConn = DriverManager.getConnection(
					"jdbc:mysql://" + TeeSysProps.getString("VMT_MYSQL_IP")
							+ ":" + TeeSysProps.getString("VMT_MYSQL_PORT")
							+ "/zlchat?characterEncoding=UTF-8", "root",
					"zlchat888");
			// dbConn = TeeDbUtility.getConnection();
			// chatConn.setAutoCommit(false);
			// dbConn.setAutoCommit(false);
			DbUtils chatDbUtils = new DbUtils(chatConn);
			// DbUtils dbUtils = new DbUtils(dbConn);

			String sql = " from meeting m left outer join rel_meeting_user u1 on u1.meet_no=m.meet_no and u1.role=2 where 1=1 ";
			String select = "select " + "m.meet_no as meetNo,"
					+ "m.meet_name as meetName," + "m.start_time as startTime,"
					+ "m.end_time as endTime," + "m.status," + "m.summary,"
					+ "u1.user_id as mainUser ";

			if (!TeePersonService.checkIsAdminPriv(loginPerson)) {// 非管理员，获取当前账户主办人的会议
				sql += " and m.cr_user=" + loginPerson.getUuid();
			}

			String meetName = TeeStringUtil.getString(request
					.getParameter("meetName"));
			String subject = TeeStringUtil.getString(request
					.getParameter("subject"));

			if (!"".equals(meetName)) {
				sql += " and m.meet_name like '%"
						+ TeeDbUtility.formatString(meetName) + "%'";
			}

			if (!"".equals(subject)) {
				sql += " and m.subject like '%"
						+ TeeDbUtility.formatString(subject) + "%'";
			}

			// 获取我的会议
			List<Map<String, Object>> list = chatDbUtils.queryToMapList(select
					+ sql + " order by m.start_time desc", null,
					dataGridModel.getFirstResult(), dataGridModel.getRows());
			Map<String, Object> totalMap = chatDbUtils.queryToMap(
					"select count(1) as c " + sql, null);
			dataGridJson.setTotal(TeeStringUtil.getLong(totalMap.get("c"), 0));

			TeePerson p = null;
			if (list != null) {
				for (Map<String, Object> data : list) {
					p = personService.selectByUuid(TeeStringUtil.getInteger(
							data.get("mainUser"), 0));
					data.put("mainUser", p.getUserName());
					totalMap = chatDbUtils.queryToMap(
							"select count(1) as c from rel_meeting_user where meet_no='"
									+ data.get("meetNo") + "'", null);
					data.put("count", totalMap.get("c"));
					// data.put("url",
					// TeeSysProps.getString("VMT_URL")+"/sso.php?pwd="+loginPerson.getPassword().subSequence(12,
					// 44)+"&username="+loginPerson.getUserId().hashCode()+"&roomID="+data.get("meetNo"));
				}
			} else {
				list = new ArrayList();
			}

			dataGridJson.setRows(list);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(chatConn);
			// TeeDbUtility.closeConn(dbConn);
		}
		return dataGridJson;
	}
	*/
	/**
	 * 获取我的视频会议列表
	 * 
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping("getMyMeetings")
	public TeeEasyuiDataGridJson getMyMeetings(TeeDataGridModel dataGridModel,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		dataGridJson.setRows(new ArrayList());
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Connection chatConn = null;
		// Connection dbConn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			chatConn = DriverManager.getConnection(
					"jdbc:mysql://" + TeeSysProps.getString("VMT_MYSQL_IP")
							+ ":" + TeeSysProps.getString("VMT_MYSQL_PORT")
							+ "/zlchat?characterEncoding=UTF-8", "root",
					"zlchat888");
			// dbConn = TeeDbUtility.getConnection();
			// chatConn.setAutoCommit(false);
			// dbConn.setAutoCommit(false);
			DbUtils chatDbUtils = new DbUtils(chatConn);
			// DbUtils dbUtils = new DbUtils(dbConn);

			String sql = " from meeting m left outer join rel_meeting_user u1 on u1.meet_no=m.meet_no and u1.role=2 where 1=1 ";
			String select = "select " + "m.meet_no as meetNo,"
					+ "m.meet_name as meetName," + "m.start_time as startTime,"
					+ "m.end_time as endTime," + "m.status," + "m.summary,"
					+ "u1.user_id as mainUser ";

			// if(!TeePersonService.checkIsAdminPriv(loginPerson)){//非管理员，获取当前账户的会议
			sql += " and exists (select 1 from rel_meeting_user u where u.meet_no=m.meet_no and u.user_id="
					+ loginPerson.getUuid() + ")";
			// }

			String meetName = TeeStringUtil.getString(request
					.getParameter("meetName"));
			String subject = TeeStringUtil.getString(request
					.getParameter("subject"));

			if (!"".equals(meetName)) {
				sql += " and m.meet_name like '%"
						+ TeeDbUtility.formatString(meetName) + "%'";
			}

			if (!"".equals(subject)) {
				sql += " and m.subject like '%"
						+ TeeDbUtility.formatString(subject) + "%'";
			}

			// 获取我的会议
			List<Map<String, Object>> list = chatDbUtils.queryToMapList(select
					+ sql + " order by m.start_time desc", null,
					dataGridModel.getFirstResult(), dataGridModel.getRows());
			Map<String, Object> totalMap = chatDbUtils.queryToMap(
					"select count(1) as c " + sql, null);
			dataGridJson.setTotal(TeeStringUtil.getLong(totalMap.get("c"), 0));

			TeePerson p = null;
			if (list != null) {
				for (Map<String, Object> data : list) {
					p = personService.selectByUuid(TeeStringUtil.getInteger(
							data.get("mainUser"), 0));
					data.put("mainUser", p.getUserName());
					totalMap = chatDbUtils.queryToMap(
							"select count(1) as c from rel_meeting_user where meet_no='"
									+ data.get("meetNo") + "'", null);
					data.put("count", totalMap.get("c"));
					data.put("url", TeeSysProps.getString("VMT_URL")
							+ "/sso.php?pwd="
							+ loginPerson.getPassword().subSequence(12, 44)
							+ "&username=" + loginPerson.getUserId().hashCode()
							+ "&roomID=" + data.get("meetNo"));
				}
			} else {
				list = new ArrayList();
			}

			dataGridJson.setRows(list);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(chatConn);
			// TeeDbUtility.closeConn(dbConn);
		}
		return dataGridJson;
	}*/

	/**
	 * 获取会议详细信息
	 * 
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping("getMeetingInfo")
	public TeeJson getMeetingInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);

		Connection chatConn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			chatConn = DriverManager.getConnection(
					"jdbc:mysql://" + TeeSysProps.getString("VMT_MYSQL_IP")
							+ ":" + TeeSysProps.getString("VMT_MYSQL_PORT")
							+ "/zlchat?characterEncoding=UTF-8", "root",
					"zlchat888");
			DbUtils chatDbUtils = new DbUtils(chatConn);

			String meetNo = TeeStringUtil.getString(request
					.getParameter("meetNo"));
			String sql = "select " + "m.meet_no as meetNo,"
					+ "m.meet_name as meetName," + "m.start_time as startTime,"
					+ "m.status," + "m.subject"
					+ " from meeting m where m.meet_no='" + meetNo + "'";

			Map<String, Object> data = chatDbUtils.queryToMap(sql);

			// 获取主办人
			Map<String, Object> mainUserData = chatDbUtils
					.queryToMap("select r.user_id as userId,c.real_name as realName from rel_meeting_user r,account c where c.id=r.user_id and r.meet_no='"
							+ meetNo + "' and r.role=2");
			data.put("mainUser", mainUserData.get("userId"));
			data.put("mainUserName", mainUserData.get("realName"));

			// 获取与会人
			List<Map<String, Object>> joinList = chatDbUtils
					.queryToMapList("select r.user_id as userId,c.real_name as realName from rel_meeting_user r,account c where c.id=r.user_id and r.meet_no='"
							+ meetNo + "' and r.role=3");
			StringBuffer ids = new StringBuffer();
			StringBuffer names = new StringBuffer();
			for (Map<String, Object> join : joinList) {
				ids.append(join.get("userId") + ",");
				names.append(join.get("realName") + ",");
			}

			if (ids.length() != 0) {
				ids.deleteCharAt(ids.length() - 1);
				names.deleteCharAt(names.length() - 1);
			}

			data.put("joiner", ids.toString());
			data.put("joinerName", names.toString());

			json.setRtData(data);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(chatConn);
		}
		return json;
	}
*/
	/**
	 * leiqisheng
	 * @return
	 *同步用户
	 */
	/*	@ResponseBody
		@RequestMapping("syncUserDatas")
		public TeeJson syncUserDatas() {
			TeeJson json = new TeeJson();
			vmeetingService.tbyh();
			json.setRtState(true);
			return json;
		}
	*/
	
	
	/**
	 * 预约会议
	 * leiqisheng
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addMeetingInfo")
	public TeeJson addMeetingInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String personIds=request.getParameter("personIds");
		MeetingModel  model = new MeetingModel();
		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		vmeetingService.addMeetingInfo(request, model,personIds);
		json.setRtState(true);
		return json;
	}
	/**
	 * 会议列表
	 * leiqisheng 
	 */
	//分页查询获得Rows 、total
	@ResponseBody
	@RequestMapping("getMyMeetings")
	public TeeEasyuiDataGridJson getMyMeetings(TeeDataGridModel dm,HttpServletRequest request){
		MeetingModel model = new MeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		String uuid = TeeStringUtil.getString(request.getParameter("uuid"), "");
		return vmeetingService.getMyMeetings(uuid,dm, model);
	

	}
	
	
	/**
	 * 	根据id查数据
	 * leiqisheng
	 * @return
	 */

	@ResponseBody
	@RequestMapping("getMeetingById")
	public TeeJson getMeetingById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String meetingId = TeeStringUtil.getString(request.getParameter("meetingId"), "");
         json.setRtData(vmeetingService.getMeetingById(meetingId));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据当前登录人ID查出会议列表
	 * leiqisheng
	 */
	//分页查询获得Rows 、total
		@ResponseBody
		@RequestMapping("getVmeetings")
		public TeeEasyuiDataGridJson getVmeetings(TeeDataGridModel dm,HttpServletRequest request){
			String userId = TeeStringUtil.getString(request.getParameter("userId"), "");
			MeetingModel model = new MeetingModel();
			TeeServletUtility.requestParamsCopyToObject(request, model);
			return vmeetingService.getVmeetings(userId,dm,model);
		}
	/**
	 * 根据选中id删除
	 * @param request
	 * @return
	 * @throws IOException 
	 */
		@ResponseBody
		@RequestMapping("delMeetingInfo")
		public TeeJson delMeetingInfo(HttpServletRequest request) throws IOException {
			TeeJson tj = new TeeJson();
			String meetingId=TeeStringUtil.getString(request.getParameter("meetingId"), "");
			   tj.setRtData(vmeetingService.delMeetingInfo(meetingId));
			   tj.setRtState(true);
		return tj;
		}
		
		/**
		 * 根据id查对象(修改)
	     *leiqisheng
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping("getMeeting")
		public TeeJson getMeeting(HttpServletRequest request){
			TeeJson json = new TeeJson();
			String meetingId = TeeStringUtil.getString(request.getParameter("meetingId"));
			json.setRtData(vmeetingService.getMeeting(meetingId));
			json.setRtState(true);
			return json;
		}
		
		
		
		/**
		 * 修改
		 * leiqisheng
		 * @param request
		 * @return
		 * @throws IOException 
		 */
		@ResponseBody
		@RequestMapping("updateMeetingInfo")
		public TeeJson updateMeeting(HttpServletRequest request) throws IOException{
			TeeJson json = new TeeJson();
	        MeetingModel  model = new MeetingModel();
			TeeServletUtility.requestParamsCopyToObject(request, model);
			vmeetingService.updateMeeting(model);
			json.setRtState(true);
//			System.out.println("------------------------------------------------------------------------------------------------SSS");
			return json;
		}
			
	
	
/*	/**
	 * 删除会议
	 * 
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("delMeetingInfo")
	public TeeJson delMeetingInfo(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);

		Connection chatConn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			chatConn = DriverManager.getConnection(
					"jdbc:mysql://" + TeeSysProps.getString("VMT_MYSQL_IP")
							+ ":" + TeeSysProps.getString("VMT_MYSQL_PORT")
							+ "/zlchat?characterEncoding=UTF-8", "root",
					"zlchat888");
			DbUtils chatDbUtils = new DbUtils(chatConn);

			String meetNo = TeeStringUtil.getString(request
					.getParameter("meetNo"));
			chatDbUtils.executeUpdate("delete from meeting where meet_no=?",
					new Object[] { meetNo });
			chatDbUtils.executeUpdate(
					"delete from rel_meeting_user where meet_no=?",
					new Object[] { meetNo });

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(chatConn);
		}
		return json;
	}*/
/**
	 * 更新会议状态
	 * 
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping("changeStatus")
	public TeeJson changeStatus(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		json.setRtState(true);

		Connection chatConn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			chatConn = DriverManager.getConnection(
					"jdbc:mysql://" + TeeSysProps.getString("VMT_MYSQL_IP")
							+ ":" + TeeSysProps.getString("VMT_MYSQL_PORT")
							+ "/zlchat?characterEncoding=UTF-8", "root",
					"zlchat888");
			DbUtils chatDbUtils = new DbUtils(chatConn);

			String meetNo = TeeStringUtil.getString(request
					.getParameter("meetNo"));
			String status = TeeStringUtil.getString(request
					.getParameter("status"));
			chatDbUtils.executeUpdate("update meeting set status=" + status
					+ " where meet_no=?", new Object[] { meetNo });

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(chatConn);
		}
		return json;
	}*/
}
