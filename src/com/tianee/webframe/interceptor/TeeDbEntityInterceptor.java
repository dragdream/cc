package com.tianee.webframe.interceptor;

import java.io.File;
import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

public class TeeDbEntityInterceptor extends EmptyInterceptor{
	
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		//entity就是当前的实体对象
		//如果当前操作的TbUser，则做处理
 		super.onDelete(entity, id, state, propertyNames, types);
 		if(entity instanceof TeeAttachment){
			TeeAttachment attachment=(TeeAttachment)entity;
			SessionFactory sessionFactory = (SessionFactory) TeeBeanFactory.getBean("sessionFactory");
			Session session = sessionFactory.openSession();
			attachment = (TeeAttachment) session.get(TeeAttachment.class, attachment.getSid());
			String filePath = attachment.getAttachSpace().getSpacePath()+File.separator+attachment.getModel()+attachment.getAttachmentPath()+attachment.getAttachmentName();
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
			session.close();
 		}
 		
	}
	
	@Override
	public String onPrepareStatement(String sql) {  
        //根据用户的时间替换sql中的表名  
		TeeRequestInfo  requestInfo = TeeRequestInfoContext.getRequestInfo();
		if(requestInfo==null){
			return sql;
		}
		String archives = TeeStringUtil.getString(requestInfo.getRequest().get("thread_local_archives"));
		if(!"".equals(archives)){
			archives = archives.replace("-", "");
			sql = sql.replace(" FLOW_RUN_PRCS ", " oaop_archives.FLOW_RUN_PRCS_"+archives+" ");
			sql = sql.replace(" FLOW_RUN ", " oaop_archives.FLOW_RUN_"+archives+" ");
			sql = sql.replace(" FLOW_PROCESS ", " oaop_archives.FLOW_PROCESS_"+archives+" ");
			sql = sql.replace(" FLOW_TYPE ", " oaop_archives.FLOW_TYPE_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_LOG ", " oaop_archives.FLOW_RUN_LOG_"+archives+" ");
			sql = sql.replace(" PERSON ", " oaop_archives.PERSON_"+archives+" ");
			sql = sql.replace(" DEPARTMENT ", " oaop_archives.DEPARTMENT_"+archives+" ");
			sql = sql.replace(" USER_ROLE ", " oaop_archives.USER_ROLE_"+archives+" ");
			sql = sql.replace(" FLOW_FROCESS_PRINT_TEMPLATE ", " oaop_archives.flow_frocess_print_template_"+archives+" ");
			sql = sql.replace(" FLOW_NTKO_PRINT_TEMPLATE ", " oaop_archives.flow_ntko_print_template_"+archives+" ");
			sql = sql.replace(" FLOW_PRINT_TEMPLATE ", " oaop_archives.flow_print_template_"+archives+" ");
			
			sql = sql.replace(" FLOW_SORT ", " oaop_archives.flow_sort_"+archives+" ");
			sql = sql.replace(" FORM ", " oaop_archives.form_"+archives+" ");
			sql = sql.replace(" FORM_ITEM ", " oaop_archives.form_item_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_CONCERN ", " oaop_archives.flow_run_concern_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_CTRL_FB ", " oaop_archives.flow_run_ctrl_fb_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_DOC ", " oaop_archives.flow_run_doc_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_UPLOAD_CTRL ", " oaop_archives.flow_run_upload_ctrl_"+archives+" ");
			sql = sql.replace(" FLOW_RUN_VIEW ", " oaop_archives.flow_run_view_"+archives+" ");
			
		}
        return sql;  
    }  
}
