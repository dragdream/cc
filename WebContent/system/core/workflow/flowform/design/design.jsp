<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 int formId=TeeStringUtil.getInteger(request.getParameter("formId"),0);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	    <link rel="stylesheet" type="text/css" href="bootstrap.css">
    <link rel="stylesheet" type="text/css" href="jquery-ui.css">
   	<link rel="stylesheet" type="text/css" href="mobile-ui.css">
   	<script type="text/javascript" src="jquery.min.js"></script>
   	<script type="text/javascript" src="jquery-ui.js"></script>
    <script src="json2.js"></script>
    <script type="text/javascript" src="main.js?v=2"></script>
	<title>移动端表单设计器</title>
	<script type="text/javascript">
	var contextPath = "<%=contextPath%>";
	var formId2=<%=formId%>;
	function saveItemGroup(){
		var cellTitle=$(".mbui_cells_title");
		var str="[";
		if(cellTitle!=null && cellTitle.length>0){
			for(var i=0;i<cellTitle.length;i++){
				var titleChildren=$(cellTitle[i]).children();
				var groupId=$(titleChildren[0]).val();
				var groupName=$(titleChildren[1]).html();
				var nextChildren=$(cellTitle[i]).next().children();
				var items="";
				if(nextChildren!=null){
					for(var j=0;j<nextChildren.length;j++){
						var itemId=$(nextChildren[j]).children("input").val();
						items+=itemId+",";
						//str+="{\"itemId\":\""+itemId+"\"},";
					}
					items=items.substring(0,items.length-1);
				}
				str+="{\"groupId\":\""+groupId+"\",\"groupName\":\""+groupName+"\",\"formId\":\""+formId2+"\",\"items\":\""+items+"\"},";
			}
			str=str.substring(0,str.length-1)+"]";
		}
		var url = contextPath+"/flowForm/updateFormItemsByFormGroup.action";
		var json = tools.requestJsonRs(url,{strArr:str});
    	if(json.rtState){
    	  alert("保存成功");
    	  window.location.reload();
    	}
	}
	
	</script>
</head>
<body>
<div class="mbui_container">
		<div class="mbui_header clearfix">
			<h3 class="mbui_header_title pull-left"><font color="gray" size="3">基本信息录入表: 移动表单设计器只支持调整控件分组和布局，不支持修改控件属性</font></h3>
			<div class="pull-right">
				<button type="button" class="btn btn-primary mbui_header_save">保存</button>
			</div>
		</div>
		<div class="mbui_main clearfix">
			<div class="mbui_panel mbui_panel_form">
				<legend class="">设计器<span class="group_add_btn">添加分组</span></legend>
				<div class="mbui_group_list">
					<div class="mbui_panel_form_tips">
						<span>提示：所有控件均须包含在组容器中</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- form templates start -->
	<!-- 说明控件 -->
	<script type="text/tmpl" id="mbui_cell_desc">
		<div class="mbui_cell mbui_cell_desc">
	        <div class="mbui_cell_hd"><label class="mbui_label">说明控件</label></div>
	        <div class="mbui_cell_layer"><div class="mbui_cell_layer_del"></div></div>
	    </div> 、
	</script>
   	<!-- 单行输入框 -->
   	<script type="text/tmpl" id="mbui_cell_xinput">
   		<div class="mbui_cell mbui_cell_text">
	        <div class="mbui_cell_hd"><label class="mbui_label">单行输入框</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{单行输入框}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	 <!-- 多行文本框-->
   	<script type="text/tmpl" id="mbui_cell_xtextarea">
   		<div class="mbui_cell mbui_cell_textarea">
	    	<div class="mbui_cell_hd"><label class="mbui_label">多行文本框</label></div>
	        <div class="mbui_cell_bd">
	            <textarea class="mbui_textarea" placeholder="{多行文本框}"></textarea>
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 下拉菜单 -->
   	<script type="text/tmpl" id="mbui_cell_xselect">
   		<div class="mbui_cell mbui_cell_select">
	    	<div class="mbui_cell_hd"><label class="mbui_label">下拉菜单</label></div>
	        <div class="mbui_cell_bd"></div>
	        <div class="mbui_cell_ft">请选择
	            <span class="mbui_select"></span>
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 单选框 -->
   	<script type="text/tmpl" id="mbui_cell_xradio">
   		<div class="mbui_cell mbui_cell_radio">
	    	<div class="mbui_radio_list">
	    		<div class="mbui_cell_hd">
	    			<label class="mbui_label">单选框</label>
		        </div>
		        <div class="mbui_cell_bd">
		            <i class="mbui_icon_radiochecked"></i>
		        </div>
		    </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 复选框 -->
   	<script type="text/tmpl" id="mbui_cell_xcheckbox">
   		<div class="mbui_cell mbui_cell_checkbox">
	    	<div class="mbui_check_list">
	    		<div class="mbui_cell_hd">
	    			<label class="mbui_label">复选框</label>
		        </div>
		        <div class="mbui_cell_bd">
		            <i class="mbui_icon_checked"></i>
		        </div>
	    	</div>
	    	<div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 计算控件 -->
   	<script type="text/tmpl" id="mbui_cell_xcalculate">
   		<div class="mbui_cell mbui_cell_calc">
	        <div class="mbui_cell_hd"><label class="mbui_label">计算控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_calc" type="text" placeholder="3.000">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	 <!-- 选择控件 -->
    <script type="text/tmpl" id="mbui_cell_xfetch">
   		<div class="mbui_cell mbui_cell_data">
	        <div class="mbui_cell_hd"><label class="mbui_label">选择控件</label></div>
	        <div class="mbui_cell_bd">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 列表控件 -->
   	<script type="text/tmpl" id="mbui_cell_xlist">
   		<div class="mbui_cell mbui_cell_listview">
	        <div class="mbui_cell_hd"><label class="mbui_label">列表控件</label></div>
	        <div class="mbui_cell_bd">
	            <img src="images/listview.png" class="mbui_icon_list" />
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 宏标记 -->
   	<script type="text/tmpl" id="mbui_cell_xmacrotag">
   		<div class="mbui_cell mbui_cell_text">
	        <div class="mbui_cell_hd"><label class="mbui_label">宏标记</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{宏标记}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 宏控件文本 -->
   	<script type="text/tmpl" id="mbui_cell_xmacro">
   		<div class="mbui_cell mbui_cell_xmacro">
	    	<div class="mbui_cell_hd"><label class="mbui_label">宏控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" value="" type="text" placeholder="{宏控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	 <!-- sql控件 -->
   	<script type="text/tmpl" id="mbui_cell_xsql">
   		<div class="mbui_cell mbui_cell_xmacro">
	    	<div class="mbui_cell_hd"><label class="mbui_label">SQL控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" value="" type="text" placeholder="{SQL控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	
   	<!-- 文号控件-->
   	<script type="text/tmpl" id="mbui_cell_xdocnum">
   		<div class="mbui_cell mbui_cell_text">
	        <div class="mbui_cell_hd"><label class="mbui_label">文号控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{文号控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
  
  <!-- 数据选择控件 -->
    <script type="text/tmpl" id="mbui_cell_xdatasel">
   		<div class="mbui_cell mbui_cell_data">
	        <div class="mbui_cell_hd"><label class="mbui_label">数据选择控件</label></div>
	        <div class="mbui_cell_bd">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 流程数据选择控件 -->
    <script type="text/tmpl" id="mbui_cell_xflowdatasel">
   		<div class="mbui_cell mbui_cell_data">
	        <div class="mbui_cell_hd"><label class="mbui_label">流程数据选择控件</label></div>
	        <div class="mbui_cell_bd">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   <!-- 图片上传控件 -->
    <script type="text/tmpl" id="mbui_cell_ximg">
   		<div class="mbui_cell mbui_cell_imgupload">
	        <div class="mbui_cell_hd"><label class="mbui_label">图片上传控件</label></div>
	        <div class="mbui_cell_bd">
	            <button type="button" class="mbui_btn mbui_btn_mini mbui_btn_primary">上传图片</button>
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 附件上传控件 -->
   	<script type="text/tmpl" id="mbui_cell_xupload">
   		<div class="mbui_cell mbui_cell_fileupload">
	        <div class="mbui_cell_hd"><label class="mbui_label">附件上传控件</label></div>
	        <div class="mbui_cell_bd">
	            <button type="button" class="mbui_btn mbui_btn_mini mbui_btn_primary">上传附件</button><button type="button" class="mbui_btn mbui_btn_mini mbui_btn_primary">上传图片</button>
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 会签意见 -->
   	<script type="text/tmpl" id="mbui_cell_xfeedback">
   		<div class="mbui_cell mbui_cell_xfeedback">
	    	<div class="mbui_cell_hd"><label class="mbui_label">会签控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" value="" type="text" placeholder="{会签控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 手写移动签章 -->
   	<script type="text/tmpl" id="mbui_cell_xseal">
   		<div class="mbui_cell mbui_cell_xseal">
	        <div class="mbui_cell_hd"><label class="mbui_label">手写移动签章</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{签章控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 图片签章 -->
   	<script type="text/tmpl" id="mbui_cell_xmobileseal">
   		<div class="mbui_cell mbui_cell_xseal">
	        <div class="mbui_cell_hd"><label class="mbui_label">图片签章</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{图片签章控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- H5手写签批 -->
   	<script type="text/tmpl" id="mbui_cell_xh5hw">
   		<div class="mbui_cell mbui_cell_xseal">
	        <div class="mbui_cell_hd"><label class="mbui_label">H5手写签批</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{H5手写签批控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 移动签章 -->
   	<script type="text/tmpl" id="mbui_cell_xmobilehandseal">
   		<div class="mbui_cell mbui_cell_mobilesign">
	        <div class="mbui_cell_hd"><label class="mbui_label">移动签章</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{移动签章控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 语音控件 -->
   	<script type="text/tmpl" id="mbui_cell_xvoice">
   		<div class="mbui_cell mbui_cell_mobilesign">
	        <div class="mbui_cell_hd"><label class="mbui_label">语音控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_input" type="text" placeholder="{语音控件}">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 自动编码控件 -->
   	<script type="text/tmpl" id="mbui_cell_xautonumber">
   		<div class="mbui_cell mbui_cell_qrcode">
	    	<div class="mbui_cell_hd"><label class="mbui_label">自动编码控件</label></div>
	    	<div class="mbui_cell_bd"><img src="images/qr_code.png" /></div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 区域联动控件 -->
   	<script type="text/tmpl" id="mbui_cell_xaddress">
   		<div class="mbui_cell mbui_cell_qrcode">
	    	<div class="mbui_cell_hd"><label class="mbui_label">区域联动控件</label></div>
	    	<div class="mbui_cell_bd"><img src="images/qr_code.png" /></div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 定位控件 -->
   	<script type="text/tmpl" id="mbui_cell_xposition">
   		<div class="mbui_cell mbui_cell_qrcode">
	    	<div class="mbui_cell_hd"><label class="mbui_label">定位控件</label></div>
	    	<div class="mbui_cell_bd"><img src="images/qr_code.png" /></div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 二维码控件 -->
   	<script type="text/tmpl" id="mbui_cell_xqrcode">
   		<div class="mbui_cell mbui_cell_qrcode">
	    	<div class="mbui_cell_hd"><label class="mbui_label">二维码控件</label></div>
	    	<div class="mbui_cell_bd"><img src="images/qr_code.png" /></div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 条形码控件 -->
   	<script type="text/tmpl" id="mbui_cell_xbarcode">
   		<div class="mbui_cell mbui_cell_qrcode">
	    	<div class="mbui_cell_hd"><label class="mbui_label">条形码控件</label></div>
	    	<div class="mbui_cell_bd"><img src="images/qr_code.png" /></div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	
   	
   	
   	
   	<!-- 宏控件下拉 -->
   	<script type="text/tmpl" id="mbui_cell_macro_select">
   		<div class="mbui_cell mbui_cell_macro">
	    	<div class="mbui_cell_hd"><label class="mbui_label">宏控件下拉</label></div>
	        <div class="mbui_cell_bd">
	        </div>
	        <div class="mbui_cell_ft">请选择
	            <span class="mbui_macro_select"></span>
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	<!-- 日历控件 -->
   	<script type="text/tmpl" id="mbui_cell_xfetch">
   		<div class="mbui_cell mbui_cell_calendar">
	    	<div class="mbui_cell_hd"><label class="mbui_label">日历控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_calendar" value="" type="text" placeholder="2015-09-16">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	<!-- 部门人员控件 -->
   	<script type="text/tmpl" id="mbui_cell_orgselect">
   		<div class="mbui_cell mbui_cell_orgselect">
	        <div class="mbui_cell_hd"><label class="mbui_label">部门人员控件</label></div>
	        <div class="mbui_cell_bd">
	            <input class="mbui_orgselect" type="text" placeholder="李佳，王德">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
    
   
    <!-- 外部数据选择控件 -->
    <script type="text/tmpl" id="mbui_cell_data_ext">
   		<div class="mbui_cell mbui_cell_data_ext">
	        <div class="mbui_cell_hd"><label class="mbui_label">外部数据选择控件</label></div>
	        <div class="mbui_cell_bd">
	        </div>
	        <div class="mbui_cell_layer"></div>
	    </div>
   	</script>
   	
   	
   
   	
   	
   	<!-- form templates end -->

   	<script type="text/javascript">
   		//展开收起组容器
	    function toggleExtension(el){
	    	//拿到容器
	    	var container = $(el).parents('.mbui_cells_group');
	    	//拿到地址
	    	var src = el.src;

	    	if(/xiala/.test(src)){
				container.removeClass("fold");
	    		el.src = "../img/shouqi.png";
	    	} else {
	    		container.addClass("fold");
	    		el.src = "../img/xiala.png";
	    	}
	    	
	    }
   	</script>

</body>
</html>