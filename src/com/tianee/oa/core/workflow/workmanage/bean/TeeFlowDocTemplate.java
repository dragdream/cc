package com.tianee.oa.core.workflow.workmanage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;





import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_DOC_TEMPLATE")
public class TeeFlowDocTemplate {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_DOC_TEMPLATE_seq_gen")
	@SequenceGenerator(name="FLOW_DOC_TEMPLATE_seq_gen", sequenceName="FLOW_DOC_TEMPLATE_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;//所属流程
	
	
	@Column(name="TEMPLATE_NAME")
	private String templateName;//模板名称
	
	
	@Column(name="PLUGIN_CLASS_PATH")
	private String pluginClassPath;//插件类路径
	
	@OneToOne()
	@JoinColumn(name="ATTACH_ID")
	private TeeAttachment attach;//模板附件

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getPluginClassPath() {
		return pluginClassPath;
	}

	public void setPluginClassPath(String pluginClassPath) {
		this.pluginClassPath = pluginClassPath;
	}

	public TeeAttachment getAttach() {
		return attach;
	}

	public void setAttach(TeeAttachment attach) {
		this.attach = attach;
	}
	
	
	
	
}
