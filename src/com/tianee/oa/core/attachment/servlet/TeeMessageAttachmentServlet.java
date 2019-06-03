package com.tianee.oa.core.attachment.servlet;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.util.global.TeeBeanFactory;


/**
 * 即时通讯附件上传专用
 * @author kakalion
 *
 */
public class TeeMessageAttachmentServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		// TODO Auto-generated method stub
		try {
			TeePerson loginUser = (TeePerson) req.getSession().getAttribute(TeeConst.LOGIN_USER);
			resp.setCharacterEncoding("UTF-8");
			int length = req.getContentLength();
			InputStream input = req.getInputStream();
			
			//跳过文件头部信息 \r 13   \n 10   读过4个10
			byte buff [] = new byte[1024];//开启1024字节，读取头文件（够用了）
			byte b[] = new byte[1];
			int enterCount = 0;
			int readByteCount = 0;
			do
			{
				input.read(b);
				buff[readByteCount++] = b[0];
				if(b[0]==10){
					enterCount++;
				}
			}while(enterCount<=3);
			
			String headerInfos[] = new String(buff,0,readByteCount).split("\r\n");
			int fileNameIndex = headerInfos[1].indexOf("filename=");
			//获取到文件名
			String fileName = headerInfos[1].substring(fileNameIndex+10,headerInfos[1].length()-1);
			
			TeeBaseUpload baseUpload = (TeeBaseUpload) TeeBeanFactory.getBean("teeBaseUpload");
			TeeAttachment attach = baseUpload.singleAttachUpload(input, length-readByteCount, fileName, "", "message", loginUser);
			
			PrintWriter pw = resp.getWriter();
			pw.write("{\"sid\":"+attach.getSid()+",\"fileName\":\""+attach.getFileName()+"\",\"attachName\":\""+attach.getAttachmentName()+"\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
		}
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		// TODO Auto-generated method stub
		doGet(req,resp);
	}
}
