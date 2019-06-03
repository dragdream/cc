package com.tianee.oa.subsys.weixin.msg.util;
/** 
 * 发送消息类 
 * @author wgg
 * @date 2016.11.15 
 */
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.tianee.oa.subsys.weixin.ParamesAPI.WeixinUtil;
import com.tianee.oa.subsys.weixin.media.util.MUDload;
import com.tianee.oa.subsys.weixin.media.util.WeixinMedia;
import com.tianee.oa.subsys.weixin.model.Article;
import com.tianee.oa.subsys.weixin.ParamesAPI.ParamesAPI;

public class SMessage {
	//发送接口
	public static String POST_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	/**
	 * text消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：text
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param content 消息内容
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String STextMsg(String touser,String toparty,String totag,String agentid,String content){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"text\",\"agentid\": %s,\"text\": {\"content\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,content);
	}
	
	/**
	 * image消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：image
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SImageMsg(String touser,String toparty,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"msgtype\": \"image\",\"agentid\": %s,\"image\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,agentid,media_id);
	}
	
	/**
	 * voice消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：voice
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SVoiceMsg(String touser,String toparty,String totag,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"voice\",\"agentid\": %s,\"voice\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id);
	}
	
	/**
	 * video消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：video
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param title 视频消息的标题
	 * @param description 视频消息的描述
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 */
	public static String SVideoMsg(String touser,String toparty,String totag,String agentid,String media_id,String title,String description){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"video\",\"agentid\": %s,\" video\": {\"media_id\": %s,\"title\": %s,\"description\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id,title,description);
	}
	
	/**
	 * file消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：file
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param media_id 媒体资源文件ID
	 * @param safe 表示是否是保密消息，0表示否，1表示是，默认0
	 * */
	public static String SFileMsg(String touser,String toparty,String totag,String agentid ,String media_id){
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"file\",\"agentid\": %s,\"file\": {\"media_id\": %s},\"safe\":\"0\"}";
		return String.format(PostData, touser,toparty,totag,agentid,media_id);
	}
	
	/**
	 * news消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：news
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param articlesList 图文集合
	 */
	public static String SNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList){
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"news\",\"agentid\": %s,\"news\": {\"articles\":%s}}";
		return String.format(postData, touser,toparty,totag,agentid,articlesList);
	}
	
	/**
	 * mpnews消息
	 * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3"
	 * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 "
	 * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 "
	 * @param msgtype 消息类型，此时固定为：mpnews
	 * @param agentid 企业应用的id，整型。可在应用的设置页面查看
	 * @param articlesList mpnews集合
	 */
	public static String SMpNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList){
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"mpnews\",\"agentid\": %s,\"mpnews\": {\"articles\":%s}\"safe\":\"0\"}";
		return String.format(postData, touser,toparty,totag,agentid,articlesList);
	}
	
	public static String releaseWechat(String corpId, String secret, String subject, String content, String type){
//		 String corpId = "wx448f402d8b6a1c43";
//		 String secret = "Uano_Q54UctZeFSFO8LtC4M5RrYv36rMzN6PZN4YGzkRqMe42cjotmi7CcSsbPXO";
		 String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();
		 WeixinMedia weixinMedia = MUDload.uploadMedia(access_token, "image", "http://pic34.nipic.com/20131015/3420027_201324008332_2.jpg");
		 
		 Articles article = new Articles();
		 //发布新闻
		 if("0".equals(type)){
			 article.setThumb_media_id(weixinMedia.getMediaId());
//		 article.setContent("<span style='color: #f00000'>测试公共html</span>");
		 //发布公告
		 }else if("1".equals(type)){
			 article.setThumb_media_id(weixinMedia.getMediaId());
//		 article.setContent("<span style='color: #f00000'>测试公共html</span>");
		 }
		 article.setTitle(subject);
		 article.setContent(content);
		 List<Articles> list = new ArrayList<Articles>();
		 list.add(article);
		// 图文转json
		 String articlesList = JSONArray.fromObject(list).toString();
		 String PostData = SMpNewsMsg("\"@all\"", "\"@all\"", "\"@all\"", "0", articlesList);
		 int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);
		   // 打印结果
			if(0==result){
				System.out.println("操作成功");
			}
			else {
				System.out.println("操作失败");
			}
		 return result+"";
		
	}
	//示例
   public static void main(String[] args) {
	   /**
	    * news示例
	    * */
	   // 调取凭证
//	   String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret).getToken();
	   String corpId = "wx448f402d8b6a1c43";
	   String secret = "Uano_Q54UctZeFSFO8LtC4M5RrYv36rMzN6PZN4YGzkRqMe42cjotmi7CcSsbPXO";
	   String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();
	   
	
	   WeixinMedia weixinMedia = MUDload.uploadMedia(access_token, "image", "http://pic34.nipic.com/20131015/3420027_201324008332_2.jpg");
	   // 新建图文
	   Article article1 = new Article();
	   article1.setTitle("公告标题");
	   article1.setDescription("公告.......................");
	   article1.setPicUrl("http://pic34.nipic.com/20131015/3420027_201324008332_2.jpg");
//	   article1.setUrl("http://www.zatp.com.cn");
	   /*Article article2 = new Article();
	   article2.setTitle("新闻标题");
	   article2.setDescription("新闻");
	   article2.setPicUrl("http://112.124.111.3/weixinClient/images/weather3.png");
	   article2.setUrl("http://www.baidu.com");*/
	   
	   Articles article3 = new Articles();
	   article3.setTitle("公告html");
	   article3.setThumb_media_id(weixinMedia.getMediaId());
	   article3.setContent("<span style='color: #f00000'>测试公共html</span>");
	   
	   // 整合图文
	   List<Articles> list = new ArrayList<Articles>();
	   list.add(article3);
//	   list.add(article2);
	   // 图文转json
	   String articlesList = JSONArray.fromObject(list).toString();
	   // Post的数据
//	   String PostData = SNewsMsg("UserID1|UserID2|UserID3", "PartyID1 | PartyID2", "TagID1 | TagID2", "1", articlesList);
//	   String PostData = SNewsMsg("\"@all\"", "\"@all\"", "\"@all\"", "0", articlesList);
	   String PostData = SMpNewsMsg("\"@all\"", "\"@all\"", "\"@all\"", "0", articlesList);
	   int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);
	   // 打印结果
		if(0==result){
			System.out.println("操作成功");
		}
		else {
			System.out.println("操作失败");
		}
}
}
