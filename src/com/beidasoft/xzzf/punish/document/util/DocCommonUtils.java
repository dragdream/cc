package com.beidasoft.xzzf.punish.document.util;

import org.apache.commons.lang3.StringUtils;

public class DocCommonUtils {

	/**
	 * 截取签章值
	 * 
	 * @param inStr
	 * @return
	 */
	public static String cut(String inStr) {
		if (StringUtils.isBlank(inStr)) {
			return "";
		}
		return StringUtils.replace(inStr, "data:image/png;base64,", "");
	}
}
