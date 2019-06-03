package com.tianee.thirdparty.wenshu.plugins;

import java.util.Map;

import com.jacob.req.JacobRequest;
import com.jacob.req.JacobResponse;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 听证公告 (21)
 * */
public class TZGGPlugin extends TeeWenShuPlugin{

	@Override
	public String process(JacobRequest jacobRequest, String wordId,
			Map<String, String> data) {
//		data.put("页眉", "编码：222222222");
//		//文号文
//		data.put("文号文", "001");
//		//文号年
//		data.put("文号年", "2018");
//		//文号
//		data.put("文号", "1");
//		//当事人名称
//		data.put("当事人名称", "王小明");
//		//当事人名称1
//		data.put("当事人名称1", "小米");
//		//听证事由
//		data.put("听证事由", "辩论");
//		//听证时间
//		data.put("听证时间", "2018年10月16日 08时30分");
//		//听证地点
//		data.put("听证地点", "北京海淀区");
//		//行政机关落款
//		data.put("行政机关落款", "中国人民法院");
//		//盖章时间
//		data.put("盖章时间", "2018年10月16日");
		
		//套入书签
		JacobResponse response = jacobRequest.replaceBookmarks(wordId, data);
		String newWordId = response.fileId;//获取套完书签后的wordId
		return newWordId;
	}

}
