package com.tianee.thirdparty.wenshu.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.org.bjca.anysign.components.bean.message.ExternalAnyWriteInfo;
import cn.org.bjca.anysign.components.bean.message.ExternalEncryptPackageInfo;
import cn.org.bjca.anysign.components.bean.message.MessageBodyReference;
import cn.org.bjca.mssp.mssg.client.CertificateUtil;
import cn.org.bjca.mssp.mssg.client.MSSGClientMul;
import cn.org.bjca.seal.esspdf.client.ClientConstant;
import cn.org.bjca.seal.esspdf.client.tools.AnySignClientTool;
import cn.org.bjca.seal.esspdf.client.tools.ESSPDFClientTool;
import cn.org.bjca.seal.esspdf.client.tools.MSSGPDFClientTool;
import cn.org.bjca.seal.esspdf.platform.sdk.CommonClientConstant;
import cn.org.bjca.seal.esspdf.platform.sdk.message.ChannelMessage;
import cn.org.bjca.seal.esspdf.platform.sdk.message.ClientSignBean;
import cn.org.bjca.seal.esspdf.platform.sdk.message.ClientSignMessage;
import cn.org.bjca.seal.esspdf.platform.sdk.message.CloudRespMessage;
import cn.org.bjca.seal.esspdf.platform.sdk.message.DocumentInfo;
import cn.org.bjca.seal.esspdf.platform.sdk.message.ReqMessage;
import cn.org.bjca.seal.esspdf.platform.sdk.message.UserInfoMessage;
import cn.org.bjca.seal.esspdf.platform.sdk.utils.ClientUtil;

import com.beidasoft.xzzf.punish.common.bean.PunishDocPdf;
import com.beidasoft.xzzf.punish.common.dao.PunishDocPdfDao;
import com.jacob.req.JacobRequest;
import com.jacob.req.JacobResponse;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.bean.TeeInterface;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowDocTemplate;
import com.tianee.oa.mobile.system.service.TeeMobileConfigService;
import com.tianee.oa.mobile.system.service.TeeMobileSystemService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.officialSeal.bean.TeeOfficialSeal;
import com.tianee.oa.subsys.officialSeal.service.TeeOfficialSealService;
import com.tianee.thirdparty.wenshu.plugins.TeeWenShuPlugin;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWenShuService extends TeeBaseService{
	
	static String algo_SHA1WithRSA="SHA1WithRSA";
	static String algo_SHA256WithRSA="SHA256WithRSA";
	static String algo_SM3WithSM2="SM3WithSM2";
	static String dialgo_SHA1="SHA1";
	static String dialgo_SHA256="SHA256";
	static String dialgo_SM3="SM3";
	
	static String ip = TeeStringUtil.getString(TeeSysProps.getString("CA_IP")) ; //网关服务器IP(实际应用时 会在用户机房部署一套认证网关)
	static int port = TeeStringUtil.getInteger(TeeSysProps.getString("CA_PORT"), 0);//服务端口
	static String appid = TeeStringUtil.getString(TeeSysProps.getString("CA_APPID")); //客户端应用
	static String channelid = TeeStringUtil.getString(TeeSysProps.getString("CA_CHANNELID")); //渠道编号 
	
	static String xssip = TeeStringUtil.getString(TeeSysProps.getString("CA_XSS_IP")) ; //网关服务器IP(实际应用时 会在用户机房部署一套认证网关)
	static int xssport = TeeStringUtil.getInteger(TeeSysProps.getString("CA_XSS_PORT"), 0);//服务端口
	
	@Autowired
	private TeeBaseUpload baseUpload;
	
	@Autowired
	private  TeeMobileSystemService mobileSystemService;
	
	@Autowired
	private TeeOfficialSealService officialSealService;
	
	@Autowired
	private TeeAttachmentService teeattachService;
	
	@Autowired
	private PunishDocPdfDao pdfDao;
	
	/**
	 * 初始化模板数据接口
	 * @param templateId
	 * @return
	 * @throws Exception 
	 */
	public TeeAttachment initDocTemplate(int templateId,Map<String,String> data) throws Exception{
		//根据templateId  获取文书模板对象
		TeeFlowDocTemplate tpl=(TeeFlowDocTemplate) simpleDaoSupport.get(TeeFlowDocTemplate.class,templateId);
		//获取模板附件
		TeeAttachment att=tpl.getAttach();
		//获取插件类路径
		String pluginClassPath=tpl.getPluginClassPath();
        //获取类
		Class clazz = Class.forName(pluginClassPath);
		//实例化一个对象
		Object object = clazz.newInstance();
		//将实例化的对象强制转换成父类
		TeeWenShuPlugin plugin = (TeeWenShuPlugin)object;
		
		//创建jacob请求对象，并设置服务的ip地址
		JacobRequest jacobRequest = new JacobRequest(TeeSysProps.getString("JACOB_ADDRESS"));
				
		//将本地一个word文件上传，获取到服务器远程的文件id
		JacobResponse jacobResponse = jacobRequest.uploadFile(att.getFilePath());
		String wordId = jacobResponse.fileId;//获取到上传后的word文件的ID
		
		
        //调用父类的方法    返回一个套完书签的word文档的id（32位）
		String replacedWordId = plugin.process(jacobRequest, wordId, data);
		
		//然后将word转换成pdf
		jacobResponse=jacobRequest.file2pdf(replacedWordId);
		String pdfId=jacobResponse.fileId;
		
		//将替换书签后的Pdf文件下载到本地
		String randomName = UUID.randomUUID().toString();
		String path = TeeSysProps.getTmpPath()+"/"+randomName+".pdf";
		jacobRequest.download(pdfId,path);
		
		//将下载的临时的pdf读取成流的格式
		FileInputStream in=new FileInputStream(path);
		TeeAttachment pdfAttach=baseUpload.singleAttachUpload(in
				,in.available() , tpl.getTemplateName()+".pdf", null, "wenshu", null);
		
		in.close();
		
		
		//将临时文件删除
		File file=new File(path);
		file.delete();
		
		
		return pdfAttach;
	}
	
//###################################盖公章PDF##############################################	
	
	/**
	 * 盖公章PDF
	 * @param docId  业务系统案件id
	 * @param att  需要盖章的pdf文档所对应的附件对象
	 * @param BussRuleNum  签章规则
	 * @param templateNum 签章模板
	 * @param creditCode 组织机构代码
	 * @param userName 企业用户名称
	 * @param docuName 文件描述信息
	 * @return
	 * @throws Exception
	 */
	/*public TeeAttachment gZPdfSign(String docId,TeeAttachment att,String BussRuleNum,String templateNum,String creditCode,String userName,String docuName) throws Exception{
		
		//1.盖章
		 //初始化连接
		 ESSPDFClientTool essPDFClientTool = new ESSPDFClientTool(ip, port);
		 
		 byte[] pdfBty = ClientUtil.readFileToByteArray(new File(att.getFilePath()));//pdf字节数组
		 long begin = System.currentTimeMillis();
		 ReqMessage reqMessage = new ReqMessage();
		 reqMessage.setAppId(appid);
		 reqMessage.setRuleNum(BussRuleNum);//签章规则 ok
		 reqMessage.setUserinfo(getUser(userName,creditCode));
		
		 reqMessage.setDocumentInfo(getDoc(userName,docuName));
		
		 TeeAttachment returnAtt=null;
		 
		 ChannelMessage message = essPDFClientTool.cloudSealPdfSign(reqMessage,pdfBty);		
		 CloudRespMessage resMessage = message.getCloudRespMessage();		
		 if(resMessage!=null && ClientConstant.SUCCESS.equals(resMessage.getStatusCode())) {
			 // ClientUtil.writeByteArrayToFile(new File("C:/Users/xsy/Desktop/test1-2.pdf"), message.getBody());
		     //返回附件
			 ByteArrayInputStream in=new ByteArrayInputStream(message.getBody());
			 returnAtt=baseUpload.singleAttachUpload(in,message.getBody().length, att.getFileName(), "", "wenshu", "",att.getUser());
		 }

		//2.修改业务系统中间表数据
		 PunishDocPdf docPdf=(PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class,docId);
		 
		 if(docPdf==null){
			 docPdf=new PunishDocPdf();
			 docPdf.setPdfId(returnAtt.getSid());
			 docPdf.setDocId(docId);
		 }else{
			 docPdf.setPdfId(returnAtt.getSid());
		 }
		 
		 commonService.saveOrUpdatePdf(docPdf);
		 return returnAtt;
	}*/
	
	public TeeAttachment gZPdfSign(String docId, TeeAttachment att,
			String docuName, HttpServletRequest request) throws Exception {
		
		// 获取当前登陆人信息
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		// 现根据关键字去查找公章的相关信息
		TeeOfficialSeal officialSeal = officialSealService.getSealByKeyWord(loginUser.getDept().getSubordinateUnits());
		if (officialSeal != null) {
			// 1.盖章
			// 初始化连接
			ESSPDFClientTool essPDFClientTool = new ESSPDFClientTool(ip, port);

			byte[] pdfBty = ClientUtil.readFileToByteArray(new File(att.getFilePath()));// pdf字节数组
			long begin = System.currentTimeMillis();
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setAppId(appid);
			reqMessage.setRuleNum(officialSeal.getBussRuleNum());// 签章规则 ok
			reqMessage.setUserinfo(getUser(officialSeal.getUserName(), officialSeal.getCreditCode()));
			reqMessage.setDocumentInfo(getDoc(officialSeal.getUserName(), docuName));

			TeeAttachment returnAtt = null;

			ChannelMessage message = essPDFClientTool.cloudSealPdfSign(reqMessage, pdfBty);
			CloudRespMessage resMessage = message.getCloudRespMessage();
			if (resMessage != null
					&& ClientConstant.SUCCESS
							.equals(resMessage.getStatusCode())) {
				// ClientUtil.writeByteArrayToFile(new
				// File("C:/Users/xsy/Desktop/test1-2.pdf"), message.getBody());
				// 返回附件
				ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
				returnAtt = baseUpload.singleAttachUpload(in, message.getBody().length, att.getFileName(), "",
						"wenshu", "", att.getUser());
			}

			// 2.修改业务系统中间表数据
			PunishDocPdf docPdf = (PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class, docId);

			if (docPdf == null) {
				docPdf = new PunishDocPdf();
				docPdf.setPdfId(returnAtt.getSid());
				docPdf.setDocId(docId);
			} else {
				docPdf.setPdfId(returnAtt.getSid());
			}

			pdfDao.saveOrUpdate(docPdf);
			return returnAtt;
		} else {
			return null;
		}
	}
	
//######################################个人签名PDF#####################################	
	
	/**
	 * 个人签名PDF
	 * @param att   需要进行签名的pdf文档所对应的附件对象
	 * @param fileUniqueId  文档编号
	 * @param msspId  信步云签名编号
	 * @param ruleNum  签名规则
	 * @return
	 * @throws Exception
	 */
	public TeeAttachment  gRPdfSign(TeeAttachment att,String fileUniqueId,String msspId,String ruleNum) throws Exception {//个人全托管PDF签章 【需要用户在移动端激活并下载证书】
		 MSSGPDFClientTool pdfClient =new MSSGPDFClientTool(ip, port);
		List<ClientSignMessage> clientSignMessages = new ArrayList<ClientSignMessage>();

		ClientSignMessage clientSignMessage = new ClientSignMessage();
		clientSignMessage.setPdfBty(ClientUtil.readFileToByteArray(new File(att.getFilePath())));
		clientSignMessage.setFileUniqueId(fileUniqueId);//文档流水号
		
//		clientSignMessage.setRuleType("2"); // 规则类型1:关键字定位普通签章、2: 坐标定位普通签章
//		clientSignMessage.setRuleType("1"); // 规则类型1:关键字定位普通签章、2: 坐标定位普通签章
//		clientSignMessage.setKeyword("位置1");
//		clientSignMessage.setMoveType("3"); // 移动类型1:重叠、2: 居下、3:居右,默认居右
//		clientSignMessage.setMoveSize(20);//印章左右偏移量
//		clientSignMessage.setHeightMoveSize(0);//印章上下偏移量
//		clientSignMessages.add(clientSignMessage);
	

		ReqMessage reqMessage = new ReqMessage();
		reqMessage.setClientSignMessages(clientSignMessages);
		reqMessage.setAppId(appid);
		reqMessage.setUserinfo(new UserInfoMessage());
		reqMessage.getUserinfo().setKeyID(msspId);// msspid
		reqMessage.setRuleNum(ruleNum);//个人PDF签章测试规则
		
//      UserSeal userSeal = new UserSeal();
//		userSeal.setSealHeight(50f); // 签章图片高度
//		userSeal.setSealWidth(100f); // 签章图片宽度
//   	userSeal.setSealImg(EncodeUtil.base64Encode(ClientUtil.readFileToByteArray(new File("D:/pdfs/44.png")))); // 签章图片文件
//		reqMessage.getUserinfo().setUserSeal(userSeal);
		
		CloudRespMessage message = pdfClient.batchPersonalSyncSign(reqMessage);
		System.out.println("状态信息：" + message.getStatusCode() + " " + message.getStatusInfo());
		TeeAttachment returnAtt=null;
		//签好的文档写到本地
		if (message != null && ClientConstant.SUCCESS.equals(message.getStatusCode())) { // 成功
			List<ClientSignBean> signBeanList = message.getClientSignList();
			for (ClientSignBean beanObj : signBeanList) {
				/*System.out.println("业务文档编号=" + beanObj.getFileUniqueId() + "； 签名文档编号=" + beanObj.getSignUniqueId() + "；  信步云签名编号=" + beanObj.getMsspSignId());
				ClientUtil.writeByteArrayToFile(new File("C:/Users/xsy/Desktop/" + beanObj.getSignUniqueId() + ".pdf"), beanObj.getPdfBty());*/
				 //返回附件
				 ByteArrayInputStream in=new ByteArrayInputStream(beanObj.getPdfBty());
				 returnAtt=baseUpload.singleAttachUpload(in,message.getBody().length, att.getFileName(), "", "wenshu", "",att.getUser());
			}
		}
		return returnAtt;
	}
	
	
//#####################################信步云扫码获取个人章####################################	
	/**发起个人网页扫码签章   返回将要生成二维码的json字符串  
	 * 
	 * @param yw  原文内容
	 * @param description  描述
	 * @param msspId 用户编号
	 * @throws Exception
	 */
    public String signData4ESS(String yw,String description,String msspId) throws Exception {
    	String jsonStr="";
    	try {
			//获取一个signId
			MSSGClientMul client = MSSGClientMul.getInstance("http://"+ip+":"+port,appid);
			String res = client.signData4ESS(yw.getBytes("UTF-8"), algo_SHA1WithRSA, description,msspId);
			System.out.println("sign4ESS result=="+res);
			//2 服务端生成二维码 移动端调用扫码签名接口 
			//拼接将要生成二维码的json字符串
			Map<String,String> map=new HashMap<String, String>();
			map.put("operType", "signData");
			map.put("data", res);
			jsonStr=TeeJsonUtil.mapToJson(map);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  jsonStr;
	}
	/**
	 * 服务端获取签名结果	
	 * @param signId
	 * @return
	 * @throws Exception
	 */
	public String getSignSealData4ESS(String signId) throws Exception {//3 服务端获取签名结果	
		//获取签章图片数据   数组中的倒数第二个
		String picData="";
		try {
			MSSGClientMul client = MSSGClientMul.getInstance("http://"+ip+":"+port,appid);
			String res = client.getSignSealData4ESS(signId);
			System.out.println("getSignData result=="+res);
			String [] resArray=res.split("[`~]");
			
			if(resArray!=null&&resArray.length>0){
				picData=resArray[resArray.length-2];
			}
			//从res里 解析出签章图片（base64字符串）：
			//结果base64编码（格式： 签名值````公钥信息````证书 信息````签名时间````容器名 称````印章数据* [`~]签章位置信息[`~]印章图片数据 [`~]） 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return picData;
	}
	
	

//#####################################信步云扫码PDF盖章####################################	
	   
	    /**
	     * 
	     * @param att  需要盖章的PDF文档 所对应的附件对象
	     * @param msspId  用户编号
	     * @param ruleNum 签章规则
	     * @return  返回需要生成二维码的json字符串
	     * @throws Exception
	     */
		public  String  cloudSealUploadDocWithKeyID(TeeAttachment att,String msspId,String ruleNum) throws Exception{//1 扫码个人PDF签章
			MSSGPDFClientTool pdfClient=new MSSGPDFClientTool(ip, port);;
			String jsonStr="";
			byte[] pdfBty = ClientUtil.readFileToByteArray(new File(att.getFilePath()));
			
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setDocumentInfo(getDoc(att.getFileName(),""));
			reqMessage.setRuleNum(ruleNum);		
			UserInfoMessage user=new UserInfoMessage();
//			UserSeal userSeal = new UserSeal();
//			userSeal.setSealHeight(65f); // 签章图片高度
//			userSeal.setSealWidth(130f); // 签章图片宽度
//			user.setUserSeal(userSeal);
			user.setKeyID(msspId); // msspID
			reqMessage.setAppId(appid);
			reqMessage.setUserinfo(user);
			ChannelMessage message = pdfClient.cloudSealUploadDocWithKeyID(reqMessage,pdfBty);
			System.out.println("状态码："+message.getStatusCode());
			System.out.println("状态信息：" + message.getStatusInfo());
			if(ClientConstant.SUCCESS.equals(message.getStatusCode())) {
				System.out.println("签章ID:"+message.getCloudRespMessage().getSignId());
			    //2 服务端生成二维码 移动端调用扫码签名接口   拼接需要生成二维码的json字符串
				Map<String,String> map=new HashMap<String,String>();
			    map.put("operType", "signData");
				map.put("data", message.getCloudRespMessage().getSignId());
				jsonStr=TeeJsonUtil.mapToJson(map);	
			}
			return jsonStr;
		}
		
		/**
		 * 
		 * @param att 需要盖章的PDF文档 所对应的附件对象
		 * @param signId  签章ID
		 * @return 返回盖完章的PDF文档所对应的附件对象
		 * @throws Exception
		 */
		public  TeeAttachment  cloudSealCommitSign(String signId,String fileName,TeePerson loginUser) throws Exception{//3 个人协同 单文档单章 获取结果
			MSSGPDFClientTool pdfClient=new MSSGPDFClientTool(ip, port);;
			ReqMessage reqMessage = new ReqMessage();
			reqMessage.setAppId(appid);
			reqMessage.setSignId(signId);	
			CloudRespMessage message = pdfClient.cloudSealCommitSign(reqMessage);
			TeeAttachment returnAtt=null;
			if(ClientConstant.SUCCESS.equals(message.getStatusCode())) {			
				//返回附件
				 ByteArrayInputStream in=new ByteArrayInputStream(message.getBody());
				 returnAtt=baseUpload.singleAttachUpload(in,message.getBody().length, fileName, "", "wenshu", "",loginUser);
			}
	        System.out.println("状态码："+message.getStatusCode());
	        System.out.println("状态信息：" + message.getStatusInfo());
	        return returnAtt;
		}
			
	
	
//##########################################信手书盖章到PDF#########################################	
		/*//1 移动端做手写签名 生成加密包 传给服务端
		//2 服务端调用信手书服务接口 生成PDF文件
		*//**
		 * 
		 * @param encodeStr 加密文件内容
		 * @return
		 * @throws Exception
		 *//*
		public TeeAttachment anySignEncPackage(String encodeStr,String fileName,TeePerson loginUser,String docId) throws Exception { // 加密包签名
			AnySignClientTool anySignClientTool = new AnySignClientTool(xssip, xssport);
			
			TeeAttachment returnAtt=null;
			
			byte[] encDataBty = encodeStr.getBytes();// 移动端传过来加密包字节数组
			long begin = System.currentTimeMillis();
			cn.org.bjca.seal.esspdf.client.message.ChannelMessage message = anySignClientTool.anyWriteEncPackage(encDataBty);
			long end = System.currentTimeMillis();
			System.out.println("********************运行时间:" + (end - begin) / 1000f + "s");
			System.out.println("状态码：" + message.getStatusCode());
			System.out.println("状态信息：" + message.getStatusInfo());
			if ("200".equals(message.getStatusCode())) {// 成功
				List<MessageBodyReference> messageBodyReferenceList = message.getMessageBodyReferenceList();
				MessageBodyReference messageBodyReference = null;
				
				for (int i = 0; i < messageBodyReferenceList.size(); i++) {
					 messageBodyReference = messageBodyReferenceList.get(i);
					
					 ByteArrayInputStream in=new ByteArrayInputStream(messageBodyReference.getFileBty());
					 returnAtt=baseUpload.singleAttachUpload(in,message.getBody().length, fileName, "", "wenshu", "",loginUser);
				}
				
				
				//2.修改业务系统中间表数据
				 PunishDocPdf docPdf=(PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class,docId);
				 
				 if(docPdf==null){
					 docPdf=new PunishDocPdf();
					 docPdf.setPdfId(returnAtt.getSid());
					 docPdf.setDocId(docId);
				 }else{
					 docPdf.setPdfId(returnAtt.getSid());
				 }
				 
				 pdfDao.saveOrUpdate(docPdf);
			}
			
			
			
			 return  returnAtt;
		}*/
	
	
	
//###################################扩展类###########################################	
	DocumentInfo getDoc(String name,String desc){//封装扩展信息
		DocumentInfo doc=new DocumentInfo();
		doc.setDocuName(name);
		doc.setFileDesc(desc);
		return doc;
	}
	UserInfoMessage getUser(String name,String code){//封装客户端(个人用户、或企业用户)
		UserInfoMessage user=new UserInfoMessage();
		user.addCreditCode(CommonClientConstant.ID_TYPE_ORG, code);
		user.setChannelId(channelid);
		return user;
	}

	

	
	
	
//&&&&&&&&&&&&&&&&&&&&&&&&&&&移动端CA登陆&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&	
	/**
	 * 发起登陆请求
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeJson beginPinCodeLogin(HttpServletRequest request) throws Exception {
		TeeJson json=new TeeJson();
		//生成一位32位数的随机数
		String randomNum=UUID.randomUUID().toString().replace("-","");
		MSSGClientMul signClient = MSSGClientMul.getInstance("http://"+ip+":"+port,appid);;
		String res = signClient.signAuth4User(randomNum, algo_SHA1WithRSA, "");
		json.setRtData(res);
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 验证登录成功还是登陆失败
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeJson checkPinCodeLogin(HttpServletRequest request) throws Exception {
		TeeJson  json=new TeeJson();
		//获取移动端传来的证书数据
        String scert=TeeStringUtil.getString(request.getParameter("scret"));
		//获取身份证id
        String cardId = CertificateUtil.getCertExtValueWithoutSpecialChar(scert, "2.16.840.1.113732.2");
        if(!TeeUtility.isNullorEmpty(cardId)){
        	if(cardId.startsWith("SF")){
        		cardId=cardId.substring(2,cardId.length());
        	}	
        }
		//System.out.println("身份证号："+cardId);
		//根据msspid去数据库中查找人  有对应的用户  则登陆成功   否则  登陆失败
		String hql=" from TeePerson p where certUniqueId=? ";
		List<TeePerson> personList=simpleDaoSupport.executeQuery(hql,new Object[]{cardId});
		if(personList!=null&&personList.size()>0){//登陆成功
			TeePerson person=personList.get(0);
			
			 Map map = new HashMap();
			 map.put("sid", person.getUuid());
		     map.put("UUID", person.getUuid());
		     map.put("luokuan", person.getDept().getSubordinateUnits());
		     map.put("userName", person.getUserName());
		     map.put("userId", person.getUserId());
		     map.put("JSESSIONID", request.getSession().getId());
		     map.put("PATH", request.getContextPath());
		     map.put("theme", person.getTheme());
		     map.put("sex", person.getSex());
		     map.put("photoId", person.getAvatar());
		     map.put("aipCode", person.getIcq_no());//aip授权码
			
		     TeeInterface itfc = (TeeInterface) simpleDaoSupport.get(TeeInterface.class,1);
             String title =  itfc.getIeTitle();
             if(title==null || "".equals(title)){
        	    title = "移动办公平台";
             }
	    
            map.put("welcomePic", TeeStringUtil.getString(itfc.getWelcomePic()));
            map.put("appIndex", TeeStringUtil.getString(itfc.getAppIndex()));
	        map.put("loginFuncStr","USER,ADDRESS,ATTENDANCE,EMAIL,NEWS,NOTIFY,CALENDAR,DIARY,WORK_FLOW,PERSONAL_NETDISK,PUBLIC_NETDISK,REPORTSHOP,DOCREC,DOCVIEW");
	        map.put("loginFunc",mobileSystemService.getMenuFuncs(person,1));//oa  app权限
	        map.put("loginZfFunc",mobileSystemService.getMenuFuncs(person,2));//执法  app权限
	        map.put("appTitle",title);
	        map.put("webVersion",TeeSysProps.getString("VERSION"));//系统web版本
	        map.put("webVersionNo",TeeSysProps.getString("VERSION_NO"));//系统web版本号
	        map.put("photo",person.getAvatar());
	        
	        //动态获取对应服务器，并将该用户写入目标服务器路由
	        map.put("mLogo",itfc.getmLogo());
	        map.put("mPic",itfc.getmPic());
	        map.put("imPic",TeeStringUtil.getString(itfc.getImPic()));
	        
	        map.put("tcpPort",TeeSysProps.getString("TCP_SOCKET_PORT"));//TCP端口
	        map.put("oaUrl","");
	        map.put("isNewVersion","");
	        map.put("currVersion4Android",TeeMobileConfigService.getAndroidCurrVersion());//当前版本
	        map.put("downloadUpdateUrl4Android","/appupdate/android/Android_Setup.apk");//下载更新地址
	        map.put("currVersion4Ios",TeeMobileConfigService.getIosCurrVersion());//当前版本
	        map.put("downloadUpdateUrl4Ios","/mobileSystemAction/downIOSFile.action");//下载更新地址
	        map.put("currVersion4PC", TeeSysProps.getString("PC_CURR_VERSION"));
	        map.put("watermark", TeeSysProps.getInt("WATER_MARK"));
	        
	        map.put("appTopBg",itfc.getAppTopBg());
	        map.put("appTopSignShow",TeeStringUtil.getString(itfc.getAppTopSignShow()));
			
			json.setRtState(true);
			
			request.getSession().setAttribute(TeeConst.LOGIN_USER, person);
			json.setRtData(map);
		}else{
			json.setRtState(false);
		}
		
		return json;
	}
	
	/**
	 * 通过docId  和runId  作更新punishFlow 的页数的操作 (保存文书时调用)
	 * 
	 * @param docId
	 * @param runId
	 * @throws Exception 
	 */
	public String getPages(String docId, String runId,HttpServletRequest request) throws Exception {
		TeeAttachment pdfAttach = null;
		// 公章是否显示
		String isShow = TeeStringUtil.getString(request.getParameter("isShow"));
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
		Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
		//初始化pdf 页数
		int pdfpage = 0;
		//通过docId 取得 docPdf 对象
		PunishDocPdf docPdf=(PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class, docId);
		int attachId = 0;
		if (docPdf != null) { //如果对象不为空
			attachId = docPdf.getPdfId();
		}
		if (attachId == 0) {
			// PDFID不存在的场合
			pdfAttach = initDocTemplate(templateId, content);
		} else {
			// PDFID存在的场合
			pdfAttach = teeattachService.getById(attachId);
		}
		if ("true".equals(isShow)) {
			pdfAttach = gZPdfSign(docId, pdfAttach, "", request);
		}
		attachId = pdfAttach.getSid();
		pdfpage = teeattachService.getPDFPageNumById(attachId);
		String nums = pdfpage +","+ attachId;
		return nums;
	}
	
	/**
	 * 通过docId  和runId  作更新punishFlow 的页数的操作 (保存文书时调用)
	 * 
	 * @param docId
	 * @param runId
	 * @throws Exception 
	 */
	public String getPages(String docId, String runId,
			Map<String, String> content, HttpServletRequest request)
			throws Exception {
		TeeAttachment pdfAttach = null;
		// 公章是否显示
		String isShow = TeeStringUtil.getString(request.getParameter("isShow"));
		int templateId = TeeStringUtil.getInteger(request.getParameter("templateId"), 0);
		//初始化pdf 页数
		int pdfpage = 0;
		//通过docId 取得 docPdf 对象
		PunishDocPdf docPdf=(PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class, docId);
		int attachId = 0;
		if (docPdf != null) { //如果对象不为空
			attachId = docPdf.getPdfId();
		}
		if (attachId == 0) {
			// PDFID不存在的场合
			pdfAttach = initDocTemplate(templateId, content);
		} else {
			// PDFID存在的场合
			pdfAttach = teeattachService.getById(attachId);
		}
		if ("true".equals(isShow)) {
			pdfAttach = gZPdfSign(docId, pdfAttach, "", request);
		}
		attachId = pdfAttach.getSid();
		pdfpage = teeattachService.getPDFPageNumById(attachId);
		String nums = pdfpage +","+ attachId;
		return nums;
	}

	
	
	
	public TeeAttachment anySignEncPackage(String encodeStr, String fileName,
			TeePerson loginUser, String docId) throws Exception {
		AnySignClientTool anySignClientTool = new AnySignClientTool(xssip, xssport);
		List<ExternalAnyWriteInfo> externalAnyWriteInfoList = new ArrayList<ExternalAnyWriteInfo>(1);
		ExternalAnyWriteInfo externalAnyWriteInfo = null;
		externalAnyWriteInfo = new ExternalAnyWriteInfo();
		externalAnyWriteInfo.setChannel("999999");
		ExternalEncryptPackageInfo encryptPackageInfo = new ExternalEncryptPackageInfo(encodeStr.getBytes());
        
		TeeAttachment returnAtt=null;
		
		List<ExternalEncryptPackageInfo> encryptPackageInfoList = new ArrayList<ExternalEncryptPackageInfo>();
		encryptPackageInfoList.add(encryptPackageInfo);
		externalAnyWriteInfo.setEncryptPackageInfoList(encryptPackageInfoList);
		externalAnyWriteInfoList.add(externalAnyWriteInfo);
		long begin = System.currentTimeMillis();
		cn.org.bjca.seal.esspdf.client.message.ChannelMessage message = anySignClientTool.anyWritePDFSignFacade(externalAnyWriteInfoList);
		long end = System.currentTimeMillis();
		System.out.println("********************运行时间:" + (end - begin) / 1000f + "s");
		System.out.println("状态码：" + message.getStatusCode());
		System.out.println("状态信息：" + message.getStatusInfo());
		//Assert.assertEquals("200", message.getStatusCode());
		if ("200".equals(message.getStatusCode())) {// 成功
		
			List<MessageBodyReference> messageBodyReferenceList = message.getMessageBodyReferenceList();
			MessageBodyReference messageBodyReference = null;
			for (int i = 0; i < messageBodyReferenceList.size(); i++) {
				
				 messageBodyReference = messageBodyReferenceList.get(i);
					
				 ByteArrayInputStream in=new ByteArrayInputStream(messageBodyReference.getFileBty());
				 returnAtt=baseUpload.singleAttachUpload(in,message.getBody().length, fileName, "", "wenshu", "",loginUser);
				
			}
			
			//2.修改业务系统中间表数据
			 PunishDocPdf docPdf=(PunishDocPdf) simpleDaoSupport.get(PunishDocPdf.class,docId);
			 
			 if(docPdf==null){
				 docPdf=new PunishDocPdf();
				 docPdf.setPdfId(returnAtt.getSid());
				 docPdf.setDocId(docId);
			 }else{
				 docPdf.setPdfId(returnAtt.getSid());
			 }
			 
			 pdfDao.saveOrUpdate(docPdf);
		}
		return returnAtt;
	}
}
