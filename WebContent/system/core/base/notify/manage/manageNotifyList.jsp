<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		String groupId = request.getParameter("groupId");
		String seqIds = request.getParameter("seqIds");
		if(groupId == null || "".equals(groupId)){
			groupId = "0";
		}
		String typeId = request.getParameter("typeId");
		typeId=typeId==null?"":typeId;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>

<title>公告管理</title>
	
<script type="text/javascript" charset="UTF-8">
	var contextPath = '<%=contextPath%>';
	var datagrid;
	var userDialog;
	var userForm;
	var passwordInput;
	var userRoleDialog;
	var userRoleForm;
	var seqIds = '<%=seqIds%>';
	var groupId = '<%=groupId%>';
	var typeId = '<%=typeId%>';
	var para = {};
	para['groupId'] = groupId;
	para['seqIds'] = seqIds;
	para['typeId'] = typeId;
	$(function() {
		
		//判断是否需要校验
		var isAud = getSysParamByNames('NOTIFY_AUDITING_SINGLE');
		var isAudState = false;
		if(isAud.length>0 && isAud[0].paraValue == '1'){
			isAudState = true;
		}
		datagrid = $('#datagrid').datagrid({
			url : contextPath + '/teeNotifyController/getManageNotify.action',
			view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
			//	toolbar : '#toolbar',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
			fit : true,
			queryParams:para,
			fitColumns : true,
			nowrap : true,
			border : false,
			idField : 'sid',
			sortOrder: 'desc',
			striped: true,
			singleSelect:false,
			remoteSort: true,
			toolbar: '#toolbar',
			columns : [ [ 
			 {field:'ck',checkbox:true},{
				title : 'id',
				field : 'sid',
				width : 200,
				hidden:true
				//sortable:true
				
			}, {
				field : 'fromPersonName',
				title : '发布人',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				
				return "<B>"+rowData.fromPersonName+"</B>";
				}
			}
			,{
				field : 'auditerId',
				title : '审批人',
				width : 10,
				hidden:true
			}
			,{
				field : 'typeId',
				title : '类型',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					return rowData.typeDesc;
				}
			}
			,{
				field : 'typeDesc',
				title : '类型描述',
				width : 100,
				hidden : true
			}
			,{
				field : 'top',
				title : '置顶',
				width : 10,
				hidden : true
			},{
				field : 'subject',
				title : '标题',
				width : 300,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
					var top = rowData.top;
					var topDesc = "";
					if(top == '1'){
						topDesc = "red";
					}
					return "<B>标题：</B>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick=loadDetail('"+rowData.sid+"') style='color:" + topDesc + "'>"+rowData.subject+"</a>";
				}
			},{
				field : 'userId',
				title : '发布范围',
				width : 240,
				formatter:function(value,rowData,rowIndex){
				var deptNames = rowData.toDeptNames;
				var personNames = rowData.toUserNames;
				var toRolesNames = rowData.toRolesNames;
				var renderHtml = "";
				if(deptNames && deptNames != ""){
					renderHtml = renderHtml + "<div title="+deptNames+" style='line-height:20px;'><B>部门：</B>&nbsp;&nbsp;"+deptNames+""+"</div>";
				}
				if(personNames && personNames != ""){
					renderHtml = renderHtml + "<div title="+personNames+" style='line-height:20px;'><B>人员：</B>&nbsp;&nbsp;"+personNames+""+"</div>";
				}
				if(toRolesNames && toRolesNames != ""){
					renderHtml = renderHtml + "<div title="+toRolesNames+" style='line-height:20px;'><B>角色：</B>&nbsp;&nbsp;"+toRolesNames+""+"</div>";
				}
				if(rowData.allPriv==1){
					renderHtml="<div>全体人员</div>";
				}
				return "<div>"+renderHtml+"</div>";
				}
			},{
				field : 'createDate',
				title : '创建时间',
				width : 150,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return getFormatDateTimeStr(rowData.createDate , 'yyyy-MM-dd HH:mm');
				}
			},			
			{
				field : 'sendTime',
				title : '发布时间',
				width : 150,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				 return getFormatDateTimeStr(rowData.sendTime , 'yyyy-MM-dd HH:mm');
				}
			}
			,{
				field : 'publish',
				title : '发布状态',
				width : 100,
				sortable : true,
				formatter:function(value,rowData,rowIndex){
				var state = rowData.publish;
				var reValue = "";
				//0-未发布
				//1-已发布（状态列显示为“生效”）
				//2-待审批
				//3-未通过
				if(state == 0){
					reValue = "未发布";
			    }else if(state == 1){
			    	reValue = "生效";
				}else if(state == 2){
					reValue = "待审批";
				}else if(state == 3){
					reValue = "未通过";
				}
				 return reValue;
				}
			},{
				field:'_manage',title:'操作',width : 200,formatter:function(value,rowData,rowIndex){
					var state = rowData.publish;
					var opt = '<a href="#" onclick="lookdetail('+rowData.sid+')" value="查阅情况" >查阅情况</a>&nbsp;&nbsp;<a href="#" onclick="editNotify('+rowData.sid+')" value="修改" >修改</a>';
					var opt1 = opt + '&nbsp;&nbsp;<a href="#" value="生效" onclick="useOrEndNotify('+rowData.sid+',1)" >生效</a>';
					var opt2 = opt + '&nbsp;&nbsp;<a href="#" value="终止" onclick="useOrEndNotify('+rowData.sid+',0)" >终止</a>';
					//0-未发布
					//1-已发布（状态列显示为“生效”）
					//2-待审批
					//3-未通过
					
					if(state == 0 && !isAudState){
						opt = opt1;
		
				    }else if(state == 1){
				    	opt = opt2;
					}else if(state == 2){
						opt = opt;
					}else if(state == 3){
						opt = opt;
					}
					var opt = opt + '&nbsp;&nbsp;<a href="#" value="删除" onclick="delNotify('+rowData.sid+')" >删除</a>';
					return opt ;
				}
			}
			] ]
		});
		
	});

    function useOrEndNotify(seqId , publish){
		var para = {sid:seqId , publish: publish};
    	var url = "<%=contextPath %>/teeNotifyController/updateNotifyPublish.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("操作成功！");
			datagrid.datagrid('reload');
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
    }


    /**
    *查看详情
    */
    function loadDetail(seqId){
        var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id="+seqId+"&isLooked=0";
        openFullWindow(url);
//         top.bsWindow(url,"公告详情",{width:"900px", height:"400px", buttons:[{name:"关闭"}],submit:function(v,h){
//             if(v=="关闭"){
// 				return true;
//             }
//         }});
     }

    
    /* 查看阅读情况 */
    function lookdetail(seqId){
        var url = contextPath + "/system/core/base/notify/manage/lookerInfoList.jsp?id="+seqId;
    	//$.jBox.open("iframe:"+url,"查看公告",800,400,{buttons: { '关闭': true }
    	//});
        bsWindow(url,"阅读情况",{width:"600", height:"300",
        buttons:[{name:"清空查阅详情",classStyle:"btn-alert-blue"},{name:"关闭",classStyle:"btn-alert-gray"}],
        submit:function(v,h){
        	var cw = h[0].contentWindow;
        	if(v=="清空查阅详情"){
        		cw.clearNotifyInfo();
				//return true;
            }
            if(v=="关闭"){
				return true;
            }
        }});
     }
    
    
    
	//编辑 公告
    function editNotify(seqId){
    	window.location.href ="<%=contextPath%>/system/core/base/notify/manage/addNotify.jsp?id="+seqId+"&typeId="+typeId;
    }
	
	/* 删除byId */
	function delNotify(sid){
		$.MsgBox.Confirm ("提示","确定要删除吗？", function(){
			var url = "<%=contextPath%>/teeNotifyController/delNotifyById.action";
	    	var jsonRs = tools.requestJsonRs(url,{"id":sid});
	    	if(jsonRs.rtState){
	    		$.MsgBox.Alert_auto("删除成功!");
	    		$('#datagrid').datagrid("reload");
	    	}else{
	    		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	    	}	
		});
	}
	
	//获取选中的值
	function getSelectItem(){
		var selections = $('#datagrid').datagrid('getSelections');
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		return ids;
	}
	
	function delBatch(){
		var ids = getSelectItem();
		if(ids.length==0){
			$.MsgBox.Alert_auto("未选中任何公告！");
			return ;
		}
		 $.MsgBox.Confirm ("提示", "是否确认删除所选公告？", function(){
			 var url = contextPath + "/teeNotifyController/delNotifyBatch.action";
				var para = {ids:ids};
				var json = tools.requestJsonRs(url,para);
				if(json.rtState){
					$.MsgBox.Alert_auto("删除成功！");
					datagrid.datagrid('reload');
					datagrid.datagrid('unselectAll');
				}  
		  });
	}
	
	</script>
</head>
<body style="margin:0px;overflow:hidden">
<div id="toolbar" class ="clearfix" >
   <div style="float:right; "  class="setHeight">
      <input type="button" class="btn-del-red" value="批量删除" onclick="delBatch()" />
   </div>
</div>
<table id="datagrid" fit="true"></table>

</body>


</html>