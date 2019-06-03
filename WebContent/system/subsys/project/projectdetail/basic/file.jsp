<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  projectId=TeeStringUtil.getString(request.getParameter("uuid"));
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目文档</title>
</head>
<script>
var projectId="<%=projectId%>";
var loginUserId=<%=loginUser.getUuid()%>;
var status=0;//项目状态
//初始化方法
function doInit(){
	getProjectStatus();
	getData();
	
}

//根据项目主键  获取项目状态
function getProjectStatus(){
	var url=contextPath+"/projectController/getInfoByUuid.action";
	var json=tools.requestJsonRs(url,{uuid:projectId});
	if(json.rtState){
		var data=json.rtData;
		status=data.status;
	}
}
//获取项目文档目录
function  getData(){
	var url=contextPath+"/projectFileController/getFileData.action";
	var json=tools.requestJsonRs(url,{projectId:projectId});
	if(json.rtState){
		var data=json.rtData;
		for(var i=0;i<data.length;i++){
			var html="<div class=\"clearfix\" style=\"padding-top: 10px;\">"
			           +"<b style=\"font-size: 14px\">"+data[i].diskName+"</b>";
		    var isCreaterOrManagerOrMember=data[i].isCreaterOrManagerOrMember;
		 
		    if(isCreaterOrManagerOrMember==1&&status==3){//判断当前登陆人  是不是项目的创建人   负责人  或者项目成员  并且  项目状态是办理中
		    	html+="<div class=\"fr right\">"
		    	     +"<input type=\"button\" class=\"btn-win-white\" value=\"上传\" onclick=\"upload("+data[i].diskId+")\"/>"
		    	     +"</div>";
		      }         
			           
			     html+="<span class=\"basic_border\" style=\"padding-top: 5px;\"></span>"
			           +"<div style=\"padding-top: 10px;\">"
		               +"<table style=\"border-collapse: collapse;border: 1px solid #f2f2f2;\" width=\"100%\" align=\"center\" >"
		               +"<tbody>"
		               +"<tr style=\"line-height:35px;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8;\">"
		     	       +"<td style=\"text-indent:10px;width:40%;\">文件名</td>"
		     	       +"<td style=\"width:20%;\">上传者</td>"
		      		   +"<td style=\"width:20%;\">上传时间</td>";
		      		   

			  if(status==3){// 项目状态是办理中
				    	html+="<td style=\"width:20%;\">操作</td>"
			               +"</tr>";
			  }else{
				  html+="</tr>"; 
			  }    		   
		      		   
		      		   
		     var fileList=data[i].fileList;
		     
		     if(fileList!=null&&fileList.length>0){
		    	 for(var j=0;j<fileList.length;j++){
			    	 html+="<tr style='line-height:35px;border-bottom:1px solid #f2f2f2;'>"
						  +"<td nowrap align='center' style='width:40%;text-indent:10px;' clazz='"+fileList[j].fileId+"'></td>"
						  +"<td nowrap align='center' style='width:20%;'>" + fileList[j].createrName + "</td>"
						  +"<td nowrap align='center' style='width:20%;'>" + fileList[j].createTimeStr + "</td>";
						  
						if(status==3){
							 html+="<td nowrap align='center' style='width:20%;'>";
							   if(loginUserId==fileList[j].createrId&&status==3){//当前登陆人是 文档的创建人  并且   项目状态是办理中
								  html+="<a href=\"#\" onclick=\"del("+fileList[j].sid+")\">删除</a>";
							    }  
							 html+= "</td>"                  
					              + "</tr>";
						} else{
							html+="</tr>";
						} 
						
			       }
		     }else{
		    	 var colspanNum=0;
		    	 if(status==3){
		    		 colspanNum=4;
		    	 }else{
		    		 colspanNum=3;
		    	 }
		    	 
		    	html+="<tr><td colspan=\""+colspanNum+"\" align=\"center\" valign=\"top\">" 
		    		+"<div class=\"msg-info\" style=\"margin-top: 20px;margin-bottom: 20px; width: 300px; text-align: center; border: 1px solid rgb(230, 230, 230); border-radius: 8px; padding: 70px 50px 15px; background: url(&quot;/common/zt_webframe/imgs/common_img/info.png&quot;) center 13px no-repeat;\">"
		    	    +"暂无符合条件的数据！"
		    	    +"</div>"
		    	    +"</td></tr>";
		    	
		    	 
		     }       
		       html+="</tbody></table></div></div><br><br><br>";
		       $("#tbody").append(html);
		       
		       if(fileList!=null&&fileList.length>0){
		    	 for(var k=0;k<fileList.length;k++){
		    		 var attachModel = {fileName:fileList[k].fileName,
		    		          priv:1+2
		    		          ,ext:fileList[k].fileExt,sid:fileList[k].attchId};
	    		     var fileItem = tools.getAttachElement(attachModel,{});
	    		     $("[clazz='"+fileList[k].fileId+"']").append(fileItem);
			     }
			   }
		}	
	}
}

//上传
function upload(diskId){
	 	var url = contextPath+"/system/subsys/project/projectdetail/basic/uploadFile.jsp?sid="+diskId+"&&projectId="+projectId;
	 	bsWindow(url,"上传文件",{width:"650",height:"250",
	 		buttons:[],
	 		submit:function(v,h){
	 		  /*  var cw = h[0].contentWindow;
	 		   cw.doSave(); */
	 		}
	 	});

}
//删除
function del(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该文档？", function(){
		 var url=contextPath+"/projectFileController/delBySid.action";
		 var json=tools.requestJsonRs(url,{sid:sid});
		 if(json.rtState){
			 
			 window.location.reload();
			 $.MsgBox.Alert_auto("删除成功！");
		 }
	  });
}
</script>
<body id="tbody" onload="doInit()">
  
</body>
</html>