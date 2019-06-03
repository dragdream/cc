package com.tianee.oa.quartzjob;


import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.bean.TeeOfficeSwitch;
import com.tianee.oa.core.office2html.POIExcelToHtml;
import com.tianee.oa.core.office2html.POIWordToHtml;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeOffice2HtmlTimmer extends TeeBaseService{
	@Autowired
	private SessionFactory sessionFactory;
	
	public void doTimmer(){
		//System.out.println(111);
		try {
			
			//获取未转换的中间表数据
			List<TeeOfficeSwitch>  list=simpleDaoSupport.executeQuery("from TeeOfficeSwitch where flag=0 ", null);
			TeeAttachment att=null;
			String sourcePath="";
			if(list!=null&&list.size()>0){
				for (TeeOfficeSwitch teeOfficeSwitch : list) {
					teeOfficeSwitch.setFlag(1);//转换中
					try {
						att=teeOfficeSwitch.getAttachment();
						if(att!=null){
							sourcePath=att.getFilePath();
							int htmlAttId=0;
							if(("doc").equals(att.getExt())||("docx").equals(att.getExt())){
								htmlAttId=TeeStringUtil.getInteger(POIWordToHtml.wordToHtml(sourcePath), 0);
							}else{
								htmlAttId=TeeStringUtil.getInteger(POIExcelToHtml.excelToHtml(sourcePath), 0);
							}
							
							TeeAttachment htmlAtt=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,htmlAttId);
							teeOfficeSwitch.setHtmlAtt(htmlAtt);
							teeOfficeSwitch.setFlag(2);//已转换
						}
					} catch (Exception e) {
						teeOfficeSwitch.setFlag(-1);//转换失败
					}finally{//保存到数据库
						simpleDaoSupport.update(teeOfficeSwitch);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
}
