/**
 * 点击查看信息后并把信息绑定到指定页面
 * @param seqId
 * @return
 */

function showInfoStr(result){
  var obj = document.getElementById("DMakeSealV61");
  if(!obj){
	document.write("<div align=\"center\"><div class=\"msg-info\" style='background: url(\"/common/zt_webframe/imgs/common_img/info.png\") no-repeat center 13px; padding: 70px 50px 15px; border-radius: 8px; border: 1px solid rgb(230, 230, 230); border-image: none; width: 300px; text-align: center; margin-top: 70px;'>控件加载失败！</div></div>"); 
    //$.MsgBox.Alert_auto("控件加载失败!");
    return false;
  }
  if(0 == obj.LoadData(result)){
    var vID = 0; 
    vID = obj.GetNextSeal(0);
    if(!vID){
     return true;
    }
    if(obj.SelectSeal(vID)) return false;
    var vSealID = obj.strSealID;
    var vSealName = obj.strSealName;
    var vSealWidth = obj.fSealWidthMM;
    var vSealHeight = obj.fSealHeightMM;
    var vCertCtrlNum = 0;
    var vID = 0 ;
    var vSignInfo = "无签名" ;
    var vCertInfo = "" ;
    var vTempFilePath = "" ;
    while(vID = obj.GetNextCtrlCert(vID)){
     //obj.GetCtrlCert(vID,""); 
     //alert(0);
     vTempFilePath = obj.GetTempFilePath(); 
     if(0 == obj.GetCtrlCert(vID,vTempFilePath)){
      if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE")){
        vCertInfo += "用户："+ obj.SubjectName+ "  序列号:" + obj.SerialNumber+"<br>";
      }
      obj.DelLocalFile(vTempFilePath);
     }
     vCertCtrlNum ++;
    }
    if(vCertCtrlNum>0)
      vCertInfo = "绑定的证书数量:"+vCertCtrlNum+"<br>"+vCertInfo;
    else
     vCertInfo = "无绑定证书";
    
    vTempFilePath = obj.GetSignCert(0); 
    if("" != vTempFilePath){
     if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE") ){
       vSignInfo +="用户:" + obj.SubjectName+"  序列号:" + obj.SerialNumber+"<br>";
     }
     obj.DelLocalFile(vTempFilePath);
    }
    
    document.getElementById("seal_id").innerHTML=vSealID;
    document.getElementById("seal_name").innerHTML=vSealName;
    document.getElementById("seal_size").innerHTML="宽度："+vSealWidth+" mm<br>高度："+vSealHeight+" mm"; 
    document.getElementById("seal_sign").innerHTML=vSealID;
    document.getElementById("seal_cert").innerHTML=vCertInfo;
    document.getElementById("seal_sign").innerHTML=vSignInfo;
    
  }else{
	document.write("<div align=\"center\"><div class=\"msg-info\" style='background: url(\"/common/zt_webframe/imgs/common_img/info.png\") no-repeat center 13px; padding: 70px 50px 15px; border-radius: 8px; border: 1px solid rgb(230, 230, 230); border-image: none; width: 300px; text-align: center; margin-top: 70px;'>读取印章数据失败！</div></div>"); 
    //$.MsgBox.Alert_auto("读取印章数据失败");
    
  }
}


/**
 * 点击查看信息后并把信息绑定到指定页面 --- 修改棉麻
 * @param seqId
 * @return
 */

function updatePwdShowInfoStr(result){
  var obj = document.getElementById("DMakeSealV61");
  if(!obj){
     $.MsgBox.Alert_auto("控件加载失败!");
     return false;
  }
  if(0 == obj.LoadData(result)){
    var vID = 0; 
    vID = obj.GetNextSeal(0);
    if(!vID){
    	return true;
    }
    if(obj.SelectSeal(vID)) return false;
    var vSealID = obj.strSealID;
    var vSealName = obj.strSealName;
    var vSealWidth = obj.fSealWidthMM;
    var vSealHeight = obj.fSealHeightMM;
    var vCertCtrlNum = 0;
    var vID = 0 ;
    var vSignInfo = "无签名" ;
    var vCertInfo = "" ;
    var vTempFilePath = "" ;
    while(vID = obj.GetNextCtrlCert(vID)){
     //obj.GetCtrlCert(vID,""); 
     //alert(0);
	     vTempFilePath = obj.GetTempFilePath(); 
	     if(0 == obj.GetCtrlCert(vID,vTempFilePath)){
	    	 if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE")){
	    		 vCertInfo += "用户："+ obj.SubjectName+ "  序列号:" + obj.SerialNumber+"<br>";
	    	 }
	    	 obj.DelLocalFile(vTempFilePath);
	     }
	     vCertCtrlNum ++;
    }
    if(vCertCtrlNum>0)
      vCertInfo = "绑定的证书数量:"+vCertCtrlNum+"<br>"+vCertInfo;
    else
     vCertInfo = "无绑定证书";
    
    vTempFilePath = obj.GetSignCert(0); 
    if("" != vTempFilePath){
     if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE") ){
       vSignInfo +="用户:" + obj.SubjectName+"  序列号:" + obj.SerialNumber+"<br>";
     }
     obj.DelLocalFile(vTempFilePath);
    }

    document.getElementById("picTr").style.display = "";
    document.getElementById("seal_id").innerHTML=vSealID;
    document.getElementById("seal_name").innerHTML=vSealName;
    
   
    document.getElementById("newPassword").value = "";
    document.getElementById("newPassword2").value = "";
   
  }else{
	  //关闭模态框
	  $(".modal-win-close").click();
	  $.MsgBox.Alert_auto("读取印章数据失败");
  }
}

/**
 * 印章管理 密码修改
 */
function updatePwdShowInfoStrForManager(result){
	var obj = document.getElementById("DMakeSealV62");
	  if(!obj){
		  $("body").html("<div align=\"center\"><div class=\"msg-info\" style='background: url(\"/common/zt_webframe/imgs/common_img/info.png\") no-repeat center 13px; padding: 70px 50px 15px; border-radius: 8px; border: 1px solid rgb(230, 230, 230); border-image: none; width: 300px; text-align: center; margin-top: 70px;'>控件加载失败！</div></div>"); 
		  failed=true;
		  return false;
	  }
	  if(0 == obj.LoadData(result)){
	    var vID = 0; 
	    vID = obj.GetNextSeal(0);
	    if(!vID){
	    	return true;
	    }
	    if(obj.SelectSeal(vID)) return false;
	    var vSealID = obj.strSealID;
	    var vSealName = obj.strSealName;
	    var vSealWidth = obj.fSealWidthMM;
	    var vSealHeight = obj.fSealHeightMM;
	    var vCertCtrlNum = 0;
	    var vID = 0 ;
	    var vSignInfo = "无签名" ;
	    var vCertInfo = "" ;
	    var vTempFilePath = "" ;
	    while(vID = obj.GetNextCtrlCert(vID)){
	     //obj.GetCtrlCert(vID,""); 
	     //alert(0);
		     vTempFilePath = obj.GetTempFilePath(); 
		     if(0 == obj.GetCtrlCert(vID,vTempFilePath)){
		    	 if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE")){
		    		 vCertInfo += "用户："+ obj.SubjectName+ "  序列号:" + obj.SerialNumber+"<br>";
		    	 }
		    	 obj.DelLocalFile(vTempFilePath);
		     }
		     vCertCtrlNum ++;
	    }
	    if(vCertCtrlNum>0)
	      vCertInfo = "绑定的证书数量:"+vCertCtrlNum+"<br>"+vCertInfo;
	    else
	     vCertInfo = "无绑定证书";
	    
	    vTempFilePath = obj.GetSignCert(0); 
	    if("" != vTempFilePath){
	     if(0 == obj.CertGetInfo(vTempFilePath,"CERTDATAFILE") ){
	       vSignInfo +="用户:" + obj.SubjectName+"  序列号:" + obj.SerialNumber+"<br>";
	     }
	     obj.DelLocalFile(vTempFilePath);
	    }
	    document.getElementById("apply_body_manage").style.display = "";
	    document.getElementById("seal_id_manage").innerHTML=vSealID;
	    document.getElementById("seal_name_manage").innerHTML=vSealName;
	    
	    document.getElementById("newPassword_manage").value = "";
	    document.getElementById("newPassword2_manage").value = ""; 
	  }else{
		  //隐藏保存按钮
		  failed=true;
		  $("body").html("<div align=\"center\"><div class=\"msg-info\" style='background: url(\"/common/zt_webframe/imgs/common_img/info.png\") no-repeat center 13px; padding: 70px 50px 15px; border-radius: 8px; border: 1px solid rgb(230, 230, 230); border-image: none; width: 300px; text-align: center; margin-top: 70px;'>读取印章数据失败！</div></div>"); 
	  }
}

/**
 * 批量删除印章
 */
function deleteSeal(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项记录");
		return ;
	}
	
	
	$.MsgBox.Confirm ("提示", "确定要删除所选中记录？", function(){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		var url = contextPath +  "/sealManage/delSeal.action?sids=" + ids;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}  
	  });

}

/**
 *开启印章
 */
function openSeal(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项记录");
		return ;
	}
	
	$.MsgBox.Confirm ("提示", "确定要开启所选中印章？", function(){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		var url = contextPath +  "/sealManage/openOrstopSeal.action";
		var para = {sids:ids , isFlag:0};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("开启成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		} 
	  });
}


/**
 *暂停印章
 */
function stopSeal(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项记录");
		return ;
	}
	
	$.MsgBox.Confirm ("提示", "确定要停用所选中印章？", function(){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		var url = contextPath +  "/sealManage/openOrstopSeal.action?";
		var para = {sids:ids , isFlag:1};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("停用成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	  });

}