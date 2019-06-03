package com.beidasoft.xzzf.power.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beidasoft.xzzf.power.bean.BaseDiscretion;
import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.bean.BasePowerDetail;
import com.beidasoft.xzzf.power.bean.BasePowerFlowsheet;
import com.beidasoft.xzzf.power.bean.BasePowerGist;
import com.beidasoft.xzzf.power.bean.BasePowerLevel;
import com.beidasoft.xzzf.power.dao.DiscretionDao;
import com.beidasoft.xzzf.power.dao.PowerFlowSheetDao;
import com.beidasoft.xzzf.power.dao.PowerSelectDao;
import com.beidasoft.xzzf.power.dao.PowerSelectDetailDao;
import com.beidasoft.xzzf.power.dao.PowerSelectGistDao;
import com.beidasoft.xzzf.power.dao.PowerSelectLevelDao;
import com.beidasoft.xzzf.power.model.PowerSelectModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.service.TeeBaseService;


@Service
public class PowerSelectService extends TeeBaseService{
	@Autowired
	private PowerSelectDao selectDao;
	@Autowired
	private PowerSelectDetailDao detailDao;
	@Autowired
	private PowerSelectLevelDao levelDao;
	@Autowired
	private PowerFlowSheetDao sheetDao;
	@Autowired
	private PowerSelectGistDao gistDao;
	@Autowired
	private DiscretionDao cretionDao;
	
	/**
	 * 根据模糊条件分页进行查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<BasePower> listByPage(int firstResult,int rows,PowerSelectModel queryModel){
	    return	selectDao.listByPage(firstResult, rows, queryModel);
	}
	
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(String id){
	    return	selectDao.getTotal(id);
	}
	/**
	 * 模糊查询返回总记录数
	 * @return
	 */
	public  long getTotal(PowerSelectModel queryModel){
	    return	selectDao.getTotal(queryModel);
	}
	
	/**
	 * 存职权Base表
	 * @param userInfo
	 */
	public Serializable save(BasePower userInfo){
		Serializable id = selectDao.save(userInfo);
		System.out.println("职权主表"+id);
		return id;
	}
	
	/**
	 * 存职权类型到职权详细信息表
	 * @param userInfo
	 */
	public void savedetail(BasePowerDetail detail) {
		// TODO Auto-generated method stub
		detailDao.save(detail);
	}
	/**
	 * 存职权层级详细信息表
	 * @param userInfo
	 */
	public void savelevel(BasePowerLevel level) {
		// TODO Auto-generated method stub
		levelDao.save(level);
	}
	/**
	 * 存图片表
	 * @param userInfo
	 */
	public void save(BasePowerFlowsheet  sheet) {
		sheetDao.save(sheet);
	}
	
	@Transactional
	public void deleteById(String id) {
		System.out.println("删除");
		selectDao.updateByPowerId(id);
	/*	detailDao.deleteByPowerId(id);
		levelDao.deleteByPowerId(id);
		sheetDao.deleteByPowerId(id);*/
		
	}
	/**
	 * 删除detail表
	 * @param id
	 */
	public void deletedetailById(String id) {
		detailDao.deleteByPowerId(id);
		
	}
	/**
	 * 删除level表
	 */
	public void deletelevelById(String id) {
		levelDao.deleteByPowerId(id);
		
	}
	
	/**
	 * 删除flow表
	 */
	public void deletesheetById(String id) {
		sheetDao.deleteByPowerId(id);
		
	}
	/**
	 *通过主键查询用户信息
	 * @param userInfo
	 */
	public BasePower getById(String id){
		return selectDao.get(id);
	}
	
	/**
	 * 更新用户信息
	 * @param userInfo
	 */
	public void update(BasePower userInfo){
		selectDao.update(userInfo);
	}
	/**
	 * 查询职权种类详细信息
	 * @param id
	 * @return
	 */
	public List<BasePowerDetail> getByPowerId(String id) {
		
		return detailDao.getByPower(id);
	}
	/**
	 * 查询职权流程图详细信息
	 * @param id
	 * @return
	 */
	public List<BasePowerFlowsheet> sheetgetByPowerId(String id) {
		
		return sheetDao.getByPower(id);
	}
	/**
	 * 查询职权依据详细信息
	 * @param id
	 * @return  //接收C-D
	 */
	public List<BasePowerGist> getGistListById(String id,TeeDataGridModel  dataGridModel) {
		return gistDao.getByPowerList(id, dataGridModel);
	}
	
	public List<BasePowerLevel> getLevelList(String id) {
		return levelDao.getLevelList(id);
	}

	public List<BasePowerDetail> getDetalByPowerId(String id) {
		return detailDao.getDetailList(id);
	}

	/**
	 * 自由裁量信息表
	 * @param id
	 * @param dataGridModel
	 * @return
	 */
	public List<BaseDiscretion> getDiscretionListById(String id,
			TeeDataGridModel dataGridModel) {
		return cretionDao.getByPowerList(id, dataGridModel);
	}
	
	/**
	 * 查询所有的职权信息
	 * @return
	 */
	public List<BasePower> getPowerList() {
		return selectDao.getPowerList();
	}
}
	

