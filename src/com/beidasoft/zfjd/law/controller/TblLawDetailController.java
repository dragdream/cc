package com.beidasoft.zfjd.law.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.model.TblLawDetailModel;
import com.beidasoft.zfjd.law.service.TblLawDetailService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 职权基础信息表CONTROLLER类
 */
@Controller
@RequestMapping("detailController")
public class TblLawDetailController {

    @Autowired
    private TblLawDetailService detailService;

    /**
     * 保存职权基础信息表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
	@RequestMapping("save")
	public TeeJson save(TblLawDetailModel detailModel){
		TeeJson json = new TeeJson();

		//创建实例化实体类对象
		TblLawDetail department = new TblLawDetail();
		BeanUtils.copyProperties(detailModel, department);
		
		department.setId(UUID.randomUUID().toString());
		detailService.save(department);
		
		json.setRtState(true);
		
		return json;
	}

	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(TblLawDetailModel detailModel){

		TeeJson json = new TeeJson();
		
		TblLawDetail userInfo = detailService.getById(detailModel.getId());
		BeanUtils.copyProperties(detailModel, userInfo);
		detailService.update(userInfo);
		json.setRtState(true);
		
		return json;	}

	@ResponseBody
	@RequestMapping("delete")
	public TeeJson dalete(String id){
		
		TeeJson json = new TeeJson();
		detailService.deleteById(id);
		
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
		TeeJson json = new TeeJson();
		TblLawDetail lawDetail = detailService.getById(id);
		
		TblLawDetailModel lawDetailModel = new TblLawDetailModel();
		BeanUtils.copyProperties(lawDetail, lawDetailModel);
		
		
		lawDetailModel.setDetailSeries(lawDetail.getDetailSeries());
		lawDetailModel.setDetailChapter(lawDetail.getDetailChapter());
		lawDetailModel.setDetailStrip(lawDetail.getDetailStrip());
		lawDetailModel.setDetailItem(lawDetail.getDetailItem());
		lawDetailModel.setContent(lawDetail.getContent());
		lawDetailModel.setDetailCatalog(lawDetail.getDetailCatalog());
		

		
		json.setRtData(lawDetailModel);
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("findAllUsers")
	public TeeJson findAllUsers(){
		return null;
	}
	//导入Excel
	@ResponseBody
	@RequestMapping("importLaw")
	public TeeJson importUser(String id,HttpServletRequest request) throws IOException{
		TblLawDetail department = new TblLawDetail();
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile file =  multipartHttpServletRequest.getFile("importDeptFile");
		List<List<Object>> law = ImportCourseExcel.readExcel(file.getInputStream(),file.getOriginalFilename());
		
		TeeJson json = new TeeJson();
		
		//Excel表格第二行开始是正文
		for (int i = 2; i < law.size()-1; i++) {
			department = new TblLawDetail();
			//System.out.println(law.get(i));
			String strSplit=law.get(i).toString();
			
			String name=strSplit.replaceAll("\\s*", "").split(",")[0];
			name = name.substring(1);
			String content=strSplit.replaceAll("\\s*", "").split(",")[6];
			Integer series=Integer.parseInt(strSplit.replaceAll("\\s*", "").split(",")[7]);
			Integer chapter=Integer.parseInt(strSplit.replaceAll("\\s*", "").split(",")[8]);
			Integer strip=Integer.parseInt(strSplit.replaceAll("\\s*", "").split(",")[9]);
			Integer item=Integer.parseInt(strSplit.replaceAll("\\s*", "").split(",")[10]);
			String cata = strSplit.replaceAll("\\s*", "").split(",")[11];
			cata = cata.substring(0, cata.length()-1);
			Integer catalog=Integer.parseInt(cata);
			
			department.setLawId(id);
			department.setId(UUID.randomUUID().toString());
			department.setLawName(name);
			department.setContent(content);
			department.setDetailSeries(series);
			department.setDetailChapter(chapter);
			department.setDetailStrip(strip);
			department.setDetailItem(item);
			department.setDetailCatalog(catalog);
			
			detailService.save(department);
			
		}
		
		
		json.setRtState(true);
		return json;
	}
	
	
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TblLawDetailModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

		long total = detailService.getTotal(queryModel);
		List<TblLawDetailModel> modelList = new ArrayList<TblLawDetailModel>();
		List<TblLawDetail> lawInfos = detailService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		for (TblLawDetail userInfo : lawInfos) {
			TblLawDetailModel infoModel = new TblLawDetailModel();
			BeanUtils.copyProperties(userInfo,infoModel);
			
//			infoModel.setBirthdayStr(TeeDateUtil.format(userInfo),
//					"yyyy-MM-dd"));
//			infoModel.setDeptId(userInfo.getDepartment().getUuid());
//			infoModel.setDeptName(userInfo.getDepartment().getDeptName());
			modelList.add(infoModel);

		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;

	}
	
	@ResponseBody
    @RequestMapping("getLawDetailByIds")
    public TeeEasyuiDataGridJson getLawDetailByIds(TblLawDetailModel lawDetailModel) {
        TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();
        if(lawDetailModel.getIds() != null && !"".equals(lawDetailModel.getIds())) {
            lawDetailModel.setIds("'" + lawDetailModel.getIds().replace(",", "','") + "'");
        }
        
        List<TblLawDetail> details = detailService.getLawDetailByIds(lawDetailModel);
        gridJson.setRows(details);
        
        return gridJson;
    }
}
