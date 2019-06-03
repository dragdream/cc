package com.tianee.oa.subsys.cms.core;

import java.util.HashMap;
import java.util.Map;

import com.tianee.oa.subsys.cms.core.directive.CmsAttachDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsChannelDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsChannelsDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsDocumentDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsDocumentsDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsLocationDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsNavigatorDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsOutDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsPagerDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsRootPathDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsSiteDirective;
import com.tianee.oa.subsys.cms.core.directive.CmsSqlDirective;

public final class CmsTemplateUtil {
	public static Map getInstance(){
		Map map = new HashMap();
		map.put("Site", new CmsSiteDirective());
		map.put("Out", new CmsOutDirective());
		map.put("Channel", new CmsChannelDirective());
		map.put("Channels", new CmsChannelsDirective());
		map.put("Document", new CmsDocumentDirective());
		map.put("Documents", new CmsDocumentsDirective());
		map.put("Rootpath", new CmsRootPathDirective());
		map.put("Pager", new CmsPagerDirective());
		map.put("Attach", new CmsAttachDirective());
		map.put("Location", new CmsLocationDirective());
		map.put("Sql", new CmsSqlDirective());
		map.put("Navigator", new CmsNavigatorDirective());
		return map;
	}
}
