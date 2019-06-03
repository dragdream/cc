package com.tianee.oa.subsys.footprint.service;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.dbutils.DbUtils;
import org.apache.pdfbox.util.operator.BeginText;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.footprint.bean.TeeFootPrint;
import com.tianee.oa.subsys.footprint.bean.TeeFootPrintRange;
import com.tianee.oa.subsys.footprint.dao.TeeFootPrintDao;
import com.tianee.oa.subsys.footprint.model.TeeFootPrintModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.logic.Kmeans;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class TeeFootPrintService extends TeeBaseService{
	@Autowired
	private TeeFootPrintDao footPrintDao;

	
	/**
	 * 添加足迹信息
	 * @param request
	 * @return
	 */
	public TeeJson addFootPrint(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//获取当前正在登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取当前时间
		Date crTime=new Date();
		String crTime1=sdf.format(crTime);
		try {
			crTime=sdf.parse(crTime1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取页面上传来的横坐标  和   纵坐标
		Double coordinateX=TeeStringUtil.getDouble(request.getParameter("coordinateX"), 0.000000);
		Double coordinateY=TeeStringUtil.getDouble(request.getParameter("coordinateY"), 0.000000);
		
		Point2D.Double p=new Point2D.Double(coordinateX,coordinateY);
		
		//获取页面上传来的地理位置描述
		String addressDescription=TeeStringUtil.getString(request.getParameter("addressDescription"));
		TeeFootPrint footPrint=new TeeFootPrint();
		footPrint.setUser(loginUser);
		footPrint.setCoordinateX(coordinateX);
		footPrint.setCoordinateY(coordinateY);
		footPrint.setCrTime(crTime);
		footPrint.setAddressDescription(addressDescription);
		

		//获取当前登陆人的电子围栏
		List <TeeFootPrintRange> rangeList=simpleDaoSupport.executeQuery("select r from TeeFootPrintRange  r where exists ( select 1 from r.users user where user.uuid=?   ) ", new Object[]{loginUser.getUuid()});
		if(rangeList!=null&&rangeList.size()>0){//该用户有设置电子围栏
			ArrayList<Point2D.Double> points=new ArrayList<Point2D.Double>();
			List<Map<String, String>> mapList=null;
			Point2D.Double point=null;
			for (TeeFootPrintRange teeFootPrintRange : rangeList) {
				mapList=TeeJsonUtil.JsonStr2MapList(teeFootPrintRange.getRangeStr());
				if(mapList!=null&&mapList.size()>0){
					for (Map<String, String> map : mapList) {
						point=new Point2D.Double(TeeStringUtil.getDouble(map.get("LNG"),0),TeeStringUtil.getDouble(map.get("LAT"),0));
					    points.add(point);
					}
					
					GeneralPath path=genGeneralPath(points);
					if(path!=null){
						if(path.contains(p)){//在范围内
							footPrint.setIsCross(0);
							break;
						}else{//不在范围内
							footPrint.setIsCross(1);
						}
					}else{//不越界
						footPrint.setIsCross(0);
					}
				}else{//不越界
					footPrint.setIsCross(0);	
				}	
			}
		}else{//该用户没有设置电子围栏   默认是不越界
			footPrint.setIsCross(0);
		}
		simpleDaoSupport.save(footPrint);
		
		json.setRtState(true);
		json.setRtMsg("添加成功！");
		return json;
	}
	
	
	/**
	 * 添加足迹信息（批量）
	 * @param request
	 * @return
	 */
	public TeeJson addFootPrintBatch(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//获取当前正在登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
//		//获取当前时间
//		Date crTime=new Date();
//		String crTime1=sdf.format(crTime);
//		try {
//			crTime=sdf.parse(crTime1);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//获取当前登陆人的电子围栏
		List <TeeFootPrintRange> rangeList=simpleDaoSupport.executeQuery(" select r from TeeFootPrintRange  r where exists ( select 1 from r.users user where user.uuid=?   ) ", new Object[]{loginUser.getUuid()});
		
		
		String jsonArrayData = TeeStringUtil.getString(request.getParameter("jsonArrayData"));
		JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);
		
		
		ArrayList<Point2D.Double> points=null;
		List<Map<String, String>> mapList=null;
		Point2D.Double point=null;
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			//获取页面上传来的横坐标  和   纵坐标
			Double coordinateX=jsonObject.getDouble("x");
			Double coordinateY=jsonObject.getDouble("y");
			
			Point2D.Double p=new Point2D.Double(coordinateX,coordinateY);
			
			String desc = jsonObject.getString("desc");
			String time = jsonObject.getString("time");
			//获取页面上传来的地理位置描述
			TeeFootPrint footPrint=new TeeFootPrint();
			footPrint.setUser(loginUser);
			footPrint.setCoordinateX(coordinateX);
			footPrint.setCoordinateY(coordinateY);
			footPrint.setCrTime(TeeDateUtil.parseDate(time));
			footPrint.setAddressDescription(desc);
			
			if(rangeList!=null&&rangeList.size()>0){//该用户有设置电子围栏
				points=new ArrayList<Point2D.Double>();
				for (TeeFootPrintRange teeFootPrintRange : rangeList) {
					mapList=TeeJsonUtil.JsonStr2MapList(teeFootPrintRange.getRangeStr());
					if(mapList!=null&&mapList.size()>0){
						for (Map<String, String> map : mapList) {
							point=new Point2D.Double(TeeStringUtil.getFloat(map.get("LNG"),0f),TeeStringUtil.getFloat(map.get("LAT"),0f));
						    points.add(point);
						}
						
						GeneralPath path=genGeneralPath(points);
						if(path!=null){
							if(path.contains(p)){//在范围内
								footPrint.setIsCross(0);
								break;
							}else{//不在范围内
								footPrint.setIsCross(1);
							}
						}else{//不越界
							footPrint.setIsCross(0);
						}
					}else{//不越界
						footPrint.setIsCross(0);	
					}	
				}
			}else{//该用户没有设置电子围栏   默认是不越界
				footPrint.setIsCross(0);
			}
			
			
			simpleDaoSupport.save(footPrint);
		}
		
		
		json.setRtState(true);
		json.setRtMsg("添加成功！");
		return json;
	}


	/**
	 * 根据时间查找我的足迹集合
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson findFootPrintList(HttpServletRequest request,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//获取当前正在登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的开始结束时间
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		Date sTime=null;
		Date eTime=null;
		List<Object> param = new ArrayList<Object>();	
		try {
			sTime=sdf.parse(startTime);
			eTime=sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql=" from TeeFootPrint fp where fp.user.uuid=? and fp.crTime>=? and fp.crTime<=? ";
		param.add(loginUser.getUuid());
		param.add(sTime);
		param.add(eTime);
		
		// 设置总记录数
		json.setTotal(footPrintDao.countByList("select count(*) " + hql, param));		
		List<TeeFootPrint> list = footPrintDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
				
		List<TeeFootPrintModel> listModel = new ArrayList<TeeFootPrintModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
	   json.setRows(listModel);
	   
	   //计算中心点
	   Kmeans k=new Kmeans(1);  
       ArrayList<double[]> dataSet=new ArrayList<double[]>();
       for (int i = 0; i < list.size(); i++) {
    	   dataSet.add(new double[]{list.get(i).getCoordinateX(),list.get(i).getCoordinateY()});
		}
       k.setDataSet(dataSet);
       k.execute();
       List<double[]> center = k.getCenter();
       double[] centerpoint = center.get(0);
	   
       List<Object> centerList = new ArrayList<Object>(centerpoint.length);
       for(double d: centerpoint){
    	   centerList.add(d);
       }
       
       json.setFooter(centerList);
	   
	   return json;
	}
	
	
	/**
	 * model转换成实体类
	 * @param fp
	 * @return
	 */
	public TeeFootPrintModel parseModel(TeeFootPrint fp){
		TeeFootPrintModel model = new TeeFootPrintModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		BeanUtils.copyProperties(fp, model);
		if(fp.getUser()!=null){
			model.setUserId(fp.getUser().getUserId() + "");
			model.setUserName(fp.getUser().getUserName() + "");
			model.setUserUuid(fp.getUser().getUuid());
		}
	
		Date crTime=fp.getCrTime();
		model.setCrTimeStr(sdf.format(crTime));
		return model;
	}


	
	/**
	 * 根据时间和人员查找足迹集合
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getListByPerson(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//获取当前正在登录的用户
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的开始结束时间
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		int personId=TeeStringUtil.getInteger(request.getParameter("personId"),0);
		Date sTime=null;
		Date eTime=null;
		List<Object> param = new ArrayList<Object>();	
		try {
			sTime=sdf.parse(startTime);
			eTime=sdf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql=" from TeeFootPrint fp where fp.user.uuid=? and fp.crTime>=? and fp.crTime<=? ";
		param.add(personId);
		param.add(sTime);
		param.add(eTime);
		
		// 设置总记录数
		json.setTotal(footPrintDao.countByList("select count(*) " + hql, param));		
		List<TeeFootPrint> list = footPrintDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
				
		List<TeeFootPrintModel> listModel = new ArrayList<TeeFootPrintModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
	   json.setRows(listModel);
	   
	   //计算中心点
	   Kmeans k=new Kmeans(1);  
       ArrayList<double[]> dataSet=new ArrayList<double[]>();
       for (int i = 0; i < list.size(); i++) {
    	   dataSet.add(new double[]{list.get(i).getCoordinateX(),list.get(i).getCoordinateY()});
		}
       k.setDataSet(dataSet);
       k.execute();
       List<double[]> center = k.getCenter();
       double[] centerpoint = center.get(0);
	   
       List<Object> centerList = new ArrayList<Object>(centerpoint.length);
       for(double d: centerpoint){
    	   centerList.add(d);
       }
       
       json.setFooter(centerList);
	   
	   return json;
	}
	
	
	
	/**
	 * 判断点是否在面内
	 * @param points
	 * @return
	 */
	public static GeneralPath genGeneralPath(ArrayList<Point2D.Double> points) {
		   GeneralPath path = new GeneralPath();

		   if (points.size() < 3) {
		    return null;
		   }

		   path.moveTo((float) points.get(0).getX(), (float) points.get(0).getY());

		   for (Iterator<Point2D.Double> it = points.iterator(); it.hasNext();) {
		    Point2D.Double point = (Point2D.Double) it.next();

		    path.lineTo((float) point.getX(), (float) point.getY());
		   }
		   path.closePath();

		   return path;
		}


	
	/**
	 * 电子围栏 报警次数统计 图
	 * @param request
	 * @return
	 */
	public TeeJson renderCrossImg(HttpServletRequest request) {
		Map data=new HashMap();
		TeeJson json=new TeeJson();
		
		//获取前台传来的日期范围
		int selectType=TeeStringUtil.getInteger(request.getParameter("selectType"), 0);
		//获取开始  结束时间
		String beginDateStr=TeeStringUtil.getString(request.getParameter("beginDateStr"));
		String endDateStr=TeeStringUtil.getString(request.getParameter("endDateStr"));
		
		Calendar begin=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		
		if(selectType==1){//今日
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==2){//昨日
			begin.add(Calendar.DATE, -1);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end.add(Calendar.DATE, -1);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			
		}else if(selectType==3){//本周
			//先获取今天是周几
			int week=begin.get(Calendar.DAY_OF_WEEK);
			if(week==1){
				week=7;
			}else{
				week=week-1;
			}
			
			begin.add(Calendar.DATE, -(week-1));
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			
			end.add(Calendar.DATE, (7-week));
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==4){//本月
			//获取当前的月数
			int month=begin.get(Calendar.MONTH);
			//获取当前月的最大天数
			int maxDays=begin.getActualMaximum(Calendar.DATE);
			
			
			begin.set(Calendar.DAY_OF_MONTH,1);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			
			end.set(Calendar.DAY_OF_MONTH,maxDays);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==5){//本年
			int year=begin.get(Calendar.YEAR);
			String b=year+"-01-01";
			String e=year+"-12-31";
			
			begin=TeeDateUtil.parseCalendar(b);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end=TeeDateUtil.parseCalendar(e);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==6){//指定范围
			if(!TeeUtility.isNullorEmpty(beginDateStr)){
				begin=TeeDateUtil.parseCalendar(beginDateStr);
				begin.set(Calendar.HOUR_OF_DAY, 0);
				begin.set(Calendar.MINUTE, 0);
				begin.set(Calendar.SECOND, 0);
			}
			
			if(!TeeUtility.isNullorEmpty(endDateStr)){
				end=TeeDateUtil.parseCalendar(endDateStr);
				end.set(Calendar.HOUR_OF_DAY, 23);
				end.set(Calendar.MINUTE, 59);
				end.set(Calendar.SECOND, 59);
			}
			
		}
		
		String hql=" select count(pt.sid) as c ,pt.user.userName as userName,pt.user.uuid as userId from TeeFootPrint pt where pt.crTime>=? and pt.crTime<=? and isCross=1 group by pt.user";
	    List<Map> mapList=simpleDaoSupport.getMaps(hql, new Object[]{begin.getTime(),end.getTime()});
	   
	    List<String> categories=new ArrayList<String>();
	   
	    List<Map<String,Object>> series=new ArrayList<Map<String,Object>>();//返回数据
	    Map<String,Object> seriesMap=new HashMap<String,Object>();
	    seriesMap.put("name", "报警次数");
	    List<Map> dataList=new ArrayList<Map>();
	    Map m=null;
	    for (Map map : mapList) {
	    	m=new HashMap();
	    	categories.add(TeeStringUtil.getString(map.get("userName")));
	    	m.put("name", TeeStringUtil.getString(map.get("userName")));
	    	m.put("y", TeeStringUtil.getInteger(map.get("c"),0));
	    	m.put("uuid", TeeStringUtil.getInteger(map.get("userId"),0));
	    	
	    	dataList.add(m);
			
		}
	    seriesMap.put("data", dataList);
	    series.add(seriesMap);
	    data.put("series", series);
	    data.put("categories", categories);
	    json.setRtData(data);
	    json.setRtState(true); 
	    return json;
	}


	/**
	 * 获取报警记录列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getAcrossList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int userId=TeeStringUtil.getInteger(request.getParameter("userId"),0);
		//获取前台传来的日期范围
		int selectType=TeeStringUtil.getInteger(request.getParameter("selectType"), 0);
		//获取开始  结束时间
		String beginDateStr=TeeStringUtil.getString(request.getParameter("beginDateStr"));
		String endDateStr=TeeStringUtil.getString(request.getParameter("endDateStr"));
		
		Calendar begin=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		
		if(selectType==1){//今日
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==2){//昨日
			begin.add(Calendar.DATE, -1);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end.add(Calendar.DATE, -1);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			
		}else if(selectType==3){//本周
			//先获取今天是周几
			int week=begin.get(Calendar.DAY_OF_WEEK);
			if(week==1){
				week=7;
			}else{
				week=week-1;
			}
			
			begin.add(Calendar.DATE, -(week-1));
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			
			end.add(Calendar.DATE, (7-week));
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==4){//本月
			//获取当前的月数
			int month=begin.get(Calendar.MONTH);
			//获取当前月的最大天数
			int maxDays=begin.getActualMaximum(Calendar.DATE);
			
			
			begin.set(Calendar.DAY_OF_MONTH,1);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			
			end.set(Calendar.DAY_OF_MONTH,maxDays);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==5){//本年
			int year=begin.get(Calendar.YEAR);
			String b=year+"-01-01";
			String e=year+"-12-31";
			
			begin=TeeDateUtil.parseCalendar(b);
			begin.set(Calendar.HOUR_OF_DAY, 0);
			begin.set(Calendar.MINUTE, 0);
			begin.set(Calendar.SECOND, 0);
			
			end=TeeDateUtil.parseCalendar(e);
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}else if(selectType==6){//指定范围
			if(!TeeUtility.isNullorEmpty(beginDateStr)){
				begin=TeeDateUtil.parseCalendar(beginDateStr);
				begin.set(Calendar.HOUR_OF_DAY, 0);
				begin.set(Calendar.MINUTE, 0);
				begin.set(Calendar.SECOND, 0);
			}
			
			if(!TeeUtility.isNullorEmpty(endDateStr)){
				end=TeeDateUtil.parseCalendar(endDateStr);
				end.set(Calendar.HOUR_OF_DAY, 23);
				end.set(Calendar.MINUTE, 59);
				end.set(Calendar.SECOND, 59);
			}
			
		}
		
		List<Object> param = new ArrayList<Object>();	
		String hql=" from TeeFootPrint pt where pt.crTime>=? and pt.crTime<=? and isCross=1 and pt.user.uuid=? ";
		param.add(begin.getTime());
		param.add(end.getTime());
		param.add(userId);
		
		// 设置总记录数
		json.setTotal(footPrintDao.countByList("select count(*) " + hql, param));		
		List<TeeFootPrint> list = footPrintDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
				
		List<TeeFootPrintModel> listModel = new ArrayList<TeeFootPrintModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
	   json.setRows(listModel);
	   
	   return json;
	}
}
