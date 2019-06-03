package com.beidasoft.zfjd.lawManage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.lawManage.bean.LawAdjustReport;
import com.beidasoft.zfjd.lawManage.dao.LawAdjustReportDao;
import com.beidasoft.zfjd.lawManage.model.LawAdjustReportModel;
import com.tianee.webframe.service.TeeBaseService;


/**
 * 法律法规调整SERVICE类
 */
@Service
public class LawAdjustReportService extends TeeBaseService {

	@Autowired
	private LawAdjustReportDao lawAdjustReportDao;
	
	/**
	 * 根据id获取数据
	 *
	 * @param String 
	 * @return LawAdjustReport
	 */
	public LawAdjustReport getById(String id) {
		return lawAdjustReportDao.get(id);
	}

	/**
	 * 保存法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public List<LawAdjustReport> listByPage(int firstResult, int rows, LawAdjustReportModel lawAdjustReportModel,
			OrgCtrlInfoModel orgCtrlInfoModel) {
		return lawAdjustReportDao.listByPage(firstResult, rows, lawAdjustReportModel, orgCtrlInfoModel);

	}

	/**
	 * 保存法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public long getTotal(LawAdjustReportModel lawAdjustReportModel, OrgCtrlInfoModel orgCtrlInfoModel) {
		return lawAdjustReportDao.getTotal(lawAdjustReportModel, orgCtrlInfoModel);
	}

	/**
	 * 保存法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public void save(LawAdjustReport lawAdjustReport) {
		try {
			lawAdjustReportDao.save(lawAdjustReport);
		} catch (Exception e) {
			System.out.println("法律法规条调整申请保存失败！");
		}
	}
	
	   /**
     * 保存法律法规调整数据
     *
     * @param beanInfo
     */
    public void deleteById(LawAdjustReport lawAdjustReport) {
        try {
            lawAdjustReportDao.delete(lawAdjustReport.getId());
        } catch (Exception e) {
            System.out.println("法律法规条调整申请保存失败！");
        }
    }
	
	/**
	 * 更新法律法规调整数据
	 *
	 * @param beanInfo
	 */
	public void updateReportInfo(LawAdjustReport lawAdjustReport) {
		try {
			lawAdjustReportDao.updateReportInfo(lawAdjustReport);
		} catch (Exception e) {
			System.out.println("法律法规条调整申请保存失败！");
		}
	}

	/**
	 * 批量提交法律法规调整申请
	 *
	 * @param beanInfo
	 */
	public void updateToSubmitByGroup(String[] submitIdGroup) {
		try {
			LawAdjustReport lawAdjustReport = new LawAdjustReport();
			lawAdjustReport.setSubmitDate(new Date());
			if (submitIdGroup != null && submitIdGroup.length > 0) {
				for (String id : submitIdGroup) {
					lawAdjustReport.setId(id);
					lawAdjustReport.setExamine(2);
					lawAdjustReportDao.updateLawReportToSubmit(lawAdjustReport);
				}
			}
			// lawAdjustReportDao.save(lawAdjustReport);
		} catch (Exception e) {
			System.out.println("法律法规条调整申请保存失败！");
		}
	}

}
