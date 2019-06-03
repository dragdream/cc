package com.tianee.oa.core.partthree.util;


import java.util.List;


import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.partthree.bean.TeePartThreeRule;
import com.tianee.oa.core.partthree.service.TeePartThreeRuleService;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;

public  class TeePartThreeUtil {

	public static boolean  checkHasPriv(TeePerson loginUser,String ruleCode){
		
		TeePersonService personService=(TeePersonService) TeeBeanFactory.getBean("teePersonService");
		TeeSysParaService sysParaService=(TeeSysParaService) TeeBeanFactory.getBean("teeSysParaService");
		TeePartThreeRuleService ruleService=(TeePartThreeRuleService) TeeBeanFactory.getBean("teePartThreeRuleService");
		
		
		TeeSysPara para=sysParaService.getSysPara("IS_OPEN_PART_THREE");
		if(para!=null){
			if(TeeStringUtil.getInteger(para.getParaValue(), 0)==1){//开启
				//根据ruleCode  判断该规则是否开启
				TeePartThreeRule rule=ruleService.getRuleByRuleCode(ruleCode);
				if(rule.getIsOpen()==1){//开启
				    //获取该规则的权限人员
					int operPriv=rule.getOperPriv();//1=系统管理员  2=系统安全员  3=安全审计员
					if(operPriv==1){//系统管理员
						TeeSysPara para1=sysParaService.getSysPara("ADMIN_PRIV");
						String adminPriv=para1.getParaValue();
						List<TeePerson> list1=personService.getPersonByUuids(adminPriv);
						if(list1!=null&&list1.size()>0){
							for (TeePerson teePerson : list1) {
								if(teePerson.getUuid()==loginUser.getUuid()){
									return true;
								}
							}
							return false;
						}else{
							return false;
						}		
					}else if(operPriv==2){//系统安全员
						//判断当前登陆人是不是系统安全员
						TeeSysPara para2=sysParaService.getSysPara("SAFER_PRIV");
						String sfPriv=para2.getParaValue();
						List<TeePerson> list2=personService.getPersonByUuids(sfPriv);
						if(list2!=null&&list2.size()>0){
							for (TeePerson teePerson : list2) {
								if(teePerson.getUuid()==loginUser.getUuid()){
									return true;
								}
							}	
							return false;
						}else{
							return false;
						}
					}else if(operPriv==3){//安全审计员
						//判断当前登陆人是不是安全审计员
						TeeSysPara para3=sysParaService.getSysPara("AUDITOR_PRIV");
						String auditorPriv=para3.getParaValue();
						List<TeePerson> list3=personService.getPersonByUuids(auditorPriv);
						if(list3!=null&&list3.size()>0){
							for (TeePerson teePerson : list3) {
								if(teePerson.getUuid()==loginUser.getUuid()){
									return true;
								}
							}	
							return false;
						}else{
							return false;
						}
					}
					
					
				}else{//停用    则有权限  返回true
					return true;	
				}
			}else{//关闭
				return true;
			}	
		}else{
			return true;
		}
		
		return false;
	}
	
}
