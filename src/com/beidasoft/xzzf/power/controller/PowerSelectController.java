package com.beidasoft.xzzf.power.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.common.bean.CodeDetail;
import com.beidasoft.xzzf.common.service.CodeService;
import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.bean.BasePowerDetail;
import com.beidasoft.xzzf.power.bean.BasePowerFlowsheet;
import com.beidasoft.xzzf.power.bean.BasePowerGist;
import com.beidasoft.xzzf.power.bean.BasePowerLevel;
import com.beidasoft.xzzf.power.model.PowerSelectModel;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("powerSelectController")
public class PowerSelectController {

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private PowerSelectService selectservice;

	/**
	 * 查询分页
	 * @param dataGridModel
	 * @param selectmodel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listBypage")
	public TeeEasyuiDataGridJson listBypage(TeeDataGridModel dataGridModel,
			PowerSelectModel selectmodel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		long total = selectservice.getTotal(selectmodel); // 根据传来的条件 （有或无） 查询总数量
		// 通过分页获取用户信息数据的list集合
		List<BasePower> basePowers = selectservice.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				selectmodel);
		List<PowerSelectModel> modelList = new ArrayList<PowerSelectModel>();
		for (BasePower basePower : basePowers) {
			String str = "";
			PowerSelectModel powermodel = new PowerSelectModel();
			List<BasePowerDetail> details = selectservice
					.getByPowerId(basePower.getId());
			if (details.size() > 0) {
				for (BasePowerDetail basePowerDetail : details) {
					str += basePowerDetail.getName() + ",";
				}
				str = str.substring(0, str.length() - 1);
				BeanUtils.copyProperties(basePower, powermodel);
				powermodel.setDetailName(str);
				modelList.add(powermodel);
			}
		}
		dataGridJson.setTotal(total);// 赋值
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	/**
	 * 保存
	 * @param selectmodel
	 * @param detailcode
	 * @param SJremark
	 * @param QJremark
	 * @param XZremark
	 * @param attachs
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	@Transactional
	public TeeJson save(PowerSelectModel selectmodel,
			@RequestParam("detailcode") String detailcode,
			@RequestParam("SJremark") String SJremark,
			@RequestParam("QJremark") String QJremark,
			@RequestParam("XZremark") String XZremark,
			@RequestParam("attachs") String attachs) {
		TeeJson json = new TeeJson();
		// 实例化实体类对象
		BasePower userInfo = new BasePower();
		// 实体类接收属性 ，将属性copy到 hibernate映射的model实体类
		selectmodel.setPowerLevel(selectmodel.getPowerLevel());
		BeanUtils.copyProperties(selectmodel, userInfo);
		userInfo.setCreateDate(TeeDateUtil.format(
				selectmodel.getCreateDateStr(), "yyyy年MM月dd日"));
		userInfo.setDeleteDate(TeeDateUtil.format(
				selectmodel.getDeleteDateStr(), "yyyy年MM月dd日"));
		Serializable id = (Serializable) selectservice.save(userInfo); // 主表id
		// 存职权类型，存到职权类型表 detail
		BasePowerDetail detail = new BasePowerDetail(); // 存detail表
		String[] split = detailcode.split(",");
		for (int i = 0; i < split.length; i++) {
			detail = new BasePowerDetail();
			String models = null;
			models = selectmodel.getPowerType();
			if (models != null && (!models.equals(""))) {
				if (models.equals("010000")) {
					detail.setName("行政处罚");
				}
			}
			detail.setPowerId((String) id); // 主表id存到职权类型表的POWER_ID
			System.out.println(split[i]);
			detail.setCode(split[i]);
			selectservice.savedetail(detail);
		}

		// 存职权层级，存到职权层级表level
		BasePowerLevel level = new BasePowerLevel();
		level.setPowerId((String) id);
		String address = null;
		address = userInfo.getPowerLevel();
		if (address != null && (!address.equals(""))) {
			if (address.equals("市级")) {
				level.setPowerLevel("1");
				level.setRemark(SJremark);
			} else if (address.equals("区级")) {
				level.setPowerLevel("3");
				level.setRemark(QJremark);
			} else if (address.equals("乡镇、街道")) {
				level.setPowerLevel("5");
				level.setRemark(XZremark);
			} else if (address.contains("市级") && address.contains("乡镇、街道")
					&& address.contains("区级")) {
				level.setPowerLevel("9");
				level.setRemark(SJremark + "," + QJremark + "," + XZremark);
			} else if (address.contains("市级") && address.contains("区级")) {
				level.setPowerLevel("4");
				level.setRemark(SJremark + "," + QJremark);
			} else if (address.contains("市级") && address.contains("乡镇、街道")) {
				level.setPowerLevel("6");
				level.setRemark(SJremark + "," + XZremark);
			} else if (address.contains("区级") && address.contains("乡镇、街道")) {
				level.setPowerLevel("8");
				level.setRemark(QJremark + "," + XZremark);
			}
		}
		selectservice.savelevel(level);
		// 处理附件,存到职权图片表
		if (attachs != null && attachs.length() != 0) {
			String sp[] = attachs.split(",");
			for (String attachsId : sp) {
				TeeAttachment attachment = attachmentService.getById(Integer
						.parseInt(attachsId));
				attachment.setModelId((Integer) id + ""); // 存职权id
				attachmentService.updateAttachment(attachment);

				BasePowerFlowsheet sheet = new BasePowerFlowsheet();
				sheet.setPowerId((String) id); // powerId
				sheet.setFileName(attachment.getFileName()); // 流程图名
				String url = "";
				url = url + "D:/" + "attach/" + attachment.getModel() + "/"
						+ attachment.getAttachmentPath() + "/"
						+ attachment.getAttachmentName();
				sheet.setFlowPicturePath(url); // 流程图路径
				selectservice.save(sheet);
			}
		}

		json.setRtState(true);
		return json;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(String id) {
		TeeJson json = new TeeJson();
		selectservice.deleteById(id);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取gistDataGrid List
	 * @param id
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getGistDataGrid")
	public TeeEasyuiDataGridJson getGistDataGrid(
			TeeDataGridModel dataGridModel, PowerSelectModel powerModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<BasePowerGist> GistList = selectservice.getGistListById(
				powerModel.getId(), dataGridModel);
		long total = selectservice.getTotal(powerModel.getId()); // 根据传来的条件
		dataGridJson.setRows(GistList);
		dataGridJson.setTotal(total);

		return dataGridJson;
	}

	/**
	 * 获取gistDataGrid List
	 * @param id
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDiscretionDataGrid")
	public TeeEasyuiDataGridJson getDiscretionDataGrid(
			TeeDataGridModel dataGridModel, PowerSelectModel powerModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<BaseDiscretion> DiscretionList = selectservice
				.getDiscretionListById(powerModel.getId(), dataGridModel);
		long total = selectservice.getTotal(powerModel.getId()); // 根据传来的条件
		dataGridJson.setRows(DiscretionList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	/**
	 * 获取id为xxx的信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		BasePower power = selectservice.getById(id);
		List<BasePowerDetail> powerDetail = selectservice.getDetalByPowerId(id);
		PowerSelectModel powerModel = new PowerSelectModel();
		BeanUtils.copyProperties(power, powerModel); // 实体类属性值传给model
		List<TeeAttachmentModel> attachments = attachmentService.getAttacheModels("power", id + "");
		// 单独处理时间
		powerModel.setCreateDateStr(TeeDateUtil.format(power.getCreateDate(),"yyyy年MM月dd日"));
		powerModel.setDeleteDateStr(TeeDateUtil.format(power.getDeleteDate(),"yyyy年MM月dd日"));
		if (attachments.size() > 0) {
			powerModel.setAttachments(attachments);
		}
		if (powerDetail.size() > 0) {
			powerModel.setPowerDetail(powerDetail);
		}
		json.setRtData(powerModel);
		json.setRtState(true);
		return json;

	}

	@ResponseBody
	@RequestMapping("update")
	@Transactional
	public TeeJson update(PowerSelectModel userInfoModel,
			@RequestParam("detailcode") String detailcode,
			@RequestParam("SJremark") String SJremark,
			@RequestParam("QJremark") String QJremark,
			@RequestParam("XZremark") String XZremark,
			@RequestParam("attachs") String attachs) {
		TeeJson json = new TeeJson();
		BasePower userInfo = selectservice.getById(userInfoModel.getId());

		org.springframework.beans.BeanUtils.copyProperties(userInfoModel,
				userInfo);
		userInfo.setCreateDate(TeeDateUtil.format(
				userInfoModel.getCreateDateStr(), "yyyy年MM月dd日"));
		userInfo.setDeleteDate(TeeDateUtil.format(
				userInfoModel.getDeleteDateStr(), "yyyy年MM月dd日"));
		selectservice.update(userInfo);

		// 存职权类型，存到职权类型表 detail
		// 先删除
		selectservice.deletedetailById(userInfo.getId());
		BasePowerDetail detail = new BasePowerDetail(); // 存detail表
		String[] split = detailcode.split(",");
		for (int i = 0; i < split.length; i++) {
			detail = new BasePowerDetail();
			if (userInfo.getPowerType().equals("010000")) {
				detail.setName("行政处罚");
			}
			detail.setPowerId(userInfo.getId()); // 主表id存到职权类型表的POWER_ID
			// System.out.println(split[i]);
			detail.setCode(split[i]);
			selectservice.savedetail(detail);
		}
		// 存职权层级，存到职权层级表level
		// 先删除
		selectservice.deletelevelById(userInfo.getId());
		BasePowerLevel level = new BasePowerLevel();
		level.setPowerId(userInfo.getId());
		String addresss = null;
		addresss = userInfo.getPowerLevel();
		if (addresss != null && (!addresss.equals(""))) {
			if (addresss.equals("市级")) {
				level.setPowerLevel("1");
				level.setRemark(SJremark);
			} else if (addresss.equals("区级")) {
				level.setPowerLevel("3");
				level.setRemark(QJremark);
			} else if (addresss.equals("乡镇、街道")) {
				level.setPowerLevel("5");
				level.setRemark(XZremark);
			} else if (addresss.contains("市级") && addresss.contains("乡镇、街道")
					&& addresss.contains("区级")) {
				level.setPowerLevel("9");
				level.setRemark(SJremark + "," + QJremark + "," + XZremark);
			} else if (addresss.contains("市级") && addresss.contains("区级")) {
				level.setPowerLevel("4");
				level.setRemark(SJremark + "," + QJremark);
			} else if (addresss.contains("市级") && addresss.contains("乡镇、街道")) {
				level.setPowerLevel("6");
				level.setRemark(SJremark + "," + XZremark);
			} else if (addresss.contains("区级") && addresss.contains("乡镇、街道")) {
				level.setPowerLevel("8");
				level.setRemark(QJremark + "," + XZremark);
			}
		}

		selectservice.savelevel(level);

		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询分页
	 * @param dataGridModel
	 * @param selectmodel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectPower")
	public TeeEasyuiDataGridJson selectPower(TeeDataGridModel dataGridModel,
			PowerSelectModel selectmodel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		long total = selectservice.getTotal(selectmodel); // 根据传来的条件 （有或无） 查询总数量
		// 通过分页获取用户信息数据的list集合
		List<BasePower> basePowers = selectservice.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(), selectmodel);
		List<PowerSelectModel> modelList = new ArrayList<PowerSelectModel>();
		List<Map<String, Object>> maps = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("ZF_INSPECTION_ORG_TYPE");
		PowerSelectModel powermodel = null;
		for (BasePower basePower : basePowers) {
			powermodel = new PowerSelectModel();
			for(int i = 0; i < maps.size(); i++) {
				if(basePower.getPowerMold() != null && maps.get(i).get("codeNo").equals(basePower.getPowerMold())) {
					basePower.setPowerMold(maps.get(i).get("codeNo").toString());
				}
			}
			
			BeanUtils.copyProperties(basePower, powermodel);
			modelList.add(powermodel);
		}
		dataGridJson.setTotal(total);// 赋值
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
	
}
