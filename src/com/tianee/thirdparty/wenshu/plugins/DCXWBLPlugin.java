package com.tianee.thirdparty.wenshu.plugins;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jacob.req.JacobRequest;
import com.jacob.req.JacobResponse;
import com.tianee.webframe.util.str.TeeUtility;
//调查询问笔录
public class DCXWBLPlugin extends TeeWenShuPlugin{

	
	@Override
	public String process(JacobRequest jacobRequest, String wordId,
			Map<String, String> data) {
		/*data.put("页眉","编号：9999999");
		data.put("询问地点", "北京市丰台区西三环南路1号（市文化执法总队）1210室           ");
		data.put("询问时间年1", "2017");
		data.put("询问时间月1", "08");
		data.put("询问时间日1", "19");
		data.put("询问时间时1", "9");
		data.put("询问时间分1", "50");
		data.put("询问时间时2", "10");
		data.put("询问时间分2", "30");
		data.put("询问人姓名", "陶迎春");
		data.put("询问人执法证号", "02120016");
		data.put("记录人姓名", "刘军");
		data.put("记录人执法证号", "02120018");
		
		data.put("被询问人姓名", "毕宇飞");
		data.put("被询问人性别", "男");
		data.put("被询问人出生年月", "1979年12月");
		data.put("被询问人联系电话", "15120059230");
		data.put("被询问人证件名称", "居民身份证");
		data.put("被询问人证件号码", "110108999993023322");
		data.put("被询问人工作单位", "北京天涯海格娱乐城有限公司");
		data.put("被询问人住址", "黑龙江省讷河市孔国乡德宝存3组（现住址：北京市海淀区学院南路33号北京艺海世纪商务酒店东区101号）");
		
		
		//内容记录
		String content = "问：你好的我们是北京市文化市场行政执法总队的行政执法人员的这是我们的执法证件，你看清楚了吗？\n";
		content += "答：我看清楚看了。\n";
		content += "问：根据《中华人民共和国行政处罚法》的有关规定，现就北京天涯海格娱乐城有限公司涉嫌未设置能够覆盖全部营业区域的中英文应急广播的相关事宜对你进行调查询问。在场进行调查询问的执法人员，你认为有需要回避的，请申请回避；在接受调查询问的过程中，你有陈述、申辩的权利，同时你也有如实回答询问并提供有关资料、证据的义务，你清楚吗？\n";
		content += "答：我清楚了。不申请回避。\n";
		content += "请你介绍一下你公司的基本情况？\n";
		content += "答：我叫李金平，是北京天涯海格娱乐城有限公司的总经理，我公司法定代表人刘希朱，刘希朱出差在外地，受他委托我来总队接收调查处理此事。我公司于2008年03月27日成立，并已取得当地文委颁发的《娱乐经营许可证》。我们场所现有39个包间，从业人员31人，包间最低消费80元。\n";
		content += "问：按执法人员的通知要求提供公司的企业法人营业执照、娱乐场所经营许可证及公司法定代表人的有效身份证件等相关资料？\n";
		content += "答：好的，我已将上述资料的复印件都带来了，请查验。\n";
		content += "问：你单位两年之内有没有受到过文化执法部门的处罚？\n";
		content += "答：没有。\n";
		content += "问：2017年08月15日18时45分，执法人员检查你单位时，你是否在现场？\n";
		content += "答：我在现场，你们的检查情况我都清楚。\n";
		content += "问：执法人员现场检查时发现你单位存在什么问题？\n";
		content += "答：执法人员检查我单位经营场所时，发现应急广播不能覆盖到卫生间、操作间。\n";
		content += "问：为什么会出现这样的情况？\n";
		content += "答：主要是我们认识不到造成的，是我们的责任，我们立即改正。\n";
		content += "问：你单位提供的《娱乐经营许可证》是2016年03月23日签发的，已经过了一年多了，你们有新的吗？\n";
		content += "答：今年年初我们主动同海淀区文委联系过此事，他们说两年后再年审。\n";
		content += "问：你单位没有设置能够覆盖全部营业区的应急广播要收到行政处罚你认可吗？\n";
		content += "答：我们认可。\n";
		content += "问：你还有需要补充的吗？\n";
		content += "答：没有。\n";
		content += "问：请你看一下以上笔录，如无异议，请签字。\n";
		content += "答：好的。";
		
		data.put("内容记录", content);*/
		
		
		String newContent = "";
		String sp[] = {};
		if (StringUtils.isNotBlank(data.get("内容记录"))) {
			sp = data.get("内容记录").split("\n");
		}
		int lineCount = sp.length;//获取原始行数（未折行的）
		for(int i=0;i<sp.length;i++){
			String line = sp[i];
			int c = line.length();//获取字符数量（全部当做中文）
			char cr[] = line.toCharArray();
			int enCount = 0;//英文字符数量
			for(char crtmp:cr){
				if((crtmp>='A' && crtmp<='Z') || (crtmp>='a' && crtmp<='z') || (crtmp>='0' && crtmp<='9')){
					enCount++;
				}
			}
			
			c = c-(enCount/2+enCount%2);//获取除去英文字符的字符数量
			double yu = c/36.00*1.00;
			int yuInt = c/36;
			if(yuInt!=0 && (yu-yuInt)>0){
				lineCount = lineCount+yuInt;
			}
			newContent+=(line+"                                                                                              \n");
			
		}
//		System.out.println(newContent);
		lineCount--;
		if(lineCount<=9){//如果行数小于等于9，说明只有第一页，然后补全剩下的行数
			for(int i=0;i<(9-lineCount);i++){
				newContent+="                                                                                               ";
				if(i!=(9-lineCount)-1){
					newContent+="\n";
				}
			}
		}else{//如果行数大于9，说明已经超出一页纸
			int yuLine = lineCount-9;//获取余下的行数
			int pageMaxLine = 21;//新的一页纸最大行数是21
			int lastPageLineCount = yuLine%pageMaxLine;//获取最后一页的行数
			for(int i=0;i<(pageMaxLine-lastPageLineCount);i++){
				newContent+="                                                                                               ";
				if(i!=(pageMaxLine-lastPageLineCount)-1){
					newContent+="\n";
				}
			}
		}
		
		data.put("内容记录", newContent);
//		System.out.println(newContent);
		
		//base64图片单独处理 
		String xwrqm= data.get("询问人签名");
		data.remove("询问人签名");
		
		String jlrqm=data.get("记录人签名");
		data.remove("记录人签名");
		
		String dsrqz = data.get("当事人签名");
		data.remove("当事人签名");
		
		String dsryj = data.get("当事人意见");
		data.remove("当事人意见");
		
		JacobResponse jacobResponse = jacobRequest.replaceBookmarks(wordId, data);
		String replacedWordId = jacobResponse.fileId;//获取替换书签后的word文件id
		
		
		//判断签名是否存在
		String scriptRun = "";
		if(!TeeUtility.isNullorEmpty(xwrqm)){
			scriptRun = "jacob.setBookMarksPicture(\"询问人签名\",\""+xwrqm+"\",7,-1,20);";
			jacobResponse = jacobRequest.scriptRun(scriptRun, replacedWordId);
			replacedWordId = jacobResponse.fileId;
		}
		
		
		if(!TeeUtility.isNullorEmpty(xwrqm)){
			scriptRun = "jacob.setBookMarksPicture(\"记录人签名\",\""+jlrqm+"\",7,-1,20);";
			jacobResponse = jacobRequest.scriptRun(scriptRun, replacedWordId);
			replacedWordId = jacobResponse.fileId;
		}
		
		if(!TeeUtility.isNullorEmpty(dsrqz)){
			scriptRun += "jacob.setBookMarksPicture(\"当事人签名\",\""+dsrqz+"\",7,-1,20);";
			jacobResponse = jacobRequest.scriptRun(scriptRun, replacedWordId);
			replacedWordId = jacobResponse.fileId;
		}
		if(!TeeUtility.isNullorEmpty(dsryj)){
			scriptRun += "jacob.setBookMarksPicture(\"当事人意见\",\""+dsryj+"\",7,-1,20);";
			jacobResponse = jacobRequest.scriptRun(scriptRun, replacedWordId);
			replacedWordId = jacobResponse.fileId;
		}
		
		
		return replacedWordId;
	}

}
