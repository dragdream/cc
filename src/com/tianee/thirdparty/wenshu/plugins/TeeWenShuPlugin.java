package com.tianee.thirdparty.wenshu.plugins;

import java.util.Map;

import com.jacob.req.JacobRequest;

public abstract class TeeWenShuPlugin {
	
	public abstract String process(JacobRequest jacobRequest,String wordId,Map<String,String> data);

}
