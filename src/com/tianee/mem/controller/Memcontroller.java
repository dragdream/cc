package com.tianee.mem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.mem.bean.Detbean;
import com.tianee.mem.bean.Membean;
import com.tianee.mem.bean.Recbean;
import com.tianee.mem.model.Detmodel;
import com.tianee.mem.model.Memmodel;
import com.tianee.mem.model.Recmodel;
import com.tianee.mem.service.Detservice;
import com.tianee.mem.service.Memservice;
import com.tianee.mem.service.Recservice;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("memController")
public class Memcontroller {
   @Autowired
   private Detservice det;
   @Autowired
   private Memservice mem;
   @Autowired
   private Recservice rec;
   /*增加模块*******************************************************/
	@ResponseBody
	@RequestMapping("savemem")
	public TeeJson savemem(Memmodel memm) {
		Membean memb=new Membean();
		TeeJson json = new TeeJson();
		BeanUtils.copyProperties(memm, memb);
		memb.setGet_time(TeeDateUtil.format(memm.getGet_timestr(),"yyyy-MM-dd"));
		memb.setSeize_time(TeeDateUtil.format(memm.getSeize_timestr(),"yyyy-MM-dd"));
		
		mem.save(memb);
		json.setRtState(true);
		return json;
	}

	@ResponseBody
	@RequestMapping("savedet")
	public TeeJson savedet(Detmodel memm) {
		TeeJson json = new TeeJson();
		Detbean memb=new Detbean();
		BeanUtils.copyProperties(memm, memb);
		det.save(memb);
		json.setRtState(true);
		return json;
	}

	@ResponseBody
	@RequestMapping("saverec")
	public TeeJson saverec(Recmodel recm) {
		TeeJson json = new TeeJson();
		Recbean recbean=new Recbean();
		BeanUtils.copyProperties(recm, recbean);
		recbean.setHandle_time(TeeDateUtil.format(recm.getHandle_timestr(),"yyyy-MM-dd"));	
		rec.save(recbean);
		json.setRtState(true);
		return json;
	}
	/*增加模块*******************************************************/
	/*删除模块*******************************************************/
	@ResponseBody
	@RequestMapping("delmemid")
	public TeeJson delmem(int id) {
		TeeJson json = new TeeJson();
		mem.del(id);
		json.setRtState(true);
		return json;	
	}
	@ResponseBody
	@RequestMapping("delrecid")
	public TeeJson delrec(int id) {
		TeeJson json = new TeeJson();
		json.setRtState(true);
		return json;	
	}
	@ResponseBody
	@RequestMapping("deldetid")
	public TeeJson deldet(int id) {
		TeeJson json = new TeeJson();
		det.del(id);
		json.setRtState(true);
		return json;	
	}
	/*修改模块***************************************************************/
	@ResponseBody
	@RequestMapping("getbyid")
	public TeeJson get(int id) {
		Membean eleinfo=mem.getbyid(id);
		TeeJson json = new TeeJson();
		Memmodel elvmodel=new Memmodel();
		BeanUtils.copyProperties(eleinfo, elvmodel);	
		elvmodel.setGet_timestr(TeeDateUtil.format(eleinfo.getGet_time(), "yyyy-MM-dd"));
		elvmodel.setSeize_timestr(TeeDateUtil.format(eleinfo.getSeize_time(),"yyyy-MM-dd"));

		json.setRtData(elvmodel);
		
		return json;
	}
	@ResponseBody
	@RequestMapping("getdetbyid")
	public TeeJson getdet(int id) {
		Detbean eleinfo=det.getbyid(id);
		TeeJson json = new TeeJson();
		Detmodel elvmodel=new Detmodel();
		BeanUtils.copyProperties(eleinfo, elvmodel);	
		json.setRtData(elvmodel);
		
		return json;
	}
	@ResponseBody
	@RequestMapping("getrecbyid")
	public TeeJson getrec(int id) {
		Recbean eleinfo=rec.getbyid(id);
		TeeJson json = new TeeJson();
		Recmodel elvmodel=new Recmodel();
		BeanUtils.copyProperties(eleinfo, elvmodel);	
		elvmodel.setHandle_timestr(TeeDateUtil.format(eleinfo.getHandle_time(),"yyyy-MM-dd"));
		json.setRtData(elvmodel);
		
		return json;
	}
	@ResponseBody
	@RequestMapping("updatemem")
	public TeeJson updatemem(Memmodel memm) {
		TeeJson json = new TeeJson();
		Membean eleinfo=mem.getbyid(memm.getId());
		BeanUtils.copyProperties(memm,eleinfo);// 两个对象类型有些字段类型不一样，不能复制
		eleinfo.setGet_time(TeeDateUtil.format(memm.getGet_timestr(),"yyyy-MM-dd"));
		eleinfo.setSeize_time(TeeDateUtil.format(memm.getSeize_timestr(),"yyyy-MM-dd"));
		mem.update(eleinfo);
		json.setRtState(true);
		return json;
		}
	@ResponseBody
	@RequestMapping("updatedet")
	public TeeJson updatedet(Detmodel det1) {
		TeeJson json = new TeeJson();
		Detbean eleinfo=det.getbyid(det1.getId());
		BeanUtils.copyProperties(det1,eleinfo);
		det.update(eleinfo);
		json.setRtState(true);
		return json;
		}
	@ResponseBody
	@RequestMapping("updaterec")
	public TeeJson updaterec(Recmodel rec1) {
		TeeJson json = new TeeJson();
		Recbean eleinfo=rec.getbyid(rec1.getId());
		BeanUtils.copyProperties(rec1,eleinfo);
		eleinfo.setHandle_time(TeeDateUtil.format(rec1.getHandle_timestr(),"yyyy-MM-dd"));
		rec.update(eleinfo);
		json.setRtState(true);
		return json;
		}
	/*展示查询***********************************************/
	@ResponseBody
	@RequestMapping("gridmem")
	public TeeEasyuiDataGridJson listbypagemem(TeeDataGridModel  dataGridModel,Memmodel searchModel) {//注意这里是easy 专用格式 TeeDataGridModelk 空间传递的参数
		TeeEasyuiDataGridJson DataGridJson =new TeeEasyuiDataGridJson();//专用返回格式
		List<Memmodel> memmodel=new ArrayList<Memmodel>();
		long total=mem.gettotal(searchModel);
		List<Membean> membean=mem.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),searchModel);
		for (Membean membeans : membean) {
			Memmodel elvmodel=new Memmodel();
			BeanUtils.copyProperties(membeans, elvmodel);
			elvmodel.setGet_timestr(TeeDateUtil.format(membeans.getGet_time(),"yyyy-MM-dd"));
			elvmodel.setSeize_timestr(TeeDateUtil.format(membeans.getSeize_time(),"yyyy-MM-dd"));
			memmodel.add(elvmodel);
		}
		DataGridJson.setTotal(total);
		DataGridJson.setRows(memmodel);
		return DataGridJson;
	}
	@ResponseBody
	@RequestMapping("griddet")
	public TeeEasyuiDataGridJson listbypagedet(TeeDataGridModel  dataGridModel,@RequestParam int sid) {//注意这里是easy 专用格式 TeeDataGridModelk 空间传递的参数		
		TeeEasyuiDataGridJson DataGridJson =new TeeEasyuiDataGridJson();//专用返回格式
		List<Detmodel> detmodellist=new ArrayList<Detmodel>();
		long total=det.gettotal(sid);
		List<Detbean> detbean=det.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),sid);
		for (Detbean detbean2 : detbean) {
			Detmodel detmodel=new Detmodel();
			BeanUtils.copyProperties(detbean2, detmodel);
			detmodellist.add(detmodel);	
		}
		DataGridJson.setTotal(total);
		DataGridJson.setRows(detmodellist);
		return DataGridJson;
	}
	@ResponseBody
	@RequestMapping("gridrec")
	public TeeEasyuiDataGridJson listbypagerec(TeeDataGridModel  dataGridModel,@RequestParam int sid) {//注意这里是easy 专用格式 TeeDataGridModelk 空间传递的参数		
		TeeEasyuiDataGridJson DataGridJson =new TeeEasyuiDataGridJson();//专用返回格式
		List<Recmodel> recmodellist=new ArrayList<Recmodel>();
		long total=rec.gettotal(sid);
		List<Recbean> recbean=rec.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),sid);
		for (Recbean recb : recbean) {
			Recmodel recmodel=new Recmodel();
			BeanUtils.copyProperties(recb, recmodel);
			recmodel.setHandle_timestr(TeeDateUtil.format(recb.getHandle_time(),"yyyy-MM-dd"));
			recmodellist.add(recmodel);	
		}
		DataGridJson.setTotal(total);
		DataGridJson.setRows(recmodellist);
		return DataGridJson;
	}
}
