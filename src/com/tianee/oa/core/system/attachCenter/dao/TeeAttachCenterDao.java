package com.tianee.oa.core.system.attachCenter.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeAttachCenterDao extends TeeBaseDao<TeeAttachment> {

	/**
	 * @function: 获取系统附件模块名称、数量
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getAttachModelCountList(String model) {
		// return 
		// executeNativeQuery("SELECT model as MODEL,count(*) as C FROM attachment group by model having model in(?)",
		// new Object[]{model},0,Integer.MAX_VALUE);
		return executeNativeQuery("SELECT model as modelName,count(*) as modelCount FROM attachment group by model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

	/**
	 * @function: 获取每个附件模块有效文件名称、数量
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getValidModelCountList() {
		return executeNativeQuery("SELECT model as modelName,count(*) as modelCount FROM attachment WHERE model_id is not null GROUP BY model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

	/**
	 * @function: 获取每个附件模块无效文件名称、数量
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getInValidModelCountList() {
		return executeNativeQuery("SELECT model as modelName,count(*) as modelCount FROM attachment WHERE model_id is null GROUP BY model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

	/**
	 * @function: 获取每个附件模块文件名称、总文件大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getAttachModelSizeList() {
		return executeNativeQuery("SELECT model as modelName,SUM(attach_size) AS fileSize  FROM attachment  GROUP BY model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

	/**
	 * @function: 获取每个附件模块有效文件名称、总文件大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getValidModelSizeList() {
		return executeNativeQuery("SELECT model as modelName,SUM(attach_size) AS fileSize  FROM attachment WHERE  model_id IS NOT NULL  GROUP BY model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

	/**
	 * @function: 获取每个附件模块无效文件名称、总文件大小
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @param model
	 * @return List<Map>
	 */
	public List<Map> getInValidModelSizeList() {
		return executeNativeQuery("SELECT model as modelName,SUM(attach_size) AS fileSize  FROM attachment WHERE  model_id IS  NULL  GROUP BY model", new Object[] {}, 0, Integer.MAX_VALUE);
	}

}
