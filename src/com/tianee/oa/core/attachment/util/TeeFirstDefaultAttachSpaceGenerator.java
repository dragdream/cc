package com.tianee.oa.core.attachment.util;

import com.tianee.oa.core.attachment.bean.TeeAttachmentSpace;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 默认根据第一个附件空间生成策略
 * @author kakalion
 *
 */
public class TeeFirstDefaultAttachSpaceGenerator extends TeeAttachSpaceGenerator{

	public TeeFirstDefaultAttachSpaceGenerator(
			TeeSimpleDaoSupport simpleDaoSupport) {
		super(simpleDaoSupport);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TeeAttachmentSpace generate() {
		//获取默认附件控件
		int defaultAttachSpace = TeeSysProps.getInt("DEFAULT_ATTACHSPACE");
		TeeAttachmentSpace attachSpace = (TeeAttachmentSpace) simpleDaoSupport.load(TeeAttachmentSpace.class,defaultAttachSpace);
		return attachSpace;
	}

}
