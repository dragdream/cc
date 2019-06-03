<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid")==null?"0":request.getParameter("sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var sid = "<%=sid%>";
var parentId=0;
function doInit(){
	getEvalType();
	getTemplateItemTree();
	getScoreLevelList();
	getScoringDesignList();
	if(sid!="" && sid!=null && sid!="null"){
		var url = "<%=contextPath%>/TeeEvalTemplateController/getById.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}
}

function commit(){
	if($("#form1").form("validate")){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeEvalTemplateController/addOrUpdate.action";
		var json = tools.requestJsonRs(url,param);
		if(json.rtState){
			sid = json.rtData.sid;
			top.$.jBox.tip(json.rtMsg,"success");
			location.href = contextPath+"/system/subsys/evaluation/template/addOrEidtTemplate.jsp?sid="+json.rtData.sid;
		}else{
			top.$.jBox.tip(json.rtMsg,"error");
		}
	}
}

function goBack(){
	location.href =  contextPath+'/system/subsys/evaluation/template/index.jsp';
}


/**
 * 隐藏显示
 */
function setOption(id){
	if(id!=1){
		if(sid==null || sid ==0 || sid =='' || sid=="0" || sid=='null'){
			alert("请先保存基本信息！");
			return;
		}
	}
	for(var i=1;i<5;i++){
		if(id!=i){
			$('#option' + i).css('display',"none");
		}
	}
	$('#option' + id).toggle();
}

function getEvalType(){
	var url = contextPath+'/TeeEvalTypeController/datagrid.action'
	var json = tools.requestJsonRs(url,{page:1,pageSize:10000});
	var html="";
	for(var i=0;i<json.rows.length;i++){
		html+="<option value='"+json.rows[i].sid+"'>"+json.rows[i].name+"</option>";
	}
	$("#evalTypeId").html($("#evalTypeId").html()+html);
}


function getTemplateItemTree(){
	var url =  "<%=contextPath %>/TeeEvalTemplateItemController/getTemplateItemTree.action?evalTemplateId="+sid;
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			asyncExtend:true,
			onAsyncSuccess:onAsyncSuccessFunc
		};
	zTreeObj = ZTreeTool.config(config); 	
}


//刷新整个树二不刷新右侧页面
function refreshTree(){
	var url =  "<%=contextPath %>/TeeEvalTemplateItemController/getTemplateItemTree.action?evalTemplateId=" + sid;
	var config = {
			zTreeId:"selectFolderZtree",
			requestURL:url,
           	onClickFunc:onClickTree,
			async:false,
			asyncExtend:true,
			onAsyncSuccess:onAsyncSuccessFunc
		};
	zTreeObj = ZTreeTool.config(config); 	
}
/**
 *  点击
 */
function onClickTree(event, treeId, treeNode) {
	//alert(treeId + " treeNode>>" + treeNode);
	var sid = treeNode.id;
	//var folderName = treeNode.name;
	//alert(sid + " folderName>>" + folderName);
	//$("#folderSid").val(sid);
	openFolderFunc(sid);
	
}

/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	//alert(tools.jsonObj2String(jsonData));
	//返回第一个id，然后右侧显示该目录文件
	var parentSid = jsonData.rtData.parentSid;
	if(parentSid>0){
	  openFolderFunc(parentSid);
	}
}

/**
 * 获取树对象
 */
function getZTrreObj(){
  zTreeObj = $.fn.zTree.getZTreeObj("selectFolderZtree"); 
  return zTreeObj;
}

/**
 * 更新树节点
 * var nodeObj = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function updateZTreeNode(nodeObj){
	if(nodeObj && nodeObj.id){
		zTreeObj = getZTrreObj(); 
		var node = zTreeObj.getNodeByParam("id",nodeObj.id,null);
		if(node){
			if(nodeObj.name){
				node.name = nodeObj.name;
			}
			if(nodeObj.iconSkin){
				node.iconSkin = nodeObj.iconSkin;
			}
			zTreeObj.updateNode(node);
		}
		
	}
}

/**
 * 在节点id为parentNodeId的节点下创建树节点newNode
 * var newNode = {id:nodeId,name:nodeName,iconSkin:iconSkin,pid:nodeParentId};
 */
function createZTreeNode(parentNodeId,newNode){
  var zTreeObj = getZTrreObj();
  var parentNode = zTreeObj.getNodeByParam("id",parentNodeId,null);
  zTreeObj.addNodes(parentNode, newNode,false);
}

 /**
  * 根据节点id删除节点（多个节点id用逗号隔开）
  */
 function deledteZTreeNode(nodeIds){
   if(nodeIds){
     nodeIds +="";
     var zTreeObj = getZTrreObj();
     var nodeIdArray = nodeIds.split(",");
     for(var i=0;i<nodeIdArray.length;i++){
       if(nodeIdArray[i]){
         var deleteNode = zTreeObj.getNodeByParam("id",nodeIdArray[i],null);
         if(deleteNode){
           zTreeObj.removeNode(deleteNode);
         }
       }
     }
   }
 }

 /**
 * 刷新 zTree 
 */
function reloadZTreeNodeFunc(){
  var zTreeObj = getZTrreObj();
  alert(zTreeObj);
  zTreeObj.refresh();
}

/**
 * 打开文件夹
 * @param sid
 */
function openFolderFunc(sid){
	parentId = sid;
	var url = contextPath + "/TeeEvalTemplateItemController/getEvalTemplateItem.action?level="+parentId;
	var json = tools.requestJsonRs(url);
	var html="";
	if(json.rtState){
		var data = json.rtData;
		for(var i=0;i<data.length;i++){
			html+="<tr class='TableData'><td>"+data[i].name+"</td><td>"+data[i].number+"</td><td>"+data[i].standard+"</td><td>"+data[i].range1+"~"+data[i].range2+"</td><td>"+data[i].remark+"</td>"+
			"<td><a href='javascript:void(0)' onclick='editTemplateItem("+data[i].sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='delTemplateItem("+data[i].sid+")'>删除</a></td></tr>";
		}
	}else{
		html="<tr class='TableData'><td colspan='6'>没有找到相关记录！</td></tr>";
	}
	$("#itemList").html(html);
}


function addTemplateItem(){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditTemplateItem.jsp?evalTemplateId="+sid+"&parentId="+parentId;
	bsWindow(url,"添加考核项",{width:"800",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			refreshTree();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function editTemplateItem(templateItemId){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditTemplateItem.jsp?evalTemplateId="+sid+"&sid="+templateItemId;
	bsWindow(url,"修改考核项",{width:"800",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			refreshTree();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}
function addScoringDesign(){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditScoringDesign.jsp?evalTemplateId="+sid;
	bsWindow(url,"添加考核项",{width:"800",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			getScoringDesignList();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}
function addScoreLevel(){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditScoreLevel.jsp?evalTemplateId="+sid;
	bsWindow(url,"添加考核等级",{width:"500",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			getScoreLevelList();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function getScoreLevelList(){
	var url = contextPath+'/TeeEvalScoreLevelController/datagrid.action?evalTemplateId='+sid;
	var json = tools.requestJsonRs(url,{page:1,pageSize:10000});
	var html="";
	for(var i=0;i<json.rows.length;i++){
		var data = json.rows[i];
		html+="<tr class='TableData'><td>"+data.name+"</td><td>"+data.range1+"< 分数 <= "+data.range2+
		"</td><td><a href='javascript:void(0)' onclick='editScoreLevel("+data.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='delScoreLevel("+data.sid+")'>删除</a></td></tr>";
	}
	if(json.rows.length<1){
		html="<tr class='TableData'><td colspan='3'>没有相关数据</td></tr>";
	}
	$("#scoreLevelList").html(html);
}

function getScoringDesignList(){
	var url = contextPath+'/TeeEvalScoringDesignController/datagrid.action?evalTemplateId='+sid;
	var json = tools.requestJsonRs(url,{page:1,pageSize:10000});
	var html="";
	for(var i=0;i<json.rows.length;i++){
		var data = json.rows[i];
		html+="<tr class='TableData'><td>"+data.scoreItemTypeName+"</td><td></td><td></td><td>"+
		"</td><td><a href='javascript:void(0)' onclick='editScoringDesign("+data.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='delScoringDesign("+data.sid+")'>删除</a></td></tr>";
	}
	if(json.rows.length<1){
		html="<tr class='TableData'><td colspan='5'>没有相关数据</td></tr>";
	}
	$("#scoringDesignList").html(html);
}


function editScoreLevel(scoreLevelId){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditScoreLevel.jsp?evalTemplateId="+sid+"&sid="+scoreLevelId;
	bsWindow(url,"修改考核等级",{width:"500",height:"200",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			getScoreLevelList();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function editScoringDesign(scoringDesignId){
	var url = contextPath+"/system/subsys/evaluation/template/addOrEditScoringDesign.jsp?evalTemplateId="+sid+"&sid="+scoringDesignId;
	bsWindow(url,"修改考核项",{width:"800",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		if(cw.commit()){
			getScoringDesignList();
			return true;//返回true就是关闭窗口
		}
		return false;//返回false不关闭窗口
	}});
}

function delScoreLevel(scoreLevelId){
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+'/TeeEvalScoreLevelController/deleteById.action';
			var json = tools.requestJsonRs(url,{sids: scoreLevelId});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"success");
				getScoreLevelList();
			}else{
				top.$.jBox.tip('删除失败!',"error");
			}
		}
	});
}

function delScoringDesign(scoringDesignId){
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+'/TeeEvalScoringDesignController/deleteById.action';
			var json = tools.requestJsonRs(url,{sids: scoringDesignId});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"success");
				getScoringDesignList();
			}else{
				top.$.jBox.tip('删除失败!',"error");
			}
		}
	});
}

function delTemplateItem(templateItemId){
	top.$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+'/TeeEvalTemplateItemController/deleteById.action';
			var json = tools.requestJsonRs(url,{sids: templateItemId});
			if(json.rtState){
				refreshTree();
				top.$.jBox.tip(json.rtMsg,"success");
			}else{
				top.$.jBox.tip('删除失败!',"error");
			}
		}
	});
}
</script>
</head>
<body onload="doInit();">
<form id="form1" name="form1">
	<div class="moduleHeader">
			<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;考核模板</b>
	</div>
	<table class='TableBlock' style='width:90%;margin:0 auto;'>
		 <tr onClick="setOption('1')" title="点击展开/收缩选项">
		    <td nowrap class="TableHeader" colspan="2" style="cursor:pointer; text-align:left;"> <span  class="Big3" >考核基本信息设置</span></td>
		 </tr>
		 <tbody id="option1">
			<tr class='TableData' align='left'>
				<td width="80px">
					主题<span style="color:red;font-weight:bold;">*</span>：
				</td>
				<td>
					<input type="text" id="subject" name="subject" required style="width:180px"class="BigInput easyui-validatebox" validType="maxLength[100]"/>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					类型：
				</td>
				<td>
					<select id='evalTypeId' name='evalTypeId' class='BigSelect'>
						<option value='0'>请选择考核类型</option>
					</select>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					启动类型：
				</td>
				<td>
					<select id='autoType' name='autoType' class='BigSelect'>
						<option value='0'>请选择启动方式</option>
						<option value='1'>手动</option>
						<option value='2'>自动</option>
					</select>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					有效期：
				</td>
				<td>
					<input type="text" id="validDays" name="validDays" required style="width:100px"class="BigInput easyui-validatebox" validType="intege[]"/>(工作日)
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					考核对象（按人员选择）：
				</td>
				<td>
					<textarea readonly id='targetsUserNames' name='targetsUserNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='targetsUsers' name='targetsUsers' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['targetsUsers','targetsUserNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('targetsUsers','targetsUserNames')">清空</a>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					考核对象（按部门选择）：
				</td>
				<td>
					<textarea readonly id='targetsDeptNames' name='targetsDeptNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='targetsDepts' name='targetsDepts' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['targetsDepts','targetsDeptNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('targetsDepts','targetsDeptNames')">清空</a>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					考核对象（按角色选择）：
				</td>
				<td>
					<textarea readonly id='targetsRoleNames' name='targetsRoleNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='targetsRoles' name='targetsRoles' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['targetsRoles','targetsRoleNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('targetsRoles','targetsRoleNames')">清空</a>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					考核查看权限：
				</td>
				<td>
					<textarea readonly id='viewPrivsUserNames' name='viewPrivsUserNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='viewPrivsUserIds' name='viewPrivsUserIds' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['viewPrivsUserIds','viewPrivsUserNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('viewPrivsUserIds','viewPrivsUserNames')">清空</a>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					考核管理：
				</td>
				<td>
					<textarea readonly id='managePrivsUserNames' name='managePrivsUserNames' class="BigTextarea readonly" cols='60' rows='5'></textarea>
					<input id='managePrivsUserIds' name='managePrivsUserIds' class="BigInput" type='hidden'/>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['managePrivsUserIds','managePrivsUserNames'])">选择</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" class="orgClear" onClick="clearData('managePrivsUserIds','managePrivsUserNames')">清空</a>
				</td>
			</tr>
			<tr class='TableData' align='left'>
				<td width="80px">
					备注：
				</td>
				<td>
					<textarea  id='remark' name='remark' class="BigTextarea" cols='60' rows='5'></textarea>
				</td>
			</tr>
		</tbody>
		 <tr onClick="setOption('2')" title="点击展开/收缩选项">
		    <td nowrap class="TableHeader" colspan="2" style="cursor:pointer;text-align:left;"> <span  class="Big3" > 考核项目设计</span></td>
		  </tr>
		   <tbody id="option2" style="display:none;">
		   			<tr class='TableData' align='left'>
						<td width="80px" valign='top'>
							<ul id="selectFolderZtree" class="ztree" style="border:0px;width:95%;height:"></ul>
						</td>
						<td valign='top'>
							<div style='text-align:right;'>
								<input type='button' class='btn btn-primary' value='添加' onclick='addTemplateItem()'/>
							</div>
							<div style='margin-top:5px;text-align:center;' >
									<table class='TableBlock' style='width:100%;'>
										<tr class='TableHeader'>
											<td>项目名称</td><td>排序号</td><td>标准分</td><td>评分范围</td><td>备注</td><td>操作</td>
										</tr>
										<tbody id='itemList'>
											
										</tbody>
									</table>
							</div>
						</td>
					</tr>
		   </tbody>
		 <tr onClick="setOption('3')" title="点击展开/收缩选项">
		    <td nowrap class="TableHeader" colspan="2" style="cursor:pointer;text-align:left;"> <span  class="Big3" >考核评分设计</span></td>
		 </tr>
		  <tbody id="option3" style="display:none;">
		   		<tr class='TableData' align='left'>
						<td colspan='2'>
							<div style='text-align:right;'>
								<input type='button' class='btn btn-primary' value='添加' onclick='addScoringDesign()'/>
							</div>
							<div style='margin-top:5px;text-align:center;' >
									<table class='TableBlock' style='width:100%;'>
										<tr class='TableHeader'>
											<td>评分类型</td><td>权重</td><td>评分人员</td><td>考核项目</td><td>操作</td>
										</tr>
										<tbody id='scoringDesignList'>
											
										</tbody>
									</table>
							</div>
						</td>
					</tr>
		  </tbody>
		 <tr onClick="setOption('4')" title="点击展开/收缩选项">
		    <td nowrap class="TableHeader" colspan="2" style="cursor:pointer;text-align:left;"> <span  class="Big3" >考核级别设计</span></td>
		 </tr>
		  <tbody id="option4" style="display:none;">
		   		<tr class='TableData' align='left'>
						<td colspan='2'>
							<div style='text-align:right;'>
								<input type='button' class='btn btn-primary' value='添加' onclick='addScoreLevel()'/>
							</div>
							<div style='margin-top:5px;text-align:center;' >
									<table class='TableBlock' style='width:100%;'>
										<tr class='TableHeader'>
											<td>等级名称</td><td>分数范围</td><td>操作</td>
										</tr>
										<tbody id='scoreLevelList'>
											
										</tbody>
									</table>
							</div>
						</td>
					</tr>
		  </tbody>
	</table>
	<br/>
	<div id="control" style='margin:0 auto;text-align:center; height:28px;line-height:28px;width:"90%"'>
		<input id="sid" name="sid" type='hidden'value="<%=sid %>"/>
		<input id="saveInfo" name="saveInfo" type='button' class="btn btn-primary" value="保存" onclick='commit();'/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="back" name="back" type='button' class="btn btn-primary" value='返回' onclick='goBack();'/>
	</div>
</form>
</body>
</html>