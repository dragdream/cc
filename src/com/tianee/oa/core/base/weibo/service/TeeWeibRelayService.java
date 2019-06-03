package com.tianee.oa.core.base.weibo.service;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.weibo.bean.TeeWeibPublish;
import com.tianee.oa.core.base.weibo.bean.TeeWeibRelay;
import com.tianee.oa.core.base.weibo.dao.TeeWeibPublishDao;
import com.tianee.oa.core.base.weibo.dao.TeeWeibRelayDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWeibRelayService extends TeeBaseService {
	
	@Autowired
	private TeeWeibRelayDao teeWeibRelayDao;
	
	@Autowired
	private TeeWeibPublishDao teeWeibPublishDao;

	/**
	 * 转发微博信息
	 * */
	public TeeJson addRelay(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String infoIdStr = request.getParameter("infoId");
		String img = request.getParameter("img");
		String imgy = request.getParameter("imgy");
		int infoId = Integer.parseInt(infoIdStr);
		String relay = request.getParameter("relay");
		//微博信息实体类
		TeeWeibPublish p=new TeeWeibPublish();
		relay=workContent(relay);
		p.setContent(relay);//发布内容
		Date date=new Date();
		p.setCreateTime(date);//发布时间
		p.setZfId(infoId);//被转发的id
		p.setImg(img);//上传的图片
		p.setImgy(imgy);
		p.setUserId(loginPerson.getUuid());//发布人员id
	    teeWeibPublishDao.save(p);
		//转发实体类
//		TeeWeibRelay rl=new TeeWeibRelay();
//		rl.setCreTime(date);//转发时间
//		rl.setInfoId(infoId);//转发微博信息id
//		rl.setRelayId(relayId);//转发微博信息id
//		rl.setUserId(loginPerson.getUuid());//转发人
//		teeWeibRelayDao.save(rl);
		TeeWeibPublish publish = teeWeibPublishDao.get(infoId);
		if(publish!=null){
			int number = publish.getNumber();
			number+=1;
			publish.setNumber(number);
			teeWeibPublishDao.update(publish);//修改转发次数
		}
		return json;
	}
	
	/**
	 * 处理表情
	 * */
	public String workContent(String content){
		String contentStr="";
		int indexOf = 0;
		int indexOf2 = 0;
		while(content!=null && !"".equals(content)){
			indexOf = content.indexOf("[");
			indexOf2 = content.indexOf("]");
			if(indexOf<0 || indexOf2<0){
				contentStr+=content;
				content="";
			}else{
				contentStr+=content.substring(0,indexOf);//
				String indexof=content.substring(indexOf+1,indexOf2);
				
				if(indexof.matches("emo_[0-9]{1,}")){
					//indexof=indexof.substring(1,indexof.length()-1);
					contentStr+="<img alt='' style='width:22px;height:22px;' src='dist/arclist/"+indexof+".png'>";
				}
				content=content.substring(indexOf2+1,content.length());
			}
		}
		return contentStr;
	}

}
