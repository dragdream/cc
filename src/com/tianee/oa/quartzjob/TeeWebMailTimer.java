package com.tianee.oa.quartzjob;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.email.thread.TeeWebMailRecProcess;
import com.tianee.oa.core.base.email.thread.TeeWebMailRecThreadPool;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Repository
public class TeeWebMailTimer{
	Logger logger = Logger.getLogger(TeeWebMailTimer.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private TeeBaseUpload baseUpload;

	private Session hibernateSession = null;

	/**
	 * 手动控制事务，细粒度
	 */
	public void doTimmer() {
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		TeeWebMailRecThreadPool pool = TeeWebMailRecThreadPool.getInstance();
		
		try{
			hibernateSession = null;
			hibernateSession = sessionFactory.openSession();
			Query query = hibernateSession
					.createQuery("select sid from TeeWebMail where checkFlag='1'");
			
			List<Integer> webMailsIds = query.list();
			TeeWebMailRecProcess mailRecProcess = null;
			List<String> taskIds = new ArrayList();
			
			for (Integer webMailId : webMailsIds) {
				//加入可执行数据
				mailRecProcess = new TeeWebMailRecProcess(TeeStringUtil.getString(webMailId));
				taskIds.add(TeeStringUtil.getString(webMailId));
				pool.execute(mailRecProcess);
			}
			
			//反查任务表，停止相关线程
			taskIds = pool.findNotExistsTaskIds(taskIds);
			//将不存在的任务从线程池中删除掉，并结束
			for(String id:taskIds){
				pool.stop(id);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(hibernateSession!=null && hibernateSession.isOpen()){
				hibernateSession.close();
			}
		}
	}

}
