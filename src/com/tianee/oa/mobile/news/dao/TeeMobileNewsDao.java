package com.tianee.oa.mobile.news.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.model.TeeNewsModel;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;


@Repository
public class TeeMobileNewsDao   extends TeeBaseDao<TeeNews>{
	
	/**
	 * 获取人员新闻列表
	 * @author syl
	 * @date 2014-4-14
	 * @param person
	 * @param state
	 * @param count
	 * @return
	 */
	public List<TeeNewsModel> getList(TeePerson person , int  state , int count){
		String hql = "from TeeNews n where  n.publish = '1'  and   exists (select 1 from n.infos info where info.toUser.uuid = "+person.getUuid()+" and n.newsTime <= ? ";
		if(state == -1){
			hql = hql + ")";
		}else{
			hql = hql + "and info.isRead = "+state+" )";
		}
		List paraList = new ArrayList();
		paraList.add(new Date());
		hql = hql + " order by n.newsTime desc";
		List<TeeNews> newses = pageFindByList(hql,0,count,paraList);// 查询
		List<TeeNewsModel> mList = new ArrayList<TeeNewsModel>();
		if (newses != null && newses.size() > 0) {
			for (TeeNews n : newses) {
				TeeNewsModel m = new TeeNewsModel();
				m.setProvider1(n.getProvider().getUserName());
				BeanUtils.copyProperties(n, m);
				int spanDay = TeeUtility.getDaySpan(n.getNewsTime() , new Date());//取得间隔数
				if(spanDay > 7){
					m.setNewsTimeStr(TeeUtility.getDateTimeStr(n.getNewsTime()));
				}else if(spanDay < 1){
					m.setNewsTimeStr("今天");
				}else{
					m.setNewsTimeStr(spanDay + "天前");
				}
				String typeDesc = "";
				if(!TeeUtility.isNullorEmpty(m.getTypeId())){
					typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE", m.getTypeId());
				}
				m.setTypeDesc(typeDesc);
				m.setContent("");
				m.setAbstracts("");
				m.setShortContent("");
				mList.add(m);
			}
		}
		return mList;
	}
	
	
}
