package com.tianee.oa.core.base.job.bean;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Entity
@Table(name="JOB")
public class TeeJob implements Serializable,StatefulJob{
	@Id
	@Column(name = "IDENTITY_")
	private String id;//主键标识  字符串类型标识
	
	@Column(name = "TYPE_")
	private int type;//类型  1：spring托管对象   2：普通JAVA类   3：HTTP接口

	@Column(name = "BODY1")
	private String body1;//body1内容
	
	@Column(name = "BODY2")
	private String body2;//body2内容
	
	@Column(name = "EXP_")
	private String exp;
	
	@Column(name = "EXP_DESC")
	private String expDesc;
	
	@Column(name = "EXP_MODEL",length=1000)
	private String expModel;
	
	@Column(name = "RUN_NODE")
	private String runNode;
	
	@Column(name = "STATUS_")
	private int status;//状态   0：停止   1：启用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBody1() {
		return body1;
	}

	public void setBody1(String body1) {
		this.body1 = body1;
	}

	public String getBody2() {
		return body2;
	}

	public void setBody2(String body2) {
		this.body2 = body2;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getExpDesc() {
		return expDesc;
	}

	public void setExpDesc(String expDesc) {
		this.expDesc = expDesc;
	}

	public String getExpModel() {
		return expModel;
	}

	public void setExpModel(String expModel) {
		this.expModel = expModel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getRunNode() {
		return runNode;
	}

	public void setRunNode(String runNode) {
		this.runNode = runNode;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		TeeJob job = (TeeJob) arg0.getJobDetail().getJobDataMap().get("job");
		if(job.getType()==1){
			
			try {
				Class clazz = Class.forName(job.getBody1().trim());
				Service serviceAnotation = (Service) clazz.getAnnotation(Service.class);
				Repository repositoryAnotation = (Repository) clazz.getAnnotation(Repository.class);
				String value = "";
				
				if(serviceAnotation!=null){
					value = serviceAnotation.value();
				}else if(repositoryAnotation!=null){
					value = repositoryAnotation.value();
				}else{
					value = null;
				}
				
				Object obj = null;
				if(!"".equals(value)){
					obj = TeeBeanFactory.getBean(value);
				}else if("".equals(value)){
					String className = clazz.getSimpleName();
					className = className.substring(0, 1).toLowerCase()+className.substring(1);
					obj = TeeBeanFactory.getBean(className);
				}else if(value==null){
					obj = clazz.newInstance();
				}
				
				if(obj!=null){
					Method method = clazz.getMethod("doTimmer");
					method.invoke(obj);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{
			String url = job.getBody1();
			HttpClientUtil.requestPost(null, url);
		}
		
	}
	
	
}
