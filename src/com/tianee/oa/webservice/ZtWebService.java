package com.tianee.oa.webservice;

import com.tianee.oa.webservice.model.Attachment;

public interface ZtWebService {
	
	/**
	 * 上传附件
	 * @param attach 附件对象
	 * @return
	 */
	public Attachment Attachment_Upload(Attachment attach);
	
	/**
	 * 验证机器码
	 * @param encodedMachineCode 加密后的机器码
	 * @return
	 */
	public boolean System_AuthMachineCode(String encodedMachineCode);
}
