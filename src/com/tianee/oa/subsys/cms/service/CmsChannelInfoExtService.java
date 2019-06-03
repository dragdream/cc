package com.tianee.oa.subsys.cms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.subsys.cms.bean.ChannelInfoExt;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class CmsChannelInfoExtService extends TeeBaseService{
	
	public void addOrUpdate(ChannelInfoExt channelInfoExt){
		channelInfoExt.setFieldName(channelInfoExt.getFieldName().toUpperCase());
		if(channelInfoExt.getSid()==0){
			String dialect = TeeSysProps.getString("dialect");
			String sql = "alter table cms_channel_info ";
			
			if("mysql".equals(dialect.toLowerCase())){
				sql+=" add column "+channelInfoExt.getFieldName().toUpperCase()+" "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
			}else if("oracle".equals(dialect.toLowerCase())){
				sql+=" add  "+channelInfoExt.getFieldName().toUpperCase()+" "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
			}else if("sqlserver".equals(dialect.toLowerCase())){
				sql+=" add  "+channelInfoExt.getFieldName().toUpperCase()+" "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
			}else if("kingbase".equals(dialect.toLowerCase())){
				sql+=" add  "+channelInfoExt.getFieldName().toUpperCase()+" "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
			}else if("dameng".equals(dialect.toLowerCase())){
				sql+=" add  "+channelInfoExt.getFieldName().toUpperCase()+" "+TeeColumnType.getColumnType(TeeColumnType.VARCHAR);
			}
			simpleDaoSupport.executeNativeUpdate(sql, null);
		}
		
		simpleDaoSupport.saveOrUpdate(channelInfoExt);
	}
	
	public void remove(int sid){
		ChannelInfoExt channelInfoExt = get(sid);
		String dialect = TeeSysProps.getString("dialect");
		String sql = "alter table cms_channel_info ";
		
		if("mysql".equals(dialect.toLowerCase())){
			sql+=" drop column "+channelInfoExt.getFieldName().toUpperCase();
		}else if("oracle".equals(dialect.toLowerCase())){
			sql+=" drop "+channelInfoExt.getFieldName().toUpperCase();
		}else if("sqlserver".equals(dialect.toLowerCase())){
			sql+=" drop "+channelInfoExt.getFieldName().toUpperCase();
		}else if("kingbase".equals(dialect.toLowerCase())){
			sql+=" drop "+channelInfoExt.getFieldName().toUpperCase();
		}else if("dameng".equals(dialect.toLowerCase())){
			sql+=" drop "+channelInfoExt.getFieldName().toUpperCase();
		}
		simpleDaoSupport.executeNativeUpdate(sql, null);
		simpleDaoSupport.delete(ChannelInfoExt.class, sid);
	}
	
	public ChannelInfoExt get(int sid){
		return (ChannelInfoExt) simpleDaoSupport.get(ChannelInfoExt.class, sid);
	}
	
	@Transactional(readOnly=true)
	public List<ChannelInfoExt> list(){
		return simpleDaoSupport.find("from ChannelInfoExt order by sid asc", null);
	}

	
	/**
	 * 判断字段名称是否已经存在
	 * @param request
	 * @return
	 */
	public TeeJson checkFieldNameIsExist(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		//获取字段名称
		String fieldName=TeeStringUtil.getString(request.getParameter("fieldName"));
		//转换成大写
		if(!TeeUtility.isNullorEmpty(fieldName)){
			fieldName=fieldName.toUpperCase();
		}
		String hql="";
		/*if(sid==0){//新增字段  只是新增的时候需要判断
*/			hql="  from ChannelInfoExt where fieldName=? ";
			List<ChannelInfoExt> extList=simpleDaoSupport.executeQuery(hql,new Object[]{fieldName});
		    if(extList!=null&&extList.size()>0){//该字段已经存在
		    	json.setRtState(false);
		    	json.setRtMsg("该字段名称已经存在！");
		    }else{
		    	json.setRtState(true);
		    }
		/*}*/
		return json;
	}
}
