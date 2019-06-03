package com.tianee.oa.core.system.resource.service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeResourceService {
	
	/**
	 * 获取安装目录的资源信息
	 * @author syl
	 * @date 2013-11-18
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeJson getResource(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		try {
			String root = "D:/";
			String rootPath = TeeStringUtil.getString(TeeSysProps
					.getRootPath());
			if (!rootPath.equals("")) {
				root = rootPath.substring(0, 3);
			}

			File file = new File(root);
			long space = file.getFreeSpace();
			long container = file.getTotalSpace();
			long used = container - space;
			DecimalFormat format = new DecimalFormat("0.0");
			double usedGb = (double) used / 1024 / 1024 / 1024;
			double spaceGb = (double) space / 1024 / 1024 / 1024;
			double containerGb = (double) container / 1024 / 1024 / 1024;
			DecimalFormat formatL = new DecimalFormat("#,##0");
			Map map = new HashMap();
			map.put("space", formatL.format(space));
			map.put("container",  formatL.format(container) );
			map.put("used", formatL.format(used));
			map.put("spaceGb", formatL.format(spaceGb));
			map.put("usedGb", formatL.format(usedGb));
			map.put("containerGb", formatL.format(containerGb));
			json.setRtData(map);
			json.setRtState(true);
		} catch (Exception ex) {
			json.setRtData(ex.getMessage());
			json.setRtState(true);
			throw ex;
		}
		return json;
	}
	
}
