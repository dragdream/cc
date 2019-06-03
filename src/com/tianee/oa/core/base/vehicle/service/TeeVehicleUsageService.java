package com.tianee.oa.core.base.vehicle.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.calendar.model.TeeFullCalendarModel;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicle;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleMaintenance;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleDao;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleMaintenanceDao;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleUsageDao;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleUsageModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeVehicleUsageService extends TeeBaseService {

    @Autowired
    private TeeVehicleUsageDao vehicleUsageDao;
    @Autowired
    private TeeSmsManager smsManager;
    @Autowired
    private TeeVehicleDao vehicleDao;
    @Autowired
    private TeePersonDao personDao;
    
    @Autowired
    private TeeVehicleMaintenanceDao maintenanceDao;

    /**
     * @author syl 新增 或者 更新
     * @param message
     * @param person
     *            系统当前登录人
     * @return
     * @throws ParseException 
     */
    public TeeJson addOrUpdate(HttpServletRequest request, TeeVehicleUsageModel model) throws ParseException {
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeJson json = new TeeJson();

        TeeVehicleUsage vehicleUsage = new TeeVehicleUsage();
        BeanUtils.copyProperties(model, vehicleUsage);

        int vehicleId = model.getVehicleId();
        TeeVehicle vehicle = vehicleDao.getById(vehicleId);
        if (model.getSid() > 0) {
            TeeVehicleUsage dbVehicleUsage = vehicleUsageDao.getById(model.getSid());
            if (dbVehicleUsage != null) {
                if (vehicle != null) {
                    dbVehicleUsage.setVuDriver(model.getVuDriver());
                    
                    dbVehicleUsage.setVuStart(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuStartStr()));
                    dbVehicleUsage.setVuEnd(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuEndStr()));
//                    vehicleUsage.setVuProposer(person);
//                    vehicleUsage.setCreateTime(new Date());
                    dbVehicleUsage.setVehicle(vehicle);
                    int userSid = model.getVuUserId();
                    TeePerson user = personDao.get(userSid);
                    if (user != null) {
                        dbVehicleUsage.setVuUser(user);
                    }
                    TeePerson vuOperator = personDao.get(model.getVuOperatorId());
                    TeePerson deptManager = personDao.get(model.getDeptManagerId());
                    if ( vuOperator != null) {
                        dbVehicleUsage.setVuOperator(vuOperator);
                    }
                    
                    dbVehicleUsage.setVuDestination(model.getVuDestination());
                    dbVehicleUsage.setVuMileage(model.getVuMileage());
                    if (deptManager != null) {
                        dbVehicleUsage.setDeptManager(deptManager);
                    }
                    
                    dbVehicleUsage.setSmsRemind(model.getSmsRemind());
                    dbVehicleUsage.setVuReason(model.getVuReason());
                    dbVehicleUsage.setVuRemark(model.getVuRemark());
                    
                    vehicleUsageDao.update(dbVehicleUsage);
                }
            }
            
            

        } else {//新建
            if (vehicle != null) {
                vehicleUsage.setStatus(0);
                vehicleUsage.setVuStart(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuStartStr()));
                vehicleUsage.setVuEnd(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuEndStr()));
                vehicleUsage.setVuProposer(person);
                vehicleUsage.setCreateTime(new Date());
                vehicleUsage.setVehicle(vehicle);
                int userSid = model.getVuUserId();
                TeePerson user = personDao.get(userSid);
                if (user != null) {
                    vehicleUsage.setVuUser(user);
                }
                TeePerson vuOperator = personDao.get(model.getVuOperatorId());
                if ( vuOperator != null) {
                    vehicleUsage.setVuOperator(vuOperator);
                }
                
                if ("1".equals(model.getSmsRemind())) {//32
                    String userListIds = "";
                    if (model.getDeptManagerId() !=0) {
                        userListIds = String.valueOf(model.getDeptManagerId());
                    }else {
                        userListIds = String.valueOf(model.getVuOperatorId());
                    }
                    Map requestData = new HashMap();
                    requestData.put("content", "车辆申请提醒！您有新的车辆申请未审批，请查阅！");
                    requestData.put("userListIds",userListIds );
                    requestData.put("moduleNo", "032");
                    requestData.put("sendTime",TeeUtility.getDateTimeStr(new Date()) );
                    requestData.put("remindUrl", "/system/core/base/vehicle/leader/index.jsp");
                    smsManager.sendSms(requestData, person);
                }
                vehicleUsageDao.save(vehicleUsage);
            }
        }
        json.setRtState(true);
        json.setRtData(model);
        json.setRtMsg("保存成功！");

        return json;
    }
    
    /**
     * 获取车辆申请信息
     * @date 2014-3-4
     * @author 
     * @param request
     * @param model
     * @return
     */
    public TeeJson getAllVelicleUsage(HttpServletRequest request, TeeVehicleUsageModel model)throws ParseException {
        TeeJson json = new TeeJson();
        
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String startTimeStr = model.getVuStartStr();
        String endTimeStr = model.getVuEndStr();
        
        Date startTime = TeeUtility.parseDate(startTimeStr + " 00:00:00");
        Date endTime = TeeUtility.parseDate(endTimeStr + " 00:00:00");  
        int vehicleSid  = TeeStringUtil.getInteger(model.getVehicleId(), 0);
        
        List<TeeVehicleUsage> list = vehicleUsageDao.selectPersonalByTime(person, startTime, endTime, vehicleSid);
      //日程model
        List<TeeFullCalendarModel> listModel = new ArrayList<TeeFullCalendarModel> ();
        for (int i = 0; i < list.size(); i++) {
            listModel.add(parseFullCalendarModel( list.get(i) , person ));
        }
        json.setRtData(listModel);
        json.setRtState(true);
        return json;
    }

    /***
     * 将日程转换为 fullCalendar对象
     * @author syl
     * @date 2014-1-5
     * @param cal
     * @return
     */
    public TeeFullCalendarModel parseFullCalendarModel(TeeVehicleUsage vehicleUsage , TeePerson loginUser){
        TeeFullCalendarModel calModel = new TeeFullCalendarModel();
        calModel.setId(vehicleUsage.getSid());
        calModel.setTitle(vehicleUsage.getVehicle().getvNum());
        int day = TeeUtility.getDaySpan(vehicleUsage.getVuStart(), vehicleUsage.getVuEnd());
        if (day>0) {
            calModel.setAllDay(true);
        }
        
        calModel.setClassName("fc-event-color");
        Date date = new Date();
        date.setTime(vehicleUsage.getVuStart().getTime());//设置开始时间
        calModel.setStart(TeeDateUtil.format(date));
        
        date.setTime(vehicleUsage.getVuEnd().getTime());//设置结束时间时间
        calModel.setEnd(TeeDateUtil.format(date));
        
        calModel.setEditable(false);
        calModel.setDeleteable(false);
        
        if(vehicleUsage.getStatus() == 0  || vehicleUsage.getStatus() == 3){//待批准和未批准
            calModel.setEditable(true);
            calModel.setDeleteable(true);
        }
        if(vehicleUsage.getVuProposer().getUuid() != loginUser.getUuid()){//不是自己申请的
            calModel.setEditable(false);
            calModel.setDeleteable(false);
        }
        
        if(vehicleUsage.getStatus() == 1){
            calModel.setClassName("fc-event-color3");
        }else if(vehicleUsage.getStatus() == 2){
            calModel.setClassName("fc-event-color1");
        }else if(vehicleUsage.getStatus() == 3){
            calModel.setClassName("fc-event-color4");
        }else if(vehicleUsage.getStatus() == 4){
            calModel.setClassName("fc-event-color6");
        }
        
        calModel.setTitle("使用人："+vehicleUsage.getVuUser().getUserName()+
        		"\r\n车牌号："+vehicleUsage.getVehicle().getvModel()
        		+"\r\n厂牌类型："+vehicleUsage.getVehicle().getvNum());
        
        return calModel;
    }
    
    
    

    /**
     * 获取所有会议室
     * 
     * @author syl
     * @date 2014-1-29
     * @param request
     * @param model
     * @return
     */
    public TeeJson getAll(HttpServletRequest request, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        List<TeeVehicleUsage> list = vehicleUsageDao.getAllVehicle(person, model);
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        for (int i = 0; i < list.size(); i++) {
            listModel.add(parseModel(list.get(i)));
        }
        json.setRtData(listModel);
        json.setRtState(true);
        return json;
    }

    /**
     * 获取有权限的会议室
     * 
     * @author syl
     * @date 2014-1-29
     * @param request
     * @param model
     * @return
     */
    public TeeJson selectPostMeetRoom(HttpServletRequest request, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        /*
         * List<TeeVehicleUsage> list =
         * vehicleUsageDao.selectPostMeetRoom(person, model);
         * 
         * for (int i = 0; i < list.size(); i++) {
         * listModel.add(parseSimpleModel(list.get(i))); }
         */
        json.setRtData(listModel);
        json.setRtState(true);
        return json;
    }

    /**
     * 对象转换
     * 
     * @author syl
     * @date 2014-1-29
     * @param out
     * @return
     */
    public TeeVehicleUsageModel parseModel(TeeVehicleUsage vehicleUsage) {
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        if (vehicleUsage == null) {
            return model;
        }
        if (vehicleUsage.getVehicle() != null) {
            model.setVehicleId(vehicleUsage.getVehicle().getSid());
            model.setVehicleName(vehicleUsage.getVehicle().getvModel());
        }
        if (vehicleUsage.getVuProposer() != null) {
            model.setVuProposerName(vehicleUsage.getVuProposer().getUserName());
        }
        if (vehicleUsage.getVuUser() != null) {
            model.setVuUserName(vehicleUsage.getVuUser().getUserName());
        }
        if (vehicleUsage.getCreateTime() != null) {
            model.setCreateTimeStr(TeeUtility.getDateTimeStr(vehicleUsage.getCreateTime()));
        }
        if (vehicleUsage.getVuStart() != null) {
            model.setVuStartStr(TeeUtility.getDateStrByFormat(vehicleUsage.getVuStart(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        }
        if (vehicleUsage.getVuEnd() != null) {
            model.setVuEndStr(TeeUtility.getDateStrByFormat(vehicleUsage.getVuEnd(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        }
        if (vehicleUsage.getVuOperator() != null) {
            model.setVuOperatorName(vehicleUsage.getVuOperator().getUserName());
        }
        BeanUtils.copyProperties(vehicleUsage, model);

        /*
         * String[] personInfo =
         * personDao.getPersonNameAndUuidByUuids(room.getManagerIds());
         * model.setManagerIds(personInfo[0]);
         * model.setManagerNames(personInfo[1]);
         */
        return model;
    }

    /**
     * 对象转换
     * 
     * @author syl
     * @date 2014-1-29
     * @param out
     * @return
     */
    public TeeVehicleUsageModel parseSimpleModel(TeeVehicleUsage room) {
        TeeVehicleUsageModel model = new TeeVehicleUsageModel();
        if (room == null) {
            return model;
        }
        BeanUtils.copyProperties(room, model);

        return model;
    }

    /**
     * 删除ById
     * @date 2014-3-9
     * @author 
     * @param request
     * @param model
     * @return
     */
    public TeeJson deleteById(HttpServletRequest request, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        if (model.getSid() > 0) {
            vehicleUsageDao.delById(model.getSid());
        }
        json.setRtState(true);
        json.setRtMsg("删除成功!");
        return json;
    }

    /**
     * 
     * @author syl 删除所有会议室
     * @date 2014-1-29
     * @param request
     * @param model
     * @return
     */
  /*  public TeeJson deleteAll(HttpServletRequest request, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        vehicleUsageDao.delAll();
        json.setRtState(true);
        json.setRtMsg("删除成功!");
        return json;
    }*/

    /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @param model
     * @return
     */
    public TeeJson getInfoByIdService(HttpServletRequest request, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        if (model.getSid() > 0) {
            TeeVehicleUsage out = vehicleUsageDao.getById(model.getSid());
            if (out != null) {
                model = parseModel(out);
                if (out.getVuStart() != null) {
                    model.setVuStartStr(TeeUtility.getDateStrByFormat(out.getVuStart(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
                }else {
                    model.setVuStartStr("");
                }
                if (out.getVuEnd() != null) {
                    model.setVuEndStr(TeeUtility.getDateStrByFormat(out.getVuEnd(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
                }else {
                    model.setVuEndStr("");
                }
                TeePerson person = out.getVuUser();
                if ( person != null) {
                    model.setVuUserId(person.getUuid());
                    model.setVuUserName(person.getUserName());
                }
                if ( out.getVehicle() != null) {
                    model.setVehicleId(out.getVehicle().getSid());
                    model.setVehicleName(out.getVehicle().getvModel());
                }
                if ( out.getVuOperator() != null) {
                    model.setVuOperatorId(out.getVuOperator().getUuid());
                    model.setVuOperatorName(out.getVuOperator().getUserName());
                }
                
                
                json.setRtData(model);
                json.setRtState(true);
                json.setRtMsg("查询成功!");
                return json;
            }
        }
        json.setRtState(false);
        json.setRtMsg("该会议室可能已被删除！");
        return json;
    }

    
    
    /**
     * 获取系统当前登录人  车辆申请记录    --- 根据车辆申请状态
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public TeeEasyuiDataGridJson getPersonalMeetByStatus(TeePerson person ,TeeVehicleUsageModel model,TeeDataGridModel dm){
    	TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        this.autoUpdateVehicleStatus(person, model);
        
        String hql = "from TeeVehicleUsage where vuProposer = ? and status = ? order by  createTime desc";
        if(model.getStatus()==2){
        	hql = "from TeeVehicleUsage where vuProposer = ? and (status = ? or status=5) order by  createTime desc";
        }
        
		List param = new ArrayList();
		param.add(person);
		param.add(model.getStatus());
		// 设置总记录数
		j.setTotal(vehicleUsageDao.countByList("select count(*) " + hql, param));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeVehicleUsage> list = vehicleUsageDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
        
        
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        if (list != null && list.size()>0) {
            for(TeeVehicleUsage vehicleUsage:list){
                listModel.add(parseModel(vehicleUsage) );  
            }
        }
        j.setRows(listModel);
        return j;
    }
    
    
    /**
     * 自动更改 状态   “已审批准” 改成  “进行中” 。“进行中” 改成  “结束”
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     */
    public void autoUpdateVehicleStatus(TeePerson person ,TeeVehicleUsageModel model){
        Date currDate = new Date();
        long currTime = currDate.getTime();
        
        /*“已批准” 和  “进行中”*/
        List<TeeVehicleUsage> list = vehicleUsageDao.getAutoLeaderVehicleByStatus(person);
        if (list != null  && list.size()>0) {
            for(TeeVehicleUsage vehicleUsage:list){
                if (vehicleUsage.getStatus() ==1) {//已批准
                    if(currTime > vehicleUsage.getVuStart().getTime() ){//如果当前时间比开始时间还大，直接更改成进行中
                        model.setStatus(2);
                        model.setSid(vehicleUsage.getSid());
                        vehicleUsageDao.autoUpdateStatus(person, model);
                    }else if(currTime > vehicleUsage.getVuEnd().getTime() ){//如果当前时间比结束时间还大，直接更改成结束
                        model.setStatus(4);
                        model.setSid(vehicleUsage.getSid());
                        vehicleUsageDao.autoUpdateStatus(person, model);
                    }
                }else if (vehicleUsage.getStatus() ==2) {//进行中
                    if(currTime > vehicleUsage.getVuEnd().getTime() ){//如果当前时间比结束时间还大，直接更改成结束
                        model.setStatus(5);//这里设置为已超时
                        model.setSid(vehicleUsage.getSid());
                        vehicleUsageDao.autoUpdateStatus(person, model);
                    }
                }
            }
        }
    }
    
    /**
     * 获取各种状态车辆申请总数
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public TeeJson getLeaderApproveCount(TeePerson person, TeeVehicleUsageModel model) {
        TeeJson json = new TeeJson();
        //自动执行更改状态
        this.autoUpdateVehicleStatus(person, model);
        
        //待审批
        model.setStatus(0);
        long applyCount = vehicleUsageDao.getLeaderApproveCount(person, model);

        //已批准
        model.setStatus(1);
        long approveCount = vehicleUsageDao.getLeaderApproveCount(person, model);
        
        
        //进行中
        model.setStatus(2);
        long IngCount = vehicleUsageDao.getLeaderApproveCount(person, model);
        
        //未批准
        model.setStatus(3);
        long noApproveCount = vehicleUsageDao.getLeaderApproveCount(person, model);

        //已结束
        model.setStatus(4);
        long endCount = vehicleUsageDao.getLeaderApproveCount(person, model);
        Map map = new HashMap();
        map.put("vehicleCount0", applyCount);
        map.put("vehicleCount1", approveCount);
        map.put("vehicleCount2", IngCount);
        map.put("vehicleCount3", noApproveCount);
        map.put("vehicleCount4", endCount);
        json.setRtData(map);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 获取当前系统登录人  车辆申请审批记录    --- 根据车辆申请状态
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public TeeEasyuiDataGridJson getApprovalVehicleByStatus(TeePerson person ,TeeVehicleUsageModel model,TeeDataGridModel dm){
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        this.autoUpdateVehicleStatus(person, model);
        
        
        String hql = "from TeeVehicleUsage where vuOperator = ? and status = ? order by  createTime desc";
        if(model.getStatus()==2){
        	hql = "from TeeVehicleUsage where vuProposer = ? and (status = ? or status=5) order by  createTime desc";
        }
        
    	List param = new ArrayList();
		param.add(person);
		param.add(model.getStatus());
		// 设置总记录数
		json.setTotal(vehicleUsageDao.countByList("select count(*) " + hql, param));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeVehicleUsage> list = vehicleUsageDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
        
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel>();
        if (list != null && list.size()>0) {
            for(TeeVehicleUsage vehicleUsage:list){
                listModel.add(parseModel(vehicleUsage) );  
            }
        }
        json.setRows(listModel);
        return json;
    }
    
    
    
    /**
     * 审批管理
     * @date 2014-3-9
     * @author 
     * @param person
     * @param model
     * @return
     */
    public TeeJson approvalService(TeePerson person,TeeVehicleUsageModel model){
        TeeJson json = new TeeJson();
        TeeVehicleUsage vehicleUsage = vehicleUsageDao.getById(model.getSid());
        if (vehicleUsage != null) {
            vehicleUsage.setStatus(model.getStatus());
            vehicleUsageDao.update(vehicleUsage);
            
            String userListIds = "";
            if (vehicleUsage.getDeptManager()!= null) {
                userListIds = String.valueOf(vehicleUsage.getDeptManager().getUuid());
            }else {
                userListIds = String.valueOf(vehicleUsage.getVuOperator().getUuid());
            }
            Map requestData = new HashMap();
            requestData.put("userListIds",userListIds );
            requestData.put("moduleNo", "032");
            requestData.put("sendTime",TeeUtility.getDateTimeStr(new Date()) );
            requestData.put("remindUrl", "/system/core/base/vehicle/personal/index.jsp");
            if(model.getStatus() == 1){//批准 
              //发送内部短信   内部短信
                String smsRemind = vehicleUsage.getSmsRemind();
                if ("1".equals(smsRemind)) {//32
                    requestData.put("content", "您的车辆申请已批准，请查阅！");
                    smsManager.sendSms(requestData, person);
                }
            }else if(model.getStatus() == 3){//不批准
                //requestData.put("content", "你的会议申请未批准,请查看!");
                requestData.put("content", "您的车辆申请未批准，请查阅！");
                smsManager.sendSms(requestData, person);
            }
        }
        json.setRtState(true);
        return json;
    }
    
    

    /**
     * 获取系统当前登录人 所有申请车辆记录
     * @date 2014-3-9
     * @author 
     * @param request
     * @param model
     * @return
     * @throws ParseException
     */
    public TeeJson getAllVehicleByTime(HttpServletRequest request , TeeVehicleUsageModel model) throws ParseException {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
        //自动执行更改状态
        autoUpdateVehicleStatus(person, model);
        String startTimeStr = model.getVuStartStr();
        String endTimeStr = model.getVuEndStr();
        Date startDate = TeeUtility.parseDate(startTimeStr + " 00:00:00");  
        Date endDate = TeeUtility.parseDate(endTimeStr + " 23:59:59");
        //日程model
        List<TeeVehicleUsageModel> listModel = new ArrayList<TeeVehicleUsageModel> ();
        
        List<TeeVehicleUsage> list = vehicleUsageDao.getAllVehicleUsageByTime(startDate, endDate);   
        for (int i = 0; i < list.size(); i++) {
            listModel.add(parseModel(list.get(i)));
        }
        json.setRtState(true);
        json.setRtData(listModel);
        return json;
    }
    
    
    

    /**
     * 通用列表
     * @param dm
     * @return
     * @throws ParseException 
     */
    @Transactional(readOnly = true)
    public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request , TeeVehicleUsageModel model) throws ParseException {
        TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
        TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        j.setTotal(vehicleUsageDao.getQueryCount(loginPerson ,model));// 设置总记录数
        int firstIndex = 0;
        firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
        Object parm[] = {};
        List<TeeVehicleUsage> list = vehicleUsageDao.getMeetPageFind(firstIndex, dm.getRows(), dm, model);// 查
        List<TeeVehicleUsageModel> modelList = new ArrayList<TeeVehicleUsageModel>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                TeeVehicleUsageModel modeltemp = parseModel(list.get(i));
                modelList.add(modeltemp);
            }
        }
        j.setRows(modelList);// 设置返回的行
        return j;
    }
    
    
    /**
     * 拖拉更改车辆信息
     * @date 2014-2-15
     * @param request
     * @param model
     * @return
     */
    public TeeJson updateChangeMeet(HttpServletRequest request, TeeVehicleUsageModel model)throws Exception {
        TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeJson json = new TeeJson();
        //处理申请时间

        if(model.getSid() > 0){
            TeeVehicleUsage vehicleUsage  = vehicleUsageDao.getById(model.getSid());
            if(vehicleUsage != null){
                vehicleUsage.setVuStart(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuStartStr()));
                vehicleUsage.setVuEnd(TeeUtility.parseDate("yyyy-MM-dd HH:mm",model.getVuEndStr()));
                vehicleUsageDao.update(vehicleUsage);
                
            }else{
                json.setRtState(false);
                json.setRtMsg("未查到到相关车辆信息！");
                return json;
            }
        }
        json.setRtState(true);
        json.setRtData(model);
        json.setRtMsg("保存成功！");
        
        if ("1".equals(model.getSmsRemind())) {//32
            String userListIds = "";
            if (model.getDeptManagerId() !=0) {
                userListIds = String.valueOf(model.getDeptManagerId());
            }else {
                userListIds = String.valueOf(model.getVuOperatorId());
            }
            Map requestData = new HashMap();
            requestData.put("content", "车辆申请提醒！内容：您有新的车辆申请未审批，请查阅！");
            requestData.put("userListIds",userListIds );
            requestData.put("moduleNo", "032");
            requestData.put("sendTime",TeeUtility.getDateTimeStr(new Date()) );
            requestData.put("remindUrl", "/core/base/vehicle/deptleader/index.jsp");
            smsManager.sendSms(requestData, person);
        }
        return json;
    }

    
	public TeeJson isApply(String vehicleId, String vuStartStr) {
		TeeJson json = new TeeJson();
		 if(!TeeUtility.isNullorEmpty(vehicleId) && !TeeUtility.isNullorEmpty(vuStartStr)){
			 TeeVehicle vehicle = vehicleDao.getById(Integer.parseInt(vehicleId));
			 Map map = new HashMap();
			 List<TeeVehicleMaintenance> maintenanceList = maintenanceDao.getMaintenanceVehicle(vehicle.getSid(),vuStartStr);
			 if(null!=maintenanceList && maintenanceList.size()>0){
				 map.put("type", "maintenancing");
				 json.setRtData(map);
				 json.setRtState(true);
				 json.setRtMsg("当前申请车辆正在维护，不能申请，请重新选择其他车辆！");
			 }
			 
			 List<TeeVehicleUsage> list = vehicleUsageDao.getUsingVehicle(vehicle.getSid(),vuStartStr);
			 if(null!=list && list.size()>0){
				 map.put("type", "using");
				 json.setRtData(map);
				 json.setRtState(true);
				 json.setRtMsg("当前申请车辆正在使用中，不能申请，请重新选择其他车辆！或者调整申请开始时间！");
			 }
	      }
		return json;
	}

	/**
	 * 归还车辆
	 * @param person
	 * @param model
	 * @return
	 */
	public TeeJson toEnd(TeePerson person, TeeVehicleUsageModel model) {
		  TeeJson json = new TeeJson();
	        TeeVehicleUsage vehicleUsage = vehicleUsageDao.getById(model.getSid());
	        if (vehicleUsage != null) {
	            vehicleUsage.setStatus(4);
	            Calendar cl = Calendar.getInstance();
	            vehicleUsage.setVuEnd(cl.getTime());
	            vehicleUsageDao.update(vehicleUsage);
	            json.setRtState(true);
	            json.setRtMsg("归还成功！");
	        }else{
	        	json.setRtState(false);
	        	json.setRtMsg("没有找到相关车辆使用信息！");
	        }
	        return json;
	}

	/**
	 * 车辆申请记录
	 * @param vehicleId
	 * @return
	 */
	public TeeJson getRecords(int vehicleId) {
		TeeJson json = new TeeJson();
		List<TeeVehicleUsage> list = vehicleUsageDao.getAllVehicle(null, null);
		 List<TeeVehicleUsageModel> models = new ArrayList<TeeVehicleUsageModel>();
		if(null!=list && list.size()>0){
			for(TeeVehicleUsage t: list){
				if(t.getVehicle().getSid()==vehicleId){
					TeeVehicleUsageModel model = new TeeVehicleUsageModel();
					model = parseModel(t);
					models.add(model);
				}
			}
			json.setRtState(true);
			json.setRtData(models);
		}else{
			json.setRtState(false);
			json.setRtMsg("没有找到相关车辆申请记录！");
		}
		return json;
	}

	

    
    
    
    
    
    
    
}
