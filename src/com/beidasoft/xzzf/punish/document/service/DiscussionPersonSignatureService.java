package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;






import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.document.bean.DocDiscussionPersonSignature;
import com.beidasoft.xzzf.punish.document.dao.DiscussionPersonSignatureDao;
import com.beidasoft.xzzf.punish.document.model.GroupDiscussionModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class DiscussionPersonSignatureService extends TeeBaseService{

	@Autowired
	private DiscussionPersonSignatureDao discussionPersonSignatureDao;
	
	/**
	 * 保存案件集体讨论记录签章信息
	 * @param docDiscussionPersonSignature
	 */
	public void save(GroupDiscussionModel model,  int uuid, String groupId) {
		String signStr = model.getSignObjStr();
		JSONArray arr = JSONArray.fromObject(signStr);
		if (arr.size() > 0) {
			//先删除集体讨论记录的所有参与人签章
			discussionPersonSignatureDao.del(groupId);
			for (int i = 0; i < arr.size(); i++) {
				JSONObject signObj = arr.getJSONObject(i);
				//先通过uuid 查一下有没有签过名
//				DocDiscussionPersonSignature signature = discussionPersonSignatureDao.getPersonSignByUUID(groupId, signObj.getString("uuid"));
//				if (signature == null) { //如果没签过名
					DocDiscussionPersonSignature newsign = new DocDiscussionPersonSignature();
					newsign.setPersonSignatureTime(Calendar.getInstance().getTime());
					newsign.setId(UUID.randomUUID().toString());
					newsign.setPersonSignatureBase64(TeeStringUtil.getString(signObj.getString("signPic"), "")); //签名图
					newsign.setPersonSignatureValue(TeeStringUtil.getString(signObj.getString("signVal"), "")); //签名值
					newsign.setPersonSignaturePlace(TeeStringUtil.getString(signObj.getString("signPlace"), "")); //签名位置
					newsign.setGroupId(groupId);
					newsign.setPersonUUID(TeeStringUtil.getInteger(signObj.getString("uuid"), 0));
					newsign.setPersonNo(TeeStringUtil.getString(signObj.getString("personNo"), ""));
					discussionPersonSignatureDao.saveOrUpdate(newsign);
//				}
			}
		}
	}

	/**
	 * 获取案件集体讨论记录签章信息
	 * @param id
	 * @return
	 */
	public List<DocDiscussionPersonSignature> getByGroupId(String groupId) {
		return discussionPersonSignatureDao.getByGroupId(groupId);
	}

	/**
	 * 更新 案件集体讨论记录签章信息
	 * @param docArticlesDetail
	 */
	public void update(DocDiscussionPersonSignature docDiscussionPersonSignature) {
		discussionPersonSignatureDao.update(docDiscussionPersonSignature);
	}
	
	/**
	 * 删除 案件集体讨论记录签章信息
	 * @param groupId
	 */
	public void del(String groupId) {
		discussionPersonSignatureDao.del(groupId);
	}
}
