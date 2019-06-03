package com.tianee.thirdparty.wenshu.plugins;

import java.util.Map;

import com.jacob.req.JacobRequest;
import com.jacob.req.JacobResponse;
//调查询问通知书
public class DCXWTZSPlugin extends TeeWenShuPlugin{

	@Override
	public String process(JacobRequest jacobRequest, String wordId,
			Map<String, String> data) {
		/*data.put("页眉", "编号：444444");
		
		data.put("当事人姓名", "王小明");
		data.put("行为内容", "共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为共享单车随意停放的行为");
		data.put("地址", "北京市文化执法大队");
		data.put("材料", "身份证  居住证");
		data.put("联系人", "李军");
		data.put("联系电话", "18810480899");
		data.put("行政机关落款", "北京市文化执法总队");
		data.put("通知下发时间", "2018年10月16日");
		data.put("时间年", "2018");
		data.put("时间月", "10");
		data.put("时间日", "16");
		data.put("时间时", "10");*/
		
		
		//套入书签
		JacobResponse response = jacobRequest.replaceBookmarks(wordId, data);
		String newWordId = response.fileId;//获取套完书签后的wordId
		
		
		return newWordId;
	}

}
