package com.tianee.oa.subsys.cms.core.directive;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.tianee.oa.subsys.cms.bean.ChannelInfo;
import com.tianee.oa.subsys.cms.model.SiteTemplateModel;
import com.tianee.oa.subsys.cms.service.CmsChannelService;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.cms.service.CmsSiteTemplateService;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class CmsSqlDirective implements TemplateDirectiveModel{
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String exec = TeeStringUtil.getString(params.get("exec"));//SQL执行语句
		int top= TeeStringUtil.getInteger(params.get("top"),40000000);//分页大小
		int ds = TeeStringUtil.getInteger(params.get("ds"),0);//数据源ID
		
		
		Map loopObj = (Map) env.getCustomAttribute("loopObj");//上层循环变量
		
		Writer writer = env.getOut();
		
		Connection dbConn = null;
		try{
			if(ds==0){//默认数据源
				dbConn = TeeDbUtility.getConnection();
			}else{//指定数据源
				Connection sysConn = null;
				Map<String,Object> dsMap = null;
				try{
					sysConn = TeeDbUtility.getConnection();
					DbUtils dbUtils = new DbUtils(sysConn);
					dsMap = dbUtils.queryToMap("select * from bis_data_source where sid="+ds);
					
					if(dsMap==null){
						throw new TeeOperationException("找不到指定数据源："+ds);
					}
					
					if("1".equals(dsMap.get("DATA_SOURCE")+"")){//内部数据源
						dbConn = TeeDbUtility.getConnection();
					}else{
						TeeDataSource dataSource = new TeeDataSource();
						dataSource.setConfigModel(TeeStringUtil.getString(dsMap.get("CONFIG_MODEL")));
						dataSource.setDriverClass(dsMap.get("DRIVER_CLASS")+"");
						dataSource.setPassWord(dsMap.get("DRIVER_PWD")+"");
						dataSource.setUrl(dsMap.get("DRIVER_URL")+"");
						dataSource.setUserName(dsMap.get("DRIVER_USERNAME")+"");
						
						dbConn = TeeDbUtility.getConnection(dataSource);
					}
					
					
					
				}catch(Exception ex){
					throw ex;
				}finally{
					TeeDbUtility.closeConn(sysConn);
				}
			}
			
			DbUtils dbUtils = new DbUtils(dbConn);
			List<Map> list = dbUtils.queryToMapList(exec, null, 0,top);
			if(list!=null){
				for(Map data:list){
					env.setCustomAttribute("loopObj", data);
					if(body!=null){
						body.render(writer);
					}
					env.setCustomAttribute("loopObj", loopObj);
				}
			}
			
		}catch(Exception ex){
			writer.write(ex.getMessage());
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
	}

}
