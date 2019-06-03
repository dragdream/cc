<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/ztree.jsp" %>

<title>组织树</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<style type="text/css">
.menuList{
	padding-bottom:5px;
	padding-top:5px;
}
</style>

<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript">

/**
 * 马上加载
 */
 var zTreeObj ;
 function doInit(){
	var moduleId = "<%=TeeModelIdConst.DIARY_POST_PRIV%>";
	var deptIdZTree= "orgZtree";
	var param = {onClickFunc:personOnClick,defaultOpen:true};//onClickFunc:  点击事件    defaultOpen:默认展开  true-展开   false-不展开*/
	getPostOrgTreeTool.getPostOrgTreeByModule(moduleId ,deptIdZTree , param);
}
/**
 * 点击节点
 */
function personOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'dept'){
		parent.personinput.location = "<%=contextPath%>/salaryManage/personList/"+uuid.split(";")[0]+".action";
	}else if(uuid.split(";").length == 2 && uuid.split(";")[1] == 'personId'){
       
    }
};
</script>
</head>
<BODY onload="doInit()" style="padding-left:3px;">
<div class="panel-group" id="accordion" style="width:250px;">
  <div class="panel panel-default">
    <div class="panel-heading menuList" align="">
      <h5 class="panel-title" style="padding-bottom:0px;">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
          	人员列表
        </a>
      </h5>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      	<div class="panel-body" style="padding:0px;">
       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:280px; padding:2px;"></ul>
   	    </div>
    </div>
  </div>
</div>

</BODY>
</html>