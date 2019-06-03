package com.tianee.oa.core.attachment.util;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 附件空间生成器
 * @author kakalion
 *
 */
public abstract class TeeAttachSpaceGenerator{
	protected TeeSimpleDaoSupport simpleDaoSupport;
	
	public TeeAttachSpaceGenerator(TeeSimpleDaoSupport simpleDaoSupport){
		this.simpleDaoSupport = simpleDaoSupport;
	}
	
	/**
	 * 生成
	 * @return
	 */
	public abstract TeeAttachmentSpace generate();
	
	/**
	 * 获取实例
	 * @param simpleDaoSupport
	 * @return
	 */
	public static TeeAttachSpaceGenerator getInstance(TeeSimpleDaoSupport simpleDaoSupport){
		TeeAttachSpaceGenerator attachSpaceGenerator = null;
		if(true){
			attachSpaceGenerator = new TeeFirstDefaultAttachSpaceGenerator(simpleDaoSupport);
		}
		return attachSpaceGenerator;
	}
	
}
